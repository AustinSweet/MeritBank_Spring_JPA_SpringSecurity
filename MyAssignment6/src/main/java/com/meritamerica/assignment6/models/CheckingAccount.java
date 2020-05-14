package com.meritamerica.assignment6.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class CheckingAccount extends BankAccount{
	@NotNull
	protected static int nextId = 1;

	public CheckingAccount() {
		this.iD = nextId++;
		this.balance = 0;
	}
	
	/**
	 * 
	 * @param balance the current balance
	 * @param IR the current Interest Rate
	 */
	public CheckingAccount(double balance) {
		//super(balance);
		// TODO Auto-generated constructor stub
		this.balance = balance;
	}
	
	
	CheckingAccount(double balance, double IR) {
		super(balance);
		// TODO Auto-generated constructor stub
		this.balance = balance;
		this.INTEREST_RATE = IR;
	}
	CheckingAccount(long accNumm, double balance, double IR, Date date) {
		super(balance);
		// TODO Auto-generated constructor stub
		this.accountNumber = accNumm;
		this.balance = balance;
		this.INTEREST_RATE = IR;
		this.setDate(date);
	}
	/**
	 * 
	 * @return String containing details of this CheckingAccount	
	 */
	public String toString() {
		String toString = 
		"Checking Account Balance: $" + getBalance() + "\n" + 
		"Checking Account Interest Rate: " + INTEREST_RATE + "\n" + 
		"Checking Account Balance in 3 years: $" + futureValue(3);
		return toString;
	}
	
	/**
	 * 
	 * @param accountData refers to save data on text file
	 * @return returns the updated class
	 */
	public static CheckingAccount readFromString(String accountData)throws ParseException{
		//1,900,0.0001,01/01/2020
		try {
			String[] temp = accountData.split(",");
			Date date = new SimpleDateFormat("dd/MM/yyyy").parse(temp[3]);
			
			CheckingAccount newAccount = new CheckingAccount(Long.valueOf(temp[0]),Double.valueOf(temp[1]),Double.valueOf(temp[2]),date);
			return newAccount;
			/*Another way of doing the code
			int accountNum = Integer.parseInt(accountData.split(",")[0]);
			double accountOpeningBalance = Double.parseDouble(accountData.split(",")[1]);
			double accountIR = Double.parseDouble(accountData.split(",")[2]);
			String openingDate = accountData.split(",")[3];
			
			CheckingAccount newAccount = new CheckingAccount(accountOpeningBalance, accountIR);
			newAccount.setAccountNumber(accountNum);
			newAccount.setINTEREST_RATE(accountIR);
			
			SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
			Date myDefaultDate = format.parse(openingDate);
			newAccount.setDate(myDefaultDate);
			return newAccount;*/
		} catch (Exception e) {
			throw new NumberFormatException();
		}
	}
}