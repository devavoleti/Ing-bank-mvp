package com.ing.mvp.BankTransaction.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "transaction")
public class Transaction {
	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "txn_type")
	private String transactionType;
	@Column(name = "act_no")
	private String accountNumber;
	@Column(name = "txn_amount")
	private long transactionAmount;
	@Column(name = "txn_date")
	private LocalDateTime transactionDateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public long getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(long transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public LocalDateTime getTransactionDateTime() {
		return transactionDateTime;
	}

	public void setTransactionDateTime(LocalDateTime transactionDateTime) {
		this.transactionDateTime = transactionDateTime;
	}

	@Override
	public String toString() {
		return "Transaction [transactionId=" + id + ", transactionType=" + transactionType + ", accountNumber="
				+ accountNumber + ", transactionAmount=" + transactionAmount + ", transactionDateTime="
				+ transactionDateTime + "]";
	}

}
