
package ua.yandex.fj;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;

public class WordCountTest {
    
    @Test
    public void testCalculateWordsCountWhenThereAreNoWords() {
        String[] words = {};
        
        WordCount wordCount = new WordCount(words);
        Map<String, Integer> actResult = wordCount.calculateWordsCount();
        Map<String, Integer> expResult = new HashMap<String, Integer>();
        
        assertEquals(expResult, actResult);
    }
    
    @Test
    public void testCalculateWordsCountWhenWordIsOne() {
        String[] words = {"One word"};
        
        WordCount wordCount = new WordCount(words);
        Map<String, Integer> actResult = wordCount.calculateWordsCount();
        Map<String, Integer> expResult = new HashMap<String, Integer>();
        expResult.put("One word", 1);
        
        assertEquals(expResult, actResult);
    }
    
    @Test
    public void testCalculateWordsCountWhenWordIsTheSameFewTimes() {
        String[] words = new String[10];
        for (int i = 0; i < 10; i++) {
            words[i] = "One word";
        }
        
        WordCount wordCount = new WordCount(words);
        Map<String, Integer> actResult = wordCount.calculateWordsCount();
        Map<String, Integer> expResult = new HashMap<String, Integer>();
        expResult.put("One word", 10);
        
        assertEquals(expResult, actResult);
    }
    
    @Test
    public void testCalculateWordsCountWhenDifferentWords() {
        String[] words = {"word1", "word5", "word2", "word3", "word3",
                            "word5", "word4", "word5", "word1"};
        
        WordCount wordCount = new WordCount(words);
        Map<String, Integer> actResult = wordCount.calculateWordsCount();
        Map<String, Integer> expResult = new HashMap<String, Integer>();
        expResult.put("word1", 2);
        expResult.put("word2", 1);
        expResult.put("word3", 2);
        expResult.put("word4", 1);
        expResult.put("word5", 3);
        
        assertEquals(expResult, actResult);
    }
    
}
