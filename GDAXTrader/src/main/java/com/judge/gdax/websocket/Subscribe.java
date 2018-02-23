package com.judge.gdax.websocket;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Subscribe
{
	@SuppressWarnings("unused")
	private String type = "subscribe";

	@SerializedName("product_ids")
	private List<String> productId = new ArrayList<String>();

	private List<String> channels = new ArrayList<String>();


	public Subscribe(String currency)
	{
		productId.add(currency + "-USD");
	}

	public void addChannel(String channel)
	{
		channels.add(channel);
	}
}
