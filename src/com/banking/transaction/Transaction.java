package com.banking.transaction;

import java.time.LocalDateTime;

/**
 * Interface defining transaction operations.
 */
public interface Transaction {
    
    /**
     * Get the transaction ID.
     * @return the transaction ID
     */
    String getTransactionId();
    
    /**
     * Get the transaction type.
     * @return the transaction type (DEPOSIT, WITHDRAW, TRANSFER)
     */
    String getTransactionType();
    
    /**
     * Get the transaction amount.
     * @return the transaction amount
     */
    double getAmount();
    
    /**
     * Get the transaction date and time.
     * @return the transaction timestamp
     */
    LocalDateTime getTimestamp();
    
    /**
     * Get the description of the transaction.
     * @return transaction description
     */
    String getDescription();
    
    /**
     * Get the transaction details as a formatted string.
     * @return formatted transaction details
     */
    String getDetails();
}
