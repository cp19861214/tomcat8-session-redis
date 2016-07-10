package org.sourcecode.redis.client;

import java.util.Set;

public interface RedisAccessor {

	boolean exists(byte[] key);

	String set(byte[] key, byte[] value);

	long del(byte[]... key);

	long expire(byte[] key, long expire);

	byte[] get(byte[] key);

	long setnx(final byte[] key, final byte[] member);

	Set<String> keys(String pattern);

	long dbSize();

	String flushDB();

}
