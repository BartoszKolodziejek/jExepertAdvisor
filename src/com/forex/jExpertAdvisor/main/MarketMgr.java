package com.forex.jExpertAdvisor.main;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.forex.jExpertAdvisor.candles.Candle;
import com.forex.jExpertAdvisor.trades.ExistingTrades;
import com.forex.jExpertAdvisor.trades.TradeConfig;
import com.forex.jExpertAdvisor.trades.TradeMgr;
import com.forex.jExpertAdvisor.web.WebQuerySender;
import com.sun.javafx.collections.MappingChange;
import org.json.JSONObject;

public class MarketMgr {

	private static BigDecimal balance;

	
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
		if(currentCandle>=historicView.size()){
			try {
				WebQuerySender.getInstance().send("localhost:8090/closeAll", new HashMap<String, String>());
			} catch (IOException e) {
				e.printStackTrace();
			}
			isEnd=true;}
		
		
	}
	
	
	
	
	
	public BigDecimal getAsk() {
		return ask;
	}





	public BigDecimal getBid() {
		return bid;
	}





	public Candle getCurrentCandle() {
		return historicView.get(currentCandle);
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
		 this.currentCandle = this.historicView.size()/2;
	}




	protected static MarketMgr instance = null;
	
	protected MarketMgr() {
		
	}

	
	public static MarketMgr getInstance() {
		if(instance==null){
			Map<String,String> param = new HashMap<>();
			param.put("name", "test");
            JSONObject account = WebQuerySender.getInstance().getJson("http://localhost:8090" , param,"get_account");
            balance = account.getBigDecimal("deposit");
			instance = new MarketMgr();}
		return instance;
	}
}
