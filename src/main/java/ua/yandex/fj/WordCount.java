
package ua.yandex.fj;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class WordCount {
    
    private final CalculatorTask calculatorTask;
    
    private class CalculatorTask extends RecursiveTask<Map<String, Integer>> {

        private static final int MAX_LENGTH_FOR_NOT_PARALLEL_CALC = 10;
        
        private final String[] words;
        private final int start;
        private final int end;
        
        private CalculatorTask(String[] words, int start, int end) {
            this.words = words;
            this.start = start;
            this.end = end;
        }
        
        public CalculatorTask(String[] words) {
            this(words, 0, words.length - 1);
        }
        
        private Map<String, Integer> calculateSimple() {
            HashMap<String, Integer> result = new HashMap<String, Integer>();
            
            for (int i = start; i <= end; i++) {
                if (result.containsKey(words[i])) {
                    result.put(words[i], result.get(words[i]) + 1);
                } else {
                    result.put(words[i], 1);
                }
            }
            
            return result;
        }
        
        private Map<String, Integer> calculateParallel() {
            int middle = (start + end) / 2;
            
            CalculatorTask firstCalcTask 
                    = new CalculatorTask(words, start, middle);
            firstCalcTask.fork();
            CalculatorTask secondCalcTask 
                    = new CalculatorTask(words, middle + 1, end);
            secondCalcTask.fork();
            
            return mergeMaps(firstCalcTask.join(), secondCalcTask.join());
        }
        
        private Map<String, Integer> mergeMaps(Map<String, Integer> firstMap, 
                                            Map<String, Integer> secondMap) {
            HashMap<String, Integer> mergedMap = new HashMap<String, Integer>();
            
            for (Map.Entry<String, Integer> entry : firstMap.entrySet()) {
                if (secondMap.containsKey(entry.getKey())) {
                    mergedMap.put(entry.getKey(), 
                            entry.getValue() + secondMap.get(entry.getKey()));
                } else {
                    mergedMap.put(entry.getKey(), entry.getValue());
                }
            }
            
            return mergedMap;
        }
        
        private boolean shouldCalculateSimple() {
            return end - start <= MAX_LENGTH_FOR_NOT_PARALLEL_CALC;
        }
        
        @Override
        protected Map<String, Integer> compute() {
            if (shouldCalculateSimple()) {
                return calculateSimple();
            } else {
                return calculateParallel();
            }
        }
    }
    
    public WordCount(String[] words) {
        calculatorTask = new CalculatorTask(words);
    }
    
    public Map<String, Integer> calculateWordsCount() {
        ForkJoinPool fjPool = new ForkJoinPool();
        return fjPool.invoke(calculatorTask);
    }
}
