package com.mwguerra;

import com.mwguerra.database.Database;
import com.mwguerra.database.DatabaseSeeder;
import com.mwguerra.models.*;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

public class Main {
  private static final Database db = new Database();
  private static CheckingAccount checkingAccount;
  private static PaycheckAccount paycheckAccount;
  private static SavingsAccount savingsAccount;

  public static void main(String[] args) {
    databaseSeeder();

    int option;

    do {
      option = mainMenu();

      switch (option) {
        case 1:
          withdraw();
          break;
        case 2:
          deposit();
          break;
        case 3:
          statement();
          break;
        case 4:
          balance();
          break;
        case 0:
        default:
          break;
      }
    } while (option != 0);
  }

  public static int mainMenu() {
    System.out.println("");
    System.out.println("----------------------------------------");
    System.out.println("-- OMEGA BANK --------------------------");
    System.out.println("----------------------------------------");
    System.out.println("-- AÇÕES PARA O CPF 123.456.789-00");
    System.out.println("-- 1. Saque");
    System.out.println("-- 2. Depósito");
    System.out.println("-- 3. Extrato");
    System.out.println("-- 4. Saldo");
    System.out.println("-- 0. Sair do sistema");
    System.out.println("--");
    System.out.print("-- Digite a opção desejada: ");

    Scanner scanner = new Scanner(System.in);
    return scanner.nextInt();
  }

  private static void databaseSeeder() {
    DatabaseSeeder seeder = new DatabaseSeeder().setDatabase(db);
    Account[] accounts = seeder.handle();
    checkingAccount = (CheckingAccount) accounts[0];
    paycheckAccount = (PaycheckAccount) accounts[1];
    savingsAccount = (SavingsAccount) accounts[2];
  }

  private static void balance() {
    System.out.println("");
    System.out.println("----------------------------------------");
    System.out.println("-- Conta Salário -----------------------");
    System.out.println("");
    System.out.println("Saldo: " + paycheckAccount.getBalance());
    System.out.println("");
    System.out.println("-- Conta Corrente ----------------------");
    System.out.println("");
    System.out.println("Saldo: " + checkingAccount.getBalance());
    System.out.println("");
    System.out.println("-- Conta Poupança ----------------------");
    System.out.println("");
    System.out.println("Saldo: " + savingsAccount.getBalance());
    System.out.println("");
  }

  private static void statement() {

    System.out.println("");
    System.out.println("----------------------------------------");
    System.out.println("-- Conta Salário -----------------------");
    System.out.println("");
    System.out.println("Saldo inicial: " + paycheckAccount.getInitialBalance());

    List<History> historyPaycheck = History.all(db, paycheckAccount.getAccountNumber());
    for (History item: historyPaycheck) {
      System.out.println(item.toString());
    }

    System.out.println("Saldo: " + paycheckAccount.getBalance());
    System.out.println("");

    System.out.println("-- Conta Corrente ----------------------");
    System.out.println("");
    System.out.println("Saldo inicial: " + checkingAccount.getInitialBalance());

    List<History> historyChecking = History.all(db, checkingAccount.getAccountNumber());
    for (History item: historyChecking) {
      System.out.println(item.toString());
    }

    System.out.println("Cheque especial: " + checkingAccount.getOverdraft());
    System.out.println("Saldo: " + checkingAccount.getBalance());
    System.out.println("");

    System.out.println("-- Conta Poupança ----------------------");
    System.out.println("");
    System.out.println("Saldo inicial: " + savingsAccount.getInitialBalance());

    List<History> historySavings = History.all(db, savingsAccount.getAccountNumber());
    for (History item: historySavings) {
      System.out.println(item.toString());
    }

    System.out.println("Saldo: " + savingsAccount.getBalance());
    System.out.println("");
  }

  private static void deposit() {
    Double amount = amountPrompt("Valor");
    String description = "Depósito em espécie";
    Account account = selectAccountPrompt("Destino");

    new History(account.getAccountNumber(), description, new GregorianCalendar(), OperationType.DEPOSIT, amount)
      .setDatabase(db)
      .save();
    account.deposit(amount);
  }

  private static Double amountPrompt(String title) {
    System.out.print("-- " + title + ": ");
    Scanner scanner = new Scanner(System.in);
    return scanner.nextDouble();
  }

  private static Account selectAccountPrompt(String title) {
    do {
      System.out.println("-- " + title.toUpperCase() + " -----------------------------");
      System.out.println("-- 1. Conta Salário");
      System.out.println("-- 2. Conta Corrente");
      System.out.println("-- 3. Conta Poupança");
      System.out.print("-- Selecione a conta: ");

      Scanner scanner = new Scanner(System.in);
      int option = scanner.nextInt();

      switch (option) {
        case 1:
          return paycheckAccount;
        case 2:
          return checkingAccount;
        case 3:
          return savingsAccount;
      }
    } while(true);
  }

  private static void withdraw() {
    Account account = selectAccountPrompt("Origem");
    String description = "Saque em espécie";
    System.out.println("Saldo disponível: " + account.getBalance());
    Double amount = amountPrompt("Valor para saque");

    if(!account.withdraw(amount)) {
      System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
      System.out.println(">> ALERTA: Operação não realizada. Saldo insuficiente.");
      System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
      return;
    }

    new History(account.getAccountNumber(), description, new GregorianCalendar(), OperationType.WITHDRAW, amount)
      .setDatabase(db)
      .save();
  }
}
