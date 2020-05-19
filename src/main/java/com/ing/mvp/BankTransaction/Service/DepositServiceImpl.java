package com.ing.mvp.BankTransaction.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ing.mvp.BankTransaction.Exception.NotAcceptableException;
import com.ing.mvp.BankTransaction.dao.TransferRepository;
import com.ing.mvp.BankTransaction.model.Account;
import com.ing.mvp.BankTransaction.model.Transaction;

@Service
public class DepositServiceImpl implements DepositService {

	@Autowired
	TransferRepository transferRepo;

	@Override
	public List<Account> depositMoney(String srcAct, String destAct, long amount) throws Exception {

		Account srcAccount = null;
		Account destAccount = null;
		Transaction depositTrans = new Transaction();
		Transaction withdrawlTrans = new Transaction();
		List<Account> accountList = new ArrayList<Account>();
		int[] updatedRow = null;
		try {

			srcAccount = transferRepo.getAccountBalance(srcAct);
			destAccount = transferRepo.getAccountBalance(destAct);

			if (srcAccount.getBalance() < amount) {
				throw new NotAcceptableException("Insucfficient funds to withdraw.");
			}

			srcAccount.setBalance(srcAccount.getBalance() - amount);
			destAccount.setBalance(destAccount.getBalance() + amount);
			accountList.add(srcAccount);
			accountList.add(destAccount);
			updatedRow = transferRepo.updateAcount(accountList);
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
			throw new Exception("Exception occured while updating Accounts :: " + e.getMessage());
		}

		return accountList;
	}

	@Override
	public Optional<Long> getCustomerAccountBalance(String custId) {
		// TODO Auto-generated method stub
		 Optional<Long> customerAccountBalance = transferRepo.getCustomerAccountBalance(custId);
		  return customerAccountBalance;
	}

}
