package cn.edu.nju.gyue.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class FilePathConfig extends WebMvcConfigurerAdapter {
    public static final String PATH = "/Users/gyue/Pictures/MyPicture/";
    public static final String AVATAR_PATH = "/Users/gyue/Pictures/MyPicture/";

    //供客户端使用的url前缀
    public static final String URL = "http://localhost:8080/picture/";
    public static final String AVATAR_URL = "http://localhost:8080/avatar/";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/picture/**").addResourceLocations("file:"+PATH);
        registry.addResourceHandler("/avatar/**").addResourceLocations("file:"+AVATAR_PATH);
        super.addResourceHandlers(registry);
    }
}
