package com.meritamerica.assignment6.models;

public class WithdrawTransaction extends Transaction{

	BankAccount targetAccount;
	double amount;
	/**
	 * 
	 * @param targetAccount the target account for this transaction
	 * @param amount the amount for ths transaction
	 */
	WithdrawTransaction(BankAccount targetAccount, double amount){
		this.targetAccount=targetAccount;
		this.amount = amount;
		if(amount > transferLimit) {
			//Reviewed by the fraud team
			FraudQueue.addTransaction(this);
		}else {
			targetAccount.withdraw(amount);
			targetAccount.addTransaction(this);//Records the transaction
		}
	}
}
