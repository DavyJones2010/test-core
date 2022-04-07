package edu.xmu.test.javaweb.httpclient;

import com.alibaba.fastjson.JSON;
import com.beust.jcommander.internal.Lists;
import com.ning.http.client.*;
import com.ning.http.client.AsyncHttpClient.BoundRequestBuilder;
import com.ning.http.client.AsyncHttpClientConfig.Builder;
import com.ning.http.client.providers.netty.NettyAsyncHttpProviderConfig;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.junit.Test;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.*;

public class HttpAsyncClientTest {
    private static final int DEFAULT_BOSS_COUNT = 1;

    private static final int DEFAULT_WORKER_COUNT = 30;

    AsyncHttpClient client = getClient();

    AsyncHttpClient getClient() {
        Builder builder = new AsyncHttpClientConfig.Builder();
//        builder.setAllowPoolingConnections(true);
        builder.setAllowPoolingConnections(false);
        // case1: 瞬时并发HTTP连接请求时, 如果TCP连接超过maxConnectionsPerHost, 则后续的请求会直接被拒绝掉, 进入到onThrowable里, 而不是放在队列里等待. 此时allowPoolingConnections其实不起作用, 因为池子里还没有连接被释放出来.
        // 如下例子, 只有3个请求是成功的, 后续97个都是失败的.
        // case2: 但如果HTTP连接请求中间有sleep, 这样在下个请求进来时, 连接池是有空闲的, 这样能复用池子里的连接, 从而连接数不会超过maxConnectionsPerHost, 从而所有请求都能成功.
        // 如下例子, 100个请求都成功的
        builder.setMaxConnectionsPerHost(3);
        builder.setRequestTimeout(30 * 1000);
        builder.setReadTimeout(30 * 1000);
        builder.setMaxRequestRetry(20);
        builder.setConnectTimeout(30 * 1000);
        // 长连接, 连接保持1秒
//        builder.setPooledConnectionIdleTimeout(1 * 1000);
//        builder.setPooledConnectionIdleTimeout(1000);

        // worker
        String workerThreadPoolName = "AsyncHttpClient-Callback";
//        ExecutorService ioThreadPool = Executors.newFixedThreadPool(DEFAULT_WORKER_COUNT, new DefaultThreadFactory(workerThreadPoolName));
        ExecutorService ioThreadPool = Executors.newCachedThreadPool(new DefaultThreadFactory(workerThreadPoolName));
//        ExecutorService ioThreadPool = new ThreadPoolExecutor(0, DEFAULT_WORKER_COUNT,
//                60L, TimeUnit.SECONDS,
//                new SynchronousQueue<Runnable>(), new DefaultThreadFactory(workerThreadPoolName), (r, e) -> {
//            System.out.println("Task Rejected");
//        });
//        ExecutorService ioThreadPool = Executors.newCachedThreadPool(new DefaultThreadFactory(workerThreadPoolName));
//        builder.setExecutorService(ioThreadPool);

        // boss
        String bossThreadPoolName = "AsyncHttpClient-Dispatcher";
        ExecutorService bossThreadPool = Executors.newFixedThreadPool(1, new DefaultThreadFactory(bossThreadPoolName));

        // channel factory,
        // httodo: 这里的 DEFAULT_WORKER_COUNT 如果与 builder.setExecutorService 冲突, 怎么处理?
        // 会报错, Failed to get all worker threads ready within 10 second(s). Make sure to specify the executor which has more threads than the requested workerCount. If unsure, use Executors.newCachedThreadPool().
        NioClientSocketChannelFactory socketChannelFactory = new NioClientSocketChannelFactory(
                bossThreadPool, ioThreadPool, DEFAULT_BOSS_COUNT, DEFAULT_WORKER_COUNT);

        NettyAsyncHttpProviderConfig providerConfig = new NettyAsyncHttpProviderConfig();
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
    public void postTest() throws InterruptedException {
        String url = "http://www.baidu.com/";
        for (int i = 0; i < 100; i++) {
            doAsyncGet(url);
            Thread.sleep(200L);
        }
        Thread.sleep(100000L);
//        doGet(url);
    }

    public static class CommonCallback extends AsyncCompletionHandler {

        @Override
        public void onThrowable(Throwable t) {
            System.out.println(Thread.currentThread().getName() + " " + Calendar.getInstance().getTimeInMillis() + " error");
        }

        @Override
        public Object onCompleted(Response response) throws Exception {
            System.out.println(Thread.currentThread().getName() + " " + Calendar.getInstance().getTimeInMillis() + " hello");
//            System.out.println(JSON.toJSONString(response));
            Thread.sleep(1 * 1000L);
            System.out.println(Thread.currentThread().getName() + " " + Calendar.getInstance().getTimeInMillis() + " end");
            return response;
        }
    }

}
