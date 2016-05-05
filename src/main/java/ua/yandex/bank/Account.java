
package ua.yandex.bank;

import java.util.concurrent.atomic.AtomicInteger;

public class Account {
    
    private static final String WITHDRAW_LESS_THAN_ZERO_MSG
                        = "Can not withdraw less than zero amount of money";
    private static final String DEPOSIT_LESS_THAN_ZERO_MSG
                        = "Can not deposit less than zero amount of money";
    
    public final AtomicInteger balance;

    public Account() {
        this(0);
    }
    
    public Account(int amount) {
        balance = new AtomicInteger(amount);
    }

    public boolean withdraw(int amount) throws IllegalArgumentException {
        if (amount < 0) {
            throw new IllegalArgumentException(WITHDRAW_LESS_THAN_ZERO_MSG);
        }
        
        while (true) {
            int oldAmount = balance.get();

            if (oldAmount < amount) {
                return false;
            }

            if (balance.compareAndSet(oldAmount, oldAmount - amount)) {
                return true;
            }
        }
    }
 
    public boolean deposit(int amount) throws IllegalArgumentException {
        if (amount < 0) {
            throw new IllegalArgumentException(DEPOSIT_LESS_THAN_ZERO_MSG);
        }
        
        while (true) {
            int oldAmount = balance.get();
            if (balance.compareAndSet(oldAmount, oldAmount + amount)) {
                return true;
            }
        }
    }

    public int getBalance() {
        return balance.get();
    }
}
