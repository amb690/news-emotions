package com.markettrender.newsemotions.service.impl;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.markettrender.newsemotions.exceptions.StockNewsApiException;
import com.markettrender.newsemotions.models.entity.Asset;
import com.markettrender.newsemotions.models.entity.Emotion;
import com.markettrender.newsemotions.models.pojo.Api;
import com.markettrender.newsemotions.models.pojo.stocknews.DailyEmotion;
import com.markettrender.newsemotions.models.pojo.stocknews.DailyEmotions;
import com.markettrender.newsemotions.models.pojo.stocknews.Ticker;
import com.markettrender.newsemotions.models.responses.ImportAllTickersResponse;
import com.markettrender.newsemotions.repositories.AssetRepository;
import com.markettrender.newsemotions.repositories.EmotionRepository;
import com.markettrender.newsemotions.service.StockNewsDataCollectorService;

@Service
public class StockNewsDataCollectorServiceImpl implements StockNewsDataCollectorService {

	protected final Log logger = LogFactory.getLog(StockNewsDataCollectorServiceImpl.class);
	
	private static final String CLASS_NAME = "StockNewsDataCollectorServiceImpl";
	private static final String START_SIGNAL = "------>" + CLASS_NAME;
	private static final String END_SIGNAL = "<------" + CLASS_NAME;
	
	private static final String API_NAME = "stocknews";

	private static final String STOCK_NEWS_DAILY_EMOTIONS_URL = "stat";
	private static final String STOCK_NEWS_GET_ALL_TICKERS_URL = "account/tickersdbv2";

	@Autowired
	private AssetRepository assetRepo;
	
	@Autowired
	private EmotionRepository emotionRepo;
	
	@Value("${api-manager-url}")
	private String apiManagerUrl;
	
	@Value("${stock-news-key}")
	private String stockNewsApiKey;
	
	@Value("${stock-news-url}")
	private String stockNewsRootApiUrl;
	
	@Override
	public ImportAllTickersResponse importAllTickers() throws Exception {
		
		int created = 0;
		int updated = 0;
		String methodName = "importAllTickers";
		logger.info(START_SIGNAL);
		logger.info("-->" + methodName);
		List<Ticker> tickers = getAllTickersResponse();
		for (Ticker ticker: tickers) {
			
			Asset asset = new Asset();
			try {
				asset = assetRepo.findbyName(ticker.getTicker());
			} catch(Exception e) {
				logger.error("Database error trying to to find ticker " + ticker.getTicker());
				throw new Exception("Database error", e);
			}
			
			if (asset == null) {
				logger.trace("Creating ticker " + ticker.getTicker());
				asset = setAssetFromTicker(new Asset(), ticker);
				created++;
			} else {
				logger.trace("Updating ticker " + ticker.getTicker());
				asset = setAssetFromTicker(asset, ticker);
				updated++;
			}
			
			try {
				assetRepo.save(asset);
			} catch(Exception e) {
				logger.error("Database error creating/updating ticker " + ticker.getTicker());
				throw new Exception("Database error", e);
			}
		}
		ImportAllTickersResponse response = new ImportAllTickersResponse(created, updated);
		logger.info(response);
		logger.info("-->" + methodName);
		logger.info(END_SIGNAL);
		
		return response;
	}
	
	private Asset setAssetFromTicker(Asset asset, Ticker ticker) {
		asset.setTicker(ticker.getTicker());
		asset.setName(ticker.getName());
		asset.setCountry(ticker.getCountry());
		asset.setIndustry(ticker.getIndustry());
		asset.setSector(ticker.getSector());
		asset.setHasNews(ticker.isHasNews());
		asset.setIpoDate(ticker.getIpoDate());
		asset.setExchange(ticker.getExchange());
		return asset;
	}
	
	private List<Ticker> getAllTickersResponse() throws IOException {
		
		String methodName = "getAllTickersResponse";
		logger.info("-->" + methodName);
		
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(stockNewsRootApiUrl + STOCK_NEWS_GET_ALL_TICKERS_URL)
				.queryParam("token", stockNewsApiKey);

		HttpEntity<?> entity = new HttpEntity<>(headers);
		HttpEntity<String> response = null;
		try {
			response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity,
					String.class);
			logger.info("successful connection");
		}catch(Exception e) {
			logger.error("stock news connection error", e);
		}
		
		String fileName = "all-tickers.json";
        ClassLoader classLoader = getClass().getClassLoader();
        File jsonFile = new File(classLoader.getResource(fileName).getFile());
		
		JsonNode node = null;
		if (response != null) {
			node = new ObjectMapper().readTree(response.getBody());
			logger.info("Retrieving data from api: " + API_NAME);
		} else {
			logger.info("Retrieving data from file: " + jsonFile.getName());
	    	node = new ObjectMapper().readTree(jsonFile);
	    }
	    JsonNode dataNode = node.get("data");
	    Iterator<JsonNode> dataElements = dataNode.elements();
	    
