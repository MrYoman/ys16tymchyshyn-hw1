
package ua.yandex.threadpool;

import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

public class ThreadPoolTest {
    
    private int[] testArray;
    
    private class DummyTask implements Runnable {

        private int taskNumber;
        
        public DummyTask(int number) {
            taskNumber = number;
        }
        
        @Override
        public void run() {
            testArray[taskNumber] = taskNumber;
        }
        
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testThreadPoolWhenPoolSizeIsLessThanZero() {
        ThreadPool pool = new ThreadPool(-1);
    }
    
    @Test
    public void testThreadPoolWhenPoolSizeIsOneAndTaskAreTen() {
        testArray = new int[10];
        testArray[0] = -1;
        DummyTask[] tasks = new DummyTask[10];
        for (int i = 0; i < 10; i++) {
            tasks[i] = new DummyTask(i);
        }
        
        ThreadPool pool = new ThreadPool(1);
        for (DummyTask task : tasks) {
            pool.addTask(task);
        }
        pool.activate();
        
        try {
            sleep(500);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        
        pool.kill();
        
        int[] expResult = new int[10];
        for (int i = 0; i < 10; i++) {
            expResult[i] = i;
        }
        
        assertArrayEquals(expResult, testArray);
    }
    
    @Test
    public void testThreadPoolWhenPoolSizeIsTenAndTaskAreThousand() {
        testArray = new int[1000];
        testArray[0] = -1;
        DummyTask[] tasks = new DummyTask[1000];
        for (int i = 0; i < 1000; i++) {
            tasks[i] = new DummyTask(i);
        }
        
        ThreadPool pool = new ThreadPool();
        for (DummyTask task : tasks) {
            pool.addTask(task);
        }
        pool.activate();
        
        try {
            sleep(500);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        
        pool.kill();
        
        int[] expResult = new int[1000];
        for (int i = 0; i < 1000; i++) {
            expResult[i] = i;
        }
        
        assertArrayEquals(expResult, testArray);
    }
    
    @Test
    public void testThreadPoolWhenPoolSizeIsDefaultEqTenAndTasksAreTen() {
        testArray = new int[10];
        testArray[0] = -1;
        DummyTask[] tasks = new DummyTask[10];
        for (int i = 0; i < 10; i++) {
            tasks[i] = new DummyTask(i);
        }
        
        ThreadPool pool = new ThreadPool();
        for (DummyTask task : tasks) {
            pool.addTask(task);
        }
        pool.activate();
        
        try {
            sleep(500);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        
        pool.kill();
        
        int[] expResult = new int[10];
        for (int i = 0; i < 10; i++) {
            expResult[i] = i;
        }
        
        assertArrayEquals(expResult, testArray);
    }
    
    @Test
    public void testThreadPoolWhenPoolSizeIsDefaultEqTenAndTasksAreTwo() {
        testArray = new int[2];
        testArray[0] = -1;
        DummyTask[] tasks = new DummyTask[2];
        for (int i = 0; i < 2; i++) {
            tasks[i] = new DummyTask(i);
        }
        
        ThreadPool pool = new ThreadPool();
        for (DummyTask task : tasks) {
            pool.addTask(task);
        }
        pool.activate();
        
        try {
            sleep(500);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        
        pool.kill();
        
        int[] expResult = new int[2];
        for (int i = 0; i < 2; i++) {
            expResult[i] = i;
        }
        
        assertArrayEquals(expResult, testArray);
    }
    
    @Test
    public void testIsAliveWhenAliveEqFalse() {
        testArray = new int[10];
        testArray[0] = -1;
        DummyTask[] tasks = new DummyTask[10];
        for (int i = 0; i < 10; i++) {
            tasks[i] = new DummyTask(i);
        }
        
        ThreadPool pool = new ThreadPool();
        for (DummyTask task : tasks) {
            pool.addTask(task);
        }
        pool.activate();
        
        try {
            sleep(500);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        
        pool.kill();
        
        boolean expResult = false;
        boolean actResult = pool.isAlive();
        
        assertEquals(expResult, actResult);
    }
    
    @Test
    public void testIsAliveWhenNotStartedYet() {
        testArray = new int[10];
        testArray[0] = -1;
        DummyTask[] tasks = new DummyTask[10];
        for (int i = 0; i < 10; i++) {
            tasks[i] = new DummyTask(i);
        }
        
        ThreadPool pool = new ThreadPool();
        for (DummyTask task : tasks) {
            pool.addTask(task);
        }
        
        boolean expResult = false;
        boolean actResult = pool.isAlive();
        
        pool.activate();
        
        try {
            sleep(500);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        
        pool.kill();
        
        assertEquals(expResult, actResult);
    }
    
    @Test
    public void testIsAliveWhenAliveEqTrue() {
        testArray = new int[10];
        testArray[0] = -1;
        DummyTask[] tasks = new DummyTask[10];
        for (int i = 0; i < 10; i++) {
            tasks[i] = new DummyTask(i);
        }
        
        ThreadPool pool = new ThreadPool();
        for (DummyTask task : tasks) {
            pool.addTask(task);
        }
        pool.activate();
        
        try {
            sleep(500);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        
        boolean expResult = true;
        boolean actResult = pool.isAlive();
        
        pool.kill();
        
        assertEquals(expResult, actResult);
    }
    
    
    
}
