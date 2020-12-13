package com.markettrender.newsemotions.service;

import java.util.Date;

import com.markettrender.newsemotions.models.responses.ImportAllTickersResponse;
import com.markettrender.newsemotions.models.responses.ImportDailyEmotionsResponse;

public interface StockNewsDataCollectorService {

	public ImportDailyEmotionsResponse importDailyEmotionsByDate(String ticker, Date from, Date to) 
			throws Exception;

	public ImportAllTickersResponse importAllTickers() throws Exception;
}
