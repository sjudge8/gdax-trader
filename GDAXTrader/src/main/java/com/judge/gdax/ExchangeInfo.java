package com.judge.gdax;

public class ExchangeInfo
{
	private BookStatus bookStatus;
	private Wallet wallet;

	public ExchangeInfo()
	{
		bookStatus = new BookStatus();
		wallet = new Wallet();
	}

	public BookStatus getBookStatus()
	{
		return bookStatus;
	}

	public Wallet getWallet()
	{
		return wallet;
	}
}
