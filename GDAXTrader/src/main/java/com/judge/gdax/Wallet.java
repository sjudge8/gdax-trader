package com.judge.gdax;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.judge.gdax.accounts.AccountNotFoundException;
import com.judge.gdax.accounts.AccountService;

public class Wallet
{
	private static final String CRYPTOCURRENCY = GdaxProperties.CRYPTOCURRENCY;
	private static final double USD_LIMIT = GdaxProperties.USD_LIMIT;

	private double crypto;
	private double usd;
	private double lastBuyUsdPrice;
	private double lastBuyCrypto;
	private boolean inited = false;
	private boolean waitingToBuy = true;

	public void initBalances(double priceInUsd)
	{
		AccountService accountService = new AccountService();

		try
		{
			usd = accountService.getAccountBalance("USD");

			if (usd > USD_LIMIT)
			{
				usd = USD_LIMIT;
			}

		} catch (AccountNotFoundException e)
		{
			usd = 0.0;
		}

		try
		{
			crypto = accountService.getAccountBalance(CRYPTOCURRENCY);

			if (crypto * priceInUsd > USD_LIMIT)
			{
				BigDecimal bd = new BigDecimal(USD_LIMIT / priceInUsd);
				bd = bd.setScale(8, RoundingMode.DOWN);
				crypto = bd.doubleValue();
			}

		} catch (AccountNotFoundException e)
		{
			crypto = 0.0;
		}

		inited = true;

	}

	public boolean isInited()
	{
		return inited;
	}

	public double getUsd()
	{
		return usd;
	}

	public void setUsd(double usd)
	{
		this.usd = usd;
	}

	public double getCrypto()
	{
		return crypto;
	}

	public void setCrypto(double crypto)
	{
		this.crypto = crypto;
	}

	public void setLastBuyUsdPrice(double usd)
	{
		lastBuyUsdPrice = usd;
	}

	public double getLastBuyUsdPrice()
	{
		return lastBuyUsdPrice;
	}

	public double getLastBuyCrypto()
	{
		return lastBuyCrypto;
	}

	public void setLastBuyCrypto(double crypto)
	{
		lastBuyCrypto = crypto;
	}

	public void setWaitingToBuy(boolean waitingToBuy)
	{
		this.waitingToBuy = waitingToBuy;
	}

	public boolean amWaitingToBuy()
	{
		return waitingToBuy;
	}
}
