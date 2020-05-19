package com.ing.mvp.BankTransaction.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ")
	@SequenceGenerator(name = "SEQ", sequenceName = "ACCOUNT_SEQ")
	
	private long id; 
	
	
	@Column(name = "act_type")
    private String actType;
    @Column(name = "act_no")
	private String actNo;
	@Column(name = "balance")
	private long balance;
	
	@Column(name = "cust_Id")
	private long custId;

	
	
	public Account() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Account(long id, String actType, String actNo, long balance, long custId) {
		super();
		this.id = id;
		this.actType = actType;
		this.actNo = actNo;
		this.balance = balance;
		this.custId = custId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public String getActType() {
		return actType;
	}

	public void setActType(String actType) {
		this.actType = actType;
	}

	public String getActNo() {
		return actNo;
	}

	public void setActNo(String actNo) {
		this.actNo = actNo;
	}

	public long getBalance() {
		return balance;
	}

	public void setBalance(long balance) {
		this.balance = balance;
	}

	public long getCustId() {
		return custId;
	}

	public void setCustId(long custId) {
		this.custId = custId;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", actType=" + actType + ", actNo=" + actNo + ", balance=" + balance
				+ ", custId=" + custId + "]";
	}
	
	
	
}
