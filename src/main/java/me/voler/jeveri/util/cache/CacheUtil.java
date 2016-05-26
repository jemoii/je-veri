package me.voler.jeveri.util.cache;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.stereotype.Service;

@Service
public class CacheUtil {
	@Autowired
	@Qualifier("simpleCacheManager")
	private SimpleCacheManager cacheManager;

	private static CustomRedisCache cache;
	private static final Logger LOGGER = LoggerFactory.getLogger(CacheUtil.class);

	@PostConstruct
	public void init() {
		CacheUtil.cache = (CustomRedisCache) cacheManager.getCache("default");
		LOGGER.info("Init cache {}, from simpleCacheManager", cache.getName());
	}

	public static Object get(String key) {
		ValueWrapper value = cache.get(key);
		if (value != null) {
			return value.get();
		}
		return null;
	}

	public static void put(String key, Object value) {
		cache.put(key, value);
	}

	public static void put(String key, Object value, long expireTime) {
		cache.put(key, value, expireTime);
	}

	public static void delete(String key) {
		Object obj = cache.get(key);
		if (obj != null) {
			cache.evict(key);
		}
	}

}
