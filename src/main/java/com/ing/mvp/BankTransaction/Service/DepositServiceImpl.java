package com.ing.mvp.BankTransaction.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ing.mvp.BankTransaction.Exception.NotAcceptableException;
import com.ing.mvp.BankTransaction.Exception.ResourceNotFoundException;
import com.ing.mvp.BankTransaction.dao.TransferRepository;
import com.ing.mvp.BankTransaction.model.Account;
import com.ing.mvp.BankTransaction.model.Transaction;

@Service
public class DepositServiceImpl implements DepositService {

	private static final Logger LOG = LoggerFactory.getLogger(DepositServiceImpl.class);
	
	@Autowired
	TransferRepository transferRepo;

	@Override
	public List<Account> depositMoney(String srcAct, String destAct, long amount) throws Exception {
		
		LOG.info("Inside DepositServiceImpl :: depositMoney ");
		Account srcAccount = null;
		Account destAccount = null;
		Transaction depositTrans = new Transaction();
		Transaction withdrawlTrans = new Transaction();
		List<Account> accountList = new ArrayList<Account>();
		int[] updatedRow = null;
		try {

			srcAccount = transferRepo.getAccountBalance(srcAct);
			destAccount = transferRepo.getAccountBalance(destAct);
			LOG.info("Inside DepositServiceImpl :: source Account {} and destination Account {} before transaction ",srcAccount,destAccount);
			if (srcAccount.getBalance() < amount) {
				throw new NotAcceptableException("Insucfficient funds to withdraw.");
			}

			srcAccount.setBalance(srcAccount.getBalance() - amount);
			destAccount.setBalance(destAccount.getBalance() + amount);
			accountList.add(srcAccount);
			accountList.add(destAccount);
			updatedRow = transferRepo.updateAcount(accountList);
			LOG.info("Inside DepositServiceImpl :: Updated {} Accounts  ",updatedRow);
			withdrawlTrans.setAccountNumber(srcAct);
			withdrawlTrans.setTransactionAmount(amount);
			withdrawlTrans.setTransactionType("W");
			depositTrans.setAccountNumber(destAct);
			depositTrans.setTransactionAmount(amount);
			depositTrans.setTransactionType("D");

			List<Transaction> transactioList = new ArrayList<Transaction>();
			transactioList.add(depositTrans);
			transactioList.add(withdrawlTrans);
			if (updatedRow != null && updatedRow.length > 0) {
				transferRepo.updateTransaction(transactioList);
			}

		} catch (Exception e) {
			LOG.error("Exception occured while updating Accounts :: {}",e.getMessage());
			throw new Exception("Exception occured while updating Accounts :: " + e.getMessage());
		}

		return accountList;
	}

	@Override
	public Optional<Long> getCustomerAccountBalance(String custId) throws ResourceNotFoundException {
		LOG.info("Inside DepositServiceImpl :: getCustomerAccountBalance {} ",custId);
		Optional<Long> customerAccountBalance = transferRepo.getCustomerAccountBalance(custId);
		LOG.info("Inside DepositServiceImpl :: retrieved CustomerAccountBalance {} ",customerAccountBalance);
		  return customerAccountBalance;
	}

	@Override
	public List<Transaction> getCustomerTransactionHistory(String id) throws ResourceNotFoundException {
		LOG.info("Inside DepositServiceImpl :: getCustomerTransactionHistory {} ",id);
		List<Transaction> transactionHistory = transferRepo.getTransactionHistory(id);
		LOG.info("Inside DepositServiceImpl :: retrieved CustomerTransactionHistory {} ",transactionHistory);
		return transactionHistory;
	}

}
