package com.meritamerica.assignment6.models;

import java.util.Arrays;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.meritamerica.assignment7.exceptions.ExceedsCombinedBalanceLimitException;
import com.meritamerica.assignment7.exceptions.ExceedsFraudSuspicionLimitException;
import com.meritamerica.assignment7.exceptions.NegativeAmountException;

@Entity
public class AccountHolder implements Comparable<AccountHolder> {

	@NotNull
	private static int nextId = 1;
	@NotNull
	@Id
	@Column
	private int iD;
	@OneToOne(cascade = CascadeType.ALL)
	@Transient
	private AccountHolderContactDetails contactDetails;

	@Size(max = 50, min = 0, message = "Invalid Input")
	@NotEmpty(message = "Please Enter Name")
	@Column
	private String firstName;

	@Size(max = 50, min = 0, message = "Invalid Input")
	@NotEmpty(message = "Please Enter Name")
	@Column
	private String middleName;

	@Size(max = 50, min = 0, message = "Invalid Input")
	@NotEmpty(message = "Please Enter Name")
	@Column
	private String lastName;

	@Size(max = 50, min = 0, message = "Invalid Input")
	@NotEmpty(message = "Please Enter Name")
	@Column
	private String ssn;
	@Transient
	@OneToMany
	CheckingAccount[] checking = new CheckingAccount[0];
	@Transient
	@OneToMany
	SavingsAccount[] savings = new SavingsAccount[0];
	@Transient
	@OneToMany
	CDAccount[] cdAccount = new CDAccount[0];

	public AccountHolder() {
		this.iD = nextId++;
		this.firstName = "";
		this.middleName = "";
		this.lastName = "";
		this.ssn = "";
	}

	/**
	 * 
	 * @param firstName the first name of person
	 * @param middleName the middle name of person
	 * @param lastName the last name of person
	 * @param ssn the social security number
	 */
	public AccountHolder(String firstName, String middleName,
			String lastName, String ssn) {
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.ssn = ssn;
	}

	public AccountHolderContactDetails getContactDetails() {
		return contactDetails;
	}

	public void setContactDetails(AccountHolderContactDetails contactDetails) {
		this.contactDetails = contactDetails;
	}

	/**
	 * 
	 * @return the firstName of this AccountHolder
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 *
	 * @param firstName  Stores firstName of this AccountHolder
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * 
	 * @return the middleName of this AccountHolder
	 */
	public String getMiddleName() {
		return middleName;
	}

	/**
	 * 
	 * @param middleName  store middleName of this AccountHolder
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	/**
	 * 
	 * @return lastName of this AccountHolder
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * 
	 * @param lastName  stares middleName of this AccountHolder
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * 
	 * @return social security number of this AccountHolder
	 */
	public String getSSN() {
		return ssn;
	}

	/**
	 * 
	 * @param ssn  Stores social security number of this AccountHolder
	 */
	public void setSSN(String ssn) {
		this.ssn = ssn;
	}

	public int getiD() {
		return iD;
	}

	public void setiD(int iD) {
		this.iD = iD;
	}

	/**
	 * 
	 * Add checking account to this AccountHolder if the balance of
	 * this CheckingAccount + all checking accounts + all savings accounts 
	 * for this AccountHolder is less than 250000
	 * 
	 * @param openingBalance to create the CheckingAccount to add to this AccountHolder
	 * @return the CheckingAccount added to this AccountHolder
	 */
	public CheckingAccount addCheckingAccount(double openingBalance)  throws ExceedsCombinedBalanceLimitException{
		///If combined balance limit is exceeded, throw ExceedsCombinedBalanceLimitException
		//Should also add a deposit transaction with the opening balance

		if(getCheckingBalance() + getSavingsBalance() + openingBalance >= 250000) {
			throw new ExceedsCombinedBalanceLimitException();
		}
		CheckingAccount newAccount = new CheckingAccount(openingBalance);
		CheckingAccount[] copy = Arrays.copyOf(checking, checking.length+1);
		checking = copy;
		checking[checking.length-1] = newAccount;
		return newAccount;
	}

