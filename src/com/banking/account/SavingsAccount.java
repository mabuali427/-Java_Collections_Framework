package com.banking.account;

/**
 * SavingsAccount class representing a savings account with higher interest rate.
 * Extends the abstract Account class.
 */
public class SavingsAccount extends Account {
    private static final double DEFAULT_INTEREST_RATE = 0.04; // 4% annual interest
    private static final double MINIMUM_BALANCE = 500.0;
    private int withdrawalCount = 0;
    private static final int MAX_MONTHLY_WITHDRAWALS = 6;

    /**
     * Constructor for SavingsAccount with default interest rate.
     * @param accountHolder the name of the account holder
     * @param initialBalance the initial balance
     */
    public SavingsAccount(String accountHolder, double initialBalance) {
        super(accountHolder, initialBalance, DEFAULT_INTEREST_RATE, "SAVINGS");
    }

    /**
     * Constructor for SavingsAccount with custom interest rate.
     * @param accountHolder the name of the account holder
     * @param initialBalance the initial balance
     * @param customInterestRate the custom interest rate
     */
    public SavingsAccount(String accountHolder, double initialBalance, double customInterestRate) {
        super(accountHolder, initialBalance, customInterestRate, "SAVINGS");
    }

    /**
     * Apply monthly interest to the account.
     */
    @Override
    public synchronized void applyInterest() {
        double interest = balance * getInterestRate() / 12; // Monthly interest
        balance += interest;
    }

    /**
     * Check if minimum balance is maintained.
     * @return true if balance meets minimum requirement
     */
    public boolean isMaintainingMinimumBalance() {
        return balance >= MINIMUM_BALANCE;
    }

    /**
     * Get the minimum balance requirement.
     * @return the minimum balance
     */
    public double getMinimumBalance() {
        return MINIMUM_BALANCE;
    }

    /**
     * Get the maximum number of monthly withdrawals allowed.
     * @return max monthly withdrawals
     */
    public int getMaxMonthlyWithdrawals() {
        return MAX_MONTHLY_WITHDRAWALS;
    }

    /**
     * Get the current withdrawal count for the month.
     * @return withdrawal count
     */
    public int getWithdrawalCount() {
        return withdrawalCount;
    }

    /**
     * Increment withdrawal count.
     */
    public void incrementWithdrawalCount() {
        withdrawalCount++;
    }

    /**
     * Reset withdrawal count (usually done monthly).
     */
    public void resetWithdrawalCount() {
        withdrawalCount = 0;
    }

    @Override
    public String getAccountDetails() {
        return String.format(
                "Savings Account Details:\n" +
                "  Account ID: %s\n" +
                "  Holder: %s\n" +
                "  Balance: $%.2f\n" +
                "  Interest Rate: %.2f%%\n" +
                "  Minimum Balance: $%.2f\n" +
                "  Monthly Withdrawals: %d/%d",
                getAccountId(), getAccountHolder(), balance, getInterestRate() * 100,
                MINIMUM_BALANCE, withdrawalCount, MAX_MONTHLY_WITHDRAWALS
        );
    }
}
