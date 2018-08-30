package com.maqway.wxht.service.serviceImpl;


import com.maqway.wxht.controller.cache.JedisUtil;
import com.maqway.wxht.service.CacheService;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CacheServiceImpl implements CacheService {
	@Autowired
	private JedisUtil.Keys jedisKeys;


	public void removeFromCache(String keyPrefix) {
		Set<String> keySet = jedisKeys.keys(keyPrefix + "*");
		for (String key : keySet) {
			jedisKeys.del(key);
		}
	}

}
