package com.forex.jExpertAdvisor.trades;

import java.math.BigDecimal;

public class TradeConfig {
	
	
	private static TradeConfig instance = null;
	private static String account = "test";
	private static String symbol;
	private  int currentSubcandle;
	private final  int maxSubcandle;
	private static int max;
	
	
	
	
	
	
	

	

	public static void setMax(int max) {
		TradeConfig.max = max;
	}

	public  int getCurrentSubcandle() {
		return currentSubcandle;
	}

	public  int nextVal() {
		this.currentSubcandle++;
		if(currentSubcandle>maxSubcandle)
			currentSubcandle=0;
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
	protected TradeConfig(Integer maxSubcandle) {
	this.maxSubcandle = maxSubcandle;
}

public static TradeConfig getInstance() {
	if(instance==null)
		return new TradeConfig(max);
	return instance;
}

	
	

}
