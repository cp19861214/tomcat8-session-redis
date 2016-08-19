package org.sourcecode.redis.client;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.exceptions.JedisClusterException;

public class JedisClusterRedisAccessor implements RedisAccessor {

	protected final Log log = LogFactory.getLog(getClass());

	private String nodes;

	private Set<HostAndPort> hostAndPorts = new HashSet<HostAndPort>();

	private JedisPoolConfig jedisPoolConfig;

	private JedisCluster jedis;

	private int timeout = Protocol.DEFAULT_TIMEOUT;

	private int maxRedirections = 6;

	public JedisClusterRedisAccessor() {

	}

	public JedisClusterRedisAccessor(String nodes) {
		initClusterConfig(nodes);
	}

	@Override
	public boolean exists(byte[] key) {
		return jedis.exists(key);
	}

	@Override
	public String set(byte[] key, byte[] value) {
		return jedis.set(key, value);
	}

	@Override
	public long del(byte[]... keys) {
		return jedis.del(keys);
	}

	@Override
	public long expire(byte[] key, long expire) {
		TimeUnit unit = TimeUnit.MILLISECONDS;
		return jedis.expire(key, (int) unit.toSeconds(expire));
	}

	@Override
	public byte[] get(byte[] key) {
		return jedis.get(key);
	}

	@Override
	public long setnx(byte[] key, byte[] member) {
		return jedis.setnx(key, member);
	}

	@Override
	public Set<String> keys(String pattern) {
		throw new JedisClusterException("redis cluster don't support KEYS command  ...");
	}

	@Override
	public long dbSize() {
		throw new JedisClusterException("redis cluster don't support dbSize command  ...");
	}

	@Override
	public String flushDB() {
		throw new JedisClusterException("redis cluster don't support flushDB command  ...");
	}

	@Override
	public String set(byte[] key, byte[] value, long expire) {
		String r = jedis.set(key, value);
		if (expire > 0) {
			expire(key, expire);
		}
		return r;
	}

	public void initClusterConfig(String nodes) {
		this.nodes = nodes;
		String[] nodeList = nodes.split(",");
		for (String node : nodeList) {
			String[] hostPort = node.split(":");
			hostAndPorts.add(new HostAndPort(hostPort[0], Integer.parseInt(hostPort[1])));
		}
		jedis = new JedisCluster(hostAndPorts, timeout, maxRedirections, jedisPoolConfig);
		log.info("create redis cluster success for " + nodes);
	}

	public String getNodes() {
		return nodes;
	}

	public void setNodes(String nodes) {
		this.nodes = nodes;
	}

}
