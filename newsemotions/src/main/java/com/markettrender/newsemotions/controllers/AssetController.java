package com.markettrender.newsemotions.controllers;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.markettrender.newsemotions.models.entity.Asset;
import com.markettrender.newsemotions.service.AssetService;

@RestController
@RequestMapping("/emotions/asset")
public class AssetController {

	@Autowired
	public AssetService assetService;
	
	@GetMapping("/{asset}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> getAssetFromTicker(@PathVariable @NotBlank String asset) throws Exception {

		Asset response = assetService.findByTicker1(asset);
		
		return new ResponseEntity<>(response, HttpStatus.OK);

	}
}
