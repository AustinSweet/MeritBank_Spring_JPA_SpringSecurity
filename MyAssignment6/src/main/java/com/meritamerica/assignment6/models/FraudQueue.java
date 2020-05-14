package com.meritamerica.assignment6.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class FraudQueue {
	static Queue<Transaction> transactionQueue = new LinkedList<>();
	static Set<String> fraudHistory = new HashSet<String>();
	public FraudQueue() {}
	/**
	 * 
	 * @return the total number of fraud cases
	 */
	public int getFraudNum() {
		return fraudHistory.size();
	}
	/**
	 * 
	 * @param data adds data to the current fraud cases
	 */
	public static void addFraudHistory(String data) {
		fraudHistory.add(data);
	}
	/**
	 * 
	 * @return each fraud data in a string for record
	 */
	public String getTransactionString() {
		ArrayList<String> temp = new ArrayList<>(fraudHistory);
		String data = "";
		for(int i = 0; i<temp.size(); i++) {
			data += temp.get(i)+"\n";
		}
		return data;
	}
	/**
	 * 
	 * @param transaction converts transaction to string then adds it to the fraud cases
	 */
	public static void addTransaction(Transaction transaction) {
		String data = transaction.sourceAccount.getAccountNumber()+","+transaction.targetAccount.getAccountNumber()+","+
				transaction.getAmount()+","+transaction.getTransactionDate();
		addFraudHistory(data);
		transactionQueue.add(transaction);//Also adds to linkedlist for a back up
		
	}
	/**
	 * 
	 * @return all transactions from transfer, deposit, and withdraw transaction
	 */
	public static Transaction getTransaction() {
		ArrayList<Transaction> temp = new ArrayList<>(transactionQueue);//Copies the transactionQueue into arraylist
		return (Transaction) Arrays.asList(temp);
	}
}
