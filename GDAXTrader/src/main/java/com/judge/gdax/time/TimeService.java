package com.judge.gdax.time;

import java.io.IOException;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.judge.gdax.rest.GdaxRESTClient;

public class TimeService
{
	private Gson gson;
	private GdaxRESTClient gdax;
	private static Logger logger = LogManager.getLogger(TimeService.class.getName());

	private static final String TIME_ENDPOINT = "/time";

	public Time getTime()
	{
		Time time = null;

		try
		{
			URL timeURL = new URL(GdaxRESTClient.API_ENDPOINT + TIME_ENDPOINT);
			time = gson.fromJson(gdax.GET(timeURL), Time.class);
		} catch (IOException e)
		{
			logger.error("Error getting GDAX time!", e);
		}

		return time;
	}
}
