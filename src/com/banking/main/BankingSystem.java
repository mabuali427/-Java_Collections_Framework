package com.banking.main;

import com.banking.account.*;
import com.banking.exception.*;
import com.banking.model.Customer;
import com.banking.transaction.Transaction;

import java.util.*;

/**
 * BankingSystem class demonstrating OOP principles in a banking application.
 * Showcases:
 * - Inheritance (SavingsAccount, CheckingAccount extending Account)
 * - Polymorphism (Transaction interface implementation)
 * - Encapsulation (private fields with public accessors)
 * - Exception handling (custom exceptions)
 * - Collections (HashMap, ArrayList)
 */
public class BankingSystem {
    private final Map<String, Customer> customers;
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Constructor for BankingSystem.
     */
    public BankingSystem() {
        this.customers = new HashMap<>();
    }

    /**
     * Register a new customer.
     * @param name customer name
     * @param email customer email
     * @param phoneNumber customer phone
     * @return the created customer
     */
    public Customer registerCustomer(String name, String email, String phoneNumber) {
        Customer customer = new Customer(name, email, phoneNumber);
        customers.put(customer.getCustomerId(), customer);
        return customer;
    }

    /**
     * Get a customer by ID.
     * @param customerId the customer ID
     * @return the customer
     * @throws IllegalArgumentException if customer not found
     */
    public Customer getCustomer(String customerId) {
        Customer customer = customers.get(customerId);
        if (customer == null) {
            throw new IllegalArgumentException("Customer not found: " + customerId);
        }
        return customer;
    }

    /**
     * Display all customers and their accounts.
     */
    public void displayAllCustomers() {
        if (customers.isEmpty()) {
            System.out.println("No customers registered.");
            return;
        }

        System.out.println("\n========== ALL CUSTOMERS ==========");
        for (Customer customer : customers.values()) {
            System.out.println(customer.getSummary());
            displayCustomerAccounts(customer);
            System.out.println();
        }
    }

    /**
     * Display accounts of a specific customer.
     * @param customer the customer
     */
    public void displayCustomerAccounts(Customer customer) {
        if (customer.getAccountCount() == 0) {
            System.out.println("  No accounts.");
            return;
        }

        System.out.println("  Accounts:");
        for (Account account : customer.getAllAccounts().values()) {
            System.out.println("    - " + account.getAccountType() + ": $" + String.format("%.2f", account.getBalance()));
        }
    }

