
package ua.yandex.prodcons;

import java.util.Random;
import ua.yandex.Buffer.*;

public class Consumer implements Runnable {

    private final int DATA_COUNT;
    private final int SLEEP_TIME_MS = 500;
    private SimpleBuffer buffer;
    
    public Consumer(SimpleBuffer buffer, int dataCount) {
        this.buffer = buffer;
        this.DATA_COUNT = dataCount;
    }
    
    @Override
    public void run() {
        Random random = new Random();
        
        for(int i = 0; i < DATA_COUNT; i++) {
            int val = (int) buffer.get();
            System.out.println("Consumer get: " + val);
            try {
                Thread.sleep(random.nextInt(SLEEP_TIME_MS));
            } 
            catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
    
}
