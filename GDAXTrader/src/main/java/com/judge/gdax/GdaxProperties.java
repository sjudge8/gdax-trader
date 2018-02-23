package com.judge.gdax;

public class GdaxProperties
{
	public static String CRYPTOCURRENCY;
	public static double USD_LIMIT;
	public static String PASSPHRASE;
	public static String API_KEY;
	public static String SECRET_KEY;
	public static String ALERT_FROM_EMAIL_ADDRESS;
	public static String ALERT_FROM_EMAIL_PASSWORD;
	public static String ALERT_TO_EMAIL_ADDRESS;

	public GdaxProperties()
	{
		CRYPTOCURRENCY = App.prop.getProperty("Cryptocurrency");
		USD_LIMIT = Double.parseDouble(App.prop.getProperty("UsdLimit"));
		PASSPHRASE = App.prop.getProperty("GDAX.API.Passphrase");
		API_KEY = App.prop.getProperty("GDAX.API.ApiKey");
		SECRET_KEY = App.prop.getProperty("GDAX.API.SecretKey");
		ALERT_FROM_EMAIL_ADDRESS = App.prop.getProperty("Alerts.FromEmailAddress");
		ALERT_FROM_EMAIL_PASSWORD = App.prop.getProperty("Alerts.FromEmailPassword");
		ALERT_TO_EMAIL_ADDRESS = App.prop.getProperty("Alerts.ToEmailAddress");
	}
}
