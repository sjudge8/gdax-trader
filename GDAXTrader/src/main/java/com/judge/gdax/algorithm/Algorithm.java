package com.judge.gdax.algorithm;

import com.judge.gdax.ExchangeInfo;
import com.judge.gdax.GdaxProperties;

public interface Algorithm
{
	static final String CRYPTOCURRENCY = GdaxProperties.CRYPTOCURRENCY;
	static final double USD_LIMIT = GdaxProperties.USD_LIMIT;

	public void run(ExchangeInfo exchangeInfo);
}