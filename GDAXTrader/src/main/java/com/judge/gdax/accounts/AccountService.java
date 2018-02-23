package com.judge.gdax.accounts;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.judge.gdax.rest.GdaxRESTClient;
import com.judge.gdax.rest.Header;

public class AccountService
{
	private Gson gson;
	private GdaxRESTClient gdax;
	private static Logger logger = LogManager.getLogger(AccountService.class.getName());

	private static final String ACCOUNT_ENDPOINT = "/accounts";

	public AccountService()
	{
		gdax = new GdaxRESTClient();
		gson = new Gson();
	}

	public Account getAccount(String currency) throws AccountNotFoundException
	{
		String epoch = Instant.now().getEpochSecond() + "";
		String signature = gdax.generateSignature(ACCOUNT_ENDPOINT, "GET", "", epoch);
		Header header = new Header(signature, epoch);

		String json = null;
		Account[] accounts = null;

		try
		{
			URL accountURL = new URL(GdaxRESTClient.API_ENDPOINT + ACCOUNT_ENDPOINT);
			json = gdax.GET(accountURL, header);

			accounts = gson.fromJson(json, Account[].class);

		} catch (IOException e)
		{
			logger.error("Error getting GDAX account!", e);
		}

		for (Account acc : accounts)
		{
			if (acc.getCurrency().equals(currency))
			{
				return acc;
			}
		}

		throw new AccountNotFoundException("Account for currency " + currency + " not found!");
	}

	public Account[] getAccounts()
	{
		String epoch = Instant.now().getEpochSecond() + "";
		String signature = gdax.generateSignature(ACCOUNT_ENDPOINT, "GET", "", epoch);
		Header header = new Header(signature, epoch);

		String json = null;
		Account[] accounts = null;

		try
		{
			URL accountURL = new URL(GdaxRESTClient.API_ENDPOINT + ACCOUNT_ENDPOINT);
			json = gdax.GET(accountURL, header);

			accounts = gson.fromJson(json, Account[].class);

		} catch (IOException e)
		{
			logger.error("Error getting GDAX accounts!", e);
		}

		return accounts;
	}

	public double getAccountBalance(String currency) throws AccountNotFoundException
	{
		Account account = getAccount(currency);

		return Double.parseDouble(account.getAvailable());
	}
}
