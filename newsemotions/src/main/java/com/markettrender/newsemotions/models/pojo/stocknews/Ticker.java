package com.markettrender.newsemotions.models.pojo.stocknews;

import java.util.Date;

public class Ticker {
	
	private String ticker;
	
	private String name;
	
	private String country;
	
	private String industry;
	
	private String sector;
	
	private boolean hasNews;
	
	private Date ipoDate;
	
	private String exchange;

	public Ticker(String ticker, String name, String country, String industry, String sector, boolean hasNews,
			Date ipoDate, String exchange) {
		
		this.ticker = ticker;
		this.name = name;
		this.country = country;
		this.industry = industry;
		this.sector = sector;
		this.hasNews = hasNews;
		this.ipoDate = ipoDate;
		this.exchange = exchange;
	}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public boolean isHasNews() {
		return hasNews;
	}

	public void setHasNews(boolean hasNews) {
		this.hasNews = hasNews;
	}

	public Date getIpoDate() {
		return ipoDate;
	}

	public void setIpoDate(Date ipoDate) {
		this.ipoDate = ipoDate;
	}

	public String getExchange() {
		return exchange;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

	@Override
	public String toString() {
		return "Ticker [ticker=" + ticker + ", name=" + name + ", country=" + country + ", industry=" + industry
				+ ", sector=" + sector + ", hasNews=" + hasNews + ", ipoDate=" + ipoDate + ", exchange=" + exchange
				+ "]";
	}

}
