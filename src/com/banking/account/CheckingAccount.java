package com.banking.account;

/**
 * CheckingAccount class representing a checking account with overdraft protection.
 * Extends the abstract Account class.
 */
public class CheckingAccount extends Account {
    private static final double DEFAULT_INTEREST_RATE = 0.01; // 1% annual interest
    private static final double OVERDRAFT_LIMIT = 500.0;
    private double overdraftUsed = 0;

    /**
     * Constructor for CheckingAccount with default interest rate.
     * @param accountHolder the name of the account holder
     * @param initialBalance the initial balance
     */
    public CheckingAccount(String accountHolder, double initialBalance) {
        super(accountHolder, initialBalance, DEFAULT_INTEREST_RATE, "CHECKING");
    }

    /**
     * Constructor for CheckingAccount with custom interest rate.
     * @param accountHolder the name of the account holder
     * @param initialBalance the initial balance
     * @param customInterestRate the custom interest rate
     */
    public CheckingAccount(String accountHolder, double initialBalance, double customInterestRate) {
        super(accountHolder, initialBalance, customInterestRate, "CHECKING");
    }

    /**
     * Apply monthly interest to the account.
     */
    @Override
    public synchronized void applyInterest() {
        double interest = balance * getInterestRate() / 12; // Monthly interest
        balance += interest;
        
        // Charge overdraft fee if account is in overdraft
        if (overdraftUsed > 0) {
            double overdraftFee = overdraftUsed * 0.05; // 5% monthly fee on overdraft amount
            balance -= overdraftFee;
        }
    }

    /**
     * Check if the account can process a transaction with overdraft protection.
     * @param amount the transaction amount
     * @return true if transaction can be processed
     */
    public boolean canWithdrawWithOverdraft(double amount) {
        return (balance + OVERDRAFT_LIMIT - overdraftUsed) >= amount;
    }

    /**
     * Get available balance including overdraft protection.
     * @return available balance
     */
    public double getAvailableBalance() {
        return balance + (OVERDRAFT_LIMIT - overdraftUsed);
    }

    /**
     * Get the overdraft limit.
     * @return overdraft limit
     */
    public double getOverdraftLimit() {
        return OVERDRAFT_LIMIT;
    }

    /**
     * Get the amount of overdraft currently used.
     * @return overdraft used
     */
    public double getOverdraftUsed() {
        return overdraftUsed;
    }

    /**
     * Update overdraft used amount based on current balance.
     */
    protected synchronized void updateOverdraft() {
        if (balance < 0) {
            overdraftUsed = -balance;
        } else {
            overdraftUsed = 0;
        }
    }

    /**
     * Get remaining overdraft available.
     * @return remaining overdraft
     */
    public double getRemainingOverdraft() {
        return OVERDRAFT_LIMIT - overdraftUsed;
    }

    @Override
    public String getAccountDetails() {
        return String.format(
                "Checking Account Details:\n" +
                "  Account ID: %s\n" +
                "  Holder: %s\n" +
                "  Balance: $%.2f\n" +
                "  Interest Rate: %.2f%%\n" +
                "  Overdraft Limit: $%.2f\n" +
                "  Overdraft Used: $%.2f\n" +
                "  Available Balance: $%.2f",
                getAccountId(), getAccountHolder(), balance, getInterestRate() * 100,
                OVERDRAFT_LIMIT, overdraftUsed, getAvailableBalance()
        );
    }
}
