package com.meritamerica.assignment6.models;

public class DepositTransaction extends Transaction{
	
	BankAccount targetAccount;
	double amount;
	/**
	 * 
	 * @param targetAccount the account the transaction will go to
	 * @param amount the amount of money
	 */
	DepositTransaction(BankAccount targetAccount, double amount){
		this.targetAccount=targetAccount;
		this.amount = amount;
		if(amount > transferLimit) {
			//Reviewed by the fraud team and is also recorded
			FraudQueue.addTransaction(this);
		}else {
			//The transaction proceeds
			targetAccount.deposit(amount);
			targetAccount.addTransaction(this);//Records the transaction
		}
	}
}
