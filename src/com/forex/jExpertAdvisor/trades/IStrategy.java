package com.forex.jExpertAdvisor.trades;

import java.math.BigDecimal;

public abstract class IStrategy {


	private String symbol;
	private BigDecimal point;

    public BigDecimal getPoint() {
        return point;
    }

    public void setPoint(BigDecimal point) {
        this.point = point;
    }

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
