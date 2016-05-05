
package ua.yandex.sumofseries.threads;

public class SeriesOfSinCos {
    
    private static final String THREADS_COUNT_LESS_THAN_ONE_MSG
                                = "Threads count can not be less than one.";
    private static final double STEP = 0.0001;
    private static final double EPS = 0.000001;

    private static class SeriesCalculator implements Runnable {
        
        private final double start;
        private final double finish;

        private Double sum = 0.;

        public SeriesCalculator(double start, double finish) {
            this.start = start;
            this.finish = finish;
        }
        
        @Override
        public void run() {
            for (double x = start; x < finish + EPS; x += STEP) {
                sum += Math.sin(x) * Math.cos(x);
            }
        }

        public Double getSum() {
            return sum;
        }
    }

    public Double calculate(double N, int threadsCount) 
                                            throws IllegalArgumentException {
        
        if (threadsCount < 1) {
            throw new IllegalArgumentException(THREADS_COUNT_LESS_THAN_ONE_MSG);
        }
        
        Thread[] threads = new Thread[threadsCount];
        SeriesCalculator[] calculators = new SeriesCalculator[threadsCount];

        double lengthOfInterval = 2 * N / threadsCount;

        for (int i = 0; i < threadsCount; i++) {
            calculators[i] = new SeriesCalculator(
                    -N + lengthOfInterval * i,
                    -N + lengthOfInterval * (i + 1));
            threads[i] = new Thread(calculators[i]);
        }

        for (int i = 0; i < threadsCount; i++) {
            threads[i].run();
        }

        try {
            for (int i = 0; i < threadsCount; i++) {
                threads[i].join();
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        double sum = .0;
        for (int i = 0; i < threadsCount; i++) {
            sum += calculators[i].getSum();
        }

        return sum;
    }

}