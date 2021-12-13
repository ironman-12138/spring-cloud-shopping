package utils;

import lombok.var;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 线程缓存工具
 */
public class TLocalHelper {

    private static final ThreadLocal<Map<String, String>> THREAD_LOCAL = new ThreadLocal<Map<String, String>>();

    public static final String LOCAL_SEQ = "localSeq";

    public static void closeLocal() {
        THREAD_LOCAL.remove();
    }

    /**
     * 私有静态内部类
     */
    private static class LastTime {
        private static long time;
        private static int i;
    }

    /**
     * 创建流水
     *
     * @return
     */
    public static String createSeq() {
        var df = new SimpleDateFormat("yyyyMMddHHmmss");
        var now = new Date();
        String sequence;
        long nowSec = now.getTime() / 1000;
        synchronized (LastTime.class) {
            if (LastTime.time >= nowSec) {
                LastTime.i++;
            } else {
                LastTime.i = 0;
                LastTime.time = nowSec;
            }
            sequence = "25" + df.format(new Date(LastTime.time * 1000)) + String.format("%04d", LastTime.i);
        }
        var tlMap = new HashMap<String, String>();
        tlMap.put(LOCAL_SEQ, sequence);
        THREAD_LOCAL.set(tlMap);
        return sequence;
    }


    /**
     * 设置异步线程流水
     *
     * @return
     */
    public static String setTaskSeq(String seq) {
        var tlMap = new HashMap<String, String>();
        tlMap.put(LOCAL_SEQ, seq);
        THREAD_LOCAL.set(tlMap);
        return seq;
    }


    /**
     * 获取流水
     */
    public static String getSeq() {
        var map = THREAD_LOCAL.get();
        return map != null ? map.get(LOCAL_SEQ) : "";
    }

    public static Map getAll() {
        return THREAD_LOCAL.get();
    }

    /**
     * 获取数据
     *
     * @return
     */
    public static String getData() {
        var tlMap = THREAD_LOCAL.get();
        return tlMap.get("requestData") != null ? tlMap.get("requestData") : "";
    }

    /**
     * 设置数据
     *
     * @param requestData
     */
    public static void setData(String requestData) {
        var tlMap = THREAD_LOCAL.get();
        tlMap.put("requestData", requestData);
    }

    /**
     * 获取外部流水�?
     *
     * @return
     */
    public static String getSerialSeq() {
        var tlMap = THREAD_LOCAL.get();
        return tlMap.get("serialSeq") != null ? tlMap.get("serialSeq") : "";
    }

    /**
     * 设置外部流水
     *
     * @param serialSeq
     */
    public static void setSerialSeq(String serialSeq) {
        var tlMap = THREAD_LOCAL.get();
        tlMap.put("serialSeq", serialSeq);
    }


    /**
     * 获取应用版本�?
     *
     * @return
     */
    public static String getVerChl() {
        var tlMap = THREAD_LOCAL.get();
        return tlMap.get("verChl") != null ? tlMap.get("verChl") : "";
    }

    /**
     * 设置应用版本�?
     *
     * @param verChl
     */
    public static void setVerChl(String verChl) {
        var tlMap = THREAD_LOCAL.get();
        tlMap.put("verChl", verChl);
    }


    /**
     * 获取应用�?
     *
     * @return
     */
    public static String getSendChl() {
        var tlMap = THREAD_LOCAL.get();
        return tlMap.get("sendChl") != null ? tlMap.get("sendChl") : "";
    }

    /**
     * 设置应用�?
     *
     * @param sendChl
     */
    public static void setSendChl(String sendChl) {
        var tlMap = THREAD_LOCAL.get();
        tlMap.put("sendChl", sendChl);
    }


    /**
     * 获取设备�?
     *
     * @return
     */
    public static String getSendDev() {
        var tlMap = THREAD_LOCAL.get();
        return tlMap.get("sendDev") != null ? tlMap.get("sendDev") : "";
    }

    /**
     * 设置设备�?
     *
     * @param sendDev
     */
    public static void setSendDev(String sendDev) {
        var tlMap = THREAD_LOCAL.get();
        tlMap.put("sendDev", sendDev);
    }


    /**
     * 获取用户验证�?
     *
     * @return
     */
    public static String getSendClient() {
        var tlMap = THREAD_LOCAL.get();
        return tlMap.get("sendClient") != null ? tlMap.get("sendClient") : "";
    }

    /**
     * 设置用户验证�?
     *
     * @param sendClient
     */
    public static void setSendClient(String sendClient) {
        var tlMap = THREAD_LOCAL.get();
        tlMap.put("sendClient", sendClient);
    }


    /**
     * 获取用户Id
     *
     * @return
     */
    public static String getUserId() {
        var tlMap = THREAD_LOCAL.get();
        return tlMap.get("userId") != null ? tlMap.get("userId") : "";
    }

    /**
     * 设置用户Id
     *
     * @param userId
     */
    public static void setUserId(String userId) {
        var tlMap = THREAD_LOCAL.get();
        tlMap.put("userId", userId);
    }


    /**
     * 获取token
     *
     * @return
     */
    public static String getToken() {
        var tlMap = THREAD_LOCAL.get();
        return tlMap.get("accessToken") != null ? tlMap.get("accessToken") : "";
    }

    /**
     * 设置token
     *
     * @param
     */
    public static void setToken(String accessToken) {
        var tlMap = THREAD_LOCAL.get();
        tlMap.put("accessToken", accessToken);
    }