	/**
	 * 
	 * Add checking account to this AccountHolder if the balance of
	 * this CheckingAccount + all checking accounts + all savings accounts 
	 * for this AccountHolder is less than 250000
	 * 
	 * @param checkingAccount  to be added to this AccountHolder
	 * @return the checkingAccount added to this AccountHolder
	 */
	public CheckingAccount addCheckingAccount(CheckingAccount checkingAccount) throws ExceedsCombinedBalanceLimitException{
		//If combined balance limit is exceeded, throw ExceedsCombinedBalanceLimitException
		//Should also add a deposit transaction with the opening balance

		if(getCheckingBalance() + getSavingsBalance() + checkingAccount.getBalance() >= 250000) {
			throw new ExceedsCombinedBalanceLimitException();
		}
		CheckingAccount[] copy = Arrays.copyOf(checking, checking.length+1);
		checking = copy;
		checking[checking.length-1] = checkingAccount;
		return checkingAccount;
	}

	/**
	 * 
	 * @return the checkingAcccounts for this AcccountHolder
	 */
	public CheckingAccount[] getCheckingAccounts() {
		return checking;
	}

	/**
	 * 
	 * @return number of checkingAccounts for this AccountHolder
	 */
	public int getNumberOfCheckingAccounts() {
		return checking.length;
	}

	/**
	 * 
	 * @return the balance of all checkingAccounts for this AccountHolder
	 */
	public double getCheckingBalance() {
		int i;
		double total = 0.0;
		for(i = 0; i < checking.length; i++) {
			total += checking[i].getBalance();
		}
		return total;
	}

	/**
	 * 
	 * Add savings account to this AccountHolder if the balance of
	 * this savingsAccount + all checking accounts + all savings accounts 
	 * for this AccountHolder is less than 250000
	 * 
	 * @param openingBalance  to create the SavingsAccount to add to this AccountHolder
	 * @return the account that was added to this AccountHolder
	 */
	public SavingsAccount addSavingsAccount(double openingBalance) throws ExceedsCombinedBalanceLimitException {
		//If combined balance limit is exceeded, throw ExceedsCombinedBalanceLimitException
		//Should also add a deposit transaction with the opening balance

		if(getCheckingBalance() + getSavingsBalance() + openingBalance >= 250000) {
			throw new ExceedsCombinedBalanceLimitException();
		}
		SavingsAccount newAccount = new SavingsAccount(openingBalance);
		SavingsAccount[] copy = Arrays.copyOf(savings, savings.length+1);
		savings = copy;
		savings[savings.length-1] = newAccount;
		return newAccount;
	}

	/**
	 * 
	 * Add savings account to this AccountHolder if the balance of
	 * this savingsAccount + all checking accounts + all savings accounts 
	 * for this AccountHolder is less than 250000
	 * 
	 * @param savingsAccount  to be added to this AccountHolder
	 * @return the savingsAccount added to this AccountHolder
	 */
	public SavingsAccount addSavingsAccount(SavingsAccount savingsAccount) throws ExceedsCombinedBalanceLimitException{
		//If combined balance limit is exceeded, throw ExceedsCombinedBalanceLimitException
		//Should also add a deposit transaction with the opening balance

		if(getCheckingBalance() + getSavingsBalance() + savingsAccount.getBalance()>= 250000) {
			throw new ExceedsCombinedBalanceLimitException();
		}
		SavingsAccount[] copy = Arrays.copyOf(savings, savings.length+1);
		savings = copy;
		savings[savings.length-1] = savingsAccount;
		return savingsAccount;

	}

	/**
	 * 
	 * @return the SavingsAccounts for this AccountHolder
	 */
	public SavingsAccount[] getSavingsAccounts() {
		return savings;
	}

	/**
	 * 
	 * @return number of SavingsAccounts for this AccountHolder
	 */
	public int getNumberOfSavingsAccounts() {
		return savings.length;
	}

	/**
	 * 
	 * @return the balance of all SavingsAccounts for this AccountHolder
	 */
	public double getSavingsBalance() {
		double total = 0.0;
		for(SavingsAccount balance : savings) {
			total += balance.getBalance();
		}
		return total;

	}

