package edu.xmu.test.hadoop.curator;

import org.apache.commons.lang.StringUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryForever;
import org.junit.Test;

public class CuratorTest {

    @Test
    public void curatorTest() {
        // hantingfixme: 增加curator测试
        String connectString = "localhost:8888";
        String namespace = "";
        int sessionTimeoutMs = 1000;
        int connectionTimeoutMs = 1000;
        RetryPolicy retryPolicy = new RetryForever(1000);
        CuratorFrameworkFactory.builder().connectString(connectString).namespace(
                StringUtils.isNotBlank(namespace) ? namespace : null).
            sessionTimeoutMs(sessionTimeoutMs).
            connectionTimeoutMs(connectionTimeoutMs).
            retryPolicy(retryPolicy).
            build();
    }
}
