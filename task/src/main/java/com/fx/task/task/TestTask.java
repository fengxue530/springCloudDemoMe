package com.fx.task.task;

import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @auther: mabaofeng
 * @date: 2019/8/15 10:22
 * @description:
 *
 * @Scheduled注解
 *
 * @Scheduled(fixedRate = 5000) ：上一次执行开始时间点之后5秒再执行
 * @Scheduled(fixedDelay = 5000) ：上一次执行完毕时间点之后5秒再执行
 * @Scheduled(initialDelay=1000, fixedRate=5000) ：第一次延迟1秒后执行，之后按fixedRate的规则每5秒执行一次。initialDelay表示第一次调用前的延时，单位毫秒，必需配合cron、fixedDelay或者fixedRate来使用。
 */
//@Scheduled(cron=" * /5 * * * * *") ：通过cron表达式定义规则，与fixedDelay类似，上次执行完毕后才进行下次调度。
@Component
public class TestTask {

    private static final FastDateFormat FORMATORDERNUM = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");

    /**
     * 每隔3秒执行一次
     */
    /*@Scheduled(fixedRate = 3000)
    public void reportCurrentTask(){
        System.out.println("=======当前线程名："+Thread.currentThread().getName()+"=========NOW：" + FORMATORDERNUM.format(new Date()));
    }*/

   /* @Scheduled(fixedRate = 1000)
    public void task1() throws InterruptedException {
        System.out.println("=======当前线程名："+Thread.currentThread().getName()+"========当前task名：task1=========NOW：" + FORMATORDERNUM.format(new Date()));
        Thread.sleep(2000);
    }

    @Scheduled(fixedRate = 2000)
    public void task2() throws InterruptedException {
        System.out.println("=======当前线程名："+Thread.currentThread().getName()+"========当前task名：task2=========NOW：" + FORMATORDERNUM.format(new Date()));
    }*/


    /**
     * 相同执行频率 优先级的问题            从打印结果来看，同一个线程中调度多个定时任务的顺序是不固定的，并没有表现出明显的优先级关系。
     * @throws InterruptedException
     */
   /* @Async
    @Scheduled(cron = "0/1 * * * * ?")
    public void task01() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " | task01 " + new Date().toLocaleString());
    }

    @Async
    @Scheduled(cron = "0/1 * * * * ?")
    public void task02() {
        System.out.println(Thread.currentThread().getName() + " | task02 " + new Date().toLocaleString());
    }*/


/*    @Scheduled(cron = "0/2 * * * * ?")
    public void task02() {
        System.out.println(Thread.currentThread().getName() + " | task02 " + new Date().toLocaleString());
    }

    @Async
    @Scheduled(cron = "0/3 * * * * ?")
    public void task03() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " | task03 " + new Date().toLocaleString());
    }*/
}
