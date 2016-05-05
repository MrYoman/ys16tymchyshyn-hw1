
package ua.yandex.mergesort;

import org.junit.Test;
import static org.junit.Assert.*;

public class MergeSortTest {
    
    private static final double EPS = 1e-4;
    
    @Test(expected = IllegalArgumentException.class)
    public void testMergeSortWhenInputArrayIsNull() {
        MergeSort mergeSort = new MergeSort();
        double[] inputArray = null;
        double[] outputArray = new double[5];
        mergeSort.mergeSort(inputArray, outputArray);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testMergeSortWhenOutputArrayIsNull() {
        MergeSort mergeSort = new MergeSort();
        double[] inputArray = new double[5];
        double[] outputArray = null;
        mergeSort.mergeSort(inputArray, outputArray);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testMergeSortWhenOutputArraySizeIsTooSmall() {
        MergeSort mergeSort = new MergeSort();
        double[] inputArray = new double[5];
        double[] outputArray = new double[4];
        mergeSort.mergeSort(inputArray, outputArray);
    }
    
    @Test
    public void testMergeSortWhenInputArrayIsSmallSoThreadWillBeOnlyOne() {
        MergeSort mergeSort = new MergeSort();
        double[] inputArray = {-12.5, 34.6, -15.0, 10.34, 11.0};
        double[] outputArray = new double[5];
        mergeSort.mergeSort(inputArray, outputArray);
        double[] expResult = {-15.0, -12.5, 10.34, 11.0, 34.6};
        
        assertArrayEquals(expResult, outputArray, EPS);
    }
    
    @Test
    public void testMergeSortWhenThereWillBeMoreThanOneThread() {
        MergeSort mergeSort = new MergeSort();
        double[] inputArray = {-12.5, 34.6, -15.0, 10.34, 11.0, 
                                2.5, 432.5, -53.0, 53.6, -25.23, 
                                2345.7, 23.5, 346.4, 76.4, -954.34,
                                76.5, 76.5, 76.5, 67.4, 35.3};
        double[] outputArray = new double[20];
        mergeSort.mergeSort(inputArray, outputArray);
        double[] expResult = {-954.34, -53.0, -25.23, -15.0, -12.5, 
                                2.5, 10.34, 11.0, 23.5, 34.6, 
                                35.3, 53.6, 67.4, 76.4, 76.5,
                                76.5, 76.5, 346.4, 432.5, 2345.7};
        
        assertArrayEquals(expResult, outputArray, EPS);
    }
    
    @Test
    public void testMergeSortWhenAllElementsAreEqual() {
        MergeSort mergeSort = new MergeSort();
        double[] inputArray = {11.0, 11.0, 11.0, 11.0, 11.0, 
                                11.0, 11.0, 11.0, 11.0, 11.0, 
                                11.0, 11.0, 11.0, 11.0, 11.0, 
                                11.0, 11.0, 11.0, 11.0, 11.0};
        double[] outputArray = new double[20];
        mergeSort.mergeSort(inputArray, outputArray);
        double[] expResult = {11.0, 11.0, 11.0, 11.0, 11.0, 
                                11.0, 11.0, 11.0, 11.0, 11.0, 
                                11.0, 11.0, 11.0, 11.0, 11.0, 
                                11.0, 11.0, 11.0, 11.0, 11.0};
        
        assertArrayEquals(expResult, outputArray, EPS);
    }
    
    @Test
    public void testMergeSortWhenThereAreManyElements() {
        MergeSort mergeSort = new MergeSort();
        double[] inputArray = new double[10000];
        double[] outputArray = new double[10000];
        for (int i = 0; i < 10000; i++) {
            inputArray[i] = 10000 - i;
        }
        mergeSort.mergeSort(inputArray, outputArray);
        double[] expResult = new double[10000];
        for (int i = 0; i < 10000; i++) {
            expResult[i] = i + 1;
        }
        
        assertArrayEquals(expResult, outputArray, EPS);
    }
    
    @Test
    public void testMergeSortWhenOutputArrayIsBiggerThanInputArray() {
        MergeSort mergeSort = new MergeSort();
        double[] inputArray = new double[1000];
        double[] outputArray = new double[10000];
        for (int i = 0; i < 1000; i++) {
            inputArray[i] = 1000 - i;
        }
        mergeSort.mergeSort(inputArray, outputArray);
        double[] expResult = new double[10000];
        for (int i = 0; i < 1000; i++) {
            expResult[i] = i + 1;
        }
        
        assertArrayEquals(expResult, outputArray, EPS);
    }
}
