package com.bloodybank.bloodybank.dto.converter;

import com.bloodybank.bloodybank.entity.Transaction;

public class TransactionConverter {
    public static String convert(Transaction t){
        return String.format("transaction N%d %s %s", t.getId(), t.getSender().getName(), t.getBloodType().getName());
        //formatted string contains transaction's id, donater's name and blood type
    }
}
