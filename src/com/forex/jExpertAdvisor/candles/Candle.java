package com.forex.jExpertAdvisor.candles;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Candle {

	private BigDecimal open;
	private BigDecimal close;
	private BigDecimal high;
	private BigDecimal low;
	private Date date;
	List<Candle> subCandles;
	public BigDecimal getOpen() {
		return open;
	}
	public BigDecimal getClose() {
		return close;
	}
	public BigDecimal getHigh() {
		return high;
	}
	public BigDecimal getLow() {
		return low;
	}
	public Date getDate() {
		return date;
	}
	
	public List<Candle> getSubCandles() {
		return subCandles;
	}
	public Candle(BigDecimal open, BigDecimal close, BigDecimal high, BigDecimal low, Date date,
			List<Candle> subCandles) {
		super();
		this.open = open;
		this.close = close;
		this.high = high;
		this.low = low;
		this.date = date;
		this.subCandles = subCandles;
	}
	
	
	
	
	
	
	
	
	
	
}
