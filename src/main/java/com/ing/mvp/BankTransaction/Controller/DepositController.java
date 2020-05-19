package com.ing.mvp.BankTransaction.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ing.mvp.BankTransaction.Exception.NotAcceptableException;
import com.ing.mvp.BankTransaction.Exception.ResourceNotFoundException;
import com.ing.mvp.BankTransaction.Service.DepositService;
import com.ing.mvp.BankTransaction.model.Account;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "/DepositController",description = "Deposit Controller")
public class DepositController {

	@Autowired
	DepositService despositService;
	
	@ApiOperation(value = "/depositMoney")
	@RequestMapping(value = "/depositMoney/to/{toAct}/from/{fromAct}/amount/{amount}",method = RequestMethod.PUT)
	public ResponseEntity<List<Account>> depostMoney(@PathVariable(value = "toAct") String toAct,
								@PathVariable(value = "fromAct") String fromAct,
								@PathVariable(value = "amount") String amt) throws Exception {
		
		long longAmt = Long.parseLong(amt);
		if(longAmt < 0.01) {
			throw new NotAcceptableException("Amount to be deposited is less than min value (0.01 Euros)");
		}
		
		List<Account> accounts = despositService.depositMoney(toAct,fromAct,longAmt);
		
		return ResponseEntity.ok(accounts);
	}
	
	@ApiOperation(value = "/getBalance")
	@RequestMapping(value = "/getBalance/customerId/{id}",method = RequestMethod.PUT)
	public ResponseEntity<Optional<Long>> getAccountBalance(@PathVariable(value = "id") String id) throws Exception {
		
		Optional<Long> customerAccountBalance = despositService.getCustomerAccountBalance(id);
		customerAccountBalance.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + id));
		return ResponseEntity.ok(customerAccountBalance);
	}
	
	
	@ApiOperation(value = "/getTransactionHistory")
	@RequestMapping(value = "/getTransactionHistory/customerId/{id}",method = RequestMethod.PUT)
	public ResponseEntity<Optional<Long>> getTransactionHistory(@PathVariable(value = "id") String id) throws Exception {
		
		//Optional<Long> customerAccountBalance = despositService.getCustomerAccountBalance(id);
		//customerAccountBalance.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + id));
		//return ResponseEntity.ok(customerAccountBalance);
		despositService.getCustomerTransactionHistory(id);
		return null;
	}
	
	
	
}
