package com.meritamerica.assignment6.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

public abstract class Transaction {
	final float transferLimit = 1000;//The limit the user can interact with before sending to fraud team
	BankAccount sourceAccount;
	BankAccount targetAccount;
	double amount;
	Date date;
	/**
	 * 
	 * @return the source account for transaction
	 */
	public BankAccount getSourceAccount() {
		return sourceAccount;
	}
	/**
	 * 
	 * @param sAccunt sets the source account for transaction
	 */
	public void setSourceAccount(BankAccount sAccunt){
		this.sourceAccount = sAccunt;
	}
	/**
	 * 
	 * @return the target account for transaction
	 */
	public BankAccount getTargetAccount(){
		return targetAccount;}
	/**
	 * 
	 * @param tAccount sets the target Account for transaction
	 */
	public void setTargetAccount(BankAccount tAccount){
		this.targetAccount = tAccount;
	}
	/**
	 * 
	 * @return the amount of transaction
	 */
	public double getAmount(){
		return amount;
	}
	/**
	 * 
	 * @param amount sets the amount for transaction
	 */
	public void setAmount(double amount){
		this.amount = amount;
	}
	/**
	 * 
	 * @return returns the date the transaction happened
	 */
	public java.util.Date getTransactionDate(){
		return date;
	}
	/**
	 * 
	 * @param date sets the date the transaction happened
	 */
	public void setTransactionDate(java.util.Date date){
		this.date = date;
	}
	/**
	 * 
	 * @return retruns a string to record on a text file
	 */
	public String writeToString(){
		//2,4,5000,01/05/2020-text format to read from
		String data = getSourceAccount().getAccountNumber()+","+getTargetAccount().getAccountNumber()+","+getAmount()+","+getTransactionDate();
		return data;
	}
	/**
	 * 
	 * @param transactionDataString data to read and apply to transaction parametes
	 * @return returns the updated data
	 * @throws NumberFormatException throws number fomat exception in case of error
	 */
	public static Transaction readFromString(String transactionDataString) throws NumberFormatException{
		//2,4,5000,01/05/2020-text format to write from
		try {
			String[] temp = transactionDataString.split(",");
			Date date = new SimpleDateFormat("dd/MM/yyyy").parse(temp[3]);
			
			Transaction trans = new Transaction() { 
	        }; 
	        
	        Long sA = Long.parseLong(temp[0]);
	        BankAccount sAccount = MeritBank.getBankAccount(sA);
	        trans.setSourceAccount(sAccount);
	        Long tA = Long.parseLong(temp[1]);
	        BankAccount tAccount = MeritBank.getBankAccount(tA);
	        trans.setTargetAccount(tAccount);
	        Double amount = Double.parseDouble(temp[2]);
	        trans.setAmount(amount);
	        trans.setTransactionDate(date);
	        return trans;
		} catch (Exception e) {
			throw new NumberFormatException();
		}
	}
}
