package edu.xmu.test.scheduled.executor;

import org.springframework.beans.factory.FactoryBean;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by davywalker on 16/10/26.
 */
public class ScheduledExecutorServiceFactory implements FactoryBean<ScheduledExecutorService> {
    static ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(4);

    /**
     * 如果该FactoryBean在spring中被注入, 则方法删除
     */
    public static ScheduledExecutorService getInstance() {
        return scheduledExecutorService;
    }

    @Override
    public ScheduledExecutorService getObject() throws Exception {
        return scheduledExecutorService;
    }

    @Override
    public Class<?> getObjectType() {
        return ScheduledExecutorService.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
