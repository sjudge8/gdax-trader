package com.judge.gdax.orders;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.time.Instant;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.judge.gdax.GdaxProperties;
import com.judge.gdax.rest.GdaxRESTClient;
import com.judge.gdax.rest.Header;

public class OrderService
{
	private Gson gson;
	private GdaxRESTClient gdax;
	private static Logger logger = LogManager.getLogger(OrderService.class.getName());

	private static final String ORDER_ENDPOINT = "/orders";

	public OrderService()
	{
		gdax = new GdaxRESTClient();
		gson = new Gson();
	}

	public OrderResponse limitBuy(double cryptoSize, double usdRate)
	{
		String epoch = Instant.now().getEpochSecond() + "";
		String jsonRequest = null, jsonResponse = null;
		OrderResponse orderResponse = null;

		BigDecimal bd = new BigDecimal(cryptoSize);
		bd = bd.setScale(8, RoundingMode.DOWN);
		cryptoSize = bd.doubleValue();

		OrderRequest orderRequest = new OrderRequest();
		orderRequest.setProductId(GdaxProperties.CRYPTOCURRENCY + "-USD");
		orderRequest.setSide("buy");
		orderRequest.setSize(cryptoSize);
		orderRequest.setPrice(usdRate);

		jsonRequest = gson.toJson(orderRequest, OrderRequest.class);

		String signature = gdax.generateSignature(ORDER_ENDPOINT, "POST", jsonRequest, epoch);
		Header header = new Header(signature, epoch);

		try
		{
			URL orderURL = new URL(GdaxRESTClient.API_ENDPOINT + ORDER_ENDPOINT);
			jsonResponse = gdax.POST(orderURL, header, jsonRequest);

			orderResponse = gson.fromJson(jsonResponse, OrderResponse.class);

		} catch (IOException e)
		{
			logger.error("Error executing buy!", e);
			System.exit(-1);
		}

		return orderResponse;
	}

	public OrderResponse limitSell(double cryptoSize, double usdRate)
	{
		String epoch = Instant.now().getEpochSecond() + "";
		String jsonRequest = null, jsonResponse = null;
		OrderResponse orderResponse = null;

		BigDecimal bd = new BigDecimal(cryptoSize);
		bd = bd.setScale(8, RoundingMode.DOWN);
		cryptoSize = bd.doubleValue();

		OrderRequest orderRequest = new OrderRequest();
		orderRequest.setProductId(GdaxProperties.CRYPTOCURRENCY + "-USD");
		orderRequest.setSide("sell");
		orderRequest.setSize(cryptoSize);
		orderRequest.setPrice(usdRate);

		jsonRequest = gson.toJson(orderRequest, OrderRequest.class);

		String signature = gdax.generateSignature(ORDER_ENDPOINT, "POST", jsonRequest, epoch);
		Header header = new Header(signature, epoch);

		try
		{
			URL orderURL = new URL(GdaxRESTClient.API_ENDPOINT + ORDER_ENDPOINT);
			jsonResponse = gdax.POST(orderURL, header, jsonRequest);

			orderResponse = gson.fromJson(jsonResponse, OrderResponse.class);

		} catch (IOException e)
		{
			logger.error("Error executing sell!", e);
		}

		return orderResponse;
	}

	public OrderResponse marketBuy(double cryptoSize)
	{
		String epoch = Instant.now().getEpochSecond() + "";
		String jsonRequest = null, jsonResponse = null;
		OrderResponse orderResponse = null;

		BigDecimal bd = new BigDecimal(cryptoSize);
		bd = bd.setScale(8, RoundingMode.DOWN);
		cryptoSize = bd.doubleValue();

		OrderRequest orderRequest = new OrderRequest();
		orderRequest.setProductId(GdaxProperties.CRYPTOCURRENCY + "-USD");
		orderRequest.setSide("buy");
		orderRequest.setType("market");
		orderRequest.setSize(cryptoSize);

		jsonRequest = gson.toJson(orderRequest, OrderRequest.class);

		String signature = gdax.generateSignature(ORDER_ENDPOINT, "POST", jsonRequest, epoch);
		Header header = new Header(signature, epoch);

		try
		{
			URL orderURL = new URL(GdaxRESTClient.API_ENDPOINT + ORDER_ENDPOINT);
			jsonResponse = gdax.POST(orderURL, header, jsonRequest);

			orderResponse = gson.fromJson(jsonResponse, OrderResponse.class);

		} catch (IOException e)
		{
			logger.error("Error executing buy!", e);
			System.exit(-1);
		}

		return orderResponse;
	}

	public OrderResponse marketSell(double cryptoSize)
	{
		String epoch = Instant.now().getEpochSecond() + "";
		String jsonRequest = null, jsonResponse = null;
		OrderResponse orderResponse = null;

		BigDecimal bd = new BigDecimal(cryptoSize);
		bd = bd.setScale(8, RoundingMode.DOWN);
		cryptoSize = bd.doubleValue();

		OrderRequest orderRequest = new OrderRequest();
		orderRequest.setProductId(GdaxProperties.CRYPTOCURRENCY + "-USD");
		orderRequest.setSide("sell");
		orderRequest.setType("market");
		orderRequest.setSize(cryptoSize);

		jsonRequest = gson.toJson(orderRequest, OrderRequest.class);

		String signature = gdax.generateSignature(ORDER_ENDPOINT, "POST", jsonRequest, epoch);
		Header header = new Header(signature, epoch);

		try
		{
			URL orderURL = new URL(GdaxRESTClient.API_ENDPOINT + ORDER_ENDPOINT);
			jsonResponse = gdax.POST(orderURL, header, jsonRequest);

			orderResponse = gson.fromJson(jsonResponse, OrderResponse.class);

		} catch (IOException e)
		{
			logger.error("Error executing sell!", e);
		}

		return orderResponse;
	}

	public OrderResponse[] getOrders()
	{
		String epoch = Instant.now().getEpochSecond() + "";
		String signature = gdax.generateSignature(ORDER_ENDPOINT, "GET", "", epoch);
		Header header = new Header(signature, epoch);

		String json = null;
		OrderResponse[] orders = null;

		try
		{
			URL orderURL = new URL(GdaxRESTClient.API_ENDPOINT + ORDER_ENDPOINT);
			json = gdax.GET(orderURL, header);

			orders = gson.fromJson(json, OrderResponse[].class);

		} catch (IOException e)
		{
			logger.error("Error getting open orders!", e);
		}

		return orders;
	}

	public boolean hasOpenOrders()
	{
		if (getOrders().length > 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
