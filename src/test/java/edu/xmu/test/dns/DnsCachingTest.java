package edu.xmu.test.dns;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

public class DnsCachingTest {
    @Test
    public void name() throws UnknownHostException, InterruptedException {
        String hostname = "www.somethingdonotexist.com";
        java.security.Security.setProperty("networkaddress.cache.negative.ttl" , "0");
        List<DnsThread> threads = Lists.newArrayList();
        for (int i = 0; i < 100; i++) {
            DnsThread t = new DnsThread("dnsThread-" + i, hostname);
            threads.add(t);
            t.start();
        }
        for (DnsThread thread : threads) {
            thread.join();
        }
    }

    static class DnsThread extends Thread {
        String hostname;

        public DnsThread(String name, String hostname) {
            super(name);
            this.hostname = hostname;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    InetAddress addr = InetAddress.getByName(hostname);
//                    System.out.println(addr.getHostAddress());
                } catch (UnknownHostException e) {
//                    System.out.println(e);
                }
            }
        }
    }
}