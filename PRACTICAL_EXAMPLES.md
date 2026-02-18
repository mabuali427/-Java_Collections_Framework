# Banking System - Practical Examples & Test Cases

## Example 1: Simple Account Operations

```java
import com.banking.account.*;
import com.banking.exception.*;

public class Example1_BasicOperations {
    public static void main(String[] args) {
        // Create a savings account
        SavingsAccount savings = new SavingsAccount("Alice Johnson", 10000);
        
        System.out.println("Initial Balance: $" + savings.getBalance());
        
        // Deposit money
        try {
            savings.deposit(2500);
            System.out.println("After deposit: $" + savings.getBalance());
        } catch (InvalidAmountException e) {
            System.out.println("Deposit failed: " + e.getMessage());
        }
        
        // Withdraw money
        try {
            savings.withdraw(1000);
            System.out.println("After withdrawal: $" + savings.getBalance());
        } catch (InvalidAmountException | InsufficientFundsException e) {
            System.out.println("Withdrawal error: " + e.getMessage());
        }
        
        // Apply interest
        savings.applyInterest();
        System.out.println("After interest: $" + savings.getBalance());
        
        System.out.println("\n" + savings.getAccountDetails());
    }
}
```

**Output**:
```
Initial Balance: $10000.00
After deposit: $12500.00
After withdrawal: $11500.00
After interest: $11538.33
```

---

## Example 2: Multiple Accounts & Customer Management

```java
import com.banking.account.*;
import com.banking.model.*;

public class Example2_CustomerManagement {
    public static void main(String[] args) {
        // Create customer
        Customer customer = new Customer(
            "Bob Wilson", 
            "bob@example.com", 
            "555-1234"
        );
        
        // Create multiple accounts
        SavingsAccount savings = new SavingsAccount("Bob Wilson", 20000);
        CheckingAccount checking = new CheckingAccount("Bob Wilson", 5000);
        
        // Add to customer
        String savingsId = customer.addAccount(savings);
        String checkingId = customer.addAccount(checking);
        
        // Display customer info
        System.out.println(customer.getSummary());
        System.out.println("\nTotal Balance: $" + customer.getTotalBalance());
        
        // Get accounts by type
        System.out.println("\nSavings accounts: " + 
            customer.getAccountsByType("SAVINGS"));
        System.out.println("Checking accounts: " + 
            customer.getAccountsByType("CHECKING"));
    }
}
```

**Output**:
```
Customer Summary:
  ID: 550e8400-e29b-41d4-a716-446655440000
  Name: Bob Wilson
  Email: bob@example.com
  Phone: 555-1234
  Number of Accounts: 2
  Total Balance: $25000.00

Total Balance: $25000.00

Savings accounts: [550e8400-e29b-41d4-a716-446655440001]
Checking accounts: [550e8400-e29b-41d4-a716-446655440002]
```

---

## Example 3: Inter-Account Transfers

```java
import com.banking.account.*;
import com.banking.exception.*;

public class Example3_Transfers {
    public static void main(String[] args) {
        SavingsAccount savings = new SavingsAccount("Carol", 10000);
        CheckingAccount checking = new CheckingAccount("Carol", 2000);
        
        System.out.println("Before Transfer:");
        System.out.println("Savings: $" + savings.getBalance());
        System.out.println("Checking: $" + checking.getBalance());
        
        // Transfer from savings to checking
        try {
            savings.transfer(checking, 3000);
            System.out.println("\nTransfer successful!");
        } catch (InvalidAmountException | InsufficientFundsException e) {
            System.out.println("Transfer failed: " + e.getMessage());
        }
        
        System.out.println("\nAfter Transfer:");
        System.out.println("Savings: $" + savings.getBalance());
        System.out.println("Checking: $" + checking.getBalance());
        
        // View both transaction histories
        System.out.println("\nSavings Transactions:");
        savings.getTransactionHistory().forEach(t -> 
            System.out.println("  " + t.getDetails())
        );
        
        System.out.println("\nChecking Transactions:");
        checking.getTransactionHistory().forEach(t -> 
            System.out.println("  " + t.getDetails())
        );
    }
}
```

**Output**:
```
Before Transfer:
Savings: $10000.00
Checking: $2000.00

Transfer successful!

After Transfer:
Savings: $7000.00
Checking: $5000.00

Savings Transactions:
  [txn-id-1] timestamp - Type: INITIAL_DEPOSIT, Amount: $10000.00, Description: Account opening deposit
  [txn-id-2] timestamp - Type: TRANSFER_OUT, Amount: $3000.00, Description: Transfer to Carol

Checking Transactions:
  [txn-id-3] timestamp - Type: INITIAL_DEPOSIT, Amount: $2000.00, Description: Account opening deposit
  [txn-id-4] timestamp - Type: TRANSFER_IN, Amount: $3000.00, Description: Transfer from Carol
```

