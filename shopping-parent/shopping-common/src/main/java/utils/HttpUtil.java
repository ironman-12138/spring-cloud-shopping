package utils;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static java.util.Collections.sort;

@Slf4j
@Component
public class HttpUtil {

    /**
     * restTemplate配置,可以访问https
     */
    private static final RestTemplate REST_TEMPLATE = new RestTemplate(generateHttpsRequestFactory());

    private static HttpComponentsClientHttpRequestFactory generateHttpsRequestFactory() {
        try {

            TrustStrategy acceptingTrustStrategy = (x509Certificates, authType) -> true;
            SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
            SSLConnectionSocketFactory connectionSocketFactory =
                    new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());

            HttpClientBuilder httpClientBuilder = HttpClients.custom();
            httpClientBuilder.setSSLSocketFactory(connectionSocketFactory);
            CloseableHttpClient httpClient = httpClientBuilder.build();
            HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
            factory.setHttpClient(httpClient);
            factory.setConnectTimeout(3 * 1_000);
            factory.setReadTimeout(3 * 1_000);
            return factory;
        } catch (Exception e) {
            log.error("创建HttpsRestTemplate失败", e);
            throw new RuntimeException("创建HttpsRestTemplate失败", e);
        }
    }

    /**
     * 获取request
     *
     * @return
     */
    public static HttpServletRequest getRequest() {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        return Optional.ofNullable(sra).map(ServletRequestAttributes::getRequest).orElse(null);
    }

    /**
     * 获取浏览器
     *
     * @param request
     * @return
     */
    public static String getBrowser(HttpServletRequest request) {
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        Browser browser = userAgent.getBrowser();
        return browser.getName();
    }


    /**
     * 发送普通post请求
     *
     * @param url
     * @param params
     * @return
     */
    public static String post(String url, Map<String, Object> params) {
        return postHttpClientJsonHeader(url, params, null);
    }

    /**
     * 发送普通get请求
     *
     * @param url
     * @param params
     * @return
     */
    public static String get(String url, Map<String, Object> params) {
        return getHttplient(url, params, null);
    }

    /**
     * 发送application/json请求
     *
     * @param url
     * @param params
     * @return
     */
    public static String postJson(String url, Map<String, Object> params) {
        var header = new HashMap<String, String>(16);
        header.put("Content-Type", "application/json");
        return postHttpClientJsonHeader(url, params, header);
    }

    /**
     * 发送post请求,自定义header
     *
     * @param url
     * @param params
     * @param header
     * @return
     */
    public static String postHttpClientJsonHeader(String url, Map<String, Object> params, Map<String, String> header) {
        HttpHeaders headers = new HttpHeaders();
        if (!CollectionUtils.isEmpty(header)) {
            // 填充header
            for (Map.Entry<String, String> entry : header.entrySet()) {
                headers.add(entry.getKey(), entry.getValue());
            }
        }
        HttpEntity<Map<String, Object>> entry = new HttpEntity<>(params, headers);
        try {
            log.info("流水号:{},调用外部接口,url:{},参数:{},header:{}", TLocalHelper.getSeq(), url, new ObjectMapper().writeValueAsString(params), header);
        } catch (JsonProcessingException e) {
            log.info("参数列表转换字符串错误：{}", e.getMessage());
        }
        ResponseEntity<String> result = REST_TEMPLATE.postForEntity(url, entry, String.class);
        log.info("流水号:{},返回结果:{}", TLocalHelper.getSeq(), result.getBody());
        return result.getBody();
    }

    public static String postHttplientStringHeader(String url, String info, Map<String, String> header) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8));
        if (!CollectionUtils.isEmpty(header)) {
            // 填充header
            for (Map.Entry<String, String> entry : header.entrySet()) {
                headers.add(entry.getKey(), entry.getValue());
            }
        }
        HttpEntity<String> entry = new HttpEntity<>(info, headers);
        log.info("流水号:{},调用外部接口,url:{},参数:{},header:{}", TLocalHelper.getSeq(), url, info, header);
        ResponseEntity<String> result = REST_TEMPLATE.postForEntity(url, entry, String.class);
        log.info("流水号:{},返回结果:{}", TLocalHelper.getSeq(), result.getBody());
        return result.getBody();
    }

    public static String postHttplientMultiValueHeader(String url, Map<String, String> params) {
        return postHttplientMultiValueHeader(null, url, params, null);
    }

    public static String postHttplientMultiValueHeader(String urlName, String url, Map<String, String> params, Map<String, String> header) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        if (!CollectionUtils.isEmpty(header)) {
            // 填充header
            for (Map.Entry<String, String> entry : header.entrySet()) {
                headers.add(entry.getKey(), entry.getValue());
            }
        }
        HttpEntity<MultiValueMap<String, String>> entry = new HttpEntity<MultiValueMap<String, String>>(popHeaders(params), headers);
        log.info("流水号:{},调用外部接口,接口名称:{},url:{},参数:{},header:{}", TLocalHelper.getSeq(), urlName, url, getJson(params), header);
        ResponseEntity<String> result = REST_TEMPLATE.postForEntity(url, entry, String.class);
        log.info("流水号:{},返回结果:{}", TLocalHelper.getSeq(), result.getBody());
        return result.getBody();
    }


    public static String postHttplientMultiValueHeaderEdu(String urlName, String url, Map<String, String> params, Map<String, String> header) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        if (!CollectionUtils.isEmpty(header)) {
            // 填充header
            for (Map.Entry<String, String> entry : header.entrySet()) {
                headers.add(entry.getKey(), entry.getValue());
            }
        }
        HttpEntity<MultiValueMap<String, String>> entry = new HttpEntity<MultiValueMap<String, String>>(popHeaders(params), headers);
        log.info("学历流水号:{},调用外部接口,接口名称:{},url:{},参数:{},header:{}", TLocalHelper.getSeq(), urlName, url, getJson(params), header);
        ResponseEntity<String> result = REST_TEMPLATE.postForEntity(url, entry, String.class);
        log.info("学历流水号:{},返回结果:{}", TLocalHelper.getSeq(), result.getBody());
        return result.getBody();
    }

    protected static MultiValueMap<String, String> popHeaders(Map<String, String> params) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            map.add(entry.getKey(), entry.getValue());
        }
        return map;
    }


    /**
     * 发送get请求
     *
     * @param url
     * @param params
     * @param header
     * @return
     */
    public static String getHttplient(String url, Map<String, Object> params, Map<String, String> header) {
        HttpHeaders headers = new HttpHeaders();
        if (!CollectionUtils.isEmpty(header)) {
            // 填充header
            for (Map.Entry<String, String> entry : header.entrySet()) {
                headers.add(entry.getKey(), entry.getValue());
            }
        }
        //HttpEntity<Map<String, Object>> entry = new HttpEntity<>(params, headers);
        log.info("流水号:{},调用外部接口,url:{},参数:{},header:{}", TLocalHelper.getSeq(), url, getJson(params), header);
        ResponseEntity<String> result = REST_TEMPLATE.getForEntity(url, String.class, params);
        log.info("流水号:{},返回结果:{}", TLocalHelper.getSeq(), result.getBody());
        return result.getBody();
    }


    public static String postUri(String uri) {
        String res = "";
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(uri);
            HttpResponse result = client.execute(post);
            /*请求发送成功，并得到响应**/
            if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                res = EntityUtils.toString(result.getEntity(), "utf-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    // 拼接Post请求参数
    public static String paramsToString(Map<String, String> params) {
        if (params != null && params.size() > 0) {
            String paramsEncoding = "UTF-8";
            StringBuilder encodedParams = new StringBuilder();
            try {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    encodedParams.append(URLEncoder.encode(entry.getKey(), paramsEncoding));
                    encodedParams.append('=');
                    encodedParams.append(URLEncoder.encode(entry.getValue(), paramsEncoding));
                    encodedParams.append('&');
                }
                return encodedParams.toString();
            } catch (UnsupportedEncodingException uee) {
                throw new RuntimeException("Encoding not supported: " + paramsEncoding, uee);
            }
        }
        return "";
    }

    /**
     * 把参数按照ASCII码递增排序，如果遇到相同字符则按照第二个字符的键值ASCII码递增排序
     * 将排序后的参数与其对应值，组合成“参数=参数值”的格式，并且把这些参数用&字符连接起来
     *
     * @param sortedParams 待签名数据和签名字段字符串
     * @return 待签名字符串
     */
    public static String getSignContent(Map<String, Object> sortedParams) {
        List<String> keys = new ArrayList();
        for (Map.Entry<String, Object> entry : sortedParams.entrySet()) {
            keys.add(entry.getKey());
        }
        sort(keys);
        StringBuilder content = new StringBuilder();
        int index = 0;
        for (String key : keys) {
            Object value = sortedParams.get(key);
            if (StringUtils.isNotEmpty(key) && Objects.nonNull(value)) {
                content.append(index == 0 ? "" : "&").append(key).append("=").append(value);
                index++;
            }
        }
        return content.toString();
    }

    private static String getJson(Object o) {
        try {
            return new ObjectMapper().writeValueAsString(o);
        } catch (Exception e) {
            return "JSON异常";
        }
    }

}
