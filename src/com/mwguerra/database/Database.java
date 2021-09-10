package com.mwguerra.database;

import com.mwguerra.models.CheckingAccount;
import com.mwguerra.models.History;
import com.mwguerra.models.PaycheckAccount;
import com.mwguerra.models.SavingsAccount;

import java.util.ArrayList;
import java.util.List;

public class Database {
  public List<CheckingAccount> checkingAccounts = new ArrayList<>();
  public List<PaycheckAccount> paycheckAccounts = new ArrayList<>();
  public List<SavingsAccount> savingsAccounts = new ArrayList<>();
  public List<History> history = new ArrayList<>();

  // TABLE STRUCTURE
  // account
    // owner
    // number
    // type
  // checking_account
    // account_id
    // balance
    // overdraft
  // paycheck_account
    // account_id
    // balance
    // max_withdraw
  // savings_account
    // account_id
    // balance
    // interest_rate
  // history
    // description
    // date
    // account_id
    // operation
    // amount
}
