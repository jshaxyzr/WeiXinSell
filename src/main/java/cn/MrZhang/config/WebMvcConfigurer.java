package cn.MrZhang.config;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 
 * Title:WebMvcConfigurer
 * @Description: TODO 指定通过URL加载静态文件路径 要不然beetl 无法加载css js image 文件
 * @author MrZhang
 * @date 2017年9月22日 下午5:15:45 
 * @version V1.0
 */
@EnableWebMvc
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

    private final Logger logger = Logger.getLogger(WebMvcConfigurer.class);

    // 页面应用 要加上 /static/css/xxx.css 指定了 static 开头的路径 指向 classpath 下static 文件夹
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/static/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/");
        super.addResourceHandlers(registry);
    }

    // /** 时 所有静态文件都 指向了 classpath 下的 static 文件夹 页面配置 “/css/xxx.css”
    // @Override
    // public void addResourceHandlers(ResourceHandlerRegistry registry) {
    //
    // registry.addResourceHandler("/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/");
    // super.addResourceHandlers(registry);
    // }

}
