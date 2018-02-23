package com.judge.gdax.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.judge.gdax.GdaxProperties;

public class RESTClient
{
	private static Logger logger = LogManager.getLogger(RESTClient.class.getName());

	public String GET(URL url) throws IOException
	{
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");

		if (conn.getResponseCode() != HttpsURLConnection.HTTP_OK)
		{
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getErrorStream())));
			logger.error("Error with GET request, http response code is " + conn.getResponseCode());
			while ((br.readLine()) != null)
			{
				logger.error(br.readLine());
			}
			System.exit(-1);
		}

		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
		String output = br.readLine();

		conn.disconnect();

		return output;
	}

	public String GET(URL url, Header header) throws IOException
	{
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
		conn.setRequestProperty("CB-ACCESS-SIGN", header.getSignature());
		conn.setRequestProperty("CB-ACCESS-TIMESTAMP", header.getTimestamp());
		conn.setRequestProperty("CB-ACCESS-KEY", GdaxProperties.API_KEY);
		conn.setRequestProperty("CB-ACCESS-PASSPHRASE", GdaxProperties.PASSPHRASE);
		conn.setRequestProperty("Content-Type", "application/json");


		if (conn.getResponseCode() != HttpsURLConnection.HTTP_OK)
		{
			String output = "";
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getErrorStream())));
			logger.error("Error with GET request, http response code is " + conn.getResponseCode());
			while ((output = br.readLine()) != null)
			{
				logger.error(output);
			}
			System.exit(-1);
		}

		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

		String output = br.readLine();

		conn.disconnect();

		return output;
	}

	public String POST(URL url, Header header, String body) throws IOException
	{
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Accept", "application/json");
		conn.setRequestProperty("CB-ACCESS-SIGN", header.getSignature());
		conn.setRequestProperty("CB-ACCESS-TIMESTAMP", header.getTimestamp());
		conn.setRequestProperty("CB-ACCESS-KEY", GdaxProperties.API_KEY);
		conn.setRequestProperty("CB-ACCESS-PASSPHRASE", GdaxProperties.PASSPHRASE);
		conn.setRequestProperty("Content-Type", "application/json");

		// send the json as body of the request
		OutputStream outputStream = conn.getOutputStream();
		outputStream.write(body.getBytes("UTF-8"));
		outputStream.close();

		conn.connect();

		if (conn.getResponseCode() != HttpsURLConnection.HTTP_OK)
		{
			String output = "";
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getErrorStream())));
			logger.error("Error with POST request, http response code is " + conn.getResponseCode());
			while ((output = br.readLine()) != null)
			{
				logger.error(output);
			}
		}

		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

		String output = br.readLine();

		conn.disconnect();

		return output;
	}

}
