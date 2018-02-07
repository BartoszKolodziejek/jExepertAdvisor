package com.forex.jExpertAdvisor.candles;

import java.math.BigDecimal;
import java.util.List;

public class Candle {

	private BigDecimal open;
	private BigDecimal close;
	private BigDecimal high;
	private BigDecimal low;
	boolean currentCandle;
	List<Candle> subCandles;
	public BigDecimal getOpen() {
		return open;
	}
	public void setOpen(BigDecimal open) {
		this.open = open;
	}
	public BigDecimal getClose() {
		return close;
	}
	public void setClose(BigDecimal close) {
		this.close = close;
	}
	public BigDecimal getHigh() {
		return high;
	}
	public void setHigh(BigDecimal high) {
		this.high = high;
	}
	public BigDecimal getLow() {
		return low;
	}
	public void setLow(BigDecimal low) {
		this.low = low;
	}
	public boolean isCurrentCandle() {
		return currentCandle;
	}
	public void setCurrentCandle(boolean currentCandle) {
		
		if(!currentCandle)
			subCandles = null;
		this.currentCandle = currentCandle;
	}
	public List<Candle> getSubCandles() {
		return subCandles;
	}
	public void setSubCandles(List<Candle> subCandles) {
		this.subCandles = subCandles;
	}
	
	
	
	
	
	
	
}
