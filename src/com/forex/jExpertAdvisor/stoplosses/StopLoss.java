package com.forex.jExpertAdvisor.stoplosses;

import java.math.BigDecimal;

public class StopLoss {
	
	private BigDecimal level;

	public BigDecimal getLevel() {
		return level;
	}

	public void setLevel(BigDecimal level) {
		this.level = level;
	}

	public StopLoss(BigDecimal level) {
		super();
		this.level = level;
	}

	@Override
	public String toString() {
		return level.toString();
	}
	
	

}
