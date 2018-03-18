package com.forex.jExpertAdvisor.stoplosses;

import com.forex.jExpertAdvisor.main.MarketMgr;
import com.forex.jExpertAdvisor.trades.Trade;
import com.forex.jExpertAdvisor.trades.TradeType;
import com.forex.jExpertAdvisor.web.WebQuerySender;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class MovingStopLoss extends StopLoss {
    private BigDecimal step;

public void move(Trade trade){
    BigDecimal diff = MarketMgr.getInstance(trade.getSymbol()).getAsk().subtract(this.getLevel());
    if((trade.getType().equals(TradeType.BUY)&&diff.compareTo(step) > 0)|| (trade.getType().equals(TradeType.SELL)&&diff.compareTo(step)<0)) {

            setLevel(this.getLevel().add(diff));

            Map<String, String> params = new HashMap<>();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss:S");
            params.put("date", simpleDateFormat.format(trade.getDateOpen()));
            params.put("stoploss", this.getLevel().add(diff).toString());
            params.put("stoplosstype", this.getClass().getName());
            try {
                WebQuerySender.getInstance().send("http://localhost:8090/updatestoploss", params);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }


}


    public MovingStopLoss(BigDecimal level, BigDecimal step) {
        super(level);
        this.step = step;
    }
}
