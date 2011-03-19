package com.cogito.bukkit.steph;

import java.math.BigDecimal;

import org.bukkit.Material;

import com.cogito.bukkit.bob.Account;
import com.cogito.bukkit.bob.Transaction;

public class SalesTransaction extends Transaction {

    public final Material item_type;
    public final int quantity;

    public SalesTransaction(BigDecimal amount, Account seller, Account buyer, Material item_type, int quantity) {
        super(amount, seller, buyer, quantity+" "+item_type);
        this.item_type = item_type;
        this.quantity = quantity;
    }

}
