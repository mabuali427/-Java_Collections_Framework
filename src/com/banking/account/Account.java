package com.banking.account;

import com.banking.exception.InsufficientFundsException;
import com.banking.exception.InvalidAmountException;
import com.banking.transaction.Transaction;
import com.banking.transaction.TransactionRecord;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Abstract Account class providing common functionality for all account types.
 * Encapsulates account details and transaction history.
 */
public abstract class Account {
    private final String accountId;
    private final String accountType;
    private final String accountHolder;
    protected double balance;
    private final double interestRate;
    private final List<Transaction> transactionHistory;

    /**
     * Constructor for Account.
     * @param accountHolder the name of the account holder
     * @param initialBalance the initial balance
     * @param interestRate the interest rate for the account
     * @param accountType the type of account
     */
    public Account(String accountHolder, double initialBalance, double interestRate, String accountType) {
        validateInput(accountHolder, initialBalance);
        this.accountId = UUID.randomUUID().toString();
        this.accountHolder = accountHolder;
        this.balance = initialBalance;
        this.interestRate = interestRate;
        this.accountType = accountType;
        this.transactionHistory = new ArrayList<>();

        // Record initial deposit
        if (initialBalance > 0) {
            transactionHistory.add(new TransactionRecord("INITIAL_DEPOSIT", initialBalance, "Account opening deposit"));
        }
    }

    /**
     * Validate input parameters.
     * @param accountHolder the account holder name
     * @param initialBalance the initial balance
     * @throws IllegalArgumentException if input is invalid
     */
    protected void validateInput(String accountHolder, double initialBalance) {
        if (accountHolder == null || accountHolder.trim().isEmpty()) {
            throw new IllegalArgumentException("Account holder name cannot be null or empty");
        }
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative");
        }
    }

    /**
     * Deposit money into the account.
     * @param amount the amount to deposit
     * @throws InvalidAmountException if amount is invalid
     */
    public synchronized void deposit(double amount) throws InvalidAmountException {
        validateAmount(amount);
        balance += amount;
        transactionHistory.add(new TransactionRecord("DEPOSIT", amount, "Deposit to account"));
    }

    /**
     * Withdraw money from the account.
     * @param amount the amount to withdraw
     * @throws InvalidAmountException if amount is invalid
     * @throws InsufficientFundsException if insufficient balance
     */
    public synchronized void withdraw(double amount) throws InvalidAmountException, InsufficientFundsException {
        validateAmount(amount);
        if (balance < amount) {
            throw new InsufficientFundsException(
                    String.format("Insufficient funds. Current balance: $%.2f, Requested amount: $%.2f", balance, amount)
            );
        }
        balance -= amount;
        transactionHistory.add(new TransactionRecord("WITHDRAW", amount, "Withdrawal from account"));
    }

    /**
     * Validate the transaction amount.
     * @param amount the amount to validate
     * @throws InvalidAmountException if amount is invalid
     */
    protected void validateAmount(double amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Transaction amount must be greater than 0");
        }
        if (amount > 1_000_000) {
            throw new InvalidAmountException("Transaction amount cannot exceed $1,000,000");
        }
    }

    /**
     * Transfer money to another account.
     * @param destinationAccount the destination account
     * @param amount the amount to transfer
     * @throws InvalidAmountException if amount is invalid
     * @throws InsufficientFundsException if insufficient balance
     */
    public synchronized void transfer(Account destinationAccount, double amount)
            throws InvalidAmountException, InsufficientFundsException {
        validateAmount(amount);
        if (balance < amount) {
            throw new InsufficientFundsException(
                    String.format("Insufficient funds for transfer. Current balance: $%.2f, Transfer amount: $%.2f", balance, amount)
            );
        }

        // Withdraw from source account
        this.balance -= amount;
        transactionHistory.add(new TransactionRecord("TRANSFER_OUT", amount,
                "Transfer to " + destinationAccount.getAccountHolder()));

        // Deposit to destination account
        destinationAccount.balance += amount;
        destinationAccount.transactionHistory.add(new TransactionRecord("TRANSFER_IN", amount,
                "Transfer from " + this.accountHolder));
    }

    /**
     * Get the account balance.
     * @return the current balance
     */
    public synchronized double getBalance() {
        return balance;
    }

    /**
     * Get the account ID.
     * @return the account ID
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * Get the account holder name.
     * @return the account holder name
     */
    public String getAccountHolder() {
        return accountHolder;
    }

    /**
     * Get the account type.
     * @return the account type
     */
    public String getAccountType() {
        return accountType;
    }

    /**
     * Get the interest rate.
     * @return the interest rate
     */
    public double getInterestRate() {
        return interestRate;
    }

    /**
     * Get the immutable transaction history.
     * @return unmodifiable list of transactions
     */
    public List<Transaction> getTransactionHistory() {
        return Collections.unmodifiableList(transactionHistory);
    }

    /**
     * Abstract method to calculate interest.
     */
    public abstract void applyInterest();

    /**
     * Abstract method to get account details.
     */
    public abstract String getAccountDetails();

    @Override
    public String toString() {
        return getAccountDetails();
    }
}
