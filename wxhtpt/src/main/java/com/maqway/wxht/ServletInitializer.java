package com.maqway.wxht;//package com.zanfen;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Created by Ma.li.ran on 2017/09/27 16:00
 * 外部tomcat打包类
 */
public class ServletInitializer extends SpringBootServletInitializer {
  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(WxhtApplication.class);
  }

}
