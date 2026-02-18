# Banking System - OOP Demonstration

A comprehensive Java banking system showcasing Object-Oriented Programming principles including inheritance, polymorphism, encapsulation, and proper exception handling.

## Project Structure

```
src/com/banking/
├── main/
│   └── BankingSystem.java          # Main demonstration class
├── account/
│   ├── Account.java                # Abstract base class for all accounts
│   ├── SavingsAccount.java         # Savings account implementation
│   └── CheckingAccount.java        # Checking account implementation
├── model/
│   └── Customer.java               # Customer class with multiple accounts
├── transaction/
│   ├── Transaction.java            # Transaction interface
│   └── TransactionRecord.java      # Transaction implementation
└── exception/
    ├── InsufficientFundsException.java
    ├── InvalidAmountException.java
    └── AccountNotFoundException.java
```

## OOP Principles Demonstrated

### 1. **Inheritance**
- `SavingsAccount` and `CheckingAccount` extend the abstract `Account` class
- Shared functionality (deposit, withdraw, transfer) implemented in base class
- Specialized behavior in subclasses (interest calculation, overdraft protection)

### 2. **Polymorphism**
- `Transaction` interface with `TransactionRecord` implementation
- Abstract methods `applyInterest()` and `getAccountDetails()` implemented differently in each account type
- Runtime polymorphism with `List<Transaction>` and `List<Account>`

### 3. **Encapsulation**
- Private fields with public getter methods
- Protected methods for subclass access (e.g., `validateAmount()`)
- Synchronized methods for thread-safe operations
- Immutable views of collections returned to clients

### 4. **Abstraction**
- `Account` abstract class hides implementation details
- `Transaction` interface defines contract without implementation
- Custom exceptions abstract error handling

## Class Details

### Account (Abstract)
**Purpose**: Base class for all account types

**Key Features**:
- Unique account ID (UUID)
- Balance tracking
- Transaction history (List)
- Abstract methods: `applyInterest()`, `getAccountDetails()`
- Synchronized methods for thread safety
- Validation for amounts and inputs

**Methods**:
- `deposit(double amount)` - Add funds to account
- `withdraw(double amount)` - Remove funds from account
- `transfer(Account dest, double amount)` - Transfer between accounts
- `getBalance()` - Get current balance
- `getTransactionHistory()` - Get immutable transaction list

### SavingsAccount
**Extends**: Account

**Features**:
- Default 4% annual interest rate
- Minimum balance requirement ($500)
- Monthly withdrawal limits (max 6)
- Higher interest compared to checking

**Key Methods**:
- `applyInterest()` - Apply monthly interest
- `isMaintainingMinimumBalance()` - Check minimum balance
- Custom interest calculation

### CheckingAccount
**Extends**: Account

**Features**:
- Lower 1% annual interest rate
- Overdraft protection (up to $500)
- Unlimited transactions
- Overdraft fee calculation

**Key Methods**:
- `applyInterest()` - Include overdraft fee calculation
- `getAvailableBalance()` - Balance including overdraft
- `canWithdrawWithOverdraft(double amount)`

### Customer
**Purpose**: Represents a bank customer

**Key Features**:
- Unique customer ID
- Multiple accounts (HashMap for O(1) lookup)
- Account type filtering
- Total balance calculation
- Validation of customer details

**Collections Used**:
- `HashMap<String, Account>` - Fast account lookup
- `ArrayList<String>` - List of account IDs by type

### Transaction Interface & TransactionRecord
**Purpose**: Record and display transaction details

**Features**:
- Unique transaction ID (UUID)
- Transaction type (DEPOSIT, WITHDRAW, TRANSFER_OUT, TRANSFER_IN)
- Timestamp tracking
- Formatted output

## Exception Handling

### Custom Exceptions
1. **InsufficientFundsException** - Raised when withdrawal exceeds balance
2. **InvalidAmountException** - Raised for invalid transaction amounts
3. **AccountNotFoundException** - Raised when account lookup fails

All exceptions include detailed error messages and support exception chaining.

## Usage Examples

