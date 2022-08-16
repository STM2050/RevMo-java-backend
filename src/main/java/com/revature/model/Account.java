package com.revature.model;

import java.util.Objects;

public class Account {

    private int accountId;
    private int typeId;
    private long balance;

    public Account(int accountId, int typeId, long balance) {
        this.accountId = accountId;
        this.typeId = typeId;
        this.balance = balance;
    }

    public Account() {

    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return accountId == account.accountId && typeId == account.typeId && balance == account.balance;
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, typeId, balance);
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", typeId=" + typeId +
                ", balance=" + balance +
                '}';
    }
}
