package com.markettrender.newsemotions.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.markettrender.newsemotions.models.entity.Asset;
import com.markettrender.newsemotions.repositories.AssetRepository;
import com.markettrender.newsemotions.service.AssetService;

@Service
public class AssetServiceImpl implements AssetService {

	@Autowired
	private AssetRepository assetRepo;

	@Override
	@Transactional(readOnly = true)
	public Asset findByTicker1(String ticker1) {

		return assetRepo.findbyName(ticker1);
	}

	@Override
	@Transactional
	public void save(Asset asset) {
		assetRepo.save(asset);
	}

}
