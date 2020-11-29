package com.markettrender.newsemotions.controllers;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.markettrender.newsemotions.models.entity.Emotion;
import com.markettrender.newsemotions.service.EmotionService;

@RestController
@RequestMapping("/newsemotions")
public class NewsEmotionsController {

	@Autowired
	public EmotionService emotionService;
	
	@GetMapping("/{ticker}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<Emotion>> getEmotionsByTicker(@PathVariable @NotBlank String ticker){
				
		List<Emotion> emotions = emotionService.findByAsset(ticker);
		return new ResponseEntity<>(emotions, HttpStatus.OK);
		
	}
	
	@GetMapping("/{ticker}/{date}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Emotion> getEmotionByTickerAndDate(@PathVariable @NotBlank String ticker,
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") @NotNull Date date){
				
		Emotion emotion = emotionService.findByAssetAndDate(ticker, date);
		return new ResponseEntity<>(emotion, HttpStatus.OK);
	}
	
	@GetMapping("/{ticker}/{from}/{to}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<Emotion>> getEmotionsBetweenTwoDates(@PathVariable @NotBlank String ticker,
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") @NotNull Date from,
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") @NotNull Date to){
				
		List<Emotion> emotions = emotionService.findByAssetBetweenTwoDates(ticker, from, to);
		return new ResponseEntity<>(emotions, HttpStatus.OK);
		
	}
	
}
