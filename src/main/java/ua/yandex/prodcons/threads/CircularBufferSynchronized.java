package ua.yandex.prodcons.threads;

import ua.yandex.Buffer.SimpleBuffer;

public class CircularBufferSynchronized implements SimpleBuffer<Integer> {

    private final int capacity;
    private int startOfData;
    private int topOfData;

    private boolean empty = true;
    private boolean full;

    private final int[] buffer;

    public CircularBufferSynchronized(int capacity) {
        buffer = new int[capacity];
        this.capacity = capacity;
    }

    @Override
    public synchronized void put(Integer element) {
        while (full) {
            try {
                wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
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

        notifyAll();
    }

    @Override
    public synchronized Integer get() {
        while (empty) {
            try {
                wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        full = false;

        topOfData--;
        if (topOfData < 0) {
            topOfData = capacity - 1;
        }
        if (size() == 0) {
            empty = true;
        }

        notifyAll();
        return buffer[topOfData];
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
