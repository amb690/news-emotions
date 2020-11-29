package com.markettrender.newsemotions.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.markettrender.newsemotions.models.entity.Emotion;
import com.markettrender.newsemotions.repositories.EmotionRepository;
import com.markettrender.newsemotions.service.EmotionService;

@Service
public class EmotionServiceImpl implements EmotionService {

	@Autowired
	private EmotionRepository sentimentRepo;

	@Override
	@Transactional(readOnly = true)
	public List<Emotion> findAll() {

		return sentimentRepo.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Emotion> findByAssetBetweenTwoDates(String asset, Date from, Date to) {

		return sentimentRepo.findbyAssetBetweenTwoDates(asset, from, to);
	}

	@Override
	@Transactional(readOnly = true)
	public Emotion findByAssetAndDate(String asset, Date monthDay) {

		return sentimentRepo.findbyAssetAndDate(asset, monthDay);
	}

	@Override
	@Transactional
	public void save(Emotion sentiment) {

		sentimentRepo.save(sentiment);
	}

	@Override
	public List<Emotion> findByAsset(String ticker) {
		
		return sentimentRepo.findbyAsset(ticker);
	}
}
