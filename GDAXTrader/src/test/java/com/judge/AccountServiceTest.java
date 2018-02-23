package com.judge;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.BeforeClass;
import org.junit.Test;

import com.judge.gdax.App;
import com.judge.gdax.accounts.AccountNotFoundException;
import com.judge.gdax.accounts.AccountService;

public class AccountServiceTest
{
	@BeforeClass
	public static void runOnceBeforeClass()
	{
		App.loadProps();
	}

	@Test
	public void test_get_account_balance() throws NumberFormatException, AccountNotFoundException
	{
		double qty1 = Double.parseDouble(new AccountService().getAccount("USD").getBalance());
		double qty2 = new AccountService().getAccountBalance("USD");

		assertThat(qty1, is(qty2));
	}
}