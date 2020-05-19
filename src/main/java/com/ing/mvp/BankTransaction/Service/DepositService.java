package com.ing.mvp.BankTransaction.Service;

import java.math.BigDecimal;
import java.util.List;

import com.ing.mvp.BankTransaction.Exception.ResourceNotFoundException;
import com.ing.mvp.BankTransaction.model.Account;

public interface DepositService {


	BigDecimal getBalance(String act);

	List<Account> depositMoney(String srcAct, String destAct, long amount) throws ResourceNotFoundException, Exception;
	
}
