package com.markettrender.newsemotions.models.pojo;

import com.markettrender.newsemotions.models.entity.Asset;

public class AssetResponse {

	public AssetResponse(Asset asset) {
	  this.name = asset.getName();
	}

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
