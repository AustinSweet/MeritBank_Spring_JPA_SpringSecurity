package com.meritamerica.assignment6.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class AccountHolderContactDetails {

	
	@Id
	@Column
	private int iD;
	
	
	public int getiD() {
		return iD;
	}

	public void setiD(int iD) {
		this.iD = iD;
	}

	//@JoinColumn(name = "contact_id", referencedColumnName = "ah_id")
	@Transient
	private AccountHolder holder;
	
	public AccountHolder getHolder() {
		return holder;
	}

	public void setHolder(AccountHolder holder) {
		this.holder = holder;
	}

	//@Column
	@NotEmpty(message = "Please Enter Name")
	private String email;
	//@Column
	@NotEmpty(message = "Please Enter Name")
	private String phoneNum;
	
	public AccountHolderContactDetails() {
	}

	public AccountHolderContactDetails(String email, String phoneNum) {
		this.email = email;
		this.phoneNum = phoneNum;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}


	
	
}
