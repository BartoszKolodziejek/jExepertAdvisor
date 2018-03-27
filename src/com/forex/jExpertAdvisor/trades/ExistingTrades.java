package com.forex.jExpertAdvisor.trades;

import java.util.HashMap;
import java.util.LinkedList;

public class ExistingTrades extends LinkedList<Trade> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected static ExistingTrades instance = null;


	
	protected ExistingTrades() {
		
	}
	
	public static ExistingTrades getInstance() {
		if(instance == null)
			instance =  new ExistingTrades();
		return instance;
	}
	
	

}
