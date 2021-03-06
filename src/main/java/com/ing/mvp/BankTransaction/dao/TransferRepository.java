package com.ing.mvp.BankTransaction.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ing.mvp.BankTransaction.Exception.ResourceNotFoundException;
import com.ing.mvp.BankTransaction.model.Account;
import com.ing.mvp.BankTransaction.model.Transaction;

@Repository
public class TransferRepository {

	private static final Logger LOG = LoggerFactory.getLogger(TransferRepository.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	public int[] updateAcount(List<Account> account) throws ResourceNotFoundException {

		String sql = "update account set Balance = ? where act_no = ?";
		int[] update = null;
		LOG.info("Inside TransferRepository :: updateAcount :: sql {} ", sql);
try {
		 update = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
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
	}catch (Exception e) {
			LOG.error("Exception occured while retrieving the Account Balance{}", e.getMessage());
			throw new ResourceNotFoundException(e.getMessage());
		}
		LOG.info("Inside TransferRepository updated the Account table");
		return update;
	}

	public void updateTransaction(List<Transaction> trans) throws ResourceNotFoundException {

		String sql = "INSERT INTO transaction (txn_type,act_no,txn_amount,txn_date) VALUES (?,?,?,?)";

		LOG.info("Inside TransferRepository :: updateTransaction :: sql {} ", sql);
		try {
			jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
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
		} catch (Exception e) {
			LOG.error("Exception occured while Updating the Transaction {}", e.getMessage());
			throw new ResourceNotFoundException(e.getMessage());
		}
		LOG.info("Inside TransferRepository updated the Transaction table");
	}

	public Account getAccountBalance(String act) throws ResourceNotFoundException {

		String sql = "select * from account WHERE act_no = ?";
		Account account = null;
		LOG.info("Inside TransferRepository :: getAccountBalance :: sql {} ", sql);
		try {
			account = (Account) jdbcTemplate.queryForObject(sql, new Object[] { act },
					(rs, rowNum) -> new Account(rs.getLong("ID"), rs.getString("act_type"), rs.getString("act_no"),
							rs.getLong("balance"), rs.getLong("cust_Id")));
		} catch (Exception e) {
			LOG.error("Exception occured while retrieving the Account Balance{}", e.getMessage());
			throw new ResourceNotFoundException(e.getMessage());
		}
		LOG.info("TransferRepository :: retrieved AccountBalance {} ",account.getBalance());
		return account;
	}

	public Optional<Long> getCustomerAccountBalance(String custId) throws ResourceNotFoundException {

		String sql = "select sum(balance) from account WHERE cust_id = ?";
		Optional<Long> balance = null;
		LOG.info("Inside TransferRepository :: getCustomerAccountBalance :: sql {} ", sql);
		try {
			balance = Optional.of(jdbcTemplate.queryForObject(sql, new Object[] { custId }, long.class));
			LOG.info("Inside TransferRepository retrieved CustomerAccountBalance {} ", balance.get());
		} catch (Exception e) {
			LOG.error("Exception occured while retrieving the Customer Account Balance{}", e.getMessage());
			throw new ResourceNotFoundException(e.getMessage());
		}
		return balance;
	}

	public List<Transaction> getTransactionHistory(String custId) throws ResourceNotFoundException {

		String sql = "select * from Transaction ts where ts.act_no =(select a.act_no from account a , customer c where a.cust_id = c.id and c.id = ?)";

		LOG.info("Inside TransferRepository :: getTransactionHistory :: sql {} ", sql);
		List<Transaction> transaction = null;
		try {
			transaction = (List<Transaction>) jdbcTemplate.query(sql, new Object[] { custId },
					(rs, rowNum) -> new Transaction(rs.getLong("ID"), rs.getString("txn_type"), rs.getString("act_no"),
							rs.getLong("txn_amount"), rs.getTimestamp("txn_date").toLocalDateTime()));

			LOG.info("Inside TransferRepository retrieved TransactionHistory");
		} catch (Exception e) {
			LOG.error("Exception occured while retrieving the Transaction history {}", e.getMessage());
			throw new ResourceNotFoundException(e.getMessage());
		}
		return transaction;

	}

}
