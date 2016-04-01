package ua.yandex.sumofseries.utilconcurrent;

import java.util.concurrent.*;

public class SeriesOfSinCosConcurrent {
    
    private static final float STEP = 0.0001f;

    
    static class SeriesCalculator implements Callable<Double> {

        private final double start;
        private final double finish;
        private double sum = 0.;
        
        public SeriesCalculator(double start, double finish) {
            this.start = start;
            this.finish = finish;
        }

        public double getSum() {
            return sum;
        }

        @Override
        public Double call() throws Exception {
            for (double x = start; x < finish + STEP; x += STEP) {
                sum += Math.sin(x) * Math.cos(x);
            }
            return sum;
        }
    }

    public Double calculate(double N, int threadsCount) {
        SeriesCalculator[] calculators = new SeriesCalculator[threadsCount];
        ExecutorService executorService 
                = Executors.newFixedThreadPool(threadsCount);
        Future<Double>[] future = new Future[threadsCount];

        double lengthOfInterval = 2 * N / threadsCount;

        for (int i = 0; i < threadsCount; i++) {
            calculators[i] = new SeriesCalculator(
                    -N + lengthOfInterval * i * STEP,
                    -N + lengthOfInterval * (i + 1) * STEP);
            future[i] = executorService.submit(calculators[i]);
        }

        double sum = .0;

        try {
            for (int i = 0; i < threadsCount; i++) {
                sum += future[i].get();
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return sum;
    }
}