package com.judge.gdax;

import java.util.ArrayList;
import java.util.List;

public class BookStatus
{
	private static final int MAX_HISTORY_PRICE_IN_MINS = 15;
	private List<Double> pricePerMinute;
	private double currentPrice;

	public BookStatus()
	{
		pricePerMinute = new ArrayList<Double>();
	}

	public void updateBook()
	{
		addPrice(currentPrice);
	}

	public void setCurrentPrice(double price)
	{
		currentPrice = price;
	}

	public double getCurrentPrice()
	{
		return currentPrice;
	}

	public void addPrice(double price)
	{
		pricePerMinute.add(0, price);

		if (pricePerMinute.size() > MAX_HISTORY_PRICE_IN_MINS * 60)
		{
			pricePerMinute.remove(MAX_HISTORY_PRICE_IN_MINS * 60);
		}
	}

	public double getAvgPriceHistory(int minutes)
	{
		List<Double> prices = pricePerMinute.subList(0, minutes);
		return getAverage(prices);
	}

	private double getAverage(List<Double> list)
	{
		double sum = 0;
		for (Double element : list)
		{
			sum += element;
		}
		return sum / list.size();
	}

	public boolean isPriceHistoryFull()
	{
		if (pricePerMinute.size() == MAX_HISTORY_PRICE_IN_MINS * 60)
		{
			return true;
		} else
		{
			return false;
		}
	}

	public int getPriceHistorySize()
	{
		return pricePerMinute.size();
	}

	public double getSlope(int minutes)
	{
		return (currentPrice - pricePerMinute.get(minutes * 60 - 1)) / minutes;
	}
}
