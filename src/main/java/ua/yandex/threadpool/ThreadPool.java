package ua.yandex.threadpool;

import static java.lang.Thread.sleep;
import ua.yandex.collections.Queue;

public class ThreadPool {

    private static final int DEFAULT_POOL_SIZE = 10;
    private static final long SLEEP_TIME_FOR_RUNNER_IF_NO_NEW_TASKS = 50;
    private static final String POOL_SIZE_LESS_THAN_ZERO
            = "Pool size can not be less than zero.";

    private Queue<Runnable> tasks;
    private TaskRunner[] taskRunners;
    private Thread[] runnersThreads;
    private int poolSize;
    private boolean alive;

    private class TaskRunner implements Runnable {

        private Queue<Runnable> tasksQueue;
        private long sleepTimeIfQueueIsEmpty;

        public TaskRunner(Queue<Runnable> tasksQueue,
                long sleepTimeIfQueueIsEmpty) {
            this.tasksQueue = tasksQueue;
            this.sleepTimeIfQueueIsEmpty = sleepTimeIfQueueIsEmpty;
        }

        @Override
        public void run() {
            while (alive) {
                while (tasksQueue.isEmpty() && alive) {
                    try {
                        sleep(sleepTimeIfQueueIsEmpty);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
                if (!tasksQueue.isEmpty()) {
                    Runnable task = null;
                    synchronized(tasksQueue) {
                        if (!tasksQueue.isEmpty()) {
                            task = tasksQueue.dequeue();
                        }
                    }
                    if (task != null) {
                        task.run();
                    }
                }
            }
        }

    }

    public ThreadPool() {
        this(DEFAULT_POOL_SIZE);
    }

    public ThreadPool(int poolSize) throws IllegalArgumentException {
        if (poolSize < 0) {
            throw new IllegalArgumentException(POOL_SIZE_LESS_THAN_ZERO);
        }
        this.poolSize = poolSize;
        tasks = new Queue<>();
        taskRunners = new TaskRunner[poolSize];
        runnersThreads = new Thread[poolSize];
        for (int i = 0; i < poolSize; i++) {
            taskRunners[i] = new TaskRunner(tasks,
                    SLEEP_TIME_FOR_RUNNER_IF_NO_NEW_TASKS);
        }
    }

    public void activate() {
        alive = true;
        
        for (int i = 0; i < poolSize; i++) {
            runnersThreads[i] = new Thread(taskRunners[i]);
            runnersThreads[i].start();
        }
    }

    public void kill() {
        alive = false;
    }

    public boolean isAlive() {
        return alive;
    }

    public void addTask(Runnable newTask) {
        tasks.enqueue(newTask);
    }

}
