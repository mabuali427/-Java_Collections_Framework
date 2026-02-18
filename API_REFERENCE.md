# API Reference

## Exception Classes

### InsufficientFundsException
```java
public class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message)
    public InsufficientFundsException(String message, Throwable cause)
}
```
**Usage**: Thrown when attempting to withdraw or transfer more than available balance.

**Example**:
```java
try {
    account.withdraw(5000); // balance is only 1000
} catch (InsufficientFundsException e) {
    System.out.println(e.getMessage());
}
```

---

### InvalidAmountException
```java
public class InvalidAmountException extends Exception {
    public InvalidAmountException(String message)
    public InvalidAmountException(String message, Throwable cause)
}
```
**Usage**: Thrown when transaction amount is invalid (≤ 0 or > 1,000,000).

**Example**:
```java
try {
    account.deposit(-100); // negative amount
} catch (InvalidAmountException e) {
    System.out.println(e.getMessage());
}
```

---

### AccountNotFoundException
```java
public class AccountNotFoundException extends Exception {
    public AccountNotFoundException(String message)
    public AccountNotFoundException(String message, Throwable cause)
}
```
**Usage**: Thrown when account lookup fails.

**Example**:
```java
try {
    Account account = customer.getAccount("invalid-id");
} catch (AccountNotFoundException e) {
    System.out.println(e.getMessage());
}
```

---

## Transaction Interface

```java
public interface Transaction {
    String getTransactionId()
    String getTransactionType()
    double getAmount()
    LocalDateTime getTimestamp()
    String getDescription()
    String getDetails()
}
```

### Methods

#### getTransactionId()
**Returns**: `String` - Unique transaction identifier (UUID)

#### getTransactionType()
**Returns**: `String` - Type of transaction:
- `DEPOSIT` - Money deposited
- `WITHDRAW` - Money withdrawn
- `TRANSFER_OUT` - Money transferred out
- `TRANSFER_IN` - Money transferred in
- `INITIAL_DEPOSIT` - Account opening

#### getAmount()
**Returns**: `double` - Transaction amount in dollars

#### getTimestamp()
**Returns**: `LocalDateTime` - Date and time of transaction

#### getDescription()
**Returns**: `String` - Human-readable description

#### getDetails()
**Returns**: `String` - Formatted transaction details

---

## TransactionRecord Class

```java
public class TransactionRecord implements Transaction
```

### Constructor
```java
public TransactionRecord(String transactionType, 
                        double amount, 
                        String description)
```

### Example Usage
```java
Transaction txn = new TransactionRecord("DEPOSIT", 500.0, "ATM Deposit");
System.out.println(txn.getDetails());
```

---

## Account (Abstract) Class

```java
public abstract class Account {
    // Methods
    public synchronized void deposit(double amount) 
        throws InvalidAmountException
    
    public synchronized void withdraw(double amount) 
        throws InvalidAmountException, InsufficientFundsException
    
    public synchronized void transfer(Account destination, double amount)
        throws InvalidAmountException, InsufficientFundsException
    
    public synchronized double getBalance()
    
    public String getAccountId()
    
    public String getAccountHolder()
    
    public String getAccountType()
    
    public double getInterestRate()
    
    public List<Transaction> getTransactionHistory()
    
    public abstract void applyInterest()
    
    public abstract String getAccountDetails()
}
```

### Methods

#### deposit(double amount)
**Parameters**: 
- `amount`: Must be > 0 and ≤ 1,000,000

**Throws**: `InvalidAmountException`

**Example**:
```java
account.deposit(500.00);
```

#### withdraw(double amount)
**Parameters**: 
- `amount`: Must be > 0 and ≤ 1,000,000

**Throws**: 
- `InvalidAmountException` - if amount invalid
- `InsufficientFundsException` - if balance < amount

**Example**:
```java
try {
    account.withdraw(250.00);
} catch (InvalidAmountException | InsufficientFundsException e) {
    e.printStackTrace();
}
```

#### transfer(Account destination, double amount)
**Parameters**: 
- `destination`: Target account
- `amount`: Transfer amount

**Throws**: 
- `InvalidAmountException` - if amount invalid
- `InsufficientFundsException` - if balance < amount

**Example**:
```java
account1.transfer(account2, 1000.00);
```

#### getBalance()
**Returns**: `double` - Current account balance

#### getTransactionHistory()
**Returns**: `List<Transaction>` - Immutable list of all transactions

#### getAccountDetails()
**Returns**: `String` - Formatted account information (abstract)

---

## SavingsAccount Class

```java
public class SavingsAccount extends Account {
    // Constructors
    public SavingsAccount(String accountHolder, double initialBalance)
    public SavingsAccount(String accountHolder, double initialBalance, 
                         double customInterestRate)
    
    // Additional Methods
    public boolean isMaintainingMinimumBalance()
    public double getMinimumBalance()
    public int getMaxMonthlyWithdrawals()
    public int getWithdrawalCount()
    public void incrementWithdrawalCount()
    public void resetWithdrawalCount()
    public void applyInterest()
    public String getAccountDetails()
}
```

### Constants
- `DEFAULT_INTEREST_RATE`: 4% (0.04)
- `MINIMUM_BALANCE`: $500.00
- `MAX_MONTHLY_WITHDRAWALS`: 6

### Example Usage
```java
SavingsAccount savings = new SavingsAccount("John Doe", 5000);
savings.deposit(1500);
savings.applyInterest(); // Applies monthly interest
System.out.println(savings.isMaintainingMinimumBalance()); // true
System.out.println(savings.getAccountDetails());
```

---

