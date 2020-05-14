package com.meritamerica.assignment6.models;

import java.util.LinkedList;
import java.util.Queue;

public class TransferTransaction extends Transaction{
	BankAccount targetAccount;
	BankAccount sourceAccount;
	double amount;
	/**
	 * 
	 * @param sourceAccount the source account for this transaction
	 * @param targetAccount the target account for this transaction
	 * @param amount the amount for this transaction
	 */
	TransferTransaction(BankAccount sourceAccount, BankAccount targetAccount, double amount){
		this.targetAccount=targetAccount;
		this.sourceAccount=sourceAccount;
		this.amount = amount;
		if(amount > transferLimit) {
			//Reviewed by the fraud team
			FraudQueue.addTransaction(this);
		}else {
			sourceAccount.withdraw(amount);
			targetAccount.deposit(amount);
			targetAccount.addTransaction(this);//Records the transaction
		}
	}
}
