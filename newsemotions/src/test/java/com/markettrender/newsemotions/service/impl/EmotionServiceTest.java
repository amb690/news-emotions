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
import com.markettrender.newsemotions.models.entity.Emotion;
import com.markettrender.newsemotions.repositories.EmotionRepository;
import com.markettrender.newsemotions.service.EmotionService;

@SpringBootTest
class EmotionServiceTest {

	@Autowired
	private EmotionService emotionService;

	@MockBean
	private EmotionRepository emotionRepo;
	
	@Test
	void findAllNewsEmotionsTest() {

		Emotion mockedEmotion = new Emotion();
		mockedEmotion.setApiName("twitter");

		List<Emotion> emotions = new ArrayList<Emotion>();
		emotions.add(mockedEmotion);

		when(emotionRepo.findAll()).thenReturn(emotions);

		List<Emotion> savedEmotions = emotionService.findAll();

		assertThat(savedEmotions.get(0).getApiName()).isEqualTo("twitter");
	}
	
	@Test
	void findNewsEmotionsByAssetBetweenTwoDatesTest() {

		Emotion mockedEmotion = new Emotion();
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
		
		List<Emotion> emotions = new ArrayList<Emotion>();
		emotions.add(mockedEmotion);

		when(emotionRepo.findbyAssetBetweenTwoDates(Mockito.anyString(), Mockito.any(Date.class), Mockito.any(Date.class)))
			.thenReturn(emotions);

		List<Emotion> savedEmotions = emotionService.findByAssetBetweenTwoDates("APPL", new Date(244444), new Date(244444));

		Emotion appleEmotion = savedEmotions.get(0);
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

		Emotion mockedEmotion = new Emotion();
		mockedEmotion.setApiName("twitter");

		List<Emotion> emotions = new ArrayList<Emotion>();
		emotions.add(mockedEmotion);

		when(emotionRepo.findbyAssetAndDate(Mockito.anyString(), Mockito.any(Date.class))).thenReturn(mockedEmotion);

		Emotion savedEmotion = emotionService.findByAssetAndDate("APPL", new Date(56L));

		assertThat(savedEmotion.getApiName()).isEqualTo("twitter");
	}
	
	@Test
	void saveNewsEmotionTest() {

		Emotion mockedEmotion = new Emotion();
		mockedEmotion.setApiName("twitter");

		when(emotionRepo.save(Mockito.any(Emotion.class))).thenReturn(mockedEmotion);

		emotionService.save(mockedEmotion);

		assertThat(mockedEmotion.getApiName()).isEqualTo("twitter");
	}
	
	@Test
	void findByAssetTest() {

		Emotion mockedEmotion = new Emotion();
		mockedEmotion.setApiName("twitter");

		List<Emotion> emotions = new ArrayList<Emotion>();
		emotions.add(mockedEmotion);

		when(emotionRepo.findbyAsset(Mockito.anyString())).thenReturn(emotions);

		List<Emotion> savedEmotionList = emotionService.findByAsset("APPL");

		assertThat(savedEmotionList.get(0).getApiName()).isEqualTo("twitter");
	}
}
