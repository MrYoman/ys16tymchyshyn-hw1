
package ua.yandex.sumofseries.utilconcurrent;

import org.junit.Test;
import static org.junit.Assert.*;

public class SeriesOfSinCosConcurrentTest {
    
    private static final double EPS = 1e-4;
    
    @Test(expected = IllegalArgumentException.class)
    public void testCalculateWhenThreadsCountIsLessThanOne() {
        SeriesOfSinCosConcurrent series = new SeriesOfSinCosConcurrent();
        double actResult = series.calculate(1.0, 0);
    }
    
    @Test
    public void testCalculateWhenThreadsCountIsOne() {
        SeriesOfSinCosConcurrent series = new SeriesOfSinCosConcurrent();
        double actResult = series.calculate(1.0, 1);
        double expResult = 0.0;
        assertEquals(expResult, actResult, EPS);
    }
    
    @Test
    public void testCalculateWhenThreadsCountIsOneAndNIsFive() {
        SeriesOfSinCosConcurrent series = new SeriesOfSinCosConcurrent();
        double actResult = series.calculate(5.0, 1);
        double expResult = 0.0;
        assertEquals(expResult, actResult, EPS);
    }
    
    @Test
    public void testCalculateWhenThreadsCountIsTwoAndNIsFive() {
        SeriesOfSinCosConcurrent series = new SeriesOfSinCosConcurrent();
        double actResult = series.calculate(5.0, 2);
        double expResult = 0.0;
        assertEquals(expResult, actResult, EPS);
    }
    
    @Test
    public void testCalculateWhenThreadsCountIsTenAndNIsHundred() {
        SeriesOfSinCosConcurrent series = new SeriesOfSinCosConcurrent();
        double actResult = series.calculate(100.0, 10);
        double expResult = 0.0;
        assertEquals(expResult, actResult, EPS);
    }
    
}
