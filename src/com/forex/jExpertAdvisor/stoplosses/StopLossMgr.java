package com.forex.jExpertAdvisor.stoplosses;

public class StopLossMgr {
	
	
	protected static  StopLossMgr instance;
	
	protected StopLossMgr() {
		
	}
	
	public String getStopLossType(StopLoss stoploss) {
		return stoploss.getClass().getName();
	}
	
	public static StopLossMgr getInstance() {
		if(instance==null)
			return new StopLossMgr();
		return instance;
	}
	

}
