package com.markettrender.newsemotions.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.markettrender.newsemotions.models.pojo.stocknews.DailyEmotion;
import com.markettrender.newsemotions.models.pojo.stocknews.DailyEmotions;
import com.markettrender.newsemotions.models.pojo.stocknews.Ticker;

public class StockNewsDataCollectorTest {
	
	private String getDailySentimentsResponse = MockStockNewsApiResponses.GET_DAILY_SENTIMENTS_RESPONSE;
	private String getAllTickersResponse = MockStockNewsApiResponses.GET_ALL_TICKERS_RESPONSE;

	@Test
	public void deserializeGetDailySentimentsResponse() throws IOException {
		
		DailyEmotions rates = new DailyEmotions();
	    JsonNode node = new ObjectMapper().readTree(getDailySentimentsResponse);
	    Iterator<Map.Entry<String,JsonNode>> dataNode = node.get("data").fields();
	    
	    int pages = node.get("total_pages").asInt();
	    List<DailyEmotion> emotionRatesList = new ArrayList<DailyEmotion>();
	    while (dataNode.hasNext()) {
	    	Map.Entry<String,JsonNode> data = dataNode.next();
	    	
	    	String dateString = data.getKey();
	    	Date publishedDate = new Date();
	    	try {
				 publishedDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
			} catch (ParseException e) {
				e.printStackTrace();
			} 
	    	
	    	JsonNode emotionRates = data.getValue().get("GOLD");
	    	int neutral = emotionRates.get("Neutral").asInt();
	    	int positive = emotionRates.get("Positive").asInt();
	    	int negative = emotionRates.get("Negative").asInt();
	    	double sentimentScore = emotionRates.get("sentiment_score").asDouble();
	    	
	    	String labelScore = "";
	    	if (positive > negative) {
	    		labelScore = "POSITIVE";
	    	} else if (negative > positive) {
	    		labelScore = "NEGATIVE";
	    	} else {
	    		labelScore = "NEUTRAL";
	    	}
	    	
	    	DailyEmotion emotionRate = new DailyEmotion(publishedDate, neutral, positive, negative, labelScore,
	    			sentimentScore);
	    	emotionRatesList.add(emotionRate);
	    	System.out.println(emotionRate.toString());
	    }
	 
    	rates.setEmotionRates(emotionRatesList);
    	rates.setPages(pages);
    	
	    assertEquals("POSITIVE" ,emotionRatesList.get(0).getScoreLabel());
	    assertEquals(1, pages);

	}
	
	@Test
	public void deserializeGetAllTickersResponse() throws IOException {
		
	    JsonNode node = new ObjectMapper().readTree(getAllTickersResponse);
	    JsonNode dataNode = node.get("data");
	    Iterator<JsonNode> dataElements = dataNode.elements();
	    
	    List<Ticker> tickers = new ArrayList<Ticker>();
	    while (dataElements.hasNext()) {
	    	
	    	JsonNode ticker = dataElements.next();
	    	
	    	String tck = ticker.get("ticker").asText();
	    	String name = ticker.get("name").asText();
	    	String country = ticker.get("country").asText();
	    	String industry = ticker.get("industry").asText();
	    	String sector = ticker.get("sector").asText();
	    	boolean hasNews = ticker.get("has_news").asText().equals("yes")? true: false;
	    	
	    	String ipoDateStr = ticker.get("ipo_date").asText();
	    	Date ipoDate = new Date();
	    	try {
				 ipoDate = new SimpleDateFormat("yyyy-MM-dd").parse(ipoDateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
	    	
	    	String exchange = ticker.get("exchange").asText();
	    	
	    	Ticker tkcUnit = new Ticker(tck, name, country, industry, sector, hasNews, ipoDate, exchange);
	    	tickers.add(tkcUnit);
	    }
	 
	    Ticker agilentTechnologies = tickers.get(0);
    	assertEquals("Agilent Technologies, Inc.", agilentTechnologies.getName());
    	assertEquals("USA", agilentTechnologies.getCountry());
    	assertEquals("Medical Laboratories & Research", agilentTechnologies.getIndustry());
    	assertEquals("Healthcare", agilentTechnologies.getSector());
	    assertEquals(true , agilentTechnologies.isHasNews());
	    assertEquals("Thu Nov 18 00:00:00 CET 1999", agilentTechnologies.getIpoDate().toString());
	    assertEquals("NYSE", agilentTechnologies.getExchange());
	}
	
}
