
package ua.yandex.prodcons;

import java.util.Random;
import ua.yandex.Buffer.*;

public class Producer implements Runnable {

    private final int DATA_COUNT;
    private final int SLEEP_TIME_MS = 2000;
    private SimpleBuffer buffer;
    
    public Producer(SimpleBuffer buffer, int dataCount) {
        this.buffer = buffer;
        this.DATA_COUNT = dataCount;
    }
    
    @Override
    public void run() {
        Random rand = new Random();
        
        for (int i = 0; i < DATA_COUNT; i++) {
            buffer.put(i);
            try {
                Thread.sleep(rand.nextInt(SLEEP_TIME_MS));
            }
            catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
    
}
