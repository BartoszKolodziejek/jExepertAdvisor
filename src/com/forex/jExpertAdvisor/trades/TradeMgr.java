package com.forex.jExpertAdvisor.trades;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.forex.jExpertAdvisor.stoplosses.MovingStopLoss;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONObject;

import com.forex.jExpertAdvisor.main.MarketMgr;
import com.forex.jExpertAdvisor.stoplosses.StopLoss;
import com.forex.jExpertAdvisor.stoplosses.StopLossMgr;
import com.forex.jExpertAdvisor.web.WebQuerySender;

public  class TradeMgr implements ITradesMgr {

	private final Logger logger = Logger.getLogger("TradeMgr");
	
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
	public void open(IStrategy strategy, StopLoss stoploss, TradeType type, String symbol) {
		BigDecimal size = TradeConfig.getInstance().getSize();
		Map<String, String> params = new HashMap<>();
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy-HH-mm");
		params.put("date_open", df.format(MarketMgr.getInstance(symbol).getCurrentCandle().getDate()));
		params.put("strategy", strategy.getClass().getName());
		params.put("symbol", MarketMgr.getInstance(symbol).getSymbol());
		params.put("open_price", MarketMgr.getInstance(symbol).getAsk().toString());
		params.put("status", "0");
		params.put("account", TradeConfig.getAccount());
		params.put("stoploss", stoploss.toString());
		params.put("interval", MarketMgr.getInstance(symbol).getInterval());
		params.put("stoploss_type", StopLossMgr.getInstance().getStopLossType(stoploss));
		params.put("type", type.toString());
		params.put("size", size.toString());
	try {
		WebQuerySender.getInstance().send("http://localhost:8090/insertactivetrades", params);
		params.clear();
		params.put("name", TradeConfig.getAccount());
		JSONObject accountObject = WebQuerySender.getInstance().getJson("http://localhost:8090", params, "get_account");
		params.clear();
		params.put("date", df.format(MarketMgr.getInstance(symbol).getCurrentCandle().getDate()) );
        params.put("symbol", symbol );
		WebQuerySender.getInstance().send("http://localhost/getrate", params);
        JSONObject object;
		do{
		Thread.sleep(5000);
		params.put("base", accountObject.getString("currency"));
		params.put("target", MarketMgr.getInstance(symbol).getSymbol().substring(3));
        object = WebQuerySender.getInstance().getJson("http://localhost:8090", params, "getrate");}
        while (object==null);
		if(calculator.calculateSafeLevel(size.toString(), object.getString("rate"), accountObject.getString("lavarage") ).compareTo(new BigDecimal(accountObject.getString("deposit")))<0){
		Trade trade = new Trade(MarketMgr.getInstance(symbol).getAsk(),new BigDecimal(0), MarketMgr.getInstance(symbol).getCurrentCandle().getDate(), stoploss, type, size, calculator.calculatePoint(size, new BigDecimal(object.getString("rate"))), strategy, symbol);
		ExistingTrades.getInstance().add(trade);}
		else
			System.out.println("Account balance is too low to open position on "+MarketMgr.getInstance(symbol).getSymbol());
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
		params.put("closeDate",simpleDateFormat.format(MarketMgr.getInstance(trade.getSymbol()).getCurrentCandle().getDate()));

		WebQuerySender.getInstance().send("http://localhost:8090/closeTrade", params);
		ExistingTrades.getInstance().remove(trade);

		

	}

	@Override
	public void updatePosition(Trade trade) throws Exception {
		Map<String, String> params = new HashMap<>();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss:S");

		params.put("date", simpleDateFormat.format(trade.getDateOpen()));
		params.put("result", calculator.calculateResult(trade).setScale(5).toString());
		WebQuerySender.getInstance().send("http://localhost:8090/update", params);
		if(trade.getStoploss() instanceof MovingStopLoss){
		    ((MovingStopLoss) trade.getStoploss()).move(trade);
        }
		if(MarketMgr.getInstance(trade.getSymbol()).getAsk().compareTo(trade.getStoploss().getLevel())<0 && trade.getType().equals(TradeType.BUY))
			close(trade);
		if(MarketMgr.getInstance(trade.getSymbol()).getBid().compareTo(trade.getStoploss().getLevel())>0 && trade.getType().equals(TradeType.SELL))
			close(trade);





	}

	private class Calculator{

		public BigDecimal calculateSafeLevel( String size,  String rate, String lavarage){

			Map<String, String> params = new HashMap<>();
			params.put("size", size);
			params.put("rate", rate);
			params.put("lavarage", lavarage);

			JSONObject json = WebQuerySender.getInstance().getJson("http://localhost:2137", params, "calculate_safelevel");
			return new BigDecimal(json.getString("result"));
		}


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
			params.put("close",  MarketMgr.getInstance(trade.getSymbol()).getAsk().toString());
			params.put("type", trade.getType().toString());
			params.put("point", trade.getPoint().toString());

			JSONObject json = WebQuerySender.getInstance().getJson("http://localhost:2137", params, "get_result");
			return new BigDecimal(json.getString("result"));
		}


	}

	

}
