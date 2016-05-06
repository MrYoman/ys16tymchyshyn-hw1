
package ua.yandex.lockfree;

import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;
import ua.yandex.collections.Queue;

public class SequenceOfPowersOfTwoTest {
    
    private static BigInteger TWO = new BigInteger("2");
    Queue<BigInteger> testArray;
    
    private class ElementGetter implements Runnable {
        @Override
        public void run() {
            synchronized(testArray) {
                testArray.enqueue(SequenceOfPowersOfTwo.next());
            }
        }
    }
    
    @Test
    public void testNext() {
        testArray = new Queue<>();
        
        ElementGetter[] getter = new ElementGetter[1000];
        Thread[] threads = new Thread[1000];
        for (int i = 0; i < 1000; i++) {
            getter[i] = new ElementGetter();
            threads[i] = new Thread(getter[i]);
        }
        
        for (Thread thread : threads) {
            thread.start();
        }
        
        for (int i = 0; i < 1000; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        
        BigInteger[] actResult = new BigInteger[1000];
        for (int i = 0; i < 1000; i++) {
            actResult[i] = testArray.dequeue();
        }
        
        BigInteger[] expResult = new BigInteger[1000];
        expResult[0] = BigInteger.ONE;
        for (int i = 1; i < 1000; i++) {
            expResult[i] = expResult[i - 1].multiply(TWO);
        }
        
        assertArrayEquals(expResult, actResult);
    }
    
}
