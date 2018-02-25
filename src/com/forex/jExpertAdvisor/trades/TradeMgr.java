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
		Map<String, String> params = new HashMap<>();
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy-HH-mm");
		params.put("date_open", df.format(new Date()));
		
		params.put("strategy", strategy.getClass().getName());
		params.put("symbol", TradeConfig.getSymbol());
		params.put("opne_price", MarketMgr.getInstance().getHistoricView().get(MarketMgr.getInstance().getHistoricView().size()-1).getSubCandles().get(TradeConfig.getInstance().getCurrentSubcandle()).toString());
		params.put("status", "0");
		params.put("account", TradeConfig.getAccount());
		params.put("stoploss", stoploss.toString());
		params.put("interval", MarketMgr.getInstance().getInterval());
		params.put("stoploss_type", StopLossMgr.getInstance().getStopLossType(stoploss));
		params.put("type", type.toString());
	try {
		WebQuerySender.getInstance().send("http://localhost:8090/insertactivetrades", params);
		Trade trade = new Trade((MarketMgr.getInstance().getHistoricView().get(MarketMgr.getInstance().getHistoricView().size()-1).getSubCandles().get(TradeConfig.getInstance().getCurrentSubcandle()).getClose()),new BigDecimal(0), new Date(), stoploss, type );
		ExistingTrades.getInstance().put(ExistingTrades.getInstance().nextVal(), trade );
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	

	}

	@Override
	public void close(Trade trade) throws Exception, IOException {
		
		Map<String, String> params = new HashMap<>();
		params.put("open", trade.getOpen().toString());
		params.put("close",  MarketMgr.getInstance().getHistoricView().get(MarketMgr.getInstance().getHistoricView().size()-1).getSubCandles().get(TradeConfig.getInstance().getCurrentSubcandle()).toString());
		params.put("type", trade.getType().toString());
		WebQuerySender.getInstance().send("http://localhost:2137/get_result", params);
		String response = WebQuerySender.getInstance().getResponse();
		//TODO - Handle with response
		
		

	}

	@Override
	public void updatePosition(Trade trade) throws Exception {
		Map<String, String> params = new HashMap<>();
		params.put("open", trade.getOpen().toString());
		params.put("close",  MarketMgr.getInstance().getAsk().toString());
		params.put("type", trade.getType().toString());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss:S");
		WebQuerySender.getInstance().send("http://127.0.0.1:2137/get_result", params);
		JSONObject json = WebQuerySender.getInstance().getJson("http://localhost:2137", params, "get_result");
		params.clear();
		params.put("date", simpleDateFormat.format(trade.getDateOpen()));
		params.put("result", (String) json.get("result"));
		WebQuerySender.getInstance().send("localhost:8090/update", params);


	}

	

	

}
