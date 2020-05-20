package com.ing.mvp.BankTransaction.controller;

import static org.assertj.core.api.Assertions.assertThat;
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
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ing.mvp.BankTransaction.Controller.DepositController;
import com.ing.mvp.BankTransaction.Exception.ResourceNotFoundException;
import com.ing.mvp.BankTransaction.Service.DepositService;
import com.ing.mvp.BankTransaction.model.Account;
import com.ing.mvp.BankTransaction.model.Transaction;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class DepositControllerTest {

	@InjectMocks
	DepositController depositController;

	@Mock
	DepositService depositService;

	@Test
	public void testGetAccountBalance() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		when(depositService.getCustomerAccountBalance(String.valueOf(4))).thenReturn(Optional.of(90l));

		ResponseEntity<Long> responseEntity = depositController.getAccountBalance(String.valueOf(4));

		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);

	}

	@Test
	public void testDepositMoney() throws ResourceNotFoundException, Exception {

		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		when(depositService.depositMoney("007100333", "007100222", 10)).thenReturn(new ArrayList<Account>());
		ResponseEntity<List<Account>> entity = depositController.depostMoney("007100333", "007100222",
				String.valueOf(10));
		assertThat(entity.getStatusCodeValue()).isEqualTo(200);
	}
	
	
	@Test
	public void testGetTransactionHistory() throws ResourceNotFoundException, Exception {

		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		when(depositService.getCustomerTransactionHistory(String.valueOf(4))).thenReturn(new ArrayList<Transaction>());
		ResponseEntity<List<Transaction>> entity = depositController.getTransactionHistory(String.valueOf(4));
		assertThat(entity.getStatusCodeValue()).isEqualTo(200);
	}
	
	
}
