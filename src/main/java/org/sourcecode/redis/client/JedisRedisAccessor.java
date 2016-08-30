package org.sourcecode.redis.client;

import java.util.Set;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisRedisAccessor implements RedisAccessor {

	protected final Log log = LogFactory.getLog(getClass());

	private JedisPoolConfig jedisPoolConfig;

	private JedisPool jedisPool;

	private int database = 0;

	private String host;

	private int port;

	public JedisRedisAccessor() {

	}

	public JedisRedisAccessor(JedisPoolConfig jedisPoolConfig, String host, int port, int database) {
		this.jedisPoolConfig = jedisPoolConfig;
		this.host = host;
		this.port = port;
		this.database = database;
	}

	@Override
	public boolean exists(final byte[] key) {

		return execute(new JedisExecutor<Boolean>() {

			public Boolean execute(Jedis jedis) {

				return jedis.exists(key);
			}

		});
	}

	public String set(byte[] key, byte[] value) {
		return set(key, value, -1);
	}

	public String set(byte[] key, byte[] value, final int expire) {
		return execute(new JedisExecutor<String>() {
			public String execute(Jedis jedis) {
				String r = jedis.set(key, value);
				if (expire > 0) {
					jedis.expire(key, expire);
				}
				return r;
			}
		});
	}

	public long expire(byte[] key, final int seconds) {

		return execute(new JedisExecutor<Long>() {

			public Long execute(Jedis jedis) {
				return jedis.expire(key, seconds);

			}

		});
	}

	public long del(final byte[]... keys) {

		return execute(new JedisExecutor<Long>() {

			public Long execute(Jedis jedis) {

				return jedis.del(keys);
			}

		});
	}

	public byte[] get(final byte[] key) {

		byte[] rawvalue = execute(new JedisExecutor<byte[]>() {

			public byte[] execute(Jedis jedis) {
				return jedis.get(key);
			}
		});
		return rawvalue;
	}

	public Set<String> keys(final String pattern) {

		Set<String> kesSet = execute(new JedisExecutor<Set<String>>() {

			public Set<String> execute(Jedis jedis) {
				// TODO Auto-generated method stub
				return jedis.keys(pattern);
			}
		});
		return kesSet;
	}

	@Override
	public long dbSize() {

		long size = execute(new JedisExecutor<Long>() {

			public Long execute(Jedis jedis) {
				return jedis.dbSize();
			}
		});
		return size;
	}

	public long setnx(final byte[] key, final byte[] member) {

		return execute(new JedisExecutor<Long>() {

			public Long execute(Jedis jedis) {
				// TODO Auto-generated method stub
				return jedis.setnx(key, member);
			}
		});
	}

	@Override
	public String flushDB() {
		// TODO Auto-generated method stub
		return execute(new JedisExecutor<String>() {

			@Override
			public String execute(Jedis jedis) {
				// TODO Auto-generated method stub
				return jedis.flushDB();
			}
		});
	}

	@Override
	public void destroy() {
		jedisPool.destroy();
		log.info("destory jedis pool ...");
	}

	@Override
	public void init() {
		jedisPool = new JedisPool(jedisPoolConfig, host, port);
	}

	protected void returnJedis2Pool(Jedis jedis, boolean broken) {
		if (broken) {
			jedisPool.returnBrokenResource(jedis);
		} else {
			jedisPool.returnResource(jedis);
		}
	}

	<T> T execute(JedisExecutor<T> jedisExecutor) {
		Jedis jedis = null;
		T t = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			long dbindex = jedis.getDB();
			if (dbindex != database) {
				jedis.select(database);
			}
			t = jedisExecutor.execute(jedis);
		} catch (Exception e) {
			broken = true;
			throw new RuntimeException("execute redis command fail " + e.getMessage());
		} finally {
			returnJedis2Pool(jedis, broken);
		}
		return t;
	}

	interface JedisExecutor<T> {

		T execute(Jedis jedis);
	}

	public JedisPoolConfig getJedisPoolConfig() {
		return jedisPoolConfig;
	}

	public void setJedisPoolConfig(JedisPoolConfig jedisPoolConfig) {
		this.jedisPoolConfig = jedisPoolConfig;
	}

	public JedisPool getJedisPool() {
		return jedisPool;
	}

	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}

	public int getDatabase() {
		return database;
	}

	public void setDatabase(int database) {
		this.database = database;
	}

	public static void main(String[] args) {
		RedisAccessor ra = new JedisRedisAccessor(new JedisPoolConfig(), "172.16.97.5", 6379, 3);
		ra.init();
		String key = "abc";
		String result = ra.set(key.getBytes(), "a".getBytes(), 5);
		byte[] value = ra.get(key.getBytes());
		System.out.println(ra.setnx(key.getBytes(), "22".getBytes()));
		System.out.println(ra.exists(key.getBytes()));
		System.out.println(result + "," + new String(value));
	}

}
