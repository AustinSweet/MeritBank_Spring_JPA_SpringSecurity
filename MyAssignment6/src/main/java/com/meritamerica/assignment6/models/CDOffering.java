package com.meritamerica.assignment6.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class CDOffering {
	
	@Id
	private Integer iD;
	
	@NotNull
	private int term;
	@NotNull
	private double interestRate=0.03;

	/**
	 * 
	 * @param term refers to the number of years
	 */
	public void setTerm(int term) {
		this.term = term;
	}
	/**
	 * 
	 * @param interestRate the interest of that year plan
	 */
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	/**
	 * 
	 * Constructor for creating CDOfferings
	 * 
	 * @param term years  of this CDOffering
	 * @param interestRate  interestRate for this CDOffering
	 */
	public CDOffering(int term, double interestRate){
		this.term = term;
		this.interestRate = interestRate;
	}
	
	public CDOffering() {
		this.term = 0;
		this.interestRate = 0;
	}

	/**
	 * 
	 * @return term in years of this CDOffering
	 */
	public int getTerm() {
		return term;
	}

	/**
	 * 
	 * @return interestRare of this CDOffering
	 */
	public double getInterestRate() {
		return interestRate;
	}
	/**
	 * 
	 * @param string refers to save data
	 * @return returns updated CDOffering class
	 */
	public static CDOffering readFromString(String string) {
		//3,0.019
		int term = Integer.parseInt(string.split(",")[0]);
		double ir = Double.parseDouble(string.split(",")[1]);

		CDOffering cd = new CDOffering(term, ir);
		return cd;
	}
	/**
	 * 
	 * @return the data in a string to write on save data for record
	 */
	public String writeToString() {
		//3,0.019
		String term = getTerm()+"";
		String ir = getInterestRate()+"";
		return term+","+ir;
	}
}

