package com.ing.mvp.BankTransaction.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import com.ing.mvp.BankTransaction.Exception.ResourceNotFoundException;
import com.ing.mvp.BankTransaction.dao.TransferRepository;
import com.ing.mvp.BankTransaction.model.Account;
import com.ing.mvp.BankTransaction.model.Transaction;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class TransferRepositoryTest {

	@Mock
	JdbcTemplate jdbcTemplate;

	@InjectMocks
	TransferRepository transferRepo;

	@Test
	public void testGetCustomerBalance() throws ResourceNotFoundException {

		String sql = "select sum(balance) from account WHERE cust_id = ?";
		ReflectionTestUtils.setField(transferRepo, "jdbcTemplate", jdbcTemplate);
		Optional<Long> customerAccountBalance = transferRepo.getCustomerAccountBalance(String.valueOf(4));
		when(jdbcTemplate.queryForObject(sql, new Object[] { String.valueOf(4) }, long.class)).thenReturn(90l);

		assertEquals(90l, customerAccountBalance.get());
	}

	@Test
	public void testGetAccountBalance() throws ResourceNotFoundException {

		String sql = "select * from account WHERE act_no = ?";
		ReflectionTestUtils.setField(transferRepo, "jdbcTemplate", jdbcTemplate);
		Account balance = transferRepo.getAccountBalance("007100777");

		when(jdbcTemplate.queryForObject(sql, new Object[] { "007100777" },
				(rs, rowNum) -> new Account(rs.getLong("ID"), rs.getString("act_type"), rs.getString("act_no"),
						rs.getLong("balance"), rs.getLong("cust_Id")))).thenReturn(balance);

		assertEquals(90l, balance.getBalance());
	}

	@Test
	public void testGetTransactionHistory() throws ResourceNotFoundException {

		String sql = "select * from Transaction ts where ts.act_no =(select a.act_no from account a , customer c where a.cust_id = c.id and c.id = ?)";
		ReflectionTestUtils.setField(transferRepo, "jdbcTemplate", jdbcTemplate);
		List<Transaction> transactionHistory = transferRepo.getTransactionHistory(String.valueOf(4));

		when(jdbcTemplate.query(sql, new Object[] { String.valueOf(4) },
				(rs, rowNum) -> new Transaction(rs.getLong("ID"), rs.getString("txn_type"), rs.getString("act_no"),
						rs.getLong("txn_amount"), rs.getTimestamp("txn_date").toLocalDateTime())))
								.thenReturn(transactionHistory);

		assertEquals(1, transactionHistory.size());
	}

}
