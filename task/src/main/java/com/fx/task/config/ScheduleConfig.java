package com.fx.task.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @auther: mabaofeng
 * @date: 2019/8/15 11:33
 * @description: 任务配置类，使用多线程 并行执行任务
 */
@Configuration
@EnableScheduling
public class ScheduleConfig implements SchedulingConfigurer, AsyncConfigurer {

    private static Logger log = LoggerFactory.getLogger(ScheduleConfig.class);

    /**
     * 自定义同步调用   定时任务调度线程池===================================start===============================
     * @param scheduledTaskRegistrar
     */
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setTaskScheduler(taskScheduler());
        /**
         * 也可以使用JDK提供的定时任务调度线程池ScheduledThreadPoolExecutor
         */
        /* scheduledTaskRegistrar.setScheduler(Executors.newScheduledThreadPool(10));*/
    }

    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        // 配置线程池大小，根据任务数量定制
        taskScheduler.setPoolSize(10);
        // 线程名称前缀
        taskScheduler.setThreadNamePrefix("spring-task-scheduler-thread-");
        // 线程池关闭前最大等待时间，确保最后一定关闭
        taskScheduler.setAwaitTerminationSeconds(60);
        // 线程池关闭时等待所有任务完成
        taskScheduler.setWaitForTasksToCompleteOnShutdown(true);
        // 任务丢弃策略
        taskScheduler.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        return taskScheduler;
    }
    /**
     * 自定义同步调用   定时任务调度线程池===================================end===============================
     */


    /**
     * 自定义异步调用   定时任务调度线程池===================================start===============================
     * @return
     */
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 配置核心线程数
        executor.setCorePoolSize(20);
        // 配置最大线程数
        executor.setMaxPoolSize(50);
        // 配置缓存队列大小
        executor.setQueueCapacity(100);
        // 空闲线程存活时间
        executor.setKeepAliveSeconds(15);
        executor.setThreadNamePrefix("spring-task-executor-thread-");
        // 线程池对拒绝任务的处理策略：这里采用了CallerRunsPolicy策略，当线程池没有处理能力的时候，该策略会直接在execute方法的调用线程中运行被拒绝的任务；如果执行程序已关闭，则会丢弃该任务
        // AbortPolicy()
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 设置线程池中任务的等待时间，如果超过这个时候还没有销毁就强制销毁，以确保应用最后能够被关闭，而不是被没有完成的任务阻塞
        executor.setAwaitTerminationSeconds(60);
        executor.initialize();
        return executor;
    }

    /**
     * 处理异步方法的异常
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SpringAsyncExceptionHandler();
    }

    class SpringAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
        @Override
        public void handleUncaughtException(Throwable arg0, Method arg1, Object... arg2) {
            log.error("Exception occurs in async method", arg0);
        }
    }
    /**
     * 自定义异步调用   定时任务调度线程池===================================end===============================
     */

}
