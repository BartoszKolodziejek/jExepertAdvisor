package com.forex.jExpertAdvisor.web;

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

public class WebQuerySender {
	
	private HttpResponse response = null;
	
	 private WebQuerySender() {
		 
	 }
	 
	 static WebQuerySender instance = null;
	 
	 public static WebQuerySender getInstance() {
		 
		 if(instance == null)
			 return new WebQuerySender();
		 return instance;
		 
	 }
	 
	 
	 private String getPametriziedUrl(Map<String, String> params, String url) {
			StringBuilder sb = new StringBuilder();
			sb.append(url+"?");
	params.forEach((k,v) -> sb.append(k+"="+v+"&"));
	sb.deleteCharAt(sb.length()-1);
	return sb.toString();
		}
			
	 public String getResponse() throws Exception {
		 
		 return this.response.toString();
		 
	 }
	 
	 public void send(String url, Map<String, String> params) throws ClientProtocolException, IOException {
		 HttpClient client = HttpClientBuilder.create().build();
		 
		    HttpGet request = new HttpGet(getPametriziedUrl(params, url + "/" ));
		    System.out.println(getPametriziedUrl(params,  url + "/" ));
		    HttpResponse response = client.execute(request);
		    this.response = response;

				
	 }

}
