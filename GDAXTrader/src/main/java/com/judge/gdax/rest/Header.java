package com.judge.gdax.rest;

public class Header
{
	private String signature;
	private String timestamp;

	public Header(String sig, String time)
	{
		signature = sig;
		timestamp = time;
	}

	public String getSignature()
	{
		return signature;
	}

	public String getTimestamp()
	{
		return timestamp;
	}
}