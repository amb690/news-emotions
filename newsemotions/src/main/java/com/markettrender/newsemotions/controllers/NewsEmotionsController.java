package com.markettrender.newsemotions.controllers;

import java.io.Serializable;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.markettrender.newsemotions.models.entity.Emotion;
import com.markettrender.newsemotions.models.responses.ImportAllTickersResponse;
import com.markettrender.newsemotions.service.EmotionService;
import com.markettrender.newsemotions.service.StockNewsDataCollectorService;

@RestController
@RequestMapping("/newsemotions")
public class NewsEmotionsController {

	@Autowired
	public StockNewsDataCollectorService dataCollectorService;
	
	@Autowired
	public EmotionService emotionService;
	
	@GetMapping("/{ticker}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<Emotion>> getEmotionsByTicker(@PathVariable @NotBlank String ticker){
				
		List<Emotion> emotions = emotionService.findByAsset(ticker);
		return new ResponseEntity<>(emotions, HttpStatus.OK);
		
	}
	
	@GetMapping("/{ticker}/{from}/{to}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<Emotion>> getEmotionsBetweenTwoDates(@PathVariable @NotBlank String ticker,
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") @NotNull Date from,
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") @NotNull Date to){
				
		List<Emotion> emotions = emotionService.findByAssetBetweenTwoDates(ticker, from, to);
		return new ResponseEntity<>(emotions, HttpStatus.OK);
		
	}

	@PostMapping("/import/{ticker}/{from}/{to}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> importDailyEmotions(@PathVariable @NotBlank String ticker,
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") @NotNull Date from,
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") @NotNull Date to,
			@RequestParam(value = "lastYear", required = true) boolean lastYear, 
			@RequestParam(value = "lastThirtyDays", required = true) boolean lastThirtyDays) throws Exception {

		if (to.before(from))
			return new ResponseEntity<>("Initial date must be previous or equal to final date",
					HttpStatus.BAD_REQUEST);

		try {
			dataCollectorService.importDailyEmotionsByDate(ticker, from, to, lastYear, lastThirtyDays);
		} catch (ResponseStatusException re) {
			return new ResponseEntity<>("Bad connection", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>("", HttpStatus.OK);

	}
	
	@PostMapping("/import/all-tickers")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Serializable> importAllTickers() throws Exception {
		
		ImportAllTickersResponse response = new ImportAllTickersResponse();  
		try {
			response = dataCollectorService.importAllTickers();
		} catch (Exception re) {
			return new ResponseEntity<>(re.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(response, HttpStatus.OK);

	}
}
