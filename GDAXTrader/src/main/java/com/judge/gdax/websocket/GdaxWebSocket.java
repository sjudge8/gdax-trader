package com.judge.gdax.websocket;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.judge.gdax.BookStatus;
import com.judge.gdax.ExchangeInfo;
import com.judge.gdax.GdaxProperties;
import com.judge.gdax.algorithm.FirstAlgorithm;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;

public class GdaxWebSocket
{
	public static final String WS_ENDPOINT = "wss://ws-feed.gdax.com";
	public static final String PRICE_STRING = "price\":";

	private static Logger logger = LogManager.getLogger(GdaxWebSocket.class.getName());
	private static final int TEN_SECONDS = 10000;
	private ExchangeInfo exchangeInfo;
	private FirstAlgorithm firstAlgo;

	public GdaxWebSocket(String jsonRequest)
	{
		exchangeInfo = new ExchangeInfo();
		firstAlgo = new FirstAlgorithm();

		try
		{
			WebSocket ws = new WebSocketFactory().createSocket(WS_ENDPOINT, TEN_SECONDS);
			ws.addListener(new WebSocketAdapter()
			{
				@Override
				public void onTextMessage(WebSocket ws, String message)
				{
					int priceIndex = message.indexOf(PRICE_STRING);

					if (priceIndex != -1)
					{
						double price = Double.parseDouble(message.substring(priceIndex + 8, priceIndex + 16));
						exchangeInfo.getBookStatus().setCurrentPrice(price);
						if (!exchangeInfo.getWallet().isInited())
						{
							exchangeInfo.getWallet().initBalances(price);
							logger.debug("USD wallet balance is: " + exchangeInfo.getWallet().getUsd() + " "
									+ GdaxProperties.CRYPTOCURRENCY + " wallet balance is: "
									+ exchangeInfo.getWallet().getCrypto());
						}
						logger.debug("price is " + price);
					}

					if (exchangeInfo.getWallet().isInited())
					{
						logger.trace("running algorithm...");
						firstAlgo.run(exchangeInfo);
					}

					// Received a response. Print the received message.
					logger.trace(message);
				}
			});

			ws.connect().sendText(jsonRequest);

		} catch (IOException | WebSocketException e)
		{
			logger.error("Error initializing websocket!", e);
		}
	}

	public BookStatus getBookStatus()
	{
		return exchangeInfo.getBookStatus();
	}

}
