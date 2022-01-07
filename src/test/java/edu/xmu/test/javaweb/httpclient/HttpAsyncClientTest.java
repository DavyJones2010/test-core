package edu.xmu.test.javaweb.httpclient;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSON;

import com.beust.jcommander.internal.Lists;
import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClient.BoundRequestBuilder;
import com.ning.http.client.AsyncHttpClientConfig;
import com.ning.http.client.AsyncHttpClientConfig.Builder;
import com.ning.http.client.ListenableFuture;
import com.ning.http.client.Response;
import com.ning.http.client.providers.netty.NettyAsyncHttpProviderConfig;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.junit.Test;

public class HttpAsyncClientTest {
    private static final int DEFAULT_BOSS_COUNT = 1;

    private static final int DEFAULT_WORKER_COUNT = 48;

    AsyncHttpClient client = getClient();

    AsyncHttpClient getClient() {
        Builder builder = new AsyncHttpClientConfig.Builder();
        builder.setAllowPoolingConnections(true);
        builder.setRequestTimeout(30 * 1000);
        builder.setReadTimeout(30 * 1000);
        builder.setConnectTimeout(30 * 1000);
        builder.setPooledConnectionIdleTimeout(1000);
        builder.setPooledConnectionIdleTimeout(1000);

        // worker
        String workerThreadPoolName = "AsyncHttpClient-Callback";
        ExecutorService workerThreadPool = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
            60L, TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>(), new DefaultThreadFactory(workerThreadPoolName));
        builder.setExecutorService(workerThreadPool);

        // boss
        NettyAsyncHttpProviderConfig providerConfig = new NettyAsyncHttpProviderConfig();
        String bossThreadPoolName = "AsyncHttpClient-Dispatcher";
        ExecutorService bossExecutorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
            60L, TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>(), new DefaultThreadFactory(bossThreadPoolName));
        providerConfig.setBossExecutorService(bossExecutorService);

        // channel factory
        NioClientSocketChannelFactory socketChannelFactory = new NioClientSocketChannelFactory(
            providerConfig.getBossExecutorService(), workerThreadPool, DEFAULT_BOSS_COUNT, DEFAULT_WORKER_COUNT);
        providerConfig.setSocketChannelFactory(socketChannelFactory);

        builder.setAsyncHttpClientProviderConfig(providerConfig);
        return new AsyncHttpClient(builder.build());
    }

    public void doAsyncGet(String url) {
        BoundRequestBuilder builder = client.prepareGet(url);
        CommonCallback callback = new CommonCallback();
        // httodo: 为啥这里异步调用失败, 啥都没返回?
        client.executeRequest(builder.build(), callback);
    }

    public void doGet(String url) {
        BoundRequestBuilder builder = client.prepareGet(url);
        List<Future> futures = Lists.newArrayList();
        for (int i = 0; i < 10000; i++) {
            ListenableFuture<Response> f = client.executeRequest(builder.build());
            futures.add(f);
        }
        try {
            for (Future future : futures) {
                System.out.println(JSON.toJSONString(future.get()));
                Thread.sleep(1000L);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void postTest() {
        String url = "https://www.baidu.com";
        //doAsyncGet(url);
        doGet(url);
    }

    public static class CommonCallback extends AsyncCompletionHandler {

        @Override
        public void onThrowable(Throwable t) {
            System.out.println("error");
        }

        @Override
        public Object onCompleted(Response response) throws Exception {
            System.out.println("hello");
            System.out.println(JSON.toJSONString(response));
            return response;
        }
    }

}
