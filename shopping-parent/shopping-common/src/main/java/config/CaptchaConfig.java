package config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * 验证码配置类
 * 时间：2021年2月12日 14:43:37
 */

@Configuration
public class CaptchaConfig {

    @Bean
    public DefaultKaptcha defaultKaptcha(){
        DefaultKaptcha defaultKaptcha=new DefaultKaptcha();
        Properties properties=new Properties();
        //是否有边框
        properties.setProperty("kaptcha.border", "yes");
        //设置边框颜色
        properties.setProperty("kaptcha.border.color", "105,179,90");
        //验证码文本字体颜色设置（默认黑色）
        properties.setProperty("kaptcha.textproducer.font.color", "255,192,55");
        //验证码图片宽度（默认200）
        properties.setProperty("kaptcha.image.width", "100");
        //验证码图片高度（默认40）
        properties.setProperty("kaptcha.image.height", "40");
        properties.setProperty("kaptcha.session.key", "code");
        properties.setProperty("kaptcha.textproducer.font.size", "30");
        properties.setProperty("kaptcha.noise.color","21,113,171");
        properties.setProperty("kaptcha.background.clear.from","0,154,255");
        properties.setProperty("kaptcha.background.clear.to","0,202,255");
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        properties.setProperty("kaptcha.textproducer.font.names", "Arial");
        Config config=new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}

