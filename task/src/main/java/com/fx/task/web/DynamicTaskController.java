package com.fx.task.web;

import com.fx.task.config.ScheduleConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @auther: mabaofeng
 * @date: 2019/8/15 14:31
 * @description:
 */
@RestController
@RequestMapping("task")
public class DynamicTaskController {

    private static Logger log = LoggerFactory.getLogger(ScheduleConfig.class);

    @Resource
    private ThreadPoolTaskScheduler taskScheduler;

    private ScheduledFuture<?> scheduledFuture;

    @Value("${timing-task.cron1}")
    private String cronStr1;

    @Value("${timing-task.cron2}")
    private String cronStr2;

    @RequestMapping("/start")
    public String startTask() {
        scheduledFuture = taskScheduler.schedule(new RunTask01(), new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                return new CronTrigger(cronStr1).nextExecutionTime(triggerContext);
            }
        });
        log.info("start timed task success ..");
        return "start task suceess";
    }

    @RequestMapping("/stop")
    public String stopTask() {
        Boolean result = null;
        if (scheduledFuture != null) {
            result = scheduledFuture.cancel(true);
        }
        log.info("stop timed task result: " + result);
        return "stop task result: " + result;
    }

    @RequestMapping("/modify")
    public String modifyTask() {
       /* TimeUnit.SECONDS.sleep();*/
        Boolean stopResult = null;
        // 停止定时任务
        if (scheduledFuture != null) {
            stopResult = scheduledFuture.cancel(true);
        } else {
            log.info("modify task error -> scheduledFuture is null");
            return "error";
        }
        // 更换cron重新开启定时任务
        if (stopResult) {
            scheduledFuture = taskScheduler.schedule(new RunTask01(), new Trigger() {
                @Override
                public Date nextExecutionTime(TriggerContext triggerContext) {
                    return new CronTrigger(cronStr2).nextExecutionTime(triggerContext);
                }
            });
            log.info("modify task success ..");
            return "success";
        }
        log.info("modify task failed ..");
        return "failed";
    }

    class RunTask01 implements Runnable {

        @Override
        public void run() {
            log.info(Thread.currentThread().getName() + "|schedule task01" + "|" + new Date().toLocaleString());
        }
    }

    class RunTask02 implements Runnable {
        @Override
        public void run() {
            log.info(Thread.currentThread().getName() + "|schedule task02" + "|" + new Date().toLocaleString());
        }
    }

}
