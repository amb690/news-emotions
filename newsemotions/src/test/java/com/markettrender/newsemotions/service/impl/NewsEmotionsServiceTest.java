package com.markettrender.newsemotions.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.markettrender.newsemotions.models.entity.Asset;
import com.markettrender.newsemotions.models.entity.NewsEmotion;
import com.markettrender.newsemotions.repositories.NewsEmotionsRepository;
import com.markettrender.newsemotions.service.EmotionService;

@SpringBootTest
class NewsEmotionsServiceTest {

	@Autowired
	private EmotionService newsEmotionsService;

	@MockBean
	private NewsEmotionsRepository newsEmotionsRepo;
	
	@Test
	void findAllNewsEmotionsTest() {

		NewsEmotion mockedEmotion = new NewsEmotion();
		mockedEmotion.setApiName("twitter");

		List<NewsEmotion> emotions = new ArrayList<NewsEmotion>();
		emotions.add(mockedEmotion);

		when(newsEmotionsRepo.findAll()).thenReturn(emotions);

		List<NewsEmotion> savedEmotions = newsEmotionsService.findAll();

		assertThat(savedEmotions.get(0).getApiName()).isEqualTo("twitter");
	}
	
	@Test
	void findNewsEmotionsByAssetBetweenTwoDatesTest() {

		NewsEmotion mockedEmotion = new NewsEmotion();
		Asset apple = new Asset();
		apple.setTicker("APPL");
		apple.setCountry("EEUU");
		apple.setCreatedAt(new Date(445555));
		apple.setExchange("NASDAQ100");
		apple.setHasNews(true);
		apple.setId(5L);
		apple.setIndustry("Technology");
		apple.setIpoDate(new Date(56666));
		apple.setName("Apple INC");
		apple.setSector("Technology");
		
		mockedEmotion.setAsset(apple);
		mockedEmotion.setApiName("twitter");
		mockedEmotion.setEmotionLabel("POSITIVE");
		mockedEmotion.setEmotionScore(6.0);
		mockedEmotion.setId(43L);
		mockedEmotion.setNegative(5);
		mockedEmotion.setNeutral(1);
		mockedEmotion.setPositive(10);
		mockedEmotion.setPublishedAt(new Date(566666));
		mockedEmotion.prePersist();
		
		List<NewsEmotion> emotions = new ArrayList<NewsEmotion>();
		emotions.add(mockedEmotion);

		when(newsEmotionsRepo.findbyAssetBetweenTwoDates(Mockito.anyString(), Mockito.any(Date.class), Mockito.any(Date.class)))
			.thenReturn(emotions);

		List<NewsEmotion> savedEmotions = newsEmotionsService.findByAssetBetweenTwoDates("APPL", new Date(244444), new Date(244444));

		NewsEmotion appleEmotion = savedEmotions.get(0);
		assertThat(appleEmotion.getApiName()).isEqualTo("twitter");
		assertThat(appleEmotion.getAsset()).isNotNull();
		assertThat(appleEmotion.getEmotionLabel()).isEqualTo("POSITIVE");
		assertThat(appleEmotion.getEmotionScore()).isEqualTo(6.0);
		assertThat(appleEmotion.getId()).isEqualTo(43L);
		assertThat(appleEmotion.getNegative()).isEqualTo(5);
		assertThat(appleEmotion.getNeutral()).isEqualTo(1);
		assertThat(appleEmotion.getPositive()).isEqualTo(10);
		assertThat(appleEmotion.getPublishedAt()).isNotNull();
		assertThat(appleEmotion.toString()).isNotNull();
		
	}
	
	@Test
	void findByAssetAndDateTest() {

		NewsEmotion mockedEmotion = new NewsEmotion();
		mockedEmotion.setApiName("twitter");

		List<NewsEmotion> emotions = new ArrayList<NewsEmotion>();
		emotions.add(mockedEmotion);

		when(newsEmotionsRepo.findbyAssetAndDate(Mockito.anyString(), Mockito.any(Date.class))).thenReturn(mockedEmotion);

		NewsEmotion savedEmotion = newsEmotionsService.findByAssetAndDate("APPL", new Date(56L));

		assertThat(savedEmotion.getApiName()).isEqualTo("twitter");
	}
	
	@Test
	void saveNewsEmotionTest() {

		NewsEmotion mockedEmotion = new NewsEmotion();
		mockedEmotion.setApiName("twitter");

		when(newsEmotionsRepo.save(Mockito.any(NewsEmotion.class))).thenReturn(mockedEmotion);

		newsEmotionsService.save(mockedEmotion);

		assertThat(mockedEmotion.getApiName()).isEqualTo("twitter");
	}
	
	@Test
	void findByAssetTest() {

		NewsEmotion mockedEmotion = new NewsEmotion();
		mockedEmotion.setApiName("twitter");

		List<NewsEmotion> emotions = new ArrayList<NewsEmotion>();
		emotions.add(mockedEmotion);

		when(newsEmotionsRepo.findbyAsset(Mockito.anyString())).thenReturn(emotions);

		List<NewsEmotion> savedEmotionList = newsEmotionsService.findByAsset("APPL");

		assertThat(savedEmotionList.get(0).getApiName()).isEqualTo("twitter");
	}
}
