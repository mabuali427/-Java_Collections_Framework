package com.banking.transaction;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * Implementation of Transaction interface to record transaction details.
 */
public class TransactionRecord implements Transaction {
    private final String transactionId;
    private final String transactionType;
    private final double amount;
    private final LocalDateTime timestamp;
    private final String description;

    /**
     * Constructor for TransactionRecord.
     * @param transactionType the type of transaction
     * @param amount the transaction amount
     * @param description the transaction description
     */
    public TransactionRecord(String transactionType, double amount, String description) {
        this.transactionId = UUID.randomUUID().toString();
        this.transactionType = transactionType;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
        this.description = description;
    }

    @Override
    public String getTransactionId() {
        return transactionId;
    }

    @Override
    public String getTransactionType() {
        return transactionType;
    }

    @Override
    public double getAmount() {
        return amount;
    }

    @Override
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getDetails() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("[%s] %s - Type: %s, Amount: $%.2f, Description: %s",
                transactionId, timestamp.format(formatter), transactionType, amount, description);
    }

    @Override
    public String toString() {
        return getDetails();
    }
}
