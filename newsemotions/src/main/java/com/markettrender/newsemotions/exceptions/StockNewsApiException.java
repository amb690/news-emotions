package com.markettrender.newsemotions.exceptions;

public class StockNewsApiException extends Exception {

	private static final long serialVersionUID = 1L;

	public StockNewsApiException(String message) {
		super(message);
	}

	public StockNewsApiException(String message, Exception e) {
		super(message, e);
	}

	public StockNewsApiException(Exception e) {
		super(e);
	}
	
}
