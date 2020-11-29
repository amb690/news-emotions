package com.markettrender.newsemotions.models.pojo.stocknews;

import java.util.ArrayList;
import java.util.List;

public class DailyEmotions {

	public DailyEmotions(List<DailyEmotion> emotionRates) {
		this.emotionRates = emotionRates;
	}

	public DailyEmotions() {
		this.emotionRates = new ArrayList<>();
	}

	private List<DailyEmotion> emotionRates;
	
	private int pages;

	public List<DailyEmotion> getEmotionRates() {
		return emotionRates;
	}

	public void setEmotionRates(List<DailyEmotion> emotionRates) {
		this.emotionRates = emotionRates;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}
	
}
