# Banking System - Class Hierarchy and Design

## Class Diagram

```
┌─────────────────────────────────────────────────────┐
│               <<interface>>                         │
│               Transaction                           │
├─────────────────────────────────────────────────────┤
│ + getTransactionId(): String                       │
│ + getTransactionType(): String                     │
│ + getAmount(): double                              │
│ + getTimestamp(): LocalDateTime                    │
│ + getDescription(): String                         │
│ + getDetails(): String                             │
└─────────────────────────────────────────────────────┘
        △
        │ implements
        │
┌─────────────────────────────────────────────────────┐
│           TransactionRecord                         │
├─────────────────────────────────────────────────────┤
│ - transactionId: String                            │
│ - transactionType: String                          │
│ - amount: double                                   │
│ - timestamp: LocalDateTime                         │
│ - description: String                              │
├─────────────────────────────────────────────────────┤
│ + TransactionRecord(type, amount, description)    │
│ + getTransactionId(): String                       │
│ + getTransactionType(): String                     │
│ + getAmount(): double                              │
│ + getTimestamp(): LocalDateTime                    │
│ + getDescription(): String                         │
│ + getDetails(): String                             │
└─────────────────────────────────────────────────────┘


┌─────────────────────────────────────────────────────┐
│            <<abstract>>                             │
│               Account                              │
├─────────────────────────────────────────────────────┤
│ # accountId: String                                │
│ # accountHolder: String                            │
│ # balance: double                                  │
│ # interestRate: double                             │
│ # transactionHistory: List<Transaction>            │
├─────────────────────────────────────────────────────┤
│ + deposit(double): void                            │
│ + withdraw(double): void                           │
│ + transfer(Account, double): void                  │
│ + getBalance(): double                             │
│ + getTransactionHistory(): List<Transaction>       │
│ <<abstract>> + applyInterest(): void               │
│ <<abstract>> + getAccountDetails(): String         │
└─────────────────────────────────────────────────────┘
        △                        △
        │ extends              │ extends
        │                      │
   ┌────────────────┐    ┌────────────────┐
   │ SavingsAccount │    │ CheckingAccount│
   ├────────────────┤    ├────────────────┤
   │ - withdrawals: │    │ - overdraft:   │
   │   int          │    │   double       │
   │ - minBalance:  │    │ - overdraft    │
   │   double       │    │   Used: double │
   ├────────────────┤    ├────────────────┤
   │ + applyInt()   │    │ + applyInt()   │
   │ + maintain():  │    │ + withdraw()   │
   │   boolean      │    │ + getAvailable│
   └────────────────┘    └────────────────┘


┌─────────────────────────────────────────────────────┐
│              Customer                              │
├─────────────────────────────────────────────────────┤
│ - customerId: String                               │
│ - name: String                                     │
│ - email: String                                    │
│ - phoneNumber: String                              │
│ - accounts: Map<String, Account>                   │
│ - registrationDate: LocalDate                      │
├─────────────────────────────────────────────────────┤
│ + Customer(name, email, phone)                     │
│ + addAccount(Account): String                      │
│ + getAccount(accountId): Account                   │
│ + getAllAccounts(): Map<String, Account>           │
│ + getTotalBalance(): double                        │
│ + getAccountsByType(type): List<String>            │
│ + removeAccount(accountId): Account                │
│ + getSummary(): String                             │
└─────────────────────────────────────────────────────┘
        △
        │ has
        │
        └─── uses ──→ Account (composition)


┌─────────────────────────────────────────────────────┐
│            BankingSystem                            │
├─────────────────────────────────────────────────────┤
│ - customers: Map<String, Customer>                 │
├─────────────────────────────────────────────────────┤
│ + registerCustomer(name, email, phone): Customer   │
│ + getCustomer(customerId): Customer                │
│ + displayAllCustomers(): void                      │
│ + displayCustomerAccounts(Customer): void          │
│ + main(String[]): void                             │
└─────────────────────────────────────────────────────┘
        △
        │ uses
        │
        └─── Customer → Account


┌──────────────────────────────────────────────────────┐
│         Exception Hierarchy                         │
├──────────────────────────────────────────────────────┤
│              Exception                              │
│                  △                                  │
│                  │ extends                         │
│                  │                                  │
│   ┌──────────────┼──────────────┐                  │
│   │              │              │                  │
│   ▽              ▽              ▽                  │
│┌─────┐  ┌──────────┐  ┌──────────┐               │
││ Ins │  │ Invalid  │  │ Account  │               │
││ uff │  │ Amount   │  │ NotFound │               │
│└─────┘  └──────────┘  └──────────┘               │
│ FundsE  Exception     Exception                  │
│ xception                                         │
└──────────────────────────────────────────────────────┘
```

