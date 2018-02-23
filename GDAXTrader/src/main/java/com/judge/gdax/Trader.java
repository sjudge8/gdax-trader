package com.judge.gdax;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.judge.gdax.accounts.Account;
import com.judge.gdax.accounts.AccountService;
import com.judge.gdax.websocket.GdaxWebSocket;
import com.judge.gdax.websocket.Subscribe;

public class Trader
{
	private static Logger logger = LogManager.getLogger(Trader.class.getName());
	private Gson gson = new Gson();
	private Subscribe sub = new Subscribe(GdaxProperties.CRYPTOCURRENCY);
	private BookStatus bookStatus;

	public void start()
	{
		logger.info("Starting " + GdaxProperties.CRYPTOCURRENCY + " Trader...");

		Account[] accounts = new AccountService().getAccounts();
		for (Account account : accounts)
		{
			logger.info(account.getCurrency() + " balance is " + account.getBalance());
		}

		sub.addChannel("ticker");

		GdaxWebSocket conn = new GdaxWebSocket(gson.toJson(sub));
		bookStatus = conn.getBookStatus();

		while (bookStatus.getCurrentPrice() < 0.01)
		{
			logger.trace("Waiting for first price to show up. Current price is: "
					+ bookStatus.getCurrentPrice());
		}


		BookUpdaterThread bookThread = new BookUpdaterThread(bookStatus);
		bookThread.start();

	}

	static class BookUpdaterThread extends Thread
	{

		BookStatus book;

		BookUpdaterThread(BookStatus book)
		{
			this.book = book;
		}

		public void run()
		{
			try
			{
				long startTime = System.nanoTime();
				long finishTime;
				long millisecondsElapsed = 0;
				long sleepTime = 0;

				while (true)
				{
					startTime = System.nanoTime();
					book.updateBook();

					logger.debug("BookStatus Updated! Current price is: " + book.getCurrentPrice());

					if (book.getPriceHistorySize() > 10 * 60)
					{
						logger.debug("Avg price last minute = " + book.getAvgPriceHistory(1));
						logger.debug("Avg price last 2 minutes = " + book.getAvgPriceHistory(2));
						logger.debug("Avg price last 3 minutes = " + book.getAvgPriceHistory(3));
						logger.debug("Avg price last 5 minutes = " + book.getAvgPriceHistory(5));
						logger.debug("Avg price last 10 minutes = " + book.getAvgPriceHistory(10));

						logger.debug("Slope of last minute = " + book.getSlope(1));
						logger.debug("Slope of last 2 minutes = " + book.getSlope(2));
						logger.debug("Slope of last 3 minutes = " + book.getSlope(3));
						logger.debug("Slope of last 5 minutes = " + book.getSlope(5));
						logger.debug("Slope of last 10 minutes = " + book.getSlope(10));
					}

					finishTime = System.nanoTime();

					millisecondsElapsed = (finishTime - startTime) / 100000;
					sleepTime = 1000 - millisecondsElapsed;
					try
					{
						if (sleepTime > 0)
						{
							logger.debug("sleep for " + sleepTime + " milliseconds");
							Thread.sleep(sleepTime);
						}
						else
						{
							logger.warn("Took longer than 1 second to update book!");
						}
					} catch (InterruptedException e)
					{
						logger.error("BookUpdaterThread error in Thread.sleep", e);
					}
				}
			} catch (Exception e)
			{
				logger.error("BookUpdaterThread exited! ", e);
			}
		}

	}
}
