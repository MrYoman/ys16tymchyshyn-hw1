package ua.yandex.bank;

import java.util.ArrayList;
import java.util.List;

public class Bank {

    private static final String TOO_BIG_ACCOUNT_INDEX_MSG
            = "Requested account index is too big. There are not so many accounts.";
    private static final String NEGATIVE_INDEX_MSG
            = "Index can not be less than zero.";
    private final List<Account> accountList = new ArrayList();

    private boolean checkAccountExistenceByIndex(int index)
            throws IllegalArgumentException {
        if (index < 0) {
            throw new IllegalArgumentException(NEGATIVE_INDEX_MSG);
        }
        return index < accountList.size();
    }

    public void transfer(Account from, Account to, int amount)
            throws IllegalArgumentException {
        while (!from.withdraw(amount)) {
        }
        while (!to.deposit(amount)) {
        }
    }

    public synchronized void addAccount(int balance) {
        Account account = new Account(balance);
        accountList.add(account);
    }

    public int getAccountsCount() {
        return accountList.size();
    }

    public Account getAccountByIndex(int index)
            throws IllegalArgumentException, IndexOutOfBoundsException {
        if (!checkAccountExistenceByIndex(index)) {
            throw new IndexOutOfBoundsException(TOO_BIG_ACCOUNT_INDEX_MSG);
        }
        return accountList.get(index);
    }

    public long getAccountBalanceByIndex(int index)
            throws IllegalArgumentException, IndexOutOfBoundsException {
        if (!checkAccountExistenceByIndex(index)) {
            throw new IndexOutOfBoundsException(TOO_BIG_ACCOUNT_INDEX_MSG);
        }
        return accountList.get(index).getBalance();
    }

    public synchronized long getTotalBalance() {
        long balance = 0;

        for (Account account : accountList) {
            balance += account.getBalance();
        }

        return balance;
    }
}
