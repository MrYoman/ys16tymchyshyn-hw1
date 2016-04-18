
package ua.yandex.mergesort;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MergeSort {
    
    private static final int MIN_ARR_LEN_FOR_THREAD = 50;
    
    private class SortRunner implements Runnable {

        private double[] inputArray;
        private int startPos;
        private int finPos;
        private double[] outputArray;
        private int outputPos;
        
        private SortRunner(double[] inputArray, int startPos, int finPos, 
                                    double[] outputArray, int outputPos) {
            this.inputArray = inputArray;
            this.startPos = startPos;
            this.finPos = finPos;
            this.outputArray = outputArray;
            this.outputPos = outputPos;
        }
        
        @Override
        public void run() {
            try {
                sort(inputArray, startPos, finPos, outputArray, outputPos);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    private class MergeRunner implements Runnable {

        private double[] array;
        private int startFirstHalf;
        private int finFirstHalf;
        private int startSecondHalf;
        private int finSecondHalf;
        private double[] outputArray;
        private int pos;

        MergeRunner(double[] array, int startFirstHalf, 
                            int finFirstHalf, int startSecondHalf, 
                            int finSecondHalf, double[] outputArray, int pos) {
            this.array = array;
            this.startFirstHalf = startFirstHalf;
            this.finFirstHalf = finFirstHalf;
            this.startSecondHalf = startSecondHalf;
            this.finSecondHalf = finSecondHalf;
            this.outputArray = outputArray;
            this.pos = pos;
        }
        
        @Override
        public void run() {
            try {
                merge(array, startFirstHalf, finFirstHalf, startSecondHalf, 
                        finSecondHalf, outputArray, pos);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    private int binarySearch(double key, double[] array, int left, int right) {
        int low = left;
        int high = Math.max(left, right + 1);
        while (low < high) {
            int middle = (low + high) / 2;
            if (key <= array[middle]) {
                high = middle;
            }
            else {
                low = middle + 1;
            }
        }
        return high;
    }
    
    private void merge(double[] array, int startFirstHalf, int finFirstHalf, 
                        int startSecondHalf, int finSecondHalf, 
                        double[] outputArray, int pos) throws InterruptedException {
        int firstPartLength = finFirstHalf - startFirstHalf + 1;
        int secondPartLength = finSecondHalf - startSecondHalf + 1;
        if (firstPartLength < secondPartLength) {
            int t = startFirstHalf;
            startFirstHalf = startSecondHalf;
            startSecondHalf = t;
            
            t = finFirstHalf;
            finFirstHalf = finSecondHalf;
            finSecondHalf = t;
            
            t = firstPartLength;
            firstPartLength = secondPartLength;
            secondPartLength = t;
        }
        if (firstPartLength == 0) {
            return;
        }
        int middleFirst = (startFirstHalf + finFirstHalf) / 2;
        int keyPos = binarySearch(array[middleFirst], array, 
                                    startSecondHalf, finSecondHalf);
        int foundPos = pos + (middleFirst - startFirstHalf) 
                                    + (keyPos - startSecondHalf);
        outputArray[foundPos] = array[middleFirst];
        
        if (foundPos - pos + 1 > MIN_ARR_LEN_FOR_THREAD) {
            MergeRunner mergeRunner1 = new MergeRunner(array, startFirstHalf, 
                    middleFirst - 1, startSecondHalf, keyPos - 1, 
                    outputArray, pos);
            MergeRunner mergeRunner2 = new MergeRunner(array, middleFirst + 1, 
                    finFirstHalf, keyPos, finSecondHalf, 
                    outputArray, foundPos + 1);
            
            Thread thread1 = new Thread(mergeRunner1);
            Thread thread2 = new Thread(mergeRunner2);
            
            thread1.start();
            thread2.start();
            
            thread1.join();
            thread2.join();
        }
        else {
            merge(array, startFirstHalf, middleFirst - 1, startSecondHalf, 
                    keyPos - 1, outputArray, pos);
            merge(array, middleFirst + 1, finFirstHalf, keyPos, finSecondHalf, 
                    outputArray, foundPos + 1);
        }
    }
    
    private void sort(double[] inputArray, int startPos,  
                        int finPos, double[] outputArray, 
                        int outputPos) throws InterruptedException {
        int inputLength = finPos - startPos + 1;
        if (inputLength == 1) {
            outputArray[outputPos] = inputArray[startPos];
        }
        else {
            double[] output = new double[inputLength];
            
            int middle = (startPos + finPos) / 2;
            int firstHalfLength = middle - startPos + 1;
            
            if (firstHalfLength > MIN_ARR_LEN_FOR_THREAD) {
                SortRunner sortRunner1 = new SortRunner(inputArray, middle + 1, 
                    finPos, output, 0);
                SortRunner sortRunner2 = new SortRunner(inputArray, startPos, 
                    finPos, output, firstHalfLength);
                
                Thread thread1 = new Thread(sortRunner1);
                Thread thread2 = new Thread(sortRunner2);

                thread1.start();
                thread2.start();

                thread1.join();
                thread2.join();
            }
            else {
                sort(inputArray, middle + 1, finPos, output, 0);
                sort(inputArray, startPos, finPos, output, firstHalfLength);
            }
            
            merge(output, 0, firstHalfLength - 1, firstHalfLength, 
                    inputLength - 1, outputArray, outputPos);
        }
    }
    
    public void mergeSort(double[] inputArray, double[] outputArray) {
        try {
            sort(inputArray, 0, inputArray.length - 1, outputArray, 0);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    
}
