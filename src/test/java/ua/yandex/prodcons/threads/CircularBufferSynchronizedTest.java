
package ua.yandex.prodcons.threads;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;
import ua.yandex.prodcons.Consumer;
import ua.yandex.prodcons.Producer;

public class CircularBufferSynchronizedTest {
    
    private static final double EPS = 1e-4;
    
    @Test(expected = IllegalArgumentException.class)
    public void testWhenBufferCapacityIsLessThanZero() {
        CircularBufferSynchronized buffer = new CircularBufferSynchronized(-1);
    }
    
    @Test
    public void testSizeWhenSizeNotZero() {
        CircularBufferSynchronized buffer = new CircularBufferSynchronized(20);
        
        int expResult = 15;
        for (int i = 0; i < expResult + 3; i++) {
            buffer.put(i);
        }
        buffer.get();
        buffer.get();
        buffer.get();
        
        int actResult = buffer.size();
        
        assertEquals(expResult, actResult);
    }
    
    @Test
    public void testSizeWhenSizeIsZero() {
        CircularBufferSynchronized buffer = new CircularBufferSynchronized(20);
        
        int expResult = 0;
        for (int i = 0; i < expResult + 3; i++) {
            buffer.put(i);
        }
        buffer.get();
        buffer.get();
        buffer.get();
        
        int actResult = buffer.size();
        
        assertEquals(expResult, actResult);
    }
    
    @Test
    public void testSizeWhenNoEllementsWasPutten() {
        CircularBufferSynchronized buffer = new CircularBufferSynchronized(20);
        
        int expResult = 0;        
        int actResult = buffer.size();
        
        assertEquals(expResult, actResult);
    }
    
    @Test
    public void testCircularBufferInWork() {
        CircularBufferSynchronized buffer = new CircularBufferSynchronized(20);
        
        int[] expResult = new int[20];
        for (int i = 0; i < 10; i++) {
            buffer.put(i);
            expResult[i] = i;
        }
        
        int[] actResult = new int[20];
        for (int i = 0; i < 5; i++) {
            actResult[i] = buffer.get();
        }
        
        for (int i = 10; i < 20; i++) {
            buffer.put(i);
            expResult[i] = i;
        }
        for (int i = 5; i < 20; i++) {
            actResult[i] = buffer.get();
        }
        
        assertArrayEquals(expResult, actResult);
    }
    
    @Test
    public void testCapacityWhenCapacityIsZero() {
        CircularBufferSynchronized buffer = new CircularBufferSynchronized(0);
        
        int expResult = 0;
        int actResult = buffer.capacity();
        
        assertEquals(expResult, actResult);
    }
    
    @Test
    public void testCapacityWhenCapacityIsNotZero() {
        CircularBufferSynchronized buffer = new CircularBufferSynchronized(20);
        
        int expResult = 20;
        int actResult = buffer.capacity();
        
        assertEquals(expResult, actResult);
    }
    
    @Test
    public void testIsEmptyWhenThereWasNoElementsPutten() {
        CircularBufferSynchronized buffer = new CircularBufferSynchronized(20);
        
        boolean expResult = true;
        boolean actResult = buffer.isEmpty();
        
        assertEquals(expResult, actResult);
    }
    
    @Test
    public void testIsEmptyWhenAllElementsWereGotten() {
        CircularBufferSynchronized buffer = new CircularBufferSynchronized(20);
        
        for (int i = 0; i < 20; i++) {
            buffer.put(i);
        }
        for (int i = 0; i < 20; i++) {
            buffer.get();
        }
        
        boolean expResult = true;
        boolean actResult = buffer.isEmpty();
        
        assertEquals(expResult, actResult);
    }
    
    @Test
    public void testIsEmptyWhenNotEmpty() {
        CircularBufferSynchronized buffer = new CircularBufferSynchronized(20);
        
        for (int i = 0; i < 20; i++) {
            buffer.put(i);
        }
        for (int i = 0; i < 10; i++) {
            buffer.get();
        }
        
        boolean expResult = false;
        boolean actResult = buffer.isEmpty();
        
        assertEquals(expResult, actResult);
    }
    
    @Test
    public void testIsFullWhenThereWasNoElementsPutten() {
        CircularBufferSynchronized buffer = new CircularBufferSynchronized(20);
        
        boolean expResult = false;
        boolean actResult = buffer.isFull();
        
        assertEquals(expResult, actResult);
    }
    
    @Test
    public void testIsFullWhenAllElementsWereGotten() {
        CircularBufferSynchronized buffer = new CircularBufferSynchronized(20);
        
        for (int i = 0; i < 20; i++) {
            buffer.put(i);
        }
        for (int i = 0; i < 20; i++) {
            buffer.get();
        }
        
        boolean expResult = false;
        boolean actResult = buffer.isFull();
        
        assertEquals(expResult, actResult);
    }
    
    @Test
    public void testIsFullWhenIsFull() {
        CircularBufferSynchronized buffer = new CircularBufferSynchronized(20);
        
        for (int i = 0; i < 20; i++) {
            buffer.put(i);
        }
        
        boolean expResult = true;
        boolean actResult = buffer.isFull();
        
        assertEquals(expResult, actResult);
    }
    
    @Test
    public void testWithProdCons() {
        CircularBufferSynchronized buffer = new CircularBufferSynchronized(20);
        
        Producer[] producers = new Producer[10];
        Consumer[] consumers = new Consumer[10];
        Thread[] threadsProd = new Thread[10];
        Thread[] threadsCons = new Thread[10];
        
        for(int i = 0; i < 10; i++) {
            producers[i] = new Producer(buffer, 10);
            threadsProd[i] = new Thread(producers[i]);
            consumers[i] = new Consumer(buffer, 10);
            threadsCons[i] = new Thread(consumers[i]);
        }
        
        for(int i = 0; i < 10; i++) {
            threadsProd[i].start();
            threadsCons[i].start();
        }
        
        for (int i = 0; i < 10; i++) {
            try {
                threadsProd[i].join();
                threadsCons[i].join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
    
}
