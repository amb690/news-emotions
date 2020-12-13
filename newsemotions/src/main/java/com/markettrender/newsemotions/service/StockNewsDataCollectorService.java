package com.markettrender.newsemotions.service;

import java.util.Date;

import com.markettrender.newsemotions.exceptions.StockNewsApiException;
import com.markettrender.newsemotions.models.responses.ImportAllTickersResponse;
import com.markettrender.newsemotions.models.responses.ImportDailyEmotionsResponse;

public interface StockNewsDataCollectorService {

	public ImportDailyEmotionsResponse importDailyEmotionsByDate(String ticker, Date from, Date to) 
			throws StockNewsApiException;

	public ImportAllTickersResponse importAllTickers() throws Exception;
}
