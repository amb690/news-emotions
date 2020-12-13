package com.markettrender.newsemotions.controllers;

import java.io.Serializable;
import java.util.Calendar;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.markettrender.newsemotions.models.entity.Asset;
import com.markettrender.newsemotions.models.responses.ImportAllTickersResponse;
import com.markettrender.newsemotions.models.responses.ImportDailyEmotionsResponse;
import com.markettrender.newsemotions.service.AssetService;
import com.markettrender.newsemotions.service.StockNewsDataCollectorService;

@RestController
@RequestMapping("/newsemotions/import")
public class DataCollectorController {
	
	@Autowired
	public StockNewsDataCollectorService stockNewsDataCollectorService;
	
	@Autowired
	public AssetService assetService;
	
	@PostMapping("/{ticker}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> importAllEmotionsFromAsset(@PathVariable @NotBlank String ticker) throws Exception {

		Calendar from = Calendar.getInstance();
		from.set(2018, 1, 31);

		ImportDailyEmotionsResponse response = new ImportDailyEmotionsResponse();
		try {
			response = stockNewsDataCollectorService.importDailyEmotionsByDate(ticker, from.getTime(), Calendar.getInstance().getTime());
		} catch (ResponseStatusException re) {
			return new ResponseEntity<>("Bad connection", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		Asset asset = assetService.findByTicker1(ticker);

		return new ResponseEntity<>(response, HttpStatus.OK);

	}
	
	@PostMapping("/all-tickers")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Serializable> importAllTickers() throws Exception {
		
		ImportAllTickersResponse response = new ImportAllTickersResponse();  
		try {
			response = stockNewsDataCollectorService.importAllTickers();
		} catch (Exception re) {
			return new ResponseEntity<>(re.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(response, HttpStatus.OK);

	}
}
