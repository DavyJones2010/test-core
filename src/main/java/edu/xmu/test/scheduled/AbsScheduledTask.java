package edu.xmu.test.scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by davywalker on 16/10/26.
 * 定时任务, 非线程安全. 参照:  {@link org.springframework.scheduling.concurrent.ScheduledExecutorTask}
 * <ol>
 * <li> 幂等性: start, stop, changeRate被多次调用, 效果幂等 </li>
 * <li> 有状态, <font color="red">非线程安全</font> </li>
 * <li> 可被终止, 可被重启, 应该被放在 ScheduledExecutorService 中执行</li>
 * </ol>
 */
public abstract class AbsScheduledTask implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(AbsScheduledTask.class);

    protected ScheduledExecutorService scheduledExecutorService;
    protected ScheduledFuture scheduledFuture;
    protected long initialDelay;
    protected long rate;
    protected TimeUnit unit;

    public AbsScheduledTask(long initialDelay, long rate, TimeUnit unit) {
        this.initialDelay = initialDelay <= 0 ? 0 : initialDelay;
        this.rate = rate <= 0 ? 100 : rate;
        this.unit = unit == null ? TimeUnit.MILLISECONDS : unit;
    }

    public boolean isRunning() {
        return null != scheduledFuture && !scheduledFuture.isDone();
    }

    public void start() {
        if (null == scheduledExecutorService) {
            logger.error("start error. scheduledExecutorService is null");
            return;
        }
        if (isRunning()) {
            logger.error("start error. already start");
            return;
        }
        this.scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(this, initialDelay, rate, unit);
    }

    public void changeRate(long initialDelay,
                           long rate,
                           TimeUnit unit) {
        if (!isRunning()) {
            this.initialDelay = initialDelay;
            this.rate = rate;
            this.unit = unit;
            start();
            return;
        }
        boolean isChanged = isChanged(initialDelay, rate, unit);
        if (isChanged) {
            this.initialDelay = initialDelay;
            this.rate = rate;
            this.unit = unit;
            stop();
            start();
        } else {
            logger.warn("changeRate error. no need to restart task.");
        }
    }

    private boolean isChanged(long initialDelay, long rate, TimeUnit unit) {
        return this.initialDelay != initialDelay || this.rate != rate || this.unit != unit;
    }

    public boolean stop() {
        return stop(true);
    }

    public boolean stop(boolean force) {
        if (!isRunning()) {
            logger.error("stop error. task already stoped!");
            return true;
        }
        return scheduledFuture.cancel(force);
    }

    public void setScheduledExecutorService(ScheduledExecutorService scheduledExecutorService) {
        this.scheduledExecutorService = scheduledExecutorService;
    }
}
