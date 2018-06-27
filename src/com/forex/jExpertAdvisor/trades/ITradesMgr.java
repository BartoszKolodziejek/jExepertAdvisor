package com.forex.jExpertAdvisor.trades;

import java.io.IOException;
import java.math.BigDecimal;

import org.apache.http.client.ClientProtocolException;

import com.forex.jExpertAdvisor.stoplosses.StopLoss;

public interface ITradesMgr {

	
	void updatePosition(Trade trade) throws ClientProtocolException, IOException, Exception;

    void open(IStrategy strategy, StopLoss stoploss, TradeType type, String symbol, BigDecimal size, String account);

    void close(Trade trade) throws Exception, IOException;

}
