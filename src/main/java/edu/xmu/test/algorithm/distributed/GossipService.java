package edu.xmu.test.algorithm.distributed;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * Gossip算法样例实现, PULL模式: A仅将数据key,version推送给B,
 * B将本地被A新的数据(key,value,version)推送给A,A更新本地
 * <a href='http://blog.csdn.net/aesop_wubo/article/details/20401431'>Gossip</a>
 * <li>当有新的节点加入, 能够保证最终所有节点都收到通知
 * <li>当有节点挂掉,能够保证最终所有节点都收到通知
 * 
 * @author davyjones
 *
 */
public class GossipService {
	static List<String> nodes = Lists.newArrayList("gossip-1", "gossip-2", "gossip-3");

	/**
	 * 所有服务节点
	 */
	List<String> serverNodes;
	/**
	 * 存活节点
	 */
	List<String> aliveNodes;
	/**
	 * 死亡节点
	 */
	List<String> deadNodes;

	public static void main(String[] args) {
		String name = args[0];

		System.out.println(name + " started");

	}

	/**
	 * 发送给所有serverNodes心跳包
	 */
	public void sendHeartBeat() {

	}

	/**
	 * 接收其他节点发送的心跳包
	 */
	public void receiveHeartBeat() {

	}

	public void receiveAck() {

	}
}
