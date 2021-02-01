package com.markettrender.newsemotions.service;

import java.util.List;

import com.markettrender.newsemotions.models.entity.Asset;

public interface AssetService {

	public Asset findByTicker1(String ticker1);

	public void save(Asset asset);

	List<Asset> findAll();

	void delete(Long id);
}
