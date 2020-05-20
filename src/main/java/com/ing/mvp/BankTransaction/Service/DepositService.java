package com.ing.mvp.BankTransaction.Service;

import java.util.List;
import java.util.Optional;

import com.ing.mvp.BankTransaction.Exception.ResourceNotFoundException;
import com.ing.mvp.BankTransaction.model.Account;
import com.ing.mvp.BankTransaction.model.Transaction;

public interface DepositService {



	List<Account> depositMoney(String srcAct, String destAct, long amount) throws ResourceNotFoundException, Exception;

	Optional<Long> getCustomerAccountBalance(String custId) throws ResourceNotFoundException;

	List<Transaction> getCustomerTransactionHistory(String id) throws ResourceNotFoundException;
	
}
