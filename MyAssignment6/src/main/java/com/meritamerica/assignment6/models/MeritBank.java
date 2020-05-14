package com.meritamerica.assignment6.models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MeritBank {

	private static AccountHolder AccountHolders[] = new AccountHolder[0];
	private static CDOffering CDOfferings[] = new CDOffering[0];
	private static long nextAccountNumber;
	static FraudQueue fraudQueue;

	/**
	 * 
	 * @param long1 the id for account
	 */
	public static void setNextAccountNumber(long long1) {
		MeritBank.nextAccountNumber = long1;
	}

	/**
	 * 
	 * @param accountHolder to be added to MeritBank
	 */
	public static void addAccountHolder(AccountHolder accountHolder) {
		AccountHolder[] copy = Arrays.copyOf(AccountHolders, AccountHolders.length+1);
		AccountHolders = copy;
		AccountHolders[AccountHolders.length-1] = accountHolder;
	}

	/**
	 * 
	 * @return the AccountHolders in MeritBank
	 */
	public static AccountHolder[] getAccountHolders() {
		return AccountHolders;
	}

	/**
	 * 
	 * @return the currently available CDOfferings
	 */
	public static CDOffering[] getCDOfferings() {
		return CDOfferings;
	}

	/**
	 * 
	 * @param depositAmount amount to be tested against the available CDOfferings
	 * @return the CDOffering with the best return for depositAmount
	 */
	public static CDOffering getBestCDOffering(double depositAmount) {
		double best = 0.0; 
		CDOffering bestOffering = null;
		if(CDOfferings == null) {
			return null;
		}
		for(CDOffering offering :  CDOfferings) {
			if(futureValue(depositAmount, offering.getInterestRate(),
					offering.getTerm()) > best) {
				bestOffering = offering;
				best = futureValue(depositAmount, offering.getInterestRate(),
						offering.getTerm());
			}
		}
		return bestOffering;
	}

	/**
	 * 
	 * @param depositAmount  amount to be tested against the available CDOfferings
	 * @return the second best CDOffering
	 */
	public static CDOffering getSecondBestCDOffering(double depositAmount) {
		if(CDOfferings == null) {
			return null;
		}
		CDOffering bestOffering = null;
		double best = 0.0; 
		CDOffering secondBestOffering = null;

		for(CDOffering offering :  CDOfferings) {
			if(futureValue(depositAmount, offering.getInterestRate(),
					offering.getTerm()) > best) {
				secondBestOffering = bestOffering;
				bestOffering = offering;
				best = futureValue(depositAmount, offering.getInterestRate(),
						offering.getTerm());
			}
		}
		return secondBestOffering;
	}

	/**
	 * 
	 * clears the current CDOfferings
	 */
	public static void clearCDOfferings() {
		CDOfferings = null;
	}

	/**
	 * 
	 * @param offerings stores the current available CDOfferings
	 */
	public static void setCDOfferings(CDOffering[] offerings) {
		CDOfferings = offerings;
	}

	/**
	 * 
	 * @return the next accountNumber to be assigned to Checking/Saving/CDAccounts
	 */
	public static long getNextAccountNumber() {
		return nextAccountNumber;
	}

	/**
	 * 
	 * @return the combined balances of all AccountHolders in the bank
	 */
	public static double totalBalances() {
		double total = 0.0;
		for(AccountHolder accounts : AccountHolders) {
			total += accounts.getCombinedBalance();
		}
		return total;

	}

	/**
	 * 
	 * @param presentValue  presentValue of the amount we are calculating futureValue of
	 * @param interestRate  interestRate we are compounding the presentValue by
	 * @param term  number of years into the future we are calculating futureValue
	 * 
	 * @return futureValue of balance given interest rate and term
	 */
	public static double futureValue(double presentValue, double interestRate, int term) {
		return presentValue*Math.pow(1 + interestRate,  term);
	}

	/**
	 * 
	 * @return returns the sorted accounts in an ascended order
	 */
	public static AccountHolder[] sortAccountHolders() {
		Arrays.sort(AccountHolders);

		return AccountHolders;
	}
	/**
	 * 
	 * @param amount amount of money
	 * @param years number of years
	 * @param interestRate the interest rate
	 * @return returns the calculated value through recursive
	 */
	public static double recursiveFutureValue(double amount, int years, double interestRate) {
		//Existing futureValue methods that used to call Math.pow() should now call this method
		if(years <=0) {
			return 1;
		}else {
			return amount*(1+interestRate)*recursiveFutureValue(1,years-1,interestRate);
		}
	}

	public static boolean processTransaction(Transaction transaction) throws NegativeAmountException, ExceedsAvailableBalanceException, ExceedsFraudSuspicionLimitException{

		//If transaction does not violate any constraints, deposit/withdraw values from the relevant BankAccounts and add the transaction to the relevant BankAccounts
		if(transaction.getAmount() < transaction.getSourceAccount().getBalance() &&
				transaction.getAmount() <= 1000 &&
				transaction.getAmount() > 0) {
			return true;
		}else if(transaction.getAmount() > 1000) {
			fraudQueue.addTransaction(transaction);
		}
		//If the transaction violates any of the basic constraints (negative amount, exceeds available balance) the relevant exception should be thrown and the processing should terminate
		//If the transaction violates the $1,000 suspicion limit, it should simply be added to the FraudQueue for future processing
		return false;
	}

	public static FraudQueue getFraudQueue() {
		return fraudQueue;
	}
	public static BankAccount getBankAccount(long accountId) {
		//Return null if account not found
		for(AccountHolder account : AccountHolders) {
			int checking = account.getCheckingAccounts().length;
			for(int j = 0;j<checking;j++) {
				if(accountId == account.getCheckingAccounts()[j].getAccountNumber()) {
					return account.getCheckingAccounts()[j];
				}
			}
			int saving = account.getSavingsAccounts().length;
			for(int j = 0;j<saving;j++) {
				if(accountId == account.getSavingsAccounts()[j].getAccountNumber()) {
					return account.getSavingsAccounts()[j];
				}
			}
			int cD = account.getCDAccounts().length;
			for(int j = 0;j<cD;j++) {
				if(accountId == account.getCDAccounts()[j].getAccountNumber()) {
					return account.getCDAccounts()[j];
				}
			}
		}
		return null;}

	public static boolean readFromFile(String fileName) {
		CDOfferings = new CDOffering[0];
		setNextAccountNumber(0);
		AccountHolders = new AccountHolder[0];
		fraudQueue = new FraudQueue();
		Set<String> transactions = new HashSet<String>();
		try(BufferedReader nextLine = new BufferedReader(new FileReader(fileName))) {
			setNextAccountNumber(Long.valueOf(nextLine.readLine()));
			int numberOfCDO = Integer.valueOf(nextLine.readLine());
			for(int i = 0; i<numberOfCDO; i++) {
				CDOfferings = Arrays.copyOf(CDOfferings, CDOfferings.length + 1);
				CDOfferings[CDOfferings.length - 1] = CDOffering.readFromString(nextLine.readLine());
			}
			int numOfAccountHolders = Integer.valueOf(nextLine.readLine());
			for(int i = 0; i < numOfAccountHolders; i++) {
				AccountHolder nextAccountHolder = AccountHolder.readFromString(nextLine.readLine());
				MeritBank.addAccountHolder(nextAccountHolder);	
				int numOfCheckingAccounts = Integer.valueOf(nextLine.readLine());
				for(int c = 0; c < numOfCheckingAccounts; c++) {
					nextAccountHolder.addCheckingAccount(CheckingAccount.readFromString(nextLine.readLine()));
					int numCheckingtrans = Integer.valueOf(nextLine.readLine());
					for(int ct = 0; ct < numCheckingtrans; ct++) {
						nextAccountHolder.getCheckingAccounts()[c].addTransactionString(nextLine.readLine());
					}
				}
				int numOfSavingsACCOUNTS = Integer.valueOf(nextLine.readLine());
				for(int s = 0; s < numOfSavingsACCOUNTS; s++) {
					nextAccountHolder.addSavingsAccount(SavingsAccount.readFromString(nextLine.readLine()));
					int numSavingstrans = Integer.valueOf(nextLine.readLine());
					for(int st = 0; st < numSavingstrans; st++) {
						nextAccountHolder.getSavingsAccounts()[s].addTransactionString(nextLine.readLine());
					}
				}
				int numOfCDAccounts = Integer.valueOf(nextLine.readLine());
				for(int cd = 0; cd < numOfCDAccounts; cd++) {
					nextAccountHolder.addCDAccount(CDAccount.readFromString(nextLine.readLine()));
					int numCDTrans = Integer.valueOf(nextLine.readLine());
					for(int cdt = 0; cdt < numCDTrans; cdt++) {
						nextAccountHolder.getCDAccounts()[cd].addTransactionString(nextLine.readLine());
					}
				}
			}
			int numFraudQT = Integer.valueOf(nextLine.readLine());
			for(int fqt = 0; fqt < numFraudQT; fqt++) {
				//fraudQueue.addTransaction(Transaction.readFromString(nextLine.readLine()));
				fraudQueue.addFraudHistory(nextLine.readLine());

			}
			/*for(String trans : transactions) {
				Transaction newTran = Transaction.readFromString(trans);
				if(newTran.getSourceAccount() == null) {
					//newTran.getTargetAccount().addTransaction(newTran);
				}
				else {
					newTran.getTargetAccount().addTransaction(newTran);
					newTran.getSourceAccount().addTransaction(newTran);
				}
			}*/
			return true;
		}catch(Exception e) {
			return false;
		}
	}

	static boolean writeToFile(String fileName) {
		/* Writes all the data in a text file called "testMeritBank_Write.txt"
		 * Located the same place as "testMeritBank_good.txt"
		 */
		try {
			//Should also write BankAccount transactions and the FraudQueue
			FileWriter fileWriter = new FileWriter(fileName);
			//Next account Number
			long accNum = getNextAccountNumber();
			fileWriter.write(accNum+"\n");
			//Number of CDOfferings
			int numberOfCDO = CDOfferings.length;
			fileWriter.write(numberOfCDO+"\n");
			for(int i = 0; i<numberOfCDO; i++) {
				String temp = CDOfferings[i].writeToString();
				fileWriter.write(temp+"\n");
			}
			//Number of account holders
			int numberOfAH = AccountHolders.length;
			fileWriter.write(numberOfAH+"\n");
			for(int i = 0; i<numberOfAH; i++) {
				String temp = AccountHolders[i].writeToString();
				fileWriter.write(temp);
			}
			//Fraud Queue has 2 transactions pending review
			int numOfFraudQueue = fraudQueue.getFraudNum();
			fileWriter.write(numOfFraudQueue+"\n");
			fileWriter.write(fraudQueue.getTransactionString());
			
			
			fileWriter.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
