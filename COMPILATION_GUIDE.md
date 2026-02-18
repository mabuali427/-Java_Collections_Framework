# Compilation and Execution Guide

## Prerequisites
- Java Development Kit (JDK) 8 or higher
- Terminal or Command Prompt
- Basic understanding of Java

## Directory Structure
```
Java_Collections_Framework/
├── src/
│   └── com/
│       └── banking/
│           ├── main/
│           │   └── BankingSystem.java
│           ├── account/
│           │   ├── Account.java
│           │   ├── SavingsAccount.java
│           │   └── CheckingAccount.java
│           ├── model/
│           │   └── Customer.java
│           ├── transaction/
│           │   ├── Transaction.java
│           │   └── TransactionRecord.java
│           └── exception/
│               ├── InsufficientFundsException.java
│               ├── InvalidAmountException.java
│               └── AccountNotFoundException.java
└── bin/
    └── (compiled .class files will be here)
```

## Step-by-Step Compilation

### Windows (Command Prompt)
```bash
cd Java_Collections_Framework
mkdir bin
javac -d bin src\com\banking\exception\*.java
javac -d bin src\com\banking\transaction\*.java
javac -d bin src\com\banking\account\*.java
javac -d bin src\com\banking\model\*.java
javac -d bin src\com\banking\main\*.java
```

### Linux/Mac (Terminal)
```bash
cd Java_Collections_Framework
mkdir -p bin
javac -d bin src/com/banking/exception/*.java
javac -d bin src/com/banking/transaction/*.java
javac -d bin src/com/banking/account/*.java
javac -d bin src/com/banking/model/*.java
javac -d bin src/com/banking/main/*.java
```

### Alternative: Single Command Compilation
```bash
# Windows
javac -d bin src\com\banking\**\*.java

# Linux/Mac
javac -d bin $(find src -name "*.java")
```

## Execution

### Windows (Command Prompt)
```bash
java -cp bin com.banking.main.BankingSystem
```

### Linux/Mac (Terminal)
```bash
java -cp bin com.banking.main.BankingSystem
```

## Compilation Output
After compilation, you should see files in the `bin` directory:
```
bin/
└── com/
    └── banking/
        ├── main/
        │   └── BankingSystem.class
        ├── account/
        │   ├── Account.class
        │   ├── SavingsAccount.class
        │   └── CheckingAccount.class
        ├── model/
        │   ├── Customer.class
        │   └── LocalDate.class
        ├── transaction/
        │   ├── Transaction.class
        │   └── TransactionRecord.class
        └── exception/
            ├── InsufficientFundsException.class
            ├── InvalidAmountException.class
            └── AccountNotFoundException.class
```

## Troubleshooting

### Issue: "javac: command not found"
**Solution**: Java is not installed or not in PATH
- Install JDK from: https://www.oracle.com/java/technologies/downloads/
- Add Java bin directory to system PATH

### Issue: "cannot find symbol"
**Solution**: Ensure all source files are compiled in correct order
- Compile dependency files first (exception, transaction, account, model, main)

### Issue: "error: class X is public, should be declared in a file named X.java"
**Solution**: Rename file to match public class name

### Issue: "Exception in thread 'main' java.lang.NoClassDefFoundError"
**Solution**: 
- Verify -cp (classpath) points to bin directory
- Check all files compiled successfully

## IDE Setup

### VS Code
1. Install "Extension Pack for Java" by Microsoft
2. Open folder containing `src` directory
3. Right-click on `BankingSystem.java` → Run
   Or press Ctrl+F5 to run

### Eclipse
1. Create new Java Project
2. Import source files into src directory
3. Right-click on `BankingSystem.java` → Run As → Java Application

### IntelliJ IDEA
1. Create new Java project
2. Add `src` as source root
3. Right-click on `BankingSystem.java` → Run

## Expected Program Output
```
╔════════════════════════════════════════════════════╗
║     BANKING SYSTEM - OOP DEMONSTRATION             ║
╚════════════════════════════════════════════════════╝

>>> Registering Customers <<<

✓ Registered: John Doe
✓ Registered: Jane Smith
...
```

## Performance Notes
- Compilation time: < 2 seconds typically
- Execution time: < 1 second for demo
- Memory usage: < 50 MB

## Java Version Compatibility
- **Minimum**: Java 8
- **Recommended**: Java 11+
- **Latest**: Java 21

All features used are compatible with Java 8+

## Cleanup
To remove compiled files:
```bash
# Windows
rmdir /s /q bin

# Linux/Mac
rm -rf bin
```
