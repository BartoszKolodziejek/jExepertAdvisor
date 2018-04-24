package com.forex.jExpertAdvisor.trades;

import java.math.BigDecimal;

public abstract class IStrategy {


	private BigDecimal size;
	private String symbol;
	private BigDecimal point;
	private String account;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public BigDecimal getSize() {
		return size;
	}

	public void setSize(BigDecimal size) {
		this.size = size;
	}

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
