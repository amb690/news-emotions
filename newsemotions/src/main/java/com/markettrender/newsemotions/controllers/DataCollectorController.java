package com.markettrender.newsemotions.controllers;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.markettrender.newsemotions.models.responses.ImportAllTickersResponse;
import com.markettrender.newsemotions.models.responses.ImportDailyEmotionsResponse;
import com.markettrender.newsemotions.service.StockNewsDataCollectorService;

@RestController
@RequestMapping("/newsemotions/import")
public class DataCollectorController {
	
	@Autowired
	public StockNewsDataCollectorService stockNewsDataCollectorService;
	
	@PostMapping("/{ticker}/{from}/{to}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> importDailyEmotions(@PathVariable @NotBlank String ticker,
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") @NotNull Date from,
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") @NotNull Date to,
			@RequestParam(value = "lastYear", required = true) boolean lastYear, 
			@RequestParam(value = "lastThirtyDays", required = true) boolean lastThirtyDays) throws Exception {

		if (to.before(from))
			return new ResponseEntity<>("Initial date must be previous or equal to final date",
					HttpStatus.BAD_REQUEST);

		ImportDailyEmotionsResponse response = new ImportDailyEmotionsResponse();
		try {
			response = stockNewsDataCollectorService.importDailyEmotionsByDate(ticker, from, to, lastYear, lastThirtyDays);
		} catch (ResponseStatusException re) {
			return new ResponseEntity<>("Bad connection", HttpStatus.INTERNAL_SERVER_ERROR);
		}

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
