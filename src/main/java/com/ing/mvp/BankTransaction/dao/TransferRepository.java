package com.ing.mvp.BankTransaction.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ing.mvp.BankTransaction.model.Account;
import com.ing.mvp.BankTransaction.model.Transaction;

@Repository
public class TransferRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public int[] updateAcount(List<Account> account) {

		String sql = "update account set Balance = ? where act_no = ?";

		int[] update = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setLong(1, account.get(i).getBalance());
				ps.setString(2, account.get(i).getActNo());
			}

			@Override
			public int getBatchSize() {
				return 2;
			}
		});

		return update;
	}

	public void updateTransaction(List<Transaction> trans) {

		String sql = "INSERT INTO transaction (txn_type,act_no,txn_amount,txn_date) VALUES (?,?,?,?)";

		int[] update = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, trans.get(i).getTransactionType());
				ps.setString(2, trans.get(i).getAccountNumber());
				ps.setLong(3, trans.get(i).getTransactionAmount());
				ps.setDate(4, new Date(Calendar.getInstance().getTime().getTime()));
			}

			@Override
			public int getBatchSize() {
				return 2;
			}
		});
		System.out.println("Inserted");
	}

	public Account getAccountBalance(String act) {

 		String sql = "select * from account WHERE act_no = ?";
		Account account = (Account) jdbcTemplate.queryForObject(sql, new Object[] { act },
				(rs, rowNum) -> new Account(rs.getLong("ID"), rs.getString("act_type"), rs.getString("act_no"),
						rs.getLong("balance"), rs.getLong("cust_Id")));
		return account;
	}
	
	public Optional<Long> getCustomerAccountBalance(String custId) {

		String sql = "select balance from account WHERE cust_id = ?";
		 Optional<Long> balance = Optional.of(jdbcTemplate.queryForObject(sql, new Object[]{custId}, long.class));
		System.out.println("Customer Balance -- "+balance);
		return balance;
	}
	
	public List<Transaction> getTransactionHistory(String custId){
		
		String sql = "select * from Transaction ts where ts.act_no =(select a.act_no from account a , customer c where a.cust_id = c.id and c.id = ?)";
		List<Transaction> transaction = (List<Transaction>) jdbcTemplate.queryForObject(sql, new Object[] { custId },
				(rs, rowNum) -> new Transaction(rs.getLong("ID"),
						rs.getString("txn_type"),
						rs.getString("act_no"),
						rs.getLong("txn_amount"),
						rs.getTimestamp("txn_date").toLocalDateTime()));
		System.out.println("get transaction ");
		
		return transaction;
		
		
		
	}
	

}
