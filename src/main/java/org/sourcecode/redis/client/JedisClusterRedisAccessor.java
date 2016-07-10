package org.sourcecode.redis.client;

import java.util.Set;

public class JedisClusterRedisAccessor implements RedisAccessor {

	@Override
	public boolean exists(byte[] key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String set(byte[] key, byte[] value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long del(byte[]... key) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long expire(byte[] key, long expire) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public byte[] get(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long setnx(byte[] key, byte[] member) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Set<String> keys(String pattern) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long dbSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String flushDB() {
		// TODO Auto-generated method stub
		return null;
	}

}
