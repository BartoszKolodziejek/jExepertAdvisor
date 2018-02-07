package com.forex.jExpertAdvisor.trades;

import java.util.HashMap;

public class ExistingTrades extends HashMap<Long, Trade> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static ExistingTrades instance = null;
	private static Long iter = new Long(-1);
	
	
	public Long nextVal() {
		return iter++;
	}
	
	private ExistingTrades() {
		
	}
	
	public static ExistingTrades getInstance() {
		if(instance == null)
			return new ExistingTrades();
		return instance;
	}
	
	

}
