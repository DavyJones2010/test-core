package edu.xmu.test.javax.redis;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.Jedis;

public class JedisTest {
	Jedis jedis;

	@Before
	public void beforeClass() {
		jedis = new Jedis("localhost");
	}

	@Test
	public void mapTest() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "xinxin");
		map.put("age", "22");
		map.put("qq", "123456");
		String hmset = jedis.hmset("user", map);
		System.out.println(hmset); // OK
		// System.out.println(jedis.get("user")); //exception

		assertEquals("123456", jedis.hmget("user", "qq").get(0));
		assertEquals(3L, jedis.hlen("user").longValue());
		jedis.hdel("user", "qq");
		assertEquals(2L, jedis.hlen("user").longValue());
	}

	@Test
	public void connectionPoolTest() {
		Jedis instance = JedisUtil.getInstance();
		instance.hset("user", "name", "xinxin");
		instance.hset("user", "age", "22");
		instance.hset("user", "qq", "123456");

		assertEquals("xinxin", instance.hget("user", "name"));
		assertEquals("22", instance.hget("user", "age"));
		instance.close();
	}

	@After
	public void tearDown() {
		jedis.close();
	}

}
