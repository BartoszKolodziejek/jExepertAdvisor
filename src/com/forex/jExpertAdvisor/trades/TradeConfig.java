package com.forex.jExpertAdvisor.trades;

public class TradeConfig {
	
	
	private static TradeConfig instance = null;
	private static String account = "test";
	private static String symbol;
	private static int currentSubcandle;
	
	

	public static int getCurrentSubcandle() {
		return currentSubcandle;
	}

	public static int nextVal() {
		TradeConfig.currentSubcandle++;
		return currentSubcandle;
		
	}

	public static String getSymbol() {
		return symbol;
	}

	public static void setSymbol(String symbol) {
		TradeConfig.symbol = symbol;
	}

	static String getAccount() {
		return account;
	}

	static void setAccount(String account) {
		TradeConfig.account = account;
	}
private TradeConfig() {
	
}

public static TradeConfig getInstance() {
	if(instance==null)
		return new TradeConfig();
	return instance;
}

	
	

}
