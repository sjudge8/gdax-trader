package com.judge.gdax.orders;

public class OrderResponse
{
	private String id;
	private double price;
	private double size;
	private String product_id;
	private String side;
	private String type;

	public String getId()
	{
		return id;
	}

	public double getPrice()
	{
		return price;
	}

	public double getSize()
	{
		return size;
	}

	public String getProduct_id()
	{
		return product_id;
	}

	public String getSide()
	{
		return side;
	}

	public String getType()
	{
		return type;
	}
}
