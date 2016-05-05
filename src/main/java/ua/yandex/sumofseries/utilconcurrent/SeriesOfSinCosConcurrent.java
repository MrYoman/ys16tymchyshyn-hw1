package ua.yandex.sumofseries.utilconcurrent;

import java.util.concurrent.*;

public class SeriesOfSinCosConcurrent {
    
    private static final String THREADS_COUNT_LESS_THAN_ONE_MSG
                                = "Threads count can not be less than one.";
    private static final double STEP = 0.0001;
    private static final double EPS = 0.000001;
    
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
            for (double x = start; x < finish + EPS; x += STEP) {
                sum += Math.sin(x) * Math.cos(x);
            }
            return sum;
        }
    }

    public Double calculate(double N, int threadsCount) 
                                            throws IllegalArgumentException {
        
        if (threadsCount < 1) {
            throw new IllegalArgumentException(THREADS_COUNT_LESS_THAN_ONE_MSG);
        }
        
        SeriesCalculator[] calculators = new SeriesCalculator[threadsCount];
        ExecutorService executorService 
                = Executors.newFixedThreadPool(threadsCount);
        Future<Double>[] future = new Future[threadsCount];

        double lengthOfInterval = 2 * N / threadsCount;

        for (int i = 0; i < threadsCount; i++) {
            calculators[i] = new SeriesCalculator(
                    -N + lengthOfInterval * i,
                    -N + lengthOfInterval * (i + 1));
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