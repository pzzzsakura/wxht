package com.maqway.wxht.config.service;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

/**
 * Ma.li.ran
 * 2017/11/17 0017 15:46
 */
@Configuration
//开启事务支持后，在service上添加@service即可
@EnableTransactionManagement
public class TransactionManagementConfiguration implements TransactionManagementConfigurer {

  //注入DataSourceConfiguration 里的dataSource
  @Autowired
  private DataSource dataSource;

  @Override
  public PlatformTransactionManager annotationDrivenTransactionManager() {
    return new DataSourceTransactionManager(dataSource);
  }
}
