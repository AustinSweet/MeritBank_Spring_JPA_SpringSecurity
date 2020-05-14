package com.meritamerica.assignment6.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class SavingsAccount  extends BankAccount{
	@NotNull
	protected static int nextId = 1;
	
	public SavingsAccount() {
		this.iD = nextId++;
		this.balance = 0;
	}
	
	/**
	 * 
	 * @param balance the balance for the saving account
	 */
	public SavingsAccount(double balance) {
		//super(balance);
		// TODO Auto-generated constructor stub
		this.balance = balance;
		this.INTEREST_RATE = 0.01;
	}
	
/**
 * 
 * @return String of details for this SavingsAcccount	
 */
	public String toString() {
		String toString = 
		"Savings Account Balance: $" + balance + "\n" + 
		"Savings Account Interest Rate: " + INTEREST_RATE + "\n" + 
		"Savings Account Balance in 3 years: $" + futureValue(3);
		return toString;
	}
	
	/**
	 * 
	 * @param accountData refers to the save data
	 * @return returns the updated account
	 */
	public static SavingsAccount readFromString(String accountData) {
		SavingsAccount newAccount = new SavingsAccount(Double.parseDouble(accountData.split(",")[1]));
		//83
		newAccount.setAccountNumber(Long.parseLong(accountData.split(",")[0]));
		//10000
		//0.02
		newAccount.setINTEREST_RATE(Double.parseDouble(accountData.split(",")[2]));
		//01/02/2020
		String dateString = accountData.split(",")[3];
		Date date;
		try {
			date = new SimpleDateFormat("dd/MM/yyyy").parse(dateString);
			newAccount.setDate(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newAccount;
	}
}