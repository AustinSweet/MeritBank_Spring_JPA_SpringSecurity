package com.meritamerica.assignment6.models;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@MappedSuperclass
public abstract class BankAccount {
	
	@NotNull
	@Id
	protected int iD;
	@NotNull
	long accountNumber = 0;
	@NotNull
	double balance = 0;
	@NotNull
	double INTEREST_RATE;

	@Transient
	Date date;
	@Transient
	CDOffering cdOffering;
	@Transient
	List<Transaction> transactions = new ArrayList<Transaction>();
	@Transient
	Set<String> transactionsHistory = new HashSet<String>();
	/**
	 * 
	 * @param accountNumber account ID
	 */
	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}
	/**
	 * 
	 * @param balance update balance upon deposit
	 */
	public void setBalance(double balance) {
		this.balance = balance;
	}
	/**
	 * 
	 * @param iNTEREST_RATE the rate of interest
	 */
	public void setINTEREST_RATE(double iNTEREST_RATE) {
		INTEREST_RATE = iNTEREST_RATE;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public BankAccount() {
	}
	
	/**
	 * 
	 * @param balance the balance on the account
	 */
	BankAccount(double balance){
		this.balance = balance;
	}
	/**
	 * 
	 * @param balancE account balance
	 * @param interestRate account interest rate
	 */
	BankAccount(double balancE, double interestRate){
		this.balance = balancE;
		this.INTEREST_RATE = interestRate;
	}
	/**
	 * 
	 * @param cd CDOffering
	 * @param balance balance of the cdOffering
	 */
	BankAccount(CDOffering cd, double balance){
		this.balance = balance;
		//this.cdOffering = cd;
	}
	/**
	 * 
	 * @param balance account balance
	 * @param interestRate account interest rate
	 * @param accountOpenedOn account starting date
	 */
	BankAccount(double balance, double interestRate, java.util.Date accountOpenedOn){}
	/**
	 * 
	 * @param accountNumber account ID
	 * @param balance account balance
	 * @param interestRate interest rate on the account
	 * @param accountOpenedOn date the account opened on
	 */
	BankAccount(long accountNumber, double balance, double interestRate, java.util.Date accountOpenedOn){}
	/**
	 * 
	 * @return the current account number
	 */
	public long getAccountNumber(){
		return accountNumber;}
	/**
	 * 
	 * @return current account balance
	 */
	public double getBalance(){
		return balance;}
	/**
	 * 
	 * @return current interest rate on the account
	 */
	public double getInterestRate(){
		return INTEREST_RATE;}
	/**
	 * 
	 * @return the date the account was created
	 */
	java.util.Date getOpenedOn(){
		return date;
	}
	/**
	 * 	
	 * @param amount  amount to withdraw from this SavingsAccount
	 * @return true if amount is more than balance and not a negative number
	 */	
	public boolean withdraw(double amount) {
		if(amount < balance && amount > 0) {
			balance -= amount;
			return true;
		}

		return false;
	}

	/**
	 * 	
	 * @param amount  to deposit into this SavingsAccount
	 * @return true if amount is not a negative number
	 */
	public boolean deposit(double amount){
		if(amount > 0 && amount <= 1000) {
			balance+=amount;
			return true;
		}else if(amount < 0){
			return false;
		}else {
			return false;
		}
	}

	/**
	 * 
	 * @param years the number of years
	 * @return the future value
	 */
	public double futureValue(int years){
		return balance*Math.pow(1 + INTEREST_RATE, years); 
	}
	/**
	 * 
	 * @param amount the amount to of money
	 * @param years the amount of years
	 * @param IR the interest rate for these years
	 * @return returns the future value
	 */
	public double futureValueInRecursive(double amount, int years, double IR) {
		if(years <= 1) {
			return 1;
		}else {
			return amount*(1+IR)*futureValueInRecursive(1, years--, IR);
		}
	}

	/**
	 * 
	 * @return data to write to string
	 * @throws java.lang.NumberFormatException throws number format exception
	 */
	String writeToString() throws java.lang.NumberFormatException{
		try {
			Format f = new SimpleDateFormat("MM/dd/yy");
			String date = f.format(getOpenedOn());
			String data = getAccountNumber()+","+getBalance()+","+getInterestRate()+","+date;
			return data;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 
	 * @param transaction adds the transaction to the list for record
	 */
	public void addTransaction(Transaction transaction){
		transactions.add(transaction);
	}
	/**
	 * 
	 * @return returns all the lists of transaction
	 */
	public List<Transaction> getTransactions(){
		return transactions;
	}
	/**
	 * 
	 * @return the number of transactions inside the list
	 */
	public int getTransactionStringSize() {
		return transactionsHistory.size();
	}
	/**
	 * 
	 * @return returns all transactions in a string
	 */
	public String getTransactionString() {
		ArrayList<String> temp = new ArrayList<>(transactionsHistory);
		String data = "";
		for(int i = 0; i<temp.size(); i++) {
			data += temp.get(i)+"\n";
		}
		return data;
		//return transactionsHistory.toString().replace("[","").replace("]","");
	}
	/**
	 * 
	 * @param data adds the data string to the list of transactions
	 */
	public void addTransactionString(String data) {
		transactionsHistory.add(data);
	}

}
