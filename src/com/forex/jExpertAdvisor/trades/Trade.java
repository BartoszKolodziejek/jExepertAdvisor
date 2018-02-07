package com.forex.jExpertAdvisor.trades;

import java.math.BigDecimal;
import java.util.Date;

import com.forex.jExpertAdvisor.stoplosses.StopLoss;

public class Trade {
	
	
	
	private BigDecimal open;
	private BigDecimal profit;
	private Date dateOpen;
	
	private StopLoss stoploss;
	private TradeType type;
	
	
	
	
	
	public StopLoss getStoploss() {
		return stoploss;
	}
		public TradeType getType() {
		return type;
	}
	public Trade(BigDecimal open, BigDecimal profit, Date dateOpen, StopLoss stoploss, TradeType type) {
		super();
		this.open = open;
		this.profit = profit;
		this.dateOpen = dateOpen;
		this.stoploss = stoploss;
		this.type = type;
	}
	void setOpen(BigDecimal open) {
		this.open = open;
	}
	void setProfit(BigDecimal profit) {
		this.profit = profit;
	}
	void setDateOpen(Date dateOpen) {
		this.dateOpen = dateOpen;
	}
	
	public BigDecimal getOpen() {
		return open;
	}
	public BigDecimal getProfit() {
		return profit;
	}
	public Date getDateOpen() {
		return dateOpen;
	}
	


	
	

}
