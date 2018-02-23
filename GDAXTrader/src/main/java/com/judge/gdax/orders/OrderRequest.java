package com.judge.gdax.orders;

import com.google.gson.annotations.SerializedName;

public class OrderRequest
{
	private double size;
	private double price;
	private String side;
	private String type;
	@SerializedName("product_id")
	private String productId;

	public double getSize()
	{
		return size;
	}

	public void setSize(double size)
	{
		this.size = size;
	}

	public double getPrice()
	{
		return price;
	}

	public void setPrice(double price)
	{
		this.price = price;
	}

	public String getSide()
	{
		return side;
	}

	public void setSide(String side)
	{
		this.side = side;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getProductId()
	{
		return productId;
	}

	public void setProductId(String productId)
	{
		this.productId = productId;
	}

}
