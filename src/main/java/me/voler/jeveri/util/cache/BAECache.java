package me.voler.jeveri.util.cache;

import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Repository;

import redis.clients.jedis.Jedis;

@Repository
public class BAECache {

	private static String host;
	private static int port;
	private static String password;

	static {
		Properties prop = new DeployUtil().getResources("cache.properties");
		host = prop.getProperty("me.cache.hostname");
		port = Integer.parseInt(prop.getProperty("me.cache.port"));
		password = prop.getProperty("me.cache.password");
	}

	private ThreadLocal<Jedis> threadLocal = new ThreadLocal<Jedis>();

	// 注意：Redis扩展服务不能使用连接池，例如JedisPool。
	private Jedis getJedis() {
		Jedis jedis = threadLocal.get();
		if (jedis == null) {
			jedis = new Jedis(host, port);
			jedis.connect();
			if (StringUtils.isNotEmpty(password)) {
				jedis.auth(password);
			}
			threadLocal.set(jedis);
		}
		return jedis;
	}

	private RedisSerializer<String> keySerializer = new StringRedisSerializer();
	/** DefaultSerializer & DefaultDeserializer */
	private RedisSerializer<Object> valueSerializer = new JdkSerializationRedisSerializer();

	public void put(String key, Object value) {
		put(key, value, 24 * 60 * 60);
	}

	public void put(String key, Object value, int expireTime) {
		Jedis jedis = getJedis();
		byte[] byteKey = keySerializer.serialize(key);
		byte[] byteValue = valueSerializer.serialize(value);
		jedis.setex(byteKey, expireTime, byteValue);
	}

	public Object get(String key) {
		Jedis jedis = getJedis();
		byte[] byteKey = keySerializer.serialize(key);
		byte[] byteValue = jedis.get(byteKey);
		return valueSerializer.deserialize(byteValue);
	}

	public void delete(String key) {
		Jedis jedis = getJedis();
		byte[] byteKey = keySerializer.serialize(key);
		System.err.println(jedis.del(byteKey));
	}

}
