
package ua.yandex.lockfree;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicReference;

public class SequenceOfPowersOfTwo {
    
    private static BigInteger TWO = new BigInteger("2");
    private static AtomicReference<BigInteger> powerOfTwo 
                                = new AtomicReference<>(BigInteger.ONE);

    public static BigInteger next() {
        
        BigInteger oldPowerOfTwo = null;

        for (boolean updated = false; !updated;) {
            oldPowerOfTwo = powerOfTwo.get();
            updated = powerOfTwo.compareAndSet(oldPowerOfTwo,
                                                oldPowerOfTwo.multiply(TWO));
        }

        return oldPowerOfTwo;
    }
    
}
