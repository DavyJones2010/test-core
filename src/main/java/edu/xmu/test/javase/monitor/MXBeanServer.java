package edu.xmu.test.javase.monitor;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;

public class MXBeanServer {
	public static void main(String[] args) throws InterruptedException {
		System.out.println("waiting...");
		MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
		
		Thread.sleep(Long.MAX_VALUE);
	}
}
