package cn.MrZhang.config;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import cn.MrZhang.interceptor.LoginInterceptor;

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

    @Value("${spring.profiles.active}")
    private String env;// 当前激活的配置文件

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

    @Bean
    LoginInterceptor localInterceptor() {
        return new LoginInterceptor();
    }

    // 添加拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 开发环境忽略拦截
        if ("dev".equals(env)) {
            // registry.addInterceptor(localInterceptor()).excludePathPatterns("/**");
            registry.addInterceptor(localInterceptor()).addPathPatterns("/**");
            // registry.addInterceptor(authenInterceptor()).addPathPatterns("/**");
            super.addInterceptors(registry);
        } else {
            registry.addInterceptor(localInterceptor()).addPathPatterns("/**").excludePathPatterns("/seller/login*",
                    "/seller/logout");
            // registry.addInterceptor(authenInterceptor()).addPathPatterns("/**");
            super.addInterceptors(registry);
        }
    }
}
