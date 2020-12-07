package com.markettrender.newsemotions.service;

import java.util.Date;
import java.util.List;

import com.markettrender.newsemotions.models.entity.NewsEmotion;

public interface EmotionService {

	public List<NewsEmotion> findAll();

	public List<NewsEmotion> findByAssetBetweenTwoDates(String asset, Date from, Date to);

	public void save(NewsEmotion emotion);

	public NewsEmotion findByAssetAndDate(String asset, Date monthDay);

	public List<NewsEmotion> findByAsset(String ticker);

}
