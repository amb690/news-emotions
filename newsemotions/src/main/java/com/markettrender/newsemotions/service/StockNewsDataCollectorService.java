package com.markettrender.newsemotions.service;

import java.util.Date;

import com.markettrender.newsemotions.models.responses.ImportAllTickersResponse;

public interface StockNewsDataCollectorService {

	public boolean importDailyEmotionsByDate(String ticker, Date from, Date to, boolean lastYear, boolean lastThirtyDays) 
			throws Exception;

	public ImportAllTickersResponse importAllTickers() throws Exception;
}
