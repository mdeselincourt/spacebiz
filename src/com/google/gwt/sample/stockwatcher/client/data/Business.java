package com.google.gwt.sample.stockwatcher.client.data;

import java.io.Serializable;

public class Business implements FinancialEntity, Serializable {

	String ownerGoogleUserId;
	String name;
	int balance;
	//Stake[] stakes; // A Stake is a pile of shares
	//Loan[] loanDebts; // References to loans this business owes
	
	public Business() {
		this.ownerGoogleUserId = null;
		this.name = "DUMMY";
		this.balance = 0;
		//this.stakes = null;
		//this.loanDebts = null;
	}
	
	public Business(String ownerGoogleUserId, String name, int balance) {
		super();
		this.ownerGoogleUserId = ownerGoogleUserId;
		this.name = name;
		this.balance = balance;
		//this.stakes = stakes;
		//this.loanDebts = loanDebts;
	}
	
	public String getOwnerGoogleUserId() {
		return ownerGoogleUserId;
	}
	public void setOwnerGoogleUserId(String ownerGoogleUserId) {
		this.ownerGoogleUserId = ownerGoogleUserId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	
}