    /**
     * Demonstrate the banking system with sample operations.
     */
    public static void main(String[] args) {
        BankingSystem bank = new BankingSystem();

        System.out.println("╔════════════════════════════════════════════════════╗");
        System.out.println("║     BANKING SYSTEM - OOP DEMONSTRATION             ║");
        System.out.println("╚════════════════════════════════════════════════════╝\n");

        // Register customers
        System.out.println(">>> Registering Customers <<<\n");
        Customer customer1 = bank.registerCustomer("John Doe", "john@example.com", "123-456-7890");
        System.out.println("✓ Registered: " + customer1.getName());

        Customer customer2 = bank.registerCustomer("Jane Smith", "jane@example.com", "098-765-4321");
        System.out.println("✓ Registered: " + customer2.getName());

        // Create accounts
        System.out.println("\n>>> Creating Accounts <<<\n");

        SavingsAccount savingsAccount1 = new SavingsAccount("John Doe", 5000);
        String acc1Id = customer1.addAccount(savingsAccount1);
        System.out.println("✓ Created Savings Account for John: $5000");

        CheckingAccount checkingAccount1 = new CheckingAccount("John Doe", 2000);
        String acc2Id = customer1.addAccount(checkingAccount1);
        System.out.println("✓ Created Checking Account for John: $2000");

        SavingsAccount savingsAccount2 = new SavingsAccount("Jane Smith", 8000, 0.05);
        String acc3Id = customer2.addAccount(savingsAccount2);
        System.out.println("✓ Created Savings Account for Jane: $8000");

        CheckingAccount checkingAccount2 = new CheckingAccount("Jane Smith", 3000);
        String acc4Id = customer2.addAccount(checkingAccount2);
        System.out.println("✓ Created Checking Account for Jane: $3000");

        // Demonstrate deposit operation
        System.out.println("\n>>> Deposit Operations <<<\n");
        try {
            savingsAccount1.deposit(1500);
            System.out.println("✓ John deposited $1500 to Savings Account");
            System.out.println("  New balance: $" + String.format("%.2f", savingsAccount1.getBalance()));
        } catch (InvalidAmountException e) {
            System.out.println("✗ Deposit failed: " + e.getMessage());
        }

        // Demonstrate withdraw operation
        System.out.println("\n>>> Withdraw Operations <<<\n");
        try {
            checkingAccount1.withdraw(500);
            System.out.println("✓ John withdrew $500 from Checking Account");
            System.out.println("  New balance: $" + String.format("%.2f", checkingAccount1.getBalance()));
        } catch (InvalidAmountException | InsufficientFundsException e) {
            System.out.println("✗ Withdrawal failed: " + e.getMessage());
        }

        // Demonstrate failed withdrawal
        System.out.println("\n>>> Attempting Invalid Withdrawal <<<\n");
        try {
            savingsAccount2.withdraw(10000);
            System.out.println("✓ Withdrawal successful");
        } catch (InsufficientFundsException e) {
            System.out.println("✗ Withdrawal failed: " + e.getMessage());
        } catch (InvalidAmountException e) {
            System.out.println("✗ Invalid amount: " + e.getMessage());
        }

        // Demonstrate transfer operation
        System.out.println("\n>>> Transfer Operations <<<\n");
        try {
            savingsAccount1.transfer(checkingAccount2, 2000);
            System.out.println("✓ John transferred $2000 from Savings to Jane's Checking Account");
            System.out.println("  John's Savings new balance: $" + String.format("%.2f", savingsAccount1.getBalance()));
            System.out.println("  Jane's Checking new balance: $" + String.format("%.2f", checkingAccount2.getBalance()));
        } catch (InvalidAmountException | InsufficientFundsException e) {
            System.out.println("✗ Transfer failed: " + e.getMessage());
        }

        // Display account details
        System.out.println("\n>>> Account Details <<<\n");
        System.out.println(savingsAccount1.getAccountDetails());
        System.out.println("\n" + checkingAccount1.getAccountDetails());
        System.out.println("\n" + savingsAccount2.getAccountDetails());
        System.out.println("\n" + checkingAccount2.getAccountDetails());

        // Display transaction history
        System.out.println("\n>>> Transaction History (John's Savings Account) <<<\n");
        List<Transaction> transactions = savingsAccount1.getTransactionHistory();
        if (transactions.isEmpty()) {
            System.out.println("No transactions.");
        } else {
            for (Transaction txn : transactions) {
                System.out.println(txn.getDetails());
            }
        }

        // Display all customers
        bank.displayAllCustomers();

        // Demonstrate interest calculations
        System.out.println("\n>>> Apply Monthly Interest <<<\n");
        savingsAccount1.applyInterest();
        System.out.println("✓ Applied interest to John's Savings Account");
        System.out.println("  New balance: $" + String.format("%.2f", savingsAccount1.getBalance()));

        checkingAccount1.applyInterest();
        System.out.println("✓ Applied interest to John's Checking Account");
        System.out.println("  New balance: $" + String.format("%.2f", checkingAccount1.getBalance()));

        // Display final summary
        System.out.println("\n>>> Final Customer Summary <<<\n");
        System.out.println(customer1.getSummary());
        System.out.println("\n" + customer2.getSummary());

        // Demonstrate exception handling with invalid amount
        System.out.println("\n>>> Testing Invalid Amount Exception <<<\n");
        try {
            savingsAccount1.deposit(-500);
        } catch (InvalidAmountException e) {
            System.out.println("✗ Exception caught: " + e.getMessage());
        }

        // Demonstrate account lookup by type
        System.out.println("\n>>> Lookup Accounts by Type (Customer: John) <<<\n");
        List<String> savingsAccounts = customer1.getAccountsByType("SAVINGS");
        List<String> checkingAccounts = customer1.getAccountsByType("CHECKING");
        System.out.println("Savings Accounts: " + savingsAccounts.size());
        System.out.println("Checking Accounts: " + checkingAccounts.size());

        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║         BANKING SYSTEM DEMONSTRATION COMPLETE       ║");
        System.out.println("╚════════════════════════════════════════════════════╝");
    }
}
