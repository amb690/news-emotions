package com.markettrender.newsemotions.service;

import java.util.Date;
import java.util.List;

import com.markettrender.newsemotions.models.entity.Emotion;

public interface EmotionService {

	public List<Emotion> findAll();

	public List<Emotion> findByAssetBetweenTwoDates(String asset, Date from, Date to);

	public void save(Emotion emotion);

	public Emotion findByAssetAndDate(String asset, Date monthDay);

	public List<Emotion> findByAsset(String ticker);

}
