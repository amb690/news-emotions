package com.markettrender.newsemotions.models.pojo.stocknews;

import java.util.Date;

public class DailyEmotion {

	private Date publishedAt;
	
	private int neutral;
	
	private int positive;
	
	private int negative;
	
	private String scoreLabel;
	
	private double totalScore;

	public DailyEmotion(Date publishedAt, int neutral, int positive, int negative, String scoreLabel,
			double totalScore) {
		this.publishedAt = publishedAt;
		this.neutral = neutral;
		this.positive = positive;
		this.negative = negative;
		this.scoreLabel = scoreLabel;
		this.totalScore = totalScore;
	}

	public DailyEmotion() {}

	public Date getPublishedAt() {
		return publishedAt;
	}

	public void setPublishedAt(Date publishedAt) {
		this.publishedAt = publishedAt;
	}

	public int getNeutral() {
		return neutral;
	}

	public void setNeutral(int neutral) {
		this.neutral = neutral;
	}

	public int getPositive() {
		return positive;
	}

	public void setPositive(int positive) {
		this.positive = positive;
	}

	public int getNegative() {
		return negative;
	}

	public void setNegative(int negative) {
		this.negative = negative;
	}

	public String getScoreLabel() {
		return scoreLabel;
	}

	public void setScoreLabel(String scoreLabel) {
		this.scoreLabel = scoreLabel;
	}

	public double getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(double totalScore) {
		this.totalScore = totalScore;
	}

	@Override
	public String toString() {
		return "EmotionRate [publishedAt=" + publishedAt + ", neutral=" + neutral + ", positive=" + positive
				+ ", negative=" + negative + ", scoreLabel=" + scoreLabel + ", totalScore=" + totalScore + "]";
	}
	
}
