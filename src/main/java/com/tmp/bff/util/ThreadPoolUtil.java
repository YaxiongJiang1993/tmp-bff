package com.tmp.bff.util;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 通用线程池工具
 *
 * @author jiangyaxiong
 */
public class ThreadPoolUtil {

    public static ExecutorService getThreadPool() {

        return ThreadPoolHolder.threadPool;
    }

    private static class ThreadPoolHolder {

        static {
            ExecutorService pool = new ThreadPoolExecutor(
                    10,
                    20,
                    120,
                    TimeUnit.SECONDS,
                    new LinkedBlockingQueue<>(100),
                    new CommonThreadFactory(),
                    new ThreadPoolExecutor.CallerRunsPolicy()
            );

            threadPool = pool;
        }

        private static ExecutorService threadPool;
    }

    /**
     * The default thread factory
     */
    static class CommonThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        CommonThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            namePrefix = "common-pool-" +
                    poolNumber.getAndIncrement() +
                    "-thread-";
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                    namePrefix + threadNumber.getAndIncrement(),
                    0);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }
    }


}
