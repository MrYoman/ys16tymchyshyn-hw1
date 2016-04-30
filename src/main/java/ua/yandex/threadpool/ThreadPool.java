package ua.yandex.threadpool;

import static java.lang.Thread.sleep;
import ua.yandex.collections.Queue;

public class ThreadPool {

    private static final int DEFAULT_POOL_SIZE = 10;
    private static final long SLEEP_TIME_FOR_RUNNER_IF_NO_NEW_TASKS = 500;

    private Queue<Runnable> tasks;
    private TaskRunner[] taskRunners;
    private Thread[] runnersThreads;
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
            if (tasksQueue.isEmpty()) {
                try {
                    sleep(sleepTimeIfQueueIsEmpty);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            } else {
                Runnable task = tasksQueue.dequeue();
                if (task != null) {
                    task.run();
                }
            }
        }

    }

    public ThreadPool() {
        this(DEFAULT_POOL_SIZE);
    }

    public ThreadPool(int poolSize) {
        tasks = new Queue<>();
        taskRunners = new TaskRunner[poolSize];
        runnersThreads = new Thread[poolSize];
        for (int i = 0; i < poolSize; i++) {
            taskRunners[i] = new TaskRunner(tasks,
                    SLEEP_TIME_FOR_RUNNER_IF_NO_NEW_TASKS);
            runnersThreads[i] = new Thread(taskRunners[i]);
        }
    }
    
    public void activate() {
        alive = true;
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
