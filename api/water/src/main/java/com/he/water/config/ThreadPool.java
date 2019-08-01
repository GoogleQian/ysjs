package com.he.water.config;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池配置
 *
 * @author hzh
 * @date 2018/11/15
 */
public class ThreadPool {
    private static final int CORE_SIZE = 8;
    private static final int MAX_SIZE = 12;
    private static final long KEEP_ALIVE_TIME = 30;
    private static final int QUEUE_SIZE = 1000;
    private static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(CORE_SIZE, MAX_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS, new ArrayBlockingQueue<>(QUEUE_SIZE), new ThreadPoolExecutor.AbortPolicy());

    public static ThreadPoolExecutor getThreadPool() {
        return threadPool;
    }
}
