package com.maqway.wxht.config.controller;

import com.google.code.kaptcha.servlet.KaptchaServlet;
import com.maqway.wxht.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Ma.li.ran
 * 2017/11/17 0017 16:18
 */
@Configuration
//等价于<mvc:annotation-driven/>
@EnableWebMvc
public class MVCConfiguration extends WebMvcConfigurerAdapter implements ApplicationContextAware {

  //Spring 容器
  private ApplicationContext applicationContext;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
  }
  /**
   * 配置静态资源
   */
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
   registry.addResourceHandler("/upload/**")
       .addResourceLocations("file:/mlr/project/wxht/images/upload/");
    registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/resources/");

  }

  /**
   * 定义默认的请求处理器
   */
  @Override
  public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
    configurer.enable();
  }



//  /**
//   * 创建视图解析器
//   */
//  @Bean(name = "viewResolver")
//  public ViewResolver createViewResolver() {
//    InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
//    //设置spring容器
//    internalResourceViewResolver.setApplicationContext(applicationContext);
//    //取消缓存
//    internalResourceViewResolver.setCache(false);
//    //设置解析前缀
//    internalResourceViewResolver.setPrefix("/WEB-INF/html/");
//    //设置解析后缀
//    internalResourceViewResolver.setSuffix(".html");
//    return internalResourceViewResolver;
//  }

  /**
   * 文件上传解析器
   */
  @Bean(name = "multipartResolver")
  public CommonsMultipartResolver createMultipartResolver() {
    CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
    commonsMultipartResolver.setDefaultEncoding("utf-8");
    //1024*1024*20 = 20M
    commonsMultipartResolver.setMaxUploadSize(20971520);
    commonsMultipartResolver.setMaxInMemorySize(20971520);
    return commonsMultipartResolver;
  }

  @Value("${kaptcha.border}")
  private String border;
  @Value("${kaptcha.textproducer.font.color}")
  private String fColor;
  @Value("${kaptcha.image.width}")
  private String width;
  @Value("${kaptcha.textproducer.char.string}")
  private String cString;
  @Value("${kaptcha.image.height}")
  private String height;
  @Value("${kaptcha.textproducer.font.size}")
  private String fSize;
  @Value("${kaptcha.noise.color}")
  private String nColor;
  @Value("${kaptcha.textproducer.char.length}")
  private String cLength;
  @Value("${kaptcha.textproducer.font.names}")
  private String fNames;

  /**
   * Kapcha验证码servlet
   */
  @Bean(name="servletRegistrationBean")
  public ServletRegistrationBean servletRegistrationBean() {

    ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(
        new KaptchaServlet(),"/Kaptcha");
    servletRegistrationBean.addInitParameter("kaptcha.border", border);
    servletRegistrationBean.addInitParameter("kaptcha.textproducer.font.color", fColor);
    servletRegistrationBean.addInitParameter("kaptcha.textproducer.char.string", cString);
    servletRegistrationBean.addInitParameter("kaptcha.image.width", width);
    servletRegistrationBean.addInitParameter("kaptcha.image.height", height);
    servletRegistrationBean.addInitParameter("kaptcha.textproducer.font.size", fSize);
    servletRegistrationBean.addInitParameter("kaptcha.noise.color", nColor);
    servletRegistrationBean.addInitParameter("kaptcha.textproducer.char.length", cLength);
    servletRegistrationBean.addInitParameter("kaptcha.textproducer.font.names", fNames);
    return servletRegistrationBean;

  }

  /**
   * 配置拦截器
   * @param registry
   */
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    String interceptPath = "/wxht/wxmanage/**";
    //注册拦截器
    InterceptorRegistration loginIR = registry.addInterceptor(new LoginInterceptor());
    //配置拦截路径
    loginIR.addPathPatterns(interceptPath);
    //配置不拦截路径
//    loginIR.excludePathPatterns("/wxht/frontend/**");
  }
}