	    List<Ticker> tickers = new ArrayList<>();
	    while (dataElements.hasNext()) {
	    	
	    	JsonNode ticker = dataElements.next();
	    	
	    	String tck = ticker.get("ticker").asText();
	    	String name = ticker.get("name").asText();
	    	String country = ticker.get("country").asText();
	    	String industry = ticker.get("industry").asText();
	    	String sector = ticker.get("sector").asText();
	    	boolean hasNews = ticker.get("has_news").asText().equals("yes");
	    	
	    	String ipoDateStr = ticker.get("ipo_date").asText();
	    	Date ipoDate = new Date();
	    	if (!ipoDateStr.equals("null")) {
		    	try {
		    		ipoDate = new SimpleDateFormat("yyyy-MM-dd").parse(ipoDateStr);
				} catch (ParseException e) {
					logger.error("ipo date parse error");
				}
	    	} else {
	    		ipoDate = null;
	    	}
	    	
	    	String exchange = ticker.get("exchange").asText();
	    	
	    	Ticker tkcUnit = new Ticker(tck, name, country, industry, sector, hasNews, ipoDate, exchange);
	    	tickers.add(tkcUnit);
	    	
			logger.trace("Successfully requested ticker " + ticker);
	    }

		logger.info("<--" + methodName);
	    
