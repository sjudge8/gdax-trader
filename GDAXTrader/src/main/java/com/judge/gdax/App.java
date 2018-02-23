package com.judge.gdax;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App 
{
	static Logger logger = LogManager.getLogger(App.class.getName());
	static Properties prop;
	static GdaxProperties gdaxProperties;

	public static void main(String[] args)
    {
		logger.info("Starting app...");

		loadProperties();
		loadCreds();
		gdaxProperties = new GdaxProperties();

		Trader trader = new Trader();
		trader.start();
	}

	private static void loadProperties()
	{
		prop = new Properties();
		InputStream input = null;

		try
		{
			input = new FileInputStream("GDAXTrader.properties");
			prop.load(input);

		} catch (IOException e)
		{
			logger.error("Error Loading Properties File!", e);
			System.exit(-1);
		} finally
		{
			if (input != null)
			{
				try
				{
					input.close();
				} catch (IOException e)
				{
					logger.error("Error Closing Properties File!", e);
					System.exit(-1);
				}
			}
		}
    }

	private static void loadCreds()
	{
		InputStream input = null;

		try
		{
			input = new FileInputStream("creds");
			prop.load(input);

		} catch (IOException e)
		{
			logger.error("Error Loading creds file!", e);
			System.exit(-1);
		} finally
		{
			if (input != null)
			{
				try
				{
					input.close();
				} catch (IOException e)
				{
					logger.error("Error Closing creds file!", e);
					System.exit(-1);
				}
			}
		}
	}

	public static void loadProps()
	{
		loadProperties();
		loadCreds();
		gdaxProperties = new GdaxProperties();
	}
}
