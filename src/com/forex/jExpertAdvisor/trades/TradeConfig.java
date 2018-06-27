package com.forex.jExpertAdvisor.trades;

import com.forex.jExpertAdvisor.web.WebQuerySender;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class TradeConfig {
	
	
	protected static TradeConfig instance = null;
	private static String account = "test";
	private  int currentSubcandle;
	private final  int maxSubcandle;
	private static int max;
	private BigDecimal size;
	private static BigDecimal balance;


	public BigDecimal getSize() {
		return size;
	}

	public void setSize(BigDecimal size) {
		this.size = size;
	}

	public static void setMax(int max) {
		TradeConfig.max = max;
	}

	public  int getCurrentSubcandle() {
		return currentSubcandle;
	}

	public int getMaxSubcandle() {
		return maxSubcandle;
	}

	public  int nextVal() {
		this.currentSubcandle++;
		if(currentSubcandle>maxSubcandle)
			currentSubcandle=0;
		return currentSubcandle;
		
	}



	public static String getAccount() {
		return account;
	}

	static void setAccount(String account) {
		TradeConfig.account = account;
	}
	protected TradeConfig(Integer maxSubcandle) {
	this.maxSubcandle = maxSubcandle;
}

public static TradeConfig getInstance() {
	Map<String, String> param;
	if(instance==null){
		param = new HashMap<>();
		param.put("name", "test");
	JSONObject account = WebQuerySender.getInstance().getJson("http://localhost:8090" , param,"get_account");
	balance = new BigDecimal(account.getString("deposit"));
		instance= new TradeConfig(max);}
	return instance;
}

	
	

}
