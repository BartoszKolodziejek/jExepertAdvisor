package com.forex.jExpertAdvisor.main;

import java.util.ArrayList;
import java.util.List;

import com.forex.jExpertAdvisor.candles.Candle;

public class MarketMgr {
	
	private List<Candle> historicView = new ArrayList<>();
	
	private  String interval;
	
	
	
	
	
	public  String getInterval() {
		return interval;
	}


	public  void setInterval(String interval) {
		this.interval = interval;
	}


	public List<Candle> getHistoricView() {
		return historicView;
	}


	public void setHistoricView(List<Candle> historicView) {
		this.historicView = historicView;
	}




	private static MarketMgr instance = null;
	
	private MarketMgr() {
		
	}

	
	public static MarketMgr getInstance() {
		if(instance==null)
			return new MarketMgr();
		return instance;
	}
}