## Component Relationships

### 1. Inheritance Hierarchy
- `SavingsAccount` IS-A `Account`
- `CheckingAccount` IS-A `Account`
- All custom exceptions extend `Exception`

### 2. Composition/Aggregation
- `Customer` HAS-MANY `Account` (composition)
- `Account` HAS-MANY `Transaction` (composition)

### 3. Implementation
- `TransactionRecord` IMPLEMENTS `Transaction`
- `SavingsAccount` and `CheckingAccount` IMPLEMENT abstract methods

## Object Creation Flow

```
1. BankingSystem created
   └── Manages Map<String, Customer>

2. registerCustomer() called
   └── Creates Customer
       └── Creates empty Map<String, Account>

3. New Account created
   └── SavingsAccount or CheckingAccount
       └── Creates List<Transaction>
           └── Adds initial transaction record

4. customer.addAccount() called
   └── Account added to customer's map

5. Operations (deposit, withdraw, transfer)
   └── Transaction recorded in account's transaction history
```

## Data Flow

### Deposit Operation
```
User Input: deposit(500)
    ↓
Account.deposit(500)
    ↓
Validate amount (> 0, ≤ 1,000,000)
    ↓
Update balance
    ↓
Create TransactionRecord("DEPOSIT", 500, ...)
    ↓
Add to transactionHistory List
    ↓
Return success
```

### Withdraw Operation
```
User Input: withdraw(300)
    ↓
Account.withdraw(300)
    ↓
Validate amount
    ↓
Check: balance >= amount ?
    ├─ No → throw InsufficientFundsException
    ├─ Yes ↓
Update balance
    ↓
Create TransactionRecord("WITHDRAW", 300, ...)
    ↓
Add to transactionHistory List
    ↓
Return success
```

### Transfer Operation
```
User Input: source.transfer(destination, 1000)
    ↓
Validate amount
    ↓
Check: source.balance >= 1000 ?
    ├─ No → throw InsufficientFundsException
    ├─ Yes ↓
Deduct from source: balance -= 1000
    ↓
Record source transaction: TransactionRecord("TRANSFER_OUT", ...)
    ↓
Add to source.transactionHistory
    ↓
Add to destination: balance += 1000
    ↓
Record destination transaction: TransactionRecord("TRANSFER_IN", ...)
    ↓
Add to destination.transactionHistory
    ↓
Return success
```

## Method Call Sequence - Example

```
main()
├─ BankingSystem bank = new BankingSystem()
├─ Customer customer = bank.registerCustomer(...)
├─ SavingsAccount savings = new SavingsAccount(...)
├─ customer.addAccount(savings)
├─ savings.deposit(1500)
│  ├─ validateAmount(1500)
│  ├─ balance += 1500
│  └─ transactionHistory.add(TransactionRecord(...))
├─ savings.withdraw(500)
│  ├─ validateAmount(500)
│  ├─ check balance >= 500
│  ├─ balance -= 500
│  └─ transactionHistory.add(TransactionRecord(...))
├─ savings.transfer(checking, 1000)
│  ├─ validateAmount(1000)
│  ├─ savings.balance -= 1000
│  ├─ savings.transactionHistory.add(TRANSFER_OUT)
│  ├─ checking.balance += 1000
│  └─ checking.transactionHistory.add(TRANSFER_IN)
├─ savings.applyInterest()
│  └─ balance += (balance * rate / 12)
└─ System.out.println(savings.getAccountDetails())
```

## Key Design Principles

### Single Responsibility Principle (SRP)
- `Account`: Account operations
- `Customer`: Customer management
- `Transaction`: Transaction recording
- `BankingSystem`: System orchestration

### Open/Closed Principle (OCP)
- `Account` is open for extension (subclasses)
- Closed for modification (new account types don't change Account)

### Liskov Substitution Principle (LSP)
- `SavingsAccount` and `CheckingAccount` can be used anywhere `Account` is expected
- Same interface, different behavior

### Interface Segregation Principle (ISP)
- `Transaction` interface has minimal required methods
- Clients implement only what they need

### Dependency Inversion Principle (DIP)
- `Customer` depends on abstract `Account`, not concrete implementations
- Can add new account types without changing `Customer`

## Thread Safety

The following methods are synchronized:
- `Account.deposit()`
- `Account.withdraw()`
- `Account.transfer()`
- `Account.getBalance()`

This ensures thread-safe concurrent operations in multi-threaded environments.