## CheckingAccount Class

```java
public class CheckingAccount extends Account {
    // Constructors
    public CheckingAccount(String accountHolder, double initialBalance)
    public CheckingAccount(String accountHolder, double initialBalance,
                          double customInterestRate)
    
    // Additional Methods
    public boolean canWithdrawWithOverdraft(double amount)
    public double getAvailableBalance()
    public double getOverdraftLimit()
    public double getOverdraftUsed()
    public double getRemainingOverdraft()
    public void applyInterest()
    public String getAccountDetails()
}
```

### Constants
- `DEFAULT_INTEREST_RATE`: 1% (0.01)
- `OVERDRAFT_LIMIT`: $500.00

### Example Usage
```java
CheckingAccount checking = new CheckingAccount("Jane Doe", 2000);
checking.withdraw(2200); // Uses $200 overdraft
System.out.println(checking.getOverdraftUsed()); // 200.0
System.out.println(checking.getAvailableBalance()); // 300.0 (500 - 200)
```

---

## Customer Class

```java
public class Customer {
    // Constructor
    public Customer(String name, String email, String phoneNumber)
    
    // Accessors
    public String getCustomerId()
    public String getName()
    public String getEmail()
    public String getPhoneNumber()
    public LocalDate getRegistrationDate()
    
    // Account Management
    public String addAccount(Account account)
    public Account getAccount(String accountId) 
        throws AccountNotFoundException
    public Map<String, Account> getAllAccounts()
    public int getAccountCount()
    public double getTotalBalance()
    public List<String> getAccountsByType(String accountType)
    public Account removeAccount(String accountId)
        throws AccountNotFoundException
    
    // Info Methods
    public String getSummary()
}
```

### Methods

#### addAccount(Account account)
**Parameters**: 
- `account`: Account to add

**Returns**: `String` - Account ID

**Throws**: `IllegalArgumentException` - if customer already has 10 accounts

**Example**:
```java
Customer customer = new Customer("John", "john@email.com", "123-456-7890");
SavingsAccount savings = new SavingsAccount("John", 1000);
String accountId = customer.addAccount(savings);
```

#### getAccount(String accountId)
**Parameters**: 
- `accountId`: Account identifier

**Returns**: `Account` - The requested account

**Throws**: `AccountNotFoundException` - if account not found

**Example**:
```java
Account account = customer.getAccount("account-id");
```

#### getAllAccounts()
**Returns**: `Map<String, Account>` - Unmodifiable map of all accounts

#### getAccountsByType(String accountType)
**Parameters**: 
- `accountType`: "SAVINGS" or "CHECKING"

**Returns**: `List<String>` - List of matching account IDs

#### getTotalBalance()
**Returns**: `double` - Sum of all account balances

#### removeAccount(String accountId)
**Parameters**: 
- `accountId`: Account to remove

**Returns**: `Account` - The removed account

**Throws**: `AccountNotFoundException` - if account not found

#### getSummary()
**Returns**: `String` - Formatted customer summary

---

## BankingSystem Class

```java
public class BankingSystem {
    // Constructor
    public BankingSystem()
    
    // Methods
    public Customer registerCustomer(String name, String email, 
                                    String phoneNumber)
    public Customer getCustomer(String customerId)
    public void displayAllCustomers()
    public void displayCustomerAccounts(Customer customer)
    public static void main(String[] args)
}
```

### Methods

#### registerCustomer(String name, String email, String phoneNumber)
**Parameters**: 
- `name`: Customer name
- `email`: Customer email
- `phoneNumber`: Customer phone

**Returns**: `Customer` - New customer instance

**Example**:
```java
BankingSystem bank = new BankingSystem();
Customer customer = bank.registerCustomer("John", "john@email.com", "123-456-7890");
```

#### getCustomer(String customerId)
**Parameters**: 
- `customerId`: Customer identifier

**Returns**: `Customer` - The requested customer

**Throws**: `IllegalArgumentException` - if customer not found

---

## Validation Rules

### Amount Validation
- Must be > 0
- Must be ≤ $1,000,000
- Decimal places: 2 (cents)

### Account Holder Validation
- Non-empty string
- Cannot be null

### Customer Details Validation
- **Name**: Non-empty string
- **Email**: Must contain "@"
- **Phone**: Non-empty string

### Account Limits
- **Per Customer**: Max 10 accounts
- **Savings Account**: Min balance $500
- **Checking Account**: Overdraft limit $500

---

## Common Usage Patterns

### Pattern 1: Basic Operations
```java
Customer customer = new Customer("John", "john@email.com", "123-456");
SavingsAccount savings = new SavingsAccount("John", 1000);
customer.addAccount(savings);

savings.deposit(500);
savings.withdraw(200);
System.out.println("Balance: $" + savings.getBalance());
```

### Pattern 2: Multiple Accounts
```java
SavingsAccount savings = new SavingsAccount("John", 5000);
CheckingAccount checking = new CheckingAccount("John", 2000);

customer.addAccount(savings);
customer.addAccount(checking);

System.out.println("Total: $" + customer.getTotalBalance());
```

### Pattern 3: Transfers
```java
try {
    savings.transfer(checking, 1000);
} catch (InvalidAmountException | InsufficientFundsException e) {
    System.out.println("Transfer failed: " + e.getMessage());
}
```

### Pattern 4: Transaction History
```java
for (Transaction txn : account.getTransactionHistory()) {
    System.out.println(txn.getDetails());
}
```

### Pattern 5: Interest Application
```java
for (Account account : customer.getAllAccounts().values()) {
    account.applyInterest();
}
```
