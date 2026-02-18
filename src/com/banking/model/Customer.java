package com.banking.model;

import com.banking.account.Account;
import com.banking.exception.AccountNotFoundException;

import java.util.*;

/**
 * Customer class representing a bank customer with multiple accounts.
 * Demonstrates encapsulation and collection usage.
 */
public class Customer {
    private final String customerId;
    private final String name;
    private final String email;
    private final String phoneNumber;
    private final Map<String, Account> accounts;
    private final LocalDate registrationDate;

    /**
     * Constructor for Customer.
     * @param name the customer name
     * @param email the customer email
     * @param phoneNumber the customer phone number
     */
    public Customer(String name, String email, String phoneNumber) {
        validateCustomerDetails(name, email, phoneNumber);
        this.customerId = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.accounts = new HashMap<>();
        this.registrationDate = LocalDate.now();
    }

    /**
     * Validate customer details.
     * @param name the customer name
     * @param email the customer email
     * @param phoneNumber the customer phone number
     * @throws IllegalArgumentException if any detail is invalid
     */
    private void validateCustomerDetails(String name, String email, String phoneNumber) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be null or empty");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email address");
        }
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be null or empty");
        }
    }

    /**
     * Get customer ID.
     * @return customer ID
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * Get customer name.
     * @return customer name
     */
    public String getName() {
        return name;
    }

    /**
     * Get customer email.
     * @return customer email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Get customer phone number.
     * @return phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Get registration date.
     * @return registration date
     */
    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    /**
     * Add an account to the customer.
     * @param account the account to add
     * @return the account ID
     * @throws IllegalArgumentException if account limit exceeded
     */
    public String addAccount(Account account) {
        if (accounts.size() >= 10) {
            throw new IllegalArgumentException("Customer can have a maximum of 10 accounts");
        }
        String accountId = account.getAccountId();
        accounts.put(accountId, account);
        return accountId;
    }

    /**
     * Get an account by account ID.
     * @param accountId the account ID
     * @return the account
     * @throws AccountNotFoundException if account not found
     */
    public Account getAccount(String accountId) throws AccountNotFoundException {
        Account account = accounts.get(accountId);
        if (account == null) {
            throw new AccountNotFoundException("Account with ID " + accountId + " not found");
        }
        return account;
    }

    /**
     * Get all accounts of the customer.
     * @return unmodifiable map of accounts
     */
    public Map<String, Account> getAllAccounts() {
        return Collections.unmodifiableMap(accounts);
    }

    /**
     * Get the number of accounts.
     * @return number of accounts
     */
    public int getAccountCount() {
        return accounts.size();
    }

    /**
     * Get total balance across all accounts.
     * @return total balance
     */
    public double getTotalBalance() {
        return accounts.values().stream()
                .mapToDouble(Account::getBalance)
                .sum();
    }

    /**
     * Get account IDs for accounts of a specific type.
     * @param accountType the type of account (SAVINGS, CHECKING)
     * @return list of account IDs
     */
    public List<String> getAccountsByType(String accountType) {
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, Account> entry : accounts.entrySet()) {
            if (entry.getValue().getAccountType().equalsIgnoreCase(accountType)) {
                result.add(entry.getKey());
            }
        }
        return result;
    }

    /**
     * Remove an account.
     * @param accountId the account ID to remove
     * @return the removed account
     * @throws AccountNotFoundException if account not found
     */
    public Account removeAccount(String accountId) throws AccountNotFoundException {
        Account removed = accounts.remove(accountId);
        if (removed == null) {
            throw new AccountNotFoundException("Account with ID " + accountId + " not found");
        }
        return removed;
    }

    /**
     * Get customer summary.
     * @return customer summary string
     */
    public String getSummary() {
        return String.format(
                "Customer Summary:\n" +
                "  ID: %s\n" +
                "  Name: %s\n" +
                "  Email: %s\n" +
                "  Phone: %s\n" +
                "  Registration Date: %s\n" +
                "  Number of Accounts: %d\n" +
                "  Total Balance: $%.2f",
                customerId, name, email, phoneNumber, registrationDate, accounts.size(), getTotalBalance()
        );
    }

    @Override
    public String toString() {
        return getSummary();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Customer)) return false;
        Customer customer = (Customer) obj;
        return customerId.equals(customer.customerId);
    }

    @Override
    public int hashCode() {
        return customerId.hashCode();
    }
}

/**
 * LocalDate utility class since we need a simple date representation.
 */
class LocalDate {
    private final int year;
    private final int month;
    private final int day;

    private LocalDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public static LocalDate now() {
        Calendar cal = Calendar.getInstance();
        return new LocalDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public String toString() {
        return String.format("%04d-%02d-%02d", year, month, day);
    }
}
