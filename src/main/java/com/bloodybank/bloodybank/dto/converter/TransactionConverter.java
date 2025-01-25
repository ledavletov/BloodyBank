package com.bloodybank.bloodybank.dto.converter;

import com.bloodybank.bloodybank.entity.Transaction;

public class TransactionConverter {
    public static String convert(Transaction t){
        String s = String.format("transaction N%d %s %s", t.getId(), t.getSender().getName(), t.getBloodType().getName());
        return s;
    }
}
