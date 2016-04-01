
package ua.yandex.sumofseries.threads;

public class SeriesOfSinCos {
    
    private static final float STEP = 0.0001f;

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
            for (double x = start; x < finish + STEP; x += STEP) {
                sum += Math.sin(x) * Math.cos(x);
            }
        }

        public Double getSum() {
            return sum;
        }
    }

    public Double calculate(double N, int threadsCount) {
        
        Thread[] threads = new Thread[threadsCount];
        SeriesCalculator[] calculators = new SeriesCalculator[threadsCount];

        double lengthOfInterval = N / threadsCount;

        calculators[0] = new SeriesCalculator(-N, -N + lengthOfInterval);
        for (int i = 0; i < threadsCount; i++) {
            calculators[i] = new SeriesCalculator(
                    -N + lengthOfInterval * i * STEP,
                    -N + lengthOfInterval * (i + 1) * STEP);
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