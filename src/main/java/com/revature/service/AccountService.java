package com.revature.service;

import com.revature.dao.AccountDao;
import com.revature.exception.InvalidParameterException;
import com.revature.model.Account;


import java.util.Map;

public class AccountService {
    private AccountDao accountDao;

    public AccountService() {
        accountDao = new AccountDao();
    }

    public Account openAccount(Map<String, String> newAccount) throws InvalidParameterException {
        Account account = new Account();
        InvalidParameterException exceptions = new InvalidParameterException();
        String typeId = newAccount.get("typeId");
        if (typeId == null) {
            exceptions.addMessage("Must have an account type");
        } else {
            account.setTypeId(Integer.parseInt(typeId));
        }
        String balance = newAccount.get("balance");
        if (balance == null) {
            exceptions.addMessage("Account balance must not be null");
        } else {
            try {
                long accountBalance = Long.parseLong(balance);
                if (accountBalance < 0) {
                    exceptions.addMessage("Account balance must be positive.");
                }
                account.setBalance(accountBalance);
            } catch (NumberFormatException e) {
                exceptions.addMessage("Account balance " + balance + " is invalid. Please enter a valid numeric amount");
            }

        }

        if (exceptions.containsMessage()) {
            throw exceptions;
        }

        return accountDao.openAccount(account);
    }
}
