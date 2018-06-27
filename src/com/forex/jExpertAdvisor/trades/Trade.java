package com.forex.jExpertAdvisor.trades;

import java.math.BigDecimal;
import java.util.Date;

import com.forex.jExpertAdvisor.stoplosses.StopLoss;

public class Trade {
	
	
	
	private BigDecimal open;
	private BigDecimal profit;
	private final Date dateOpen;
	private final BigDecimal size;
	private final StopLoss stoploss;
	private TradeType type;
	private BigDecimal point;
	private final IStrategy strategy;
	private final String symbol;

	public BigDecimal getPoint() {
		return point;
	}

	public String getSymbol() {
		return symbol;
	}

	public StopLoss getStoploss() {
		return stoploss;
	}
		public TradeType getType() {
		return type;
	}
	public Trade(BigDecimal open, BigDecimal profit, Date dateOpen, StopLoss stoploss, TradeType type, BigDecimal size, BigDecimal point, IStrategy strategy, String symbol) {
		super();
		this.point = point;
		this.size = size.multiply(new BigDecimal(100000));
		this.open = open;
		this.profit = profit;
		this.dateOpen = dateOpen;
		this.stoploss = stoploss;
		this.type = type;
		this.strategy = strategy;
		this.symbol = symbol;
	}

    public IStrategy getStrategy() {
        return strategy;
    }

    public BigDecimal getSize() {
		return size;
	}

	public void setProfit(BigDecimal profit) {
		this.profit = profit;
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
