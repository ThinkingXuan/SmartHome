package com.nuist.you.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;

public class Timer {
    /** 定时检测线程 */
    private Thread thread;

    /** 任务执行线程池，防止任务长时间执行阻塞检测线程 */
    private ExecutorService taskService = Executors.newCachedThreadPool();

    /** 用于等待和唤醒 */
    private Object lock = new Object();

    /** 任务队列，时间早的排前面 */
    private PriorityBlockingQueue<ScheduleTask> queue = new PriorityBlockingQueue<>();

    /** 是否被shutdown */
    private volatile boolean shutdowned = false;

    /**
     *	检测执行时间是否到达：
     * 	1.从队列拿到任务的时候，时间到了，直接执行
     * 	2.时间没到，阻塞直到时间到达
     * 	3.阻塞期间，被唤醒，说明有新任务到达，此时检测时间是否到了，没到放回队列里面
     * 	4.执行完任务，检测是否为循环执行的任务，是则改变触发时间，添加回队列
     */
    private Runnable checkTimeTask = () -> {
        while (true) {
            if (shutdowned) {
                break;
            }
            ScheduleTask task = null;
            try {
                task = queue.take();
            } catch (InterruptedException e1) {
                break;
            }
            boolean hasExecuted = true;
            long currentTime = System.currentTimeMillis();
            if (task.time <= currentTime) {
                taskService.execute(task.task);
            } else {
                synchronized (lock) {
                    try {
                        lock.wait(task.time - currentTime);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
                if (System.currentTimeMillis() < task.time) {
                    //说明有新任务进来
                    hasExecuted = false;
                    queue.add(task);
                } else {
                    taskService.execute(task.task);
                }
            }
            if (hasExecuted && task.period > 0) {
                task.time += task.period;
                queue.add(task);
            }
        }
        taskService.shutdown();
    };

    public void start() {
        thread = new Thread(checkTimeTask);
        thread.start();
    }

    public void shutdown() {
        shutdowned = true;
        thread.interrupt();
    }

    public void schedule(long delay, Runnable task) {
        scheduleAtFixedRate(delay, 0, task);
    }

    public void scheduleAtFixedRate(long delay, long period, Runnable task) {
        checkShutdown();
        queue.add(new ScheduleTask(System.currentTimeMillis() + delay, task, period));
        synchronized (lock) {
            lock.notify(); //notify防止新任务被延误
        }
    }

    public void scheduleAtTime(long time, Runnable task) {
        checkTime(time);
        checkShutdown();
        queue.add(new ScheduleTask(time, task, 0));
        synchronized (lock) {
            lock.notify();
        }
    }

    private void checkShutdown() {
        if (shutdowned) {
            throw new IllegalStateException("timer is shutdowned，can't submit task！");
        }
    }

    private void checkTime(long time) {
        if (time > System.currentTimeMillis()) {
            throw new IllegalArgumentException("time illegal, is passed！");
        }
    }

    /**
     * 	定时任务信息封装
     */
    private static class ScheduleTask implements Comparable<ScheduleTask> {
        long time;
        final Runnable task;
        final long period;

        public ScheduleTask(long time, Runnable task, long period) {
            this.time = time;
            this.task = task;
            this.period = period;
        }

        @Override
        public int compareTo(ScheduleTask o) {
            return (int)(time - o.time);
        }

    }

    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.start();
        timer.schedule(3000, () -> {
            System.out.println("three seconds later");
        });
        timer.scheduleAtFixedRate(10000, 2000, () -> {
            System.out.println("one second later, 2 seconds period");
        });
        try {
            Thread.sleep(1500000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("going to shutdown");
        timer.shutdown();
    }

}
