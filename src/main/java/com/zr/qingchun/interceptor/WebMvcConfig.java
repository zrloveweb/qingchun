package com.zr.qingchun.interceptor;

//import com.zr.qingchun.interceptor.MyInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName WebMvcConfig
 * @Decription 拦截static下面的静态资源
 * @Author Administrator
 * @Date 2018/10/15 0015 10:53
 * Version 1.0
 **/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

 /*   @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getSecurityInterceptor())
                .addPathPatterns("/**")//拦截所有路径文件
                .excludePathPatterns("/login.html","/login","/selectMenuByUserId","/alipay/**","/css/**","/images/**","/js/**")//排除拦截的路径
                .excludePathPatterns("/swagger-resources/**","/webjars/**", "/v2/**", "/swagger-ui.html/**");//排除拦截的路径
    }
*/
   /* @Bean
    public MyInterceptor getSecurityInterceptor() {
        return new MyInterceptor();
    }*/
}