---

## Example 4: Error Handling

```java
import com.banking.account.*;
import com.banking.exception.*;

public class Example4_ErrorHandling {
    public static void main(String[] args) {
        SavingsAccount account = new SavingsAccount("David", 1000);
        
        // Test 1: Negative amount
        System.out.println("Test 1: Negative Amount");
        try {
            account.deposit(-500);
        } catch (InvalidAmountException e) {
            System.out.println("✓ Caught: " + e.getMessage());
        }
        
        // Test 2: Amount too large
        System.out.println("\nTest 2: Amount Too Large");
        try {
            account.deposit(2000000);
        } catch (InvalidAmountException e) {
            System.out.println("✓ Caught: " + e.getMessage());
        }
        
        // Test 3: Insufficient funds
        System.out.println("\nTest 3: Insufficient Funds");
        try {
            account.withdraw(2000);
        } catch (InsufficientFundsException e) {
            System.out.println("✓ Caught: " + e.getMessage());
        } catch (InvalidAmountException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        // Test 4: Zero amount
        System.out.println("\nTest 4: Zero Amount");
        try {
            account.withdraw(0);
        } catch (InvalidAmountException e) {
            System.out.println("✓ Caught: " + e.getMessage());
        } catch (InsufficientFundsException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
```

**Output**:
```
Test 1: Negative Amount
✓ Caught: Transaction amount must be greater than 0

Test 2: Amount Too Large
✓ Caught: Transaction amount cannot exceed $1,000,000

Test 3: Insufficient Funds
✓ Caught: Insufficient funds. Current balance: $1000.00, Requested amount: $2000.00

Test 4: Zero Amount
✓ Caught: Transaction amount must be greater than 0
```

---

## Example 5: Savings Account Features

```java
import com.banking.account.*;
import com.banking.exception.*;

public class Example5_SavingsFeatures {
    public static void main(String[] args) {
        SavingsAccount account = new SavingsAccount("Eve", 5000, 0.05);
        
        System.out.println("Savings Account Features:");
        System.out.println("Initial Balance: $" + account.getBalance());
        System.out.println("Minimum Balance: $" + account.getMinimumBalance());
        System.out.println("Interest Rate: " + (account.getInterestRate() * 100) + "%");
        System.out.println("Max monthly withdrawals: " + account.getMaxMonthlyWithdrawals());
        
        // Make withdrawals
        try {
            for (int i = 1; i <= 3; i++) {
                account.withdraw(100);
                account.incrementWithdrawalCount();
                System.out.println("Withdrawal " + i + ": Count = " + 
                    account.getWithdrawalCount());
            }
        } catch (InvalidAmountException | InsufficientFundsException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        // Check minimum balance
        System.out.println("\nMaintaining minimum balance? " + 
            account.isMaintainingMinimumBalance());
        
        // Apply interest
        System.out.println("\nBalance before interest: $" + account.getBalance());
        account.applyInterest();
        System.out.println("Balance after interest: $" + account.getBalance());
        
        // Reset monthly count
        account.resetWithdrawalCount();
        System.out.println("Withdrawal count reset: " + account.getWithdrawalCount());
    }
}
```

**Output**:
```
Savings Account Features:
Initial Balance: $5000.00
Minimum Balance: $500.00
Interest Rate: 5.0%
Max monthly withdrawals: 6

Withdrawal 1: Count = 1
Withdrawal 2: Count = 2
Withdrawal 3: Count = 3

Maintaining minimum balance? true

Balance before interest: $4700.00
Balance after interest: $4719.58

Withdrawal count reset: 0
```

---

## Example 6: Checking Account with Overdraft

