package com.moqian.bank.model;

import com.moqian.bank.R.string;

public class TransRecordInfo {
	private String collectionAccount;
	private String collectionBank;
	private String name;
	private String amount;
	private String date;
	private String state;
	private int id;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCollectionAccount() {
		return collectionAccount;
	}
	public void setCollectionAccount(String collectionAccount) {
		this.collectionAccount = collectionAccount;
	}
	public String getCollectionBank() {
		return collectionBank;
	}
	public void setCollectionBank(String collectionBank) {
		this.collectionBank = collectionBank;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	
	

}
