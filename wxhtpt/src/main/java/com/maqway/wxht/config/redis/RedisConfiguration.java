package com.maqway.wxht.config.redis;

import com.maqway.wxht.controller.cache.JedisPoolWriper;
import com.maqway.wxht.controller.cache.JedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Ma.li.ran
 * 2017/11/17 0017 15:51
 * redis配置
 */
@Configuration
public class RedisConfiguration {

  @Value("${redis.hostname}")
  private String hostname;
  @Value("${redis.port}")
  private int port;
  @Value("${redis.pool.maxActive}")
  private int maxTotal;
  @Value("${redis.pool.maxIdle}")
  private int maxIdle;
  @Value("${redis.pool.maxWait}")
  private long maxWaitMillis;
  @Value("${redis.pool.testOnBorrow}")
  private boolean testOnBorrow;

  @Autowired
  private JedisPoolConfig jedisPoolConfig;
  @Autowired
  private JedisPoolWriper jedisPoolWriper;
  @Autowired
  private JedisUtil jedisUtil;



  /**
   * 创建redis连接池的设置
   */
  @Bean(name = "jedisPoolConfig")
  public JedisPoolConfig createJedisConfig() {
    JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
    //控制一个pool可分配多少个jedis实例
    jedisPoolConfig.setMaxTotal(maxTotal);
    /*
      连接池中最多可空闲maxIdle个连接，这里取值为20，表示即使没有数据库连接依然可以空闲20个随时等待待命
     */
    jedisPoolConfig.setMaxIdle(maxIdle);
    //最大等待时间，当没有可用链接时，连接池等待的最大归还时间，超出抛出异常
    jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
    //在获取连接时检查有效性
    jedisPoolConfig.setTestOnBorrow(testOnBorrow);
    return jedisPoolConfig;
  }

  /**
   * 创建redis连接池
   */
  @Bean(name = "jedisPool")
  public JedisPoolWriper createJedisPoolWriper() {
    jedisPoolWriper = new JedisPoolWriper(jedisPoolConfig, hostname, port);
    return jedisPoolWriper;
  }

  /**
   * keys
   * @return
   */
  @Bean(name = "jedisKeys")
  public JedisUtil.Keys createJedisKeys() {
    JedisUtil.Keys jedisKeys = jedisUtil.new Keys();
    return jedisKeys;
  }

  /**
   * Strings
   * @return
   */
  @Bean(name = "jedisStrings")
  public JedisUtil.Strings createJedisStrings() {
    JedisUtil.Strings jedisStrings= jedisUtil.new Strings();
    return jedisStrings;
  }

  /**
   * Hash
   * @return
   */
  @Bean(name = "jedisHash")
  public JedisUtil.Hash createJedisHash() {
    JedisUtil.Hash jedisHash= jedisUtil.new Hash();
    return jedisHash;
  }

  /**
   * Lists
   * @return
   */
  @Bean(name = "jedisLists")
  public JedisUtil.Lists createJedisLists() {
    JedisUtil.Lists jedisLists= jedisUtil.new Lists();
    return jedisLists;
  }

  /**
   * Sets
   * @return
   */
  @Bean(name = "jedisSets")
  public JedisUtil.Sets createJedisSets() {
    JedisUtil.Sets jedisSets= jedisUtil.new Sets();
    return jedisSets;
  }

}
