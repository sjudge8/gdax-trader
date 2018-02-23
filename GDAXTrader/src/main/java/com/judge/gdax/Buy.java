package com.judge.gdax;

public class Buy
{
	private double usdRate;
	private double usdCost;
	private double cryptoCost;

	public Buy(double usdCost, double usdRate)
	{
		this.usdCost = usdCost;
		this.usdRate = usdRate;
	}
}
