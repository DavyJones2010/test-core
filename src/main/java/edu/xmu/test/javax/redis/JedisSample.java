package edu.xmu.test.javax.redis;

import redis.clients.jedis.Jedis;

public class JedisSample {
	public static void main(String[] args) {
		Jedis jedis = new Jedis("localhost");
		System.out.println(jedis.ping());
		jedis.set("username", "hanting");
		System.out.println(jedis.get("username"));
		Long del = jedis.del("username");
		System.out.println(del);
		jedis.set("username", "davywalker");
		jedis.close();
	}
}