```java
import com.banking.account.*;
import com.banking.exception.*;

public class Example6_CheckingOverdraft {
    public static void main(String[] args) {
        CheckingAccount account = new CheckingAccount("Frank", 1000);
        
        System.out.println("Checking Account with Overdraft:");
        System.out.println("Initial Balance: $" + account.getBalance());
        System.out.println("Overdraft Limit: $" + account.getOverdraftLimit());
        System.out.println("Available Balance: $" + account.getAvailableBalance());
        
        // Attempt withdrawal exceeding balance but within overdraft
        try {
            System.out.println("\nAttempting to withdraw $1200...");
            account.withdraw(1200);
            System.out.println("✓ Withdrawal successful (using overdraft)");
        } catch (InsufficientFundsException | InvalidAmountException e) {
            System.out.println("✗ " + e.getMessage());
        }
        
        System.out.println("\nAfter Withdrawal:");
        System.out.println("Balance: $" + account.getBalance());
        System.out.println("Overdraft Used: $" + account.getOverdraftUsed());
        System.out.println("Remaining Overdraft: $" + account.getRemainingOverdraft());
        System.out.println("Available Balance: $" + account.getAvailableBalance());
        
        // Apply interest with overdraft fee
        System.out.println("\nApplying interest with overdraft fee...");
        double balanceBefore = account.getBalance();
        account.applyInterest();
        double balanceAfter = account.getBalance();
        System.out.println("Balance before: $" + balanceBefore);
        System.out.println("Balance after: $" + balanceAfter);
        System.out.println("Fee charged: $" + (balanceBefore - balanceAfter));
    }
}
```

**Output**:
```
Checking Account with Overdraft:
Initial Balance: $1000.00
Overdraft Limit: $500.00
Available Balance: $1500.00

Attempting to withdraw $1200...
✓ Withdrawal successful (using overdraft)

After Withdrawal:
Balance: $-200.00
Overdraft Used: $200.00
Remaining Overdraft: $300.00
Available Balance: $300.00

Applying interest with overdraft fee...
Balance before: $-200.00
Balance after: $-210.00
Fee charged: $10.00
```

---

## Example 7: Batch Operation Verification

```java
import com.banking.account.*;
import com.banking.exception.*;
import com.banking.model.*;

public class Example7_BatchOperations {
    public static void main(String[] args) {
        Customer customer = new Customer("Grace", "grace@example.com", "555-5678");
        
        // Create multiple accounts
        Account[] accounts = {
            new SavingsAccount("Grace", 5000),
            new SavingsAccount("Grace", 3000),
            new CheckingAccount("Grace", 2000)
        };
        
        String[] ids = new String[accounts.length];
        for (int i = 0; i < accounts.length; i++) {
            ids[i] = customer.addAccount(accounts[i]);
        }
        
        System.out.println("Created " + accounts.length + " accounts\n");
        
        // Perform operations on all accounts
        System.out.println("Performing deposits on all accounts...");
        for (Account account : accounts) {
            try {
                account.deposit(1000);
            } catch (InvalidAmountException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        
        // Apply interest to all
        System.out.println("\nApplying interest to all accounts...");
        for (Account account : accounts) {
            account.applyInterest();
        }
        
        // Display summary
        System.out.println("\nAccount Summary:");
        for (Account account : accounts) {
            System.out.println(account.getAccountType() + ": $" + 
                String.format("%.2f", account.getBalance()));
        }
        
        System.out.println("\nCustomer Total: $" + 
            String.format("%.2f", customer.getTotalBalance()));
    }
}
```

---

## Example 8: Transaction History Analysis

```java
import com.banking.account.*;
import com.banking.exception.*;
import com.banking.transaction.*;

public class Example8_TransactionAnalysis {
    public static void main(String[] args) {
        SavingsAccount account = new SavingsAccount("Henry", 5000);
        
        // Perform multiple operations
        try {
            account.deposit(2000);
            account.withdraw(500);
            account.withdraw(300);
            account.deposit(1000);
        } catch (InvalidAmountException | InsufficientFundsException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        // Analyze transaction history
        System.out.println("Transaction Analysis:\n");
        double totalDeposits = 0;
        double totalWithdrawals = 0;
        
        for (Transaction txn : account.getTransactionHistory()) {
            System.out.println(txn.getDetails());
            
            String type = txn.getTransactionType();
            if (type.contains("DEPOSIT")) {
                totalDeposits += txn.getAmount();
            } else if (type.contains("WITHDRAW")) {
                totalWithdrawals += txn.getAmount();
            }
        }
        
        System.out.println("\nSummary:");
        System.out.println("Total Deposits: $" + String.format("%.2f", totalDeposits));
        System.out.println("Total Withdrawals: $" + String.format("%.2f", totalWithdrawals));
        System.out.println("Net: $" + String.format("%.2f", totalDeposits - totalWithdrawals));
        System.out.println("Final Balance: $" + String.format("%.2f", account.getBalance()));
    }
}
```

---

## Example 9: Input Validation Testing

