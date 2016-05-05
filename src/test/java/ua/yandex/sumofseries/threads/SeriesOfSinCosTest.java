
package ua.yandex.sumofseries.threads;

import org.junit.Test;
import static org.junit.Assert.*;

public class SeriesOfSinCosTest {
    
    private static final double EPS = 1e-4;
    
    @Test(expected = IllegalArgumentException.class)
    public void testCalculateWhenThreadsCountIsLessThanOne() {
        SeriesOfSinCos series = new SeriesOfSinCos();
        double actResult = series.calculate(1.0, 0);
    }
    
    @Test
    public void testCalculateWhenThreadsCountIsOne() {
        SeriesOfSinCos series = new SeriesOfSinCos();
        double actResult = series.calculate(1.0, 1);
        double expResult = 0.0;
        assertEquals(expResult, actResult, EPS);
    }
    
    @Test
    public void testCalculateWhenThreadsCountIsOneAndNIsFive() {
        SeriesOfSinCos series = new SeriesOfSinCos();
        double actResult = series.calculate(5.0, 1);
        double expResult = 0.0;
        assertEquals(expResult, actResult, EPS);
    }
    
    @Test
    public void testCalculateWhenThreadsCountIsTwoAndNIsFive() {
        SeriesOfSinCos series = new SeriesOfSinCos();
        double actResult = series.calculate(5.0, 2);
        double expResult = 0.0;
        assertEquals(expResult, actResult, EPS);
    }
    
    @Test
    public void testCalculateWhenThreadsCountIsTenAndNIsHundred() {
        SeriesOfSinCos series = new SeriesOfSinCos();
        double actResult = series.calculate(100.0, 10);
        double expResult = 0.0;
        assertEquals(expResult, actResult, EPS);
    }
}
