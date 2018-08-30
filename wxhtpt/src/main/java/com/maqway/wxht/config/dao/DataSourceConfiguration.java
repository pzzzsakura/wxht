package com.maqway.wxht.config.dao;

import com.maqway.wxht.util.DESUtil;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.beans.PropertyVetoException;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Ma.li.ran
 * 2017/11/17 0017 11:39
 * 配置dataSource到ioc容器里面
 */
@Configuration
//配置mybatis mapper的扫描路径
@MapperScan("com.maqway.wxht.dao")
public class DataSourceConfiguration {

  @Value("${jdbc.driver}")
  private String jdbcDriver;
  @Value("${jdbc.url}")
  private String jdbcUrl;
  @Value("${jdbc.username}")
  private String jdbcUserName;
  @Value("${jdbc.password}")
  private String jdbcPassword;

  /**
   * 生成bean dataSource
   * @return
   * @throws PropertyVetoException
   */
  @Bean(name = "dataSource")
  public ComboPooledDataSource createDataSource() throws PropertyVetoException {

    //生成dataSource 实例
    ComboPooledDataSource dataSource = new ComboPooledDataSource();
    //驱动
    dataSource.setDriverClass(jdbcDriver);
    //url
    dataSource.setJdbcUrl(jdbcUrl);
    //用户名
    dataSource.setUser(DESUtil.getDecryptString(jdbcUserName));
    //密码
    dataSource.setPassword(DESUtil.getDecryptString(jdbcPassword));
    //配置c3p0连接池私有属性
    //连接池最大线程数
    dataSource.setMaxPoolSize(30);
    //连接池最小线程数
    dataSource.setMinPoolSize(10);
    //关闭连接后不自动commit
    dataSource.setAutoCommitOnClose(false);
    //连接超时时间
    dataSource.setCheckoutTimeout(10000);
    //连接失败重试次数
    dataSource.setAcquireRetryAttempts(2);
    return dataSource;
  }


}
