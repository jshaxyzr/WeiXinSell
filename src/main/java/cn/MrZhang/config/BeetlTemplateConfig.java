package cn.MrZhang.config;

import org.beetl.core.GroupTemplate;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.beetl.ext.spring.BeetlSpringViewResolver;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 
 * Title:BeetlTemplateConfig
 * @Description: TODO beetl 模板配置
 * @author MrZhang
 * @date 2017年9月21日 下午2:42:58 
 * @version V1.0
 */
@Component
public class BeetlTemplateConfig {

    @Value("${beetl.templatesPath}")
    String templatesPath;// 模板根目录

    @Value("${beetl.suffix}")
    String suffix;// 模板后缀

    @Bean(initMethod = "init", name = "beetlConfig")
    @ConditionalOnMissingBean(name = { "beetlConfig" })
    public BeetlGroupUtilConfiguration getBeetlGroupUtilConfiguration() {
        BeetlGroupUtilConfiguration beetlGroupUtilConfiguration = new BeetlGroupUtilConfiguration();
        try {
            ClasspathResourceLoader cploder = new ClasspathResourceLoader(BeetlTemplateConfig.class.getClassLoader(),
                    templatesPath);
            beetlGroupUtilConfiguration.setResourceLoader(cploder);
            return beetlGroupUtilConfiguration;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Bean(name = "beetlViewResolver")
    @ConditionalOnMissingBean(name = { "beetlViewResolver" })
    public BeetlSpringViewResolver getBeetlSpringViewResolver(
            @Qualifier("beetlConfig") BeetlGroupUtilConfiguration beetlGroupUtilConfiguration) {
        BeetlSpringViewResolver beetlSpringViewResolver = new BeetlSpringViewResolver();
        beetlSpringViewResolver.setContentType("text/html;charset=UTF-8");
        // beetlSpringViewResolver.setViewNames("*." + suffix);
        beetlSpringViewResolver.setSuffix("." + suffix);
        beetlSpringViewResolver.setOrder(0);
        beetlSpringViewResolver.setConfig(beetlGroupUtilConfiguration);
        return beetlSpringViewResolver;
    }

    @Bean(name = "groupTemplate")
    public GroupTemplate getGroupTemplate(@Qualifier("beetlConfig") BeetlGroupUtilConfiguration beetlGroupUtilConfiguration) {
        return beetlGroupUtilConfiguration.getGroupTemplate();
    }

}
