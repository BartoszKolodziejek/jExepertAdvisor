package com.forex.jExpertAdvisor.main;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.forex.jExpertAdvisor.candles.Candle;
import com.forex.jExpertAdvisor.trades.ExistingTrades;
import com.forex.jExpertAdvisor.trades.TradeConfig;
import com.forex.jExpertAdvisor.trades.TradeMgr;

public class MarketMgr {
	
	private List<Candle> historicView = new ArrayList<>();
	
	private  String interval;
	
	private BigDecimal ask;
	private BigDecimal bid;
	private int currentCandle;
	private boolean isEnd;
	
	public void update() {
		TradeConfig.getInstance().nextVal();
		//TODO perform spread
		ExistingTrades.getInstance().forEach((k, t) -> {
			try {
				TradeMgr.getInstance().updatePosition(t);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		ask = historicView.get(currentCandle).getSubCandles().get(TradeConfig.getInstance().getCurrentSubcandle()).getClose();
		bid = historicView.get(currentCandle).getSubCandles().get(TradeConfig.getInstance().getCurrentSubcandle()).getClose();
		if(TradeConfig.getInstance().getCurrentSubcandle()==0)
			currentCandle++;
		if(currentCandle>=historicView.size())
			isEnd=true;
		
		
	}
	
	
	
	
	
	public BigDecimal getAsk() {
		return ask;
	}





	public BigDecimal getBid() {
		return bid;
	}





	public int getCurrentCandle() {
		return currentCandle;
	}





	public boolean isEnd() {
		return isEnd;
	}





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
		if(historicView!=null)
		this.historicView = historicView;
		int currentCandle = this.historicView.size()/2;
	}




	protected static MarketMgr instance = null;
	
	protected MarketMgr() {
		
	}

	
	public static MarketMgr getInstance() {
		if(instance==null)
			return new MarketMgr();
		return instance;
	}
}
