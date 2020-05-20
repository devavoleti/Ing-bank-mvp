package com.ing.mvp.BankTransaction.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ing.mvp.BankTransaction.Exception.ResourceNotFoundException;
import com.ing.mvp.BankTransaction.Service.DepositServiceImpl;
import com.ing.mvp.BankTransaction.dao.TransferRepository;
import com.ing.mvp.BankTransaction.model.Account;
import com.ing.mvp.BankTransaction.model.Transaction;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class DepositServiceTest {

	@InjectMocks
	DepositServiceImpl depositService;

	@Mock
	TransferRepository depositDao;
	
	@Test
	public void testGetCustomerAccountBalance() throws ResourceNotFoundException {
		
		when(depositDao.getCustomerAccountBalance(String.valueOf(4))).thenReturn(Optional.of(90l));
		Optional<Long> customerAccountBalance = depositService.getCustomerAccountBalance(String.valueOf(4));
		assertEquals(90l,customerAccountBalance.get());
	}
	
	@Test
	public void testGetTransactionHistory() throws ResourceNotFoundException {
		
		Transaction trans = new Transaction();
		trans.setAccountNumber("007100777");
		trans.setId((long) 16);
		trans.setTransactionAmount(10);
		trans.setTransactionType("W");
		
		List<Transaction> transactionList = new ArrayList<Transaction>();
		
		transactionList.add(trans);
		
		when(depositDao.getTransactionHistory(String.valueOf(4))).thenReturn(new ArrayList<Transaction>());
		List<Transaction> customerTransactionHistory = depositService.getCustomerTransactionHistory(String.valueOf(4));
		
		//trans.setTransactionDateTime(transactionDateTime);
		assertEquals(transactionList.size(),customerTransactionHistory.size());
	}
	
	@Test
	public void testDepositMoney() throws Exception {
		
		
		when(depositDao.getAccountBalance("007100333")).thenReturn(new Account());
		when(depositDao.getAccountBalance("007100222")).thenReturn(new Account());
		
		//List<Account> accountList = depositService.depositMoney("007100333", "007100222", 10);
		
		assertThrows(Exception.class, ()-> depositService.depositMoney("007100333", "007100222", 10),"Insucfficient funds to withdraw.");
	}
	
}
