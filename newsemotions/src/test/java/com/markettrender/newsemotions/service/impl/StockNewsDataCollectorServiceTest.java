package com.markettrender.newsemotions.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
import com.markettrender.newsemotions.models.pojo.Api;
import com.markettrender.newsemotions.models.pojo.stocknews.DailyEmotion;
import com.markettrender.newsemotions.models.pojo.stocknews.DailyEmotions;
import com.markettrender.newsemotions.models.pojo.stocknews.Ticker;
import com.markettrender.newsemotions.models.responses.ImportAllTickersResponse;

class StockNewsDataCollectorServiceTest {
	
	private String getDailySentimentsResponse = MockStockNewsApiResponses.GET_DAILY_SENTIMENTS_RESPONSE;
	private String getAllTickersResponse = MockStockNewsApiResponses.GET_ALL_TICKERS_RESPONSE;

	@Test
	void deserializeGetDailySentimentsResponse() throws IOException {
		
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
	void deserializeGetAllTickersResponse() throws IOException {
		
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
	    assertEquals("NYSE", agilentTechnologies.getExchange());
	}
	
	// POJOS
	
	@Test
	void dailyEmotionsPojoTest() throws IOException {
		
		DailyEmotion dailyEmotion = new DailyEmotion();
		List<DailyEmotion> dailyEmotionsList = new ArrayList<>();
		dailyEmotionsList.add(dailyEmotion);
		
		DailyEmotions dailyEmotions = new DailyEmotions(dailyEmotionsList);
		dailyEmotions.setEmotionRates(new ArrayList<>());
		dailyEmotions.setPages(0);
		
		assertNotNull(dailyEmotions.getEmotionRates());
	    assertEquals(0, dailyEmotions.getPages());
	}
	
	@Test
	void dailyEmotionPojoTest() throws IOException {
		
		DailyEmotion dailyEmotion = new DailyEmotion();
		dailyEmotion.setNegative(2);
		dailyEmotion.setNeutral(3);
		dailyEmotion.setPositive(54);
		dailyEmotion.setPublishedAt(new Date());
		dailyEmotion.setScoreLabel("POSITIVE");
		dailyEmotion.setTotalScore(4.5);
		
    	assertEquals(2, dailyEmotion.getNegative());
    	assertEquals(3, dailyEmotion.getNeutral());
    	assertEquals(54, dailyEmotion.getPositive());
    	assertNotNull(dailyEmotion.getPublishedAt());
    	assertEquals("POSITIVE", dailyEmotion.getScoreLabel());
    	assertEquals(4.5, dailyEmotion.getTotalScore());
    	
	}
	
	@Test
	void tickerPojoTest() throws IOException {
		
		Ticker ticker = new Ticker();
		ticker.setCountry("EEUU");
		ticker.setExchange("NASDAQ100");
		ticker.setHasNews(true);
		ticker.setIndustry("Technologies");
		ticker.setIpoDate(new Date());
		ticker.setName("APPLE");
		ticker.setSector("Technologies");
		ticker.setTicker("AAPL");
		
    	assertEquals("EEUU", ticker.getCountry());
    	assertEquals("NASDAQ100", ticker.getExchange());
    	assertEquals(true, ticker.isHasNews());
    	assertEquals("Technologies", ticker.getIndustry());
    	assertNotNull(ticker.getIpoDate());
    	assertEquals("APPLE", ticker.getName());
    	assertEquals("Technologies", ticker.getSector());
    	assertEquals("AAPL", ticker.getTicker());
    	assertNotNull(ticker.toString());
    	
	}
	
	@Test
	void apiPojoTest() throws IOException {
		
		Api api = new Api();
		api.setCreatedAt(new Date());
		api.setId(1L);
		api.setLastPetition(new Date());
		api.setMaxPetitions(50000);
		api.setName("stocknewsapi");
		api.setNumberOfPetitions(10L);
		
    	assertNotNull(api.getCreatedAt());
    	assertEquals(1L, api.getId());
    	assertNotNull(api.getLastPetition());
    	assertEquals(50000, api.getMaxPetitions());
    	assertEquals("stocknewsapi", api.getName());
    	assertEquals(10L, api.getNumberOfPetitions());
    	assertNotNull(api.toString());
	}
	
	@Test
	void importAllTickersResponsesPojoTest() throws IOException {
		
		ImportAllTickersResponse allTickersResponse2 = new ImportAllTickersResponse();
		ImportAllTickersResponse allTickersResponse = new ImportAllTickersResponse(50, 100);
		allTickersResponse.setCreatedTickers(500);
		allTickersResponse.setUdpatedTickers(600);
		
    	assertNotNull(allTickersResponse.toString());
    	assertEquals(500, allTickersResponse.getCreatedTickers());
    	assertEquals(600, allTickersResponse.getUdpatedTickers());
    	assertNotNull(allTickersResponse2);
	}
	
}