    /**
     * 获取接口地址
     *
     * @return
     */
    public static String getReqUrl() {
        var tlMap = THREAD_LOCAL.get();
        return tlMap.get("reqUri") != null ? tlMap.get("reqUri") : "";
    }

    /**
     * 设置接口地址
     *
     * @param
     */
    public static void setReqUrl(String reqUri) {
        var tlMap = THREAD_LOCAL.get();
        tlMap.put("reqUri", reqUri);
    }


    /**
     * 终端类型
     *
     * @return
     */
    public static String getTerminal() {
        var tlMap = THREAD_LOCAL.get();
        return tlMap.get("terminal") != null ? tlMap.get("terminal") : "";
    }

    /**
     * 设置终端类型
     *
     * @param
     */
    public static void setTerminal(String terminal) {
        var tlMap = THREAD_LOCAL.get();
        tlMap.put("terminal", terminal);
    }


    /**
     * 获取错误返回码
     *
     * @return
     */
    public static String getMsgCode() {
        var tlMap = THREAD_LOCAL.get();
        return tlMap.get("msgCode") != null ? tlMap.get("msgCode") : "";
    }

    /**
     * 设置错误返回码
     *
     * @param
     */
    public static void setMsgCode(String msgCode) {
        var tlMap = THREAD_LOCAL.get();
        tlMap.put("msgCode", msgCode);
    }


    /**
     * 获取用户名
     *
     * @return
     */
    public static String getUserName() {
        var tlMap = THREAD_LOCAL.get();
        return tlMap.get("userName") != null ? tlMap.get("userName") : "";
    }

    /**
     * 设置用户名
     *
     * @param userName
     */
    public static void setUserName(String userName) {
        var tlMap = THREAD_LOCAL.get();
        tlMap.put("userName", userName);
    }


    /**
     * 获取app版本
     *
     * @return
     */
    public static String getVersion() {
        var tlMap = THREAD_LOCAL.get();
        return tlMap.get("version") != null ? tlMap.get("version") : "";
    }

    /**
     * 设置app版本
     *
     * @param version
     */
    public static void setVersion(String version) {
        var tlMap = THREAD_LOCAL.get();
        tlMap.put("version", version);
    }

    /**
     * 设置ip
     *
     * @param ip
     */
    public static void setIp(String ip) {
        var tlMap = THREAD_LOCAL.get();
        tlMap.put("ip", ip);
    }

    /**
     * 获取ip
     *
     * @return
     */
    public static String getIp() {
        var tlMap = THREAD_LOCAL.get();
        return tlMap.get("ip");
    }


    /**
     * 获取浏览器型号和版本
     *
     * @return
     */
    public static String getUseAgent() {
        var tlMap = THREAD_LOCAL.get();
        return tlMap.get("userAgent") != null ? tlMap.get("userAgent") : "";
    }

    /**
     * 设置浏览器型号和版本
     *
     * @param
     */
    public static void setUseAgent(String userAgent) {
        var tlMap = THREAD_LOCAL.get();
        tlMap.put("userAgent", userAgent);
    }


    /**
     * 获取客户号
     *
     * @return
     */
    public static String getClientCode() {
        var tlMap = THREAD_LOCAL.get();
        return tlMap.get("clientCode") != null ? tlMap.get("clientCode") : "";
    }

    /**
     * 设置客户号
     *
     * @param
     */
    public static void setClientCode(String clientCode) {
        var tlMap = THREAD_LOCAL.get();
        tlMap.put("clientCode", clientCode);
    }


    /**
     * 获取chlType(渠道类型：scan,market)
     *
     * @return
     */
    public static String getChlType() {
        var tlMap = THREAD_LOCAL.get();
        return tlMap.get("chlType") != null ? tlMap.get("chlType") : "";
    }

    /**
     * 设置chlType
     *
     * @param
     */
    public static void setChlType(String chlType) {
        var tlMap = THREAD_LOCAL.get();
        tlMap.put("chlType", chlType);
    }


    /**
     * 获取chlName（渠道名:market-yyb）
     *
     * @return
     */
    public static String getChlName() {
        var tlMap = THREAD_LOCAL.get();
        return tlMap.get("chlName") != null ? tlMap.get("chlName") : "";
    }

    /**
     * 设置chlName
     *
     * @param
     */
    public static void setChlName(String chlName) {
        var tlMap = THREAD_LOCAL.get();
        tlMap.put("chlName", chlName);
    }


    /**
     * 获取设备型号
     *
     * @return
     */
    public static String getDevice() {
        var tlMap = THREAD_LOCAL.get();
        return tlMap.get("device") != null ? tlMap.get("device") : "";
    }

    /**
     * 设置设备型号
     *
     * @param device
     */
    public static void setDevice(String device) {
        var tlMap = THREAD_LOCAL.get();
        tlMap.put("device", device);
    }


    /**
     * 获取实际用户id
     *
     * @return
     */
    public static String getRealUserId() {
        var tlMap = THREAD_LOCAL.get();
        return tlMap.get("realUserId") != null ? tlMap.get("realUserId") : "";
    }

    /**
     * 设置实际用户id
     *
     * @param realUserId
     */
    public static void setRealUserId(String realUserId) {
        var tlMap = THREAD_LOCAL.get();
        tlMap.put("realUserId", realUserId);
    }
}