### Create Customer with Accounts
```java
BankingSystem bank = new BankingSystem();

// Register customer
Customer customer = bank.registerCustomer("John Doe", "john@email.com", "123-456-7890");

// Create accounts
SavingsAccount savings = new SavingsAccount("John Doe", 5000);
CheckingAccount checking = new CheckingAccount("John Doe", 2000);

// Add to customer
customer.addAccount(savings);
customer.addAccount(checking);
```

### Deposit Money
```java
try {
    savings.deposit(1500);
    System.out.println("Deposit successful");
} catch (InvalidAmountException e) {
    System.out.println("Error: " + e.getMessage());
}
```

### Withdraw Money
```java
try {
    checking.withdraw(500);
} catch (InvalidAmountException e) {
    System.out.println("Invalid amount");
} catch (InsufficientFundsException e) {
    System.out.println("Insufficient funds");
}
```

### Transfer Between Accounts
```java
try {
    savings.transfer(checking, 1000);
    System.out.println("Transfer successful");
} catch (InvalidAmountException | InsufficientFundsException e) {
    System.out.println("Transfer failed: " + e.getMessage());
}
```

### Apply Interest
```java
savings.applyInterest();  // Monthly interest calculation
checking.applyInterest(); // With overdraft fee if applicable
```

### View Transaction History
```java
List<Transaction> history = account.getTransactionHistory();
for (Transaction txn : history) {
    System.out.println(txn.getDetails());
}
```

## Compilation and Execution

### Compile
```bash
javac -d bin src/com/banking/**/*.java
```

### Run
```bash
java -cp bin com.banking.main.BankingSystem
```

## Features Highlighted

✅ **Abstract Classes** - `Account` provides template for subclasses
✅ **Inheritance** - Specialized account types inherit from `Account`
✅ **Polymorphism** - Different behavior for `applyInterest()` in each account type
✅ **Interfaces** - `Transaction` defines contract for transaction records
✅ **Encapsulation** - Private fields with controlled access
✅ **Collections** - HashMap for customer accounts, ArrayList for transaction history
✅ **Exception Handling** - Custom exceptions with detailed messages
✅ **Thread Safety** - Synchronized methods for concurrent access
✅ **UUID Usage** - Unique IDs for accounts, customers, and transactions
✅ **Validation** - Input validation in constructors and methods

## Sample Output

```
╔════════════════════════════════════════════════════╗
║     BANKING SYSTEM - OOP DEMONSTRATION             ║
╚════════════════════════════════════════════════════╝

>>> Registering Customers <<<

✓ Registered: John Doe
✓ Registered: Jane Smith

>>> Creating Accounts <<<

✓ Created Savings Account for John: $5000
✓ Created Checking Account for John: $2000
✓ Created Savings Account for Jane: $8000
✓ Created Checking Account for Jane: $3000

>>> Deposit Operations <<<

✓ John deposited $1500 to Savings Account
  New balance: $6500.00
  
... [More operations] ...

╔════════════════════════════════════════════════════╗
║     BANKING SYSTEM DEMONSTRATION COMPLETE          ║
╚════════════════════════════════════════════════════╝
```

## Design Patterns Used

1. **Template Method Pattern** - `Account` class defines the template for account operations
2. **Strategy Pattern** - Different interest calculation strategies in `SavingsAccount` and `CheckingAccount`
3. **Singleton Pattern (potential)** - `BankingSystem` can be extended to singleton
4. **Factory Pattern (potential)** - Can create factory for account creation

## Validation Rules

- Account holder name: Non-empty string
- Initial balance: Non-negative
- Transaction amount: 0 < amount ≤ 1,000,000
- Customer name: Non-empty
- Email: Must contain "@"
- Phone number: Non-empty

## Account Limits

- **Savings Account**: Minimum balance $500, max 6 withdrawals/month
- **Checking Account**: Overdraft protection up to $500
- **Customer**: Maximum 10 accounts per customer

## Future Enhancements

- Database integration (JDBC/JPA)
- GUI implementation (Swing/JavaFX)
- Multi-threading for concurrent transactions
- Interest calculation scheduler
- Transaction audit trail
- Loan and credit card accounts
- Bill payment system
- Mobile/Web API

## Author Notes

This project demonstrates enterprise-level OOP principles suitable for real banking applications. The use of collections, proper encapsulation, and exception handling makes the code production-ready.