	    return tickers;
	}

	@Override
	public boolean importDailyEmotionsByDate(String ticker, Date from, Date to, boolean lastYear, boolean lastThirtyDays) throws Exception {

		String methodName = "importDailyEmotions";
		logger.info("-->" + methodName);
		
		// se obtienen los datos de la api
		Api apiResponse = this.getApiData();
		
		// se comprueba que no se ha excedido la cuota máxima de la api
		long numPetitions = apiResponse.getNumberOfPetitions();
		long maxPetitions = apiResponse.getMaxPetitions();
		if (numPetitions >= maxPetitions) {
			logger.error("Stock news api quota exceeded");
			throw new StockNewsApiException("Stock news api quota exceeded");
		}
		
		DailyEmotions dailyEmotions = getDailyEmotionsResponse(from, to, ticker, 1, lastYear, lastThirtyDays);
		List<DailyEmotion> dailyEmotionsList = dailyEmotions.getEmotionRates();
		
		int pages = dailyEmotions.getPages();
		if (numPetitions + pages >= maxPetitions)
			throw new StockNewsApiException("Stock news api quota exceeded");
		
		for (int i=2; i<=pages; i++) {
			DailyEmotions otherDailyEmotions = getDailyEmotionsResponse(from, to, ticker, i, lastYear, lastThirtyDays);
			List<DailyEmotion> otherDailyEmotionsList = otherDailyEmotions.getEmotionRates();
			dailyEmotionsList.addAll(otherDailyEmotionsList);
		}
		dailyEmotions.setEmotionRates(dailyEmotionsList);
		
		this.saveDailyEmotions(ticker, dailyEmotionsList, apiResponse.getName());

		this.updateApiPetitions(apiResponse, pages);
		
		logger.info("<--" + methodName);
		
		return true;
	}
	
	private Api getApiData() throws StockNewsApiException {
		
		String methodName = "getApiData";
		logger.info("-->" + methodName);
		
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<Api> apiManagerResponse = new ResponseEntity<>(HttpStatus.OK);
		
		Api apiResponse = new Api();
		try {
			apiManagerResponse = restTemplate.getForEntity(apiManagerUrl + API_NAME, Api.class);
			apiResponse = apiManagerResponse.getBody();

		} catch (Exception e) {
			logger.error("Api manager connection error");
			throw new StockNewsApiException("Api manager connection error");
		}
		
		logger.info("Api response: " + apiResponse);
		logger.info("<--" + methodName);
		return apiResponse;
	}
	
	private void updateApiPetitions(Api apiResponse, int petitions) throws StockNewsApiException {
		
		String methodName = "updateApiPetitions";
		logger.info("-->" + methodName);
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		
		// actualiza peticiones en api manager
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiManagerUrl + "petitions/" + apiResponse.getId())
				.queryParam("numberOfPetitions", (apiResponse.getNumberOfPetitions() + petitions));

		try {
		HttpEntity<?> entity = new HttpEntity<>(headers);
		restTemplate.exchange(builder.toUriString(), HttpMethod.PUT, entity,
				String.class);
		} catch(Exception e) {
			logger.error("Api manager connection error");
			throw new StockNewsApiException("Api manager connection error", e);
		}
		logger.info("Api " + apiResponse.getName() + " successfully updated with " + (apiResponse.getNumberOfPetitions() + petitions) + " petitions");
		logger.info("<--" + methodName);
	}

	private DailyEmotions getDailyEmotionsResponse(Date from, Date to, String ticker, int pageNumber, boolean lastYear, boolean lastThirtyDays) throws StockNewsApiException {
		
		String methodName = "getDailyEmotionsResponse";
		logger.info("-->" + methodName);
		
		RestTemplate restTemplate = new RestTemplate();
		
		SimpleDateFormat format = new SimpleDateFormat("MMddyyyy");
		String formatedFromDate = format.format(from);
		String formatedToDate = format.format(to);
		
		String date = formatedFromDate.concat("-").concat(formatedToDate);

		if (lastThirtyDays) {
			logger.info("Retrieving last 30 days emotions");
			date = "last30days";
		}
		if (lastYear) {
			logger.info("Retrieving last year emotions");
			date = "yeartodate";
		}
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(stockNewsRootApiUrl + STOCK_NEWS_DAILY_EMOTIONS_URL)
				.queryParam("tickers", ticker)
				.queryParam("date", date)
				.queryParam("page", pageNumber)
				.queryParam("token", stockNewsApiKey);

		HttpEntity<?> entity = new HttpEntity<>(headers);

		HttpEntity<String> response = null;
		try {
			response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity,
				String.class);
		} catch(Exception e) {
			throw new StockNewsApiException("Error getting daily emotions", e);
		}
		
		DailyEmotions dailyEmotions = new DailyEmotions();
	    JsonNode node = null;
	    
		try {
			node = new ObjectMapper().readTree(response.getBody());
		} catch (JsonProcessingException e1) {
			logger.error("Json read error", e1);	
		}
		
		if (node == null) {
			return dailyEmotions;
		}
		
	    Iterator<Map.Entry<String,JsonNode>> dataNode = node.get("data").fields();
	    
	    int pages = node.get("total_pages").asInt();
	    List<DailyEmotion> dailyEmotionsList = new ArrayList<>();
	    while (dataNode.hasNext()) {
	    	Map.Entry<String,JsonNode> data = dataNode.next();
	    	
	    	String dateString = data.getKey();
	    	Date publishedAt = new Date();
	    	try {
	    		publishedAt = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
			} catch (ParseException e) {
				logger.error("error parsing publishedAt date");
			} 
	    	
	    	JsonNode emotionRates = data.getValue().get(ticker);
	    	int neutral = emotionRates.get("Neutral").asInt();
	    	int positive = emotionRates.get("Positive").asInt();
	    	int negative = emotionRates.get("Negative").asInt();
	    	double sentimentScore = emotionRates.get("sentiment_score").asDouble();
	    	
	    	String labelScore = "";
	    	if (positive > negative) {
	    		labelScore = "POSITIVE";
	    	} else if (negative > positive) {
	    		labelScore = "NEGATIVE";
	    	} else {
	    		labelScore = "NEUTRAL";
	    	}
	    	
	    	DailyEmotion emotionRate = new DailyEmotion(publishedAt, neutral, positive, negative, labelScore,
	    			sentimentScore);
	    	dailyEmotionsList.add(emotionRate);
	    }
	 
	    dailyEmotions.setEmotionRates(dailyEmotionsList);
	    dailyEmotions.setPages(pages);
    	
    	return dailyEmotions;
	}
	
	private void saveDailyEmotions(String ticker, List<DailyEmotion> dailyEmotions, String apiName) {
		
		String methodName = "saveDailyEmotions";
		logger.info("-->" + methodName);
		
		Asset asset = assetRepo.findbyName(ticker);
		if (asset == null) {
			asset = new Asset();
			asset.setTicker(ticker);
			assetRepo.save(asset);
		}
		logger.debug("Asset info: " + asset);
		
		for (DailyEmotion dailyEmotion: dailyEmotions) {
			
			Emotion importedEmotion = emotionRepo.findbyAssetAndDate(ticker, dailyEmotion.getPublishedAt());
			
			if (importedEmotion != null)
				continue;
			
			Emotion emotion = new Emotion();
			emotion.setAsset(asset);
			emotion.setPositive(dailyEmotion.getPositive());
			emotion.setNeutral(dailyEmotion.getNeutral());
			emotion.setNegative(dailyEmotion.getNegative());
			emotion.setEmotionLabel(dailyEmotion.getScoreLabel());
			emotion.setEmotionScore(dailyEmotion.getTotalScore());
			emotion.setPublishedAt(dailyEmotion.getPublishedAt());
			emotion.setApiName(apiName);
			
			try {
				emotionRepo.save(emotion);
			}catch(Exception e) {
				logger.error("Error trying to save daily emotion");
			}
			logger.trace("Emotion " + emotion + " successfully imported");
			
		}
		
		logger.info("<--" + methodName);
	}

}