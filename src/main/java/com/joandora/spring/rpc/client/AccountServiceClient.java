package com.joandora.spring.rpc.client;

import com.joandora.spring.rpc.Account;
import com.joandora.spring.rpc.AccountService;


public class AccountServiceClient implements AccountService {
	private AccountService service;
	
	@Override
	public Account getAccountById(String id) {
		return service.getAccountById(id);
	}

	public void setService(AccountService service) {
		this.service = service;
	}
	
}
