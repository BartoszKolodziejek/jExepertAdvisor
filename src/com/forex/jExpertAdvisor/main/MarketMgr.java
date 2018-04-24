package com.forex.jExpertAdvisor.main;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import com.forex.jExpertAdvisor.candles.Candle;
import com.forex.jExpertAdvisor.trades.ExistingTrades;
import com.forex.jExpertAdvisor.trades.Trade;
import com.forex.jExpertAdvisor.trades.TradeConfig;
import com.forex.jExpertAdvisor.trades.TradeMgr;
import com.forex.jExpertAdvisor.web.WebQuerySender;
import com.sun.javafx.collections.MappingChange;
import org.json.JSONObject;

public class MarketMgr {



	
	private List<Candle> historicView = new ArrayList<>();
	
	private  String interval;
	
	private BigDecimal ask;
	private BigDecimal bid;
	private int currentCandle;
	private boolean isEnd;
	private  String symbol;
	private static Map<String, MarketMgr> instances;



	public void update() {
		TradeConfig.getInstance().nextVal();
		//TODO perform spread
		ExistingTrades.getInstance().forEach((t) -> {
			try {
				TradeMgr.getInstance().updatePosition(t);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		try{
		ask = historicView.get(currentCandle).getSubCandles().get(TradeConfig.getInstance().getCurrentSubcandle()).getClose();
		bid = historicView.get(currentCandle).getSubCandles().get(TradeConfig.getInstance().getCurrentSubcandle()).getClose();}
		catch (Exception e){
		    int i = 0;
		    do{
		    	try {
			ask = historicView.get(currentCandle).getSubCandles().get(TradeConfig.getInstance().getCurrentSubcandle()-i).getClose();
			bid = historicView.get(currentCandle).getSubCandles().get(TradeConfig.getInstance().getCurrentSubcandle()-i).getClose();
		    i++;}
		    catch (Exception ex){
		    	}
			}
		    while (historicView.get(currentCandle).getSubCandles().get(TradeConfig.getInstance().getCurrentSubcandle()-i).getClose()==null);
		}
		if(TradeConfig.getInstance().getCurrentSubcandle()==0)
			currentCandle++;
		if(currentCandle>=historicView.size()){
			try {
                Map<String,String> params = new HashMap<String, String>();
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-HH-mm");
                params.put("close",simpleDateFormat.format(historicView.get(currentCandle-1).getSubCandles().get(TradeConfig.getInstance().getCurrentSubcandle()+TradeConfig.getInstance().getMaxSubcandle()-1).getDate()) );
				WebQuerySender.getInstance().send("http://localhost:8090/closeAll", params);

				for (int i = ExistingTrades.getInstance().size()-1; i>=0; i--) {
					if(ExistingTrades.getInstance().get(i).getSymbol().equals(getSymbol()))
						ExistingTrades.getInstance().remove(i);

				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			isEnd=true;
			if(isEnd)
			historicView.clear();}
		
		
	}
	
	
	
	
	
	public BigDecimal getAsk() {
		return ask;
	}





	public BigDecimal getBid() {
		return bid;
	}





	public Candle getCurrentCandle() {
		return historicView.get(currentCandle-1);
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


	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	protected MarketMgr() {
		
	}

	
	public static MarketMgr getInstance(String symbol) {
	    if (instances==null)
	        instances = new HashMap<>();
		if(instances.get(symbol)==null)
			instances.put(symbol,new MarketMgr());



		return instances.get(symbol);
	}
}
