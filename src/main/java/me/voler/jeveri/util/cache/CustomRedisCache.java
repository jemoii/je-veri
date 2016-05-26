package me.voler.jeveri.util.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

public class CustomRedisCache implements Cache {

	private String name;
	private RedisTemplate<String, Object> redisTemplate;
	private RedisSerializer<String> keySerializer = new StringRedisSerializer();
	/** DefaultSerializer & DefaultDeserializer */
	private RedisSerializer<Object> valueSerializer = new JdkSerializationRedisSerializer();

	@Override
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Object getNativeCache() {
		return this.redisTemplate;
	}

	@Override
	public ValueWrapper get(Object key) {
		final String cacheKey = (String) key;

		Object obj = redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] byteKey = keySerializer.serialize(cacheKey);// cacheKey.getBytes();
				byte[] value = connection.get(byteKey);

				if (value == null) {
					return null;
				}
				return valueSerializer.deserialize(value);
			}
		});
		return (obj != null ? new SimpleValueWrapper(obj) : null);
	}

	/**
	 * default expireTime is 24h
	 */
	@Override
	public void put(Object key, Object value) {
		put(key, value, 24 * 60 * 60);
	}

	public void put(Object key, Object value, long expireTime) {
		final String cacheKey = (String) key;
		final Object cacheValue = value;
		final long cacheExpireTime = expireTime;

		redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] byteKey = keySerializer.serialize(cacheKey);// cacheKey.getBytes();
				byte[] valueb = valueSerializer.serialize(cacheValue);

				connection.set(byteKey, valueb);
				if (cacheExpireTime > 0) {
					connection.expire(byteKey, cacheExpireTime);
				}
				return null;
			}
		});
	}

	@Override
	public void evict(Object key) {
		final String cacheKey = (String) key;

		redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.del(cacheKey.getBytes());
			}
		});
	}

	@Override
	public void clear() {
		redisTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				connection.flushDb();
				return null;
			}
		});
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T get(Object key, Class<T> type) {
		Object value = get(key);
		if (value != null && type != null && !type.isInstance(value)) {
			throw new IllegalStateException("Cached value is not of required type [" + type.getName() + "]: " + value);
		}
		return (T) value;
	}

	@Override
	public ValueWrapper putIfAbsent(Object key, Object value) {
		Object obj = get(key);
		if (obj == null) {
			put(key, value);
			return null;
		} else {
			return (obj != null ? new SimpleValueWrapper(obj) : null);
		}
	}

	public RedisTemplate<String, Object> getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public RedisSerializer<String> getKeySerializer() {
		return keySerializer;
	}

	public void setKeySerializer(RedisSerializer<String> keySerializer) {
		this.keySerializer = keySerializer;
	}

	public RedisSerializer<Object> getValueSerializer() {
		return valueSerializer;
	}

	public void setValueSerializer(RedisSerializer<Object> valueSerializer) {
		this.valueSerializer = valueSerializer;
	}
}
