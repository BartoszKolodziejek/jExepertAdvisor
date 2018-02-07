package com.forex.jExpertAdvisor.trades;


public class Strategy implements IStrategy {

	
	private static Strategy instance = null;
	@Override
	public  void OnInit() {
		
	}

	@Override
	public void OnDenit() {
		
	}

	@Override
	public void OnStart() {
		
	}
	
	protected Strategy () {
		
	}
	
	public static  Strategy getInstance() {
		
		if(instance==null)
			return new Strategy();
		
		return instance;
	
	}
}
