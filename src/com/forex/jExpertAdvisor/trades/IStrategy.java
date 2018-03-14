package com.forex.jExpertAdvisor.trades;

public abstract class IStrategy {


	private String symbol;

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public abstract void OnInit();
	
	public abstract void OnDenit();
	public abstract void OnStart();
	

}
