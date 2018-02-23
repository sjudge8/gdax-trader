package com.judge.gdax.algorithm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.judge.gdax.BookStatus;
import com.judge.gdax.ExchangeInfo;
import com.judge.gdax.Trader;
import com.judge.gdax.Wallet;
import com.judge.gdax.alerts.SmsAlerts;
import com.judge.gdax.orders.OrderService;

public class FirstLimitAlgorithm implements Algorithm
{
	private static final int SHORT_SLOPE = 2;
	private static final int LONG_SLOPE = 4;
	private static Logger logger = LogManager.getLogger(Trader.class.getName());
	private OrderService orderService = new OrderService();
	private long startTime = System.currentTimeMillis();
	private Wallet wallet;
	private BookStatus book;
	private double currentPriceUsd;
	private SmsAlerts alert = new SmsAlerts();
	private boolean didBuy = false;

	@Override
	public void run(ExchangeInfo exchangeInfo)
	{
		if (System.currentTimeMillis() - (LONG_SLOPE * 60000) > startTime)
		{
			logger.trace("algo started!");
			book = exchangeInfo.getBookStatus();
			wallet = exchangeInfo.getWallet();
			currentPriceUsd = exchangeInfo.getBookStatus().getCurrentPrice();

			boolean didBuy = buy();
			
			if (!didBuy)
			{
				sell();
			}
			
		}
		else
		{
			logger.trace("Waiting to start algo...");
		}

	}

	private boolean buy()
	{
		// if SMALL_SLOPE > LARGE_SLOPE AND positive or close to positive AND no pending
		// sells, then buy
		if (wallet.amWaitingToBuy() && book.getSlope(SHORT_SLOPE) > book.getSlope(LONG_SLOPE))
		{
			try
			{
				orderService.limitBuy(USD_LIMIT / currentPriceUsd, currentPriceUsd - 0.01);

				wallet.setWaitingToBuy(false);
				wallet.setLastBuyUsdPrice(currentPriceUsd);
				wallet.setLastBuyCrypto(USD_LIMIT / currentPriceUsd);

				logger.warn("Buy! " + wallet.getLastBuyCrypto() + CRYPTOCURRENCY + " at $" + currentPriceUsd
						+ " Slope(" + SHORT_SLOPE + ") was " + book.getSlope(SHORT_SLOPE) + " and Slope(" + LONG_SLOPE
						+ ") was " + book.getSlope(LONG_SLOPE));

				// alert.sendAlert("Bought " + String.format("%.5f", wallet.getLastBuyCrypto())
				// + CRYPTOCURRENCY
				// + " at $" + currentPriceUsd);

				// TODO: implement this loop to keep updating the limit order until it buys or
				// until the opportunity passes
				while (!didBuy)
				{
					didBuy = updateBuy();
				}

				return true;

			} catch (Exception e)
			{
				logger.error("Error executing buy! ", e);
				alert.sendAlert("Error executing buy!");
				System.exit(-1);
				return false;
			}
		}
		else
		{
			return false;
		}
	}

	private boolean sell()
	{
		// if slope of last 3 minutes < slope last 10 minutes AND greater than (the buy
		// price - 1%), then sell
		if (!wallet.amWaitingToBuy() && book.getSlope(SHORT_SLOPE) < book.getSlope(LONG_SLOPE)
				&& book.getSlope(SHORT_SLOPE) < 0.00000
				&& currentPriceUsd > wallet.getLastBuyUsdPrice())
		{
			wallet.setWaitingToBuy(true);

			try
			{
				orderService.limitSell(wallet.getLastBuyCrypto(), currentPriceUsd + 0.01);

				logger.warn("Sell " + wallet.getLastBuyCrypto() + CRYPTOCURRENCY + " at " + currentPriceUsd
						+ " Slope(" + SHORT_SLOPE + ") was " + book.getSlope(SHORT_SLOPE) + " and Slope(" + LONG_SLOPE
						+ ") was " + book.getSlope(LONG_SLOPE) + ". Profit would be $"
						+ (((USD_LIMIT / currentPriceUsd - wallet.getLastBuyCrypto()) * currentPriceUsd)
								- (USD_LIMIT * 0.003)
								- ((USD_LIMIT
										+ (currentPriceUsd * (USD_LIMIT / currentPriceUsd - wallet.getLastBuyCrypto())))
										* 0.003)));

				// alert.sendAlert("Sold " + String.format("%.5f", wallet.getLastBuyCrypto()) +
				// CRYPTOCURRENCY
				// + " at $" + currentPriceUsd);



				return true;

			} catch (Exception e)
			{
				logger.error("Error executing sell! Tried to sell "
						+ wallet.getLastBuyCrypto() + CRYPTOCURRENCY + "!", e);
				alert.sendAlert("Error executing sell!");
				wallet.setWaitingToBuy(false);
				// System.exit(-1);
				return false;
			}
		}
		else
		{
			return false;
		}
	}

	// TODO: update existing buy limit order
	private boolean updateBuy()
	{
		return false;
	}

}