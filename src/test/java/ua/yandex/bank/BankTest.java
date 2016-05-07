
package ua.yandex.bank;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

public class BankTest {
    
    private static final int MAX_BALANCE = 10000;
    
    private class RandomTransferer implements Runnable {
        private Random randAmount = new Random();
        private Random randAccount = new Random();
        private Bank bank;
        private int accountsCount;
        private int transactionCount;

        public RandomTransferer(Bank bank, int accountsCount, 
                                                int transactionCount) {
            this.bank = bank;
            this.accountsCount = accountsCount;
            this.transactionCount = transactionCount;
        }

        private int randAccountIndex() {
            int account = randAccount.nextInt(accountsCount);
            while (account < 0) {
                account = randAccount.nextInt(accountsCount);
            }
            return account;
        }
        
        @Override
        public void run() {
            for (int i = 0; i < transactionCount; i++) {
                int from = randAccountIndex();
                int to = randAccountIndex();
                while (from == to) {
                    to = randAccountIndex();
                }
                int amount = randAmount
                        .nextInt((int)bank.getAccountBalanceByIndex(from));
                bank.transfer(bank.getAccountByIndex(from), 
                                    bank.getAccountByIndex(to), amount);
            }
        }
    }
    
    private Bank randomBank(int accountsCount) {
        Random randBalance = new Random();
        Bank bank = new Bank();
        for (int i = 0; i < accountsCount; i++) {
            int balance = randBalance.nextInt(MAX_BALANCE);
            while (balance < 0) {
                balance = randBalance.nextInt(MAX_BALANCE);
            }
            bank.addAccount(balance);
        }
        return bank;
    }
    
    @Test
    public void testTransferWhenThereAreManyThreads() {
        Bank bank = randomBank(500);
        long expTotalBalance = bank.getTotalBalance();
        
        RandomTransferer[] transferer = new RandomTransferer[5000];
        Thread[] threads = new Thread[5000];
        for (int i = 0; i < 5000; i++) {
            transferer[i] = new RandomTransferer(bank, 500, 10);
            threads[i] = new Thread(transferer[i]);
        }
        for (int i = 0; i < 5000; i++) {
            threads[i].start();
        }
        for (int i = 0; i < 5000; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        
        long actTotalBalance = bank.getTotalBalance();
        assertEquals(expTotalBalance, actTotalBalance);
    }
    
    @Test
    public void testTransferWhenThereAreManyThreadsAndFewAccounts() {
        Bank bank = randomBank(10);
        long expTotalBalance = bank.getTotalBalance();
        
        RandomTransferer[] transferer = new RandomTransferer[5000];
        Thread[] threads = new Thread[5000];
        for (int i = 0; i < 5000; i++) {
            transferer[i] = new RandomTransferer(bank, 10, 10);
            threads[i] = new Thread(transferer[i]);
        }
        for (int i = 0; i < 5000; i++) {
            threads[i].start();
        }
        for (int i = 0; i < 5000; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        
        long actTotalBalance = bank.getTotalBalance();
        assertEquals(expTotalBalance, actTotalBalance);
    }
    
    @Test
    public void testTransferWhenThreadIsOne() {
        Bank bank = randomBank(500);
        long expTotalBalance = bank.getTotalBalance();
        
        RandomTransferer[] transferer = new RandomTransferer[1];
        Thread[] threads = new Thread[1];
        for (int i = 0; i < 1; i++) {
            transferer[i] = new RandomTransferer(bank, 500, 10);
            threads[i] = new Thread(transferer[i]);
        }
        for (int i = 0; i < 1; i++) {
            threads[i].start();
        }
        for (int i = 0; i < 1; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        
        long actTotalBalance = bank.getTotalBalance();
        assertEquals(expTotalBalance, actTotalBalance);
    }
    
}
