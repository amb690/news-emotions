package com.markettrender.newsemotions.models.responses;

import java.io.Serializable;

public class ImportAllTickersResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private int createdTickers;
	
	private int updatedTickers;

	public ImportAllTickersResponse(int createdTickers, int updatedTickers) {
		super();
		this.createdTickers = createdTickers;
		this.updatedTickers = updatedTickers;
	}

	public ImportAllTickersResponse() {}

	public int getCreatedTickers() {
		return createdTickers;
	}

	public void setCreatedTickers(int createdTickers) {
		this.createdTickers = createdTickers;
	}

	public int getUdpatedTickers() {
		return updatedTickers;
	}

	public void setUdpatedTickers(int updatedTickers) {
		this.updatedTickers = updatedTickers;
	}
	
	@Override
	public String toString() {
		
		return "Successfully created tickers: [" + createdTickers + "]"
				+ "\nSuccessfully updated tickers: [" + updatedTickers + "]"; 
	}
}