	/**
	 * 
	 * @param offering  the CDOffer to be added to this AccountHolder
	 * @param openingBalance  the balance to be stored in new CDAccount for this AccountHolder
	 * @return CDAccount added to this AccountHolder
	 * @throws ExceedsFraudSuspicionLimitException 
	 */
	public CDAccount addCDAccount(CDOffering offering, double openingBalance) throws ExceedsFraudSuspicionLimitException {
		try {
			//Should also add a deposit transaction with the opening balance
			if(offering == null) {
				throw new ExceedsFraudSuspicionLimitException();
			}else {
				if(openingBalance > 1000) {
					throw new ExceedsFraudSuspicionLimitException();
				}else if(openingBalance < 0){
					throw new NegativeAmountException();
				}else {
					CDAccount newAccount = new CDAccount(offering, openingBalance);
					CDAccount[] copy = Arrays.copyOf(cdAccount, cdAccount.length+1);
					cdAccount = copy;
					cdAccount[cdAccount.length-1] = newAccount;
					return newAccount;
				}
			}

		}
		catch(ExceedsFraudSuspicionLimitException | NegativeAmountException e) {
			throw new ExceedsFraudSuspicionLimitException();
		}
	}

	/**
	 * @param cdAccount  to be added to this AccountHolder
	 * @return the CDAccount that was added to this AccountHolder
	 */
	public CDAccount addCDAccount(CDAccount cdAccount) {
		//hould also add a deposit transaction with the opening balance

		CDAccount[] copy = Arrays.copyOf(this.cdAccount,this.cdAccount.length+1);
		this.cdAccount = copy;
		this.cdAccount[this.cdAccount.length-1] = cdAccount;
		return cdAccount;
	}

	/**
	 * 
	 * @return the CDAccounts for this AccountHolder
	 */
	public CDAccount[] getCDAccounts() {
		return cdAccount;
	}

	/**
	 * 
	 * @return number of cdAccounts for this AccountHolder
	 */
	public int getNumberOfCDAccounts() {
		return cdAccount.length;
	}

	/**
	 * 
	 * @return get balance for this AccountHolders cd's
	 */
	public double getCDBalance() {
		double total = 0.0;
		for(CDAccount balance : cdAccount) {
			total += balance.getBalance();
		}
		return total;
	}

	/**
	 * 
	 * @return the combinedBalance for this AccountHolder
	 */
	public double getCombinedBalance() {
		return getCDBalance() + getSavingsBalance() + getCheckingBalance();
	}

	/**
	 * Used for sorting accounts
	 */
	public int compareTo(AccountHolder account) {
		if(this.getCombinedBalance() > account.getCombinedBalance()) {
			return 1;
		}else {
			return -1;
		}
	}
	/**
	 * 
	 * @return the updated file to save data
	 */
	public String writeToString() {
		String Line0 = getFirstName()+","+getMiddleName()+","+getLastName()+","+getSSN() + "\n";

		String Checking = getCheckingAccounts().length+"\n";
		String CheckData = "";
		for(int i = 0; i<getCheckingAccounts().length;i++) {
			int transactionSize = checking[i].getTransactionStringSize();
			CheckData += checking[i].writeToString()+"\n"+
					transactionSize+"\n"+
					checking[i].getTransactionString();
		}
		String Saving = getNumberOfSavingsAccounts()+"\n";
		String SaveData = "";
		for(int i = 0; i<getSavingsAccounts().length;i++) {
			int transactionSize = savings[i].getTransactionStringSize();
			SaveData += savings[i].writeToString()+"\n"+
					transactionSize+"\n"+
					savings[i].getTransactionString();
		}
		String CD = getNumberOfCDAccounts()+"\n";
		String CDData = "";
		for(int i = 0; i<cdAccount.length;i++) {
			int transactionSize = savings[i].getTransactionStringSize();
			CDData += cdAccount[i].writeToString()+"\n"+
					transactionSize+"\n"+
					cdAccount[i].getTransactionString();
		}
		String file = Line0 + Checking + CheckData + Saving + SaveData + CD + CDData;
		return file;}
	/**
	 * 
	 * @param accountHolderData the file path to read from
	 * @return the updated account
	 * @throws Exception
	 */
	static AccountHolder readFromString(String accountHolderData) throws Exception{
		try {
			String[] temp = accountHolderData.split(",");
			/* 0=FirstName
			 * 1=MiddleName
			 * 2=LastName
			 * 3=SSN
			 */
			AccountHolder account = new AccountHolder(temp[0],temp[2],temp[2],temp[3]);
			return account;
		}catch(Exception e) {
			return null;
		}
	}

}