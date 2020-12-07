package com.markettrender.newsemotions.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.markettrender.newsemotions.models.entity.NewsEmotion;
import com.markettrender.newsemotions.repositories.NewsEmotionsRepository;
import com.markettrender.newsemotions.service.EmotionService;

@Service
public class NewsEmotionsServiceImpl implements EmotionService {

	@Autowired
	private NewsEmotionsRepository sentimentRepo;

	@Override
	@Transactional(readOnly = true)
	public List<NewsEmotion> findAll() {

		return sentimentRepo.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<NewsEmotion> findByAssetBetweenTwoDates(String asset, Date from, Date to) {

		return sentimentRepo.findbyAssetBetweenTwoDates(asset, from, to);
	}

	@Override
	@Transactional(readOnly = true)
	public NewsEmotion findByAssetAndDate(String asset, Date monthDay) {

		return sentimentRepo.findbyAssetAndDate(asset, monthDay);
	}

	@Override
	@Transactional
	public void save(NewsEmotion sentiment) {

		sentimentRepo.save(sentiment);
	}

	@Override
	public List<NewsEmotion> findByAsset(String ticker) {
		
		return sentimentRepo.findbyAsset(ticker);
	}
}