```java
import com.banking.model.*;

public class Example9_ValidationTesting {
    public static void main(String[] args) {
        System.out.println("Testing Customer Creation Validation:\n");
        
        // Test 1: Valid input
        System.out.println("Test 1: Valid Customer");
        try {
            Customer customer = new Customer("Isaac", "isaac@email.com", "555-9999");
            System.out.println("✓ Customer created successfully");
        } catch (IllegalArgumentException e) {
            System.out.println("✗ " + e.getMessage());
        }
        
        // Test 2: Null name
        System.out.println("\nTest 2: Null Name");
        try {
            Customer customer = new Customer(null, "jacob@email.com", "555-0000");
            System.out.println("✗ Should have thrown exception");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Caught: " + e.getMessage());
        }
        
        // Test 3: Invalid email (no @)
        System.out.println("\nTest 3: Invalid Email");
        try {
            Customer customer = new Customer("Kate", "kate.email.com", "555-0001");
            System.out.println("✗ Should have thrown exception");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Caught: " + e.getMessage());
        }
        
        // Test 4: Empty phone
        System.out.println("\nTest 4: Empty Phone");
        try {
            Customer customer = new Customer("Leo", "leo@email.com", "");
            System.out.println("✗ Should have thrown exception");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Caught: " + e.getMessage());
        }
    }
}
```

---

## Example 10: Complete Full Scenario

```java
import com.banking.main.*;
import com.banking.account.*;
import com.banking.model.*;

public class Example10_FullScenario {
    public static void main(String[] args) {
        // Initialize banking system
        BankingSystem bank = new BankingSystem();
        
        // Register customers
        Customer john = bank.registerCustomer("John Smith", "john@bank.com", "123-456");
        Customer jane = bank.registerCustomer("Jane Doe", "jane@bank.com", "654-321");
        
        // Create accounts
        SavingsAccount johnSavings = new SavingsAccount("John Smith", 10000);
        CheckingAccount johnChecking = new CheckingAccount("John Smith", 3000);
        SavingsAccount janeSavings = new SavingsAccount("Jane Doe", 15000);
        CheckingAccount janeChecking = new CheckingAccount("Jane Doe", 5000);
        
        // Add accounts to customers
        john.addAccount(johnSavings);
        john.addAccount(johnChecking);
        jane.addAccount(janeSavings);
        jane.addAccount(janeChecking);
        
        System.out.println("===== Banking System Simulation =====\n");
        
        // Simulate business transactions
        performTransaction(johnSavings, 2500, "deposit", "Monthly deposit");
        performTransaction(johnChecking, 1000, "withdraw", "ATM withdrawal");
        performTransaction(johnSavings, janeSavings, 5000, 
            "John's savings to Jane's checking (transfer)");
        
        // Month-end operations
        System.out.println("\n===== Month-End Interest Calculation =====\n");
        applyMonthlyInterest(john);
        applyMonthlyInterest(jane);
        
        // Final reports
        System.out.println("\n===== Final Reports =====\n");
        System.out.println(john.getSummary());
        System.out.println("\n" + jane.getSummary());
    }
    
    static void performTransaction(Account account, double amount, 
                                   String type, String description) {
        try {
            if (type.equals("deposit")) {
                account.deposit(amount);
                System.out.println("✓ " + description + ": +" + amount);
            } else if (type.equals("withdraw")) {
                account.withdraw(amount);
                System.out.println("✓ " + description + ": -" + amount);
            }
        } catch (Exception e) {
            System.out.println("✗ Transaction failed: " + e.getMessage());
        }
    }
    
    static void performTransaction(Account source, Account destination, 
                                   double amount, String description) {
        try {
            source.transfer(destination, amount);
            System.out.println("✓ " + description + ": " + amount);
        } catch (Exception e) {
            System.out.println("✗ Transfer failed: " + e.getMessage());
        }
    }
    
    static void applyMonthlyInterest(Customer customer) {
        for (Account account : customer.getAllAccounts().values()) {
            double balanceBefore = account.getBalance();
            account.applyInterest();
            double interest = account.getBalance() - balanceBefore;
            System.out.println(account.getAccountType() + " - Interest earned: $" + 
                String.format("%.2f", interest));
        }
    }
}
```

---

## Running these Examples

To run these examples:

1. **Copy example code** into a new `.java` file in the same package
2. **Compile**: `javac -cp bin ExampleName.java`
3. **Run**: `java -cp .:bin ExampleName`

Or add to your IDE and run as a Java Application.

## Tips for Testing

- Use try-catch blocks for all exception-throwing methods
- Test edge cases (zero, negative, very large amounts)
- Verify transaction history after operations
- Check balance consistency between related operations
- Test validation rules thoroughly
- Use multiple accounts for transfer testing
