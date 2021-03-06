package ua.yandex.prodcons.utilconcurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import ua.yandex.Buffer.SimpleBuffer;

public class CircularBufferConcurrent implements SimpleBuffer<Integer> {

    private static final String CAPACITY_LESS_THAN_ZERO_MSG 
                            = "Capacity of buffer can not be less than zero.";
    
    private final int capacity;
    private int startOfData;
    private int topOfData;

    private boolean empty = true;
    private boolean full;

    private final int[] buffer;

    private final Lock lock = new ReentrantLock();
    private final Condition conditionFull = lock.newCondition();
    private final Condition conditionEmpty = lock.newCondition();

    public CircularBufferConcurrent(int capacity) 
                                            throws IllegalArgumentException {
        if (capacity < 0) {
            throw new IllegalArgumentException(CAPACITY_LESS_THAN_ZERO_MSG);
        }
        buffer = new int[capacity];
        this.capacity = capacity;
    }

    @Override
    public void put(Integer element) {
        lock.lock();
        try {
            while (full) {
                try {
                    conditionFull.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            empty = false;

            this.buffer[topOfData++] = element;
            if (topOfData == capacity) {
                topOfData = 0;
            }
            if (topOfData == startOfData) {
                full = true;
            }

            conditionEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Integer get() {
        lock.lock();
        
        Integer value;
        try {
            while (empty) {
                try {
                    conditionEmpty.await();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

            full = false;

            value = buffer[startOfData++];
            if (startOfData == capacity) {
                startOfData = 0;
            }
            if (size() == 0) {
                empty = true;
            }
            
            conditionFull.signal();
        } finally {
            lock.unlock();
        }
        
        return value;
    }

    @Override
    public int size() {
        if (full) {
            return capacity;
        }
        if (topOfData >= startOfData) {
            return topOfData - startOfData;
        }
        return capacity - (startOfData - topOfData);
    }

    @Override
    public int capacity() {
        return capacity;
    }

    @Override
    public boolean isEmpty() {
        return empty;
    }

    @Override
    public boolean isFull() {
        return full;
    }

}
