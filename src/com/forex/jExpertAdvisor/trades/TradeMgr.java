package com.forex.jExpertAdvisor.trades;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONObject;

import com.forex.jExpertAdvisor.main.MarketMgr;
import com.forex.jExpertAdvisor.stoplosses.StopLoss;
import com.forex.jExpertAdvisor.stoplosses.StopLossMgr;
import com.forex.jExpertAdvisor.web.WebQuerySender;

public  class TradeMgr implements ITradesMgr {
	
	protected static TradeMgr instance = null;

	private Calculator calculator =new Calculator();
	
	public static TradeMgr getInstance() {
		
		if(instance == null)
			instance=new  TradeMgr();
		return instance;
	}
	

	
	
	protected static void setInstance(TradeMgr instance) {
		TradeMgr.instance = instance;
	}




	private TradeMgr() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void open(IStrategy strategy, StopLoss stoploss, TradeType type) {
		BigDecimal size = TradeConfig.getInstance().getSize();
		Map<String, String> params = new HashMap<>();
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy-HH-mm");
		params.put("date_open", df.format(MarketMgr.getInstance().getCurrentCandle().getDate()));
		params.put("strategy", strategy.getClass().getName());
		params.put("symbol", TradeConfig.getSymbol());
		params.put("open_price", MarketMgr.getInstance().getAsk().toString());
		params.put("status", "0");
		params.put("account", TradeConfig.getAccount());
		params.put("stoploss", stoploss.toString());
		params.put("interval", MarketMgr.getInstance().getInterval());
		params.put("stoploss_type", StopLossMgr.getInstance().getStopLossType(stoploss));
		params.put("type", type.toString());
		params.put("size", size.toString());
	try {
		WebQuerySender.getInstance().send("http://localhost:8090/insertactivetrades", params);
		params.clear();
		params.put("name", TradeConfig.getAccount());
		JSONObject accountObject = WebQuerySender.getInstance().getJson("http://localhost:8090", params, "get_account");
		params.clear();
		params.put("date", df.format(MarketMgr.getInstance().getCurrentCandle().getDate()) );
		WebQuerySender.getInstance().send("http://localhost/getrate", params);
		Thread.sleep(5000);
		params.put("base", accountObject.getString("currency"));
		params.put("target", TradeConfig.getSymbol().substring(3));
        JSONObject object = WebQuerySender.getInstance().getJson("http://localhost:8090", params, "getrate");

		Trade trade = new Trade(MarketMgr.getInstance().getAsk(),new BigDecimal(0), MarketMgr.getInstance().getCurrentCandle().getDate(), stoploss, type, size, calculator.calculatePoint(size, new BigDecimal(object.getString("rate"))), strategy);
		ExistingTrades.getInstance().put(ExistingTrades.getInstance().nextVal(), trade );
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (InterruptedException e) {
		e.printStackTrace();
	}


	}

	@Override
	public void close(Trade trade) throws Exception, IOException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-HH-mm");
		Map<String, String> params = new HashMap<>();
		params.put("date", simpleDateFormat.format(trade.getDateOpen()));
		params.put("strategyName", trade.getStrategy().getClass().getName());
		params.put("close",simpleDateFormat.format(MarketMgr.getInstance().getCurrentCandle().getDate()));

		

	}

	@Override
	public void updatePosition(Trade trade) throws Exception {
		Map<String, String> params = new HashMap<>();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss:S");

		params.put("date", simpleDateFormat.format(trade.getDateOpen()));
		params.put("result", calculator.calculateResult(trade).toString());
		WebQuerySender.getInstance().send("http://localhost:8090/update", params);




	}

	private class Calculator{


		public BigDecimal calculatePoint(BigDecimal size, BigDecimal rate){
			Map<String, String> params = new HashMap<>();
			params.put("size", size.toString());
			params.put("rate", rate.toString());
			JSONObject jsonObject = WebQuerySender.getInstance().getJson("http://localhost:2137", params, "calculate_point");
			return new BigDecimal(jsonObject.getString("point"));
		}
		public BigDecimal calculateResult(Trade trade){
			Map<String, String> params = new HashMap<>();
			params.put("open", trade.getOpen().toString());
			params.put("close",  MarketMgr.getInstance().getAsk().toString());
			params.put("type", trade.getType().toString());
			params.put("point", trade.getPoint().toString());

			JSONObject json = WebQuerySender.getInstance().getJson("http://localhost:2137", params, "get_result");
			return new BigDecimal(json.getString("result"));
		}


	}

	

}
