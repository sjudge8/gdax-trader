package com.judge.gdax.accounts;

public class AccountNotFoundException extends Exception
{
	private static final long serialVersionUID = 6395709012063778223L;

	public AccountNotFoundException(String message)
	{
		super(message);
	}
}