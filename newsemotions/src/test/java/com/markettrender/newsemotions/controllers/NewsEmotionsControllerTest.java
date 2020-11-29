package com.markettrender.newsemotions.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.markettrender.newsemotions.models.entity.Emotion;
import com.markettrender.newsemotions.service.EmotionService;

@WebMvcTest(controllers = NewsEmotionsController.class)
class NewsEmotionsControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private EmotionService emotionService;
	
	private List<Emotion> emotionList;

	@BeforeEach
	void setUp() {
		this.emotionList = new ArrayList<>();

		Emotion emotion = new Emotion();
		emotion.setEmotionLabel("SCORE");
		this.emotionList.add(emotion);
	}

	@Test
	void getEmotionsByTickerTest() throws Exception {
		
		Mockito.when(emotionService.findByAsset(Mockito.anyString())).thenReturn(emotionList);
		
		this.mockMvc.perform(get("/newsemotions/{ticker}", "GOLD", new Date()))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.size()", is(emotionList.size())));
	}
	
	@Test
	void getEmotionsByTickerAndDateTest() throws Exception {
		
		Mockito.when(emotionService.findByAssetAndDate(Mockito.anyString(), Mockito.any(Date.class))).thenReturn(new Emotion());
		
		this.mockMvc.perform(get("/newsemotions/{ticker}/{date}", "GOLD", "2020-10-25"))
			.andExpect(status().isOk());
	}
	
	@Test
	void getEmotionsBetweenTwoDatesTest() throws Exception {
		
		Mockito.when(emotionService.findByAssetBetweenTwoDates(Mockito.anyString(), Mockito.any(Date.class), Mockito.any(Date.class))).thenReturn(emotionList);
		
		this.mockMvc.perform(get("/newsemotions/{ticker}/{from}/{to}", "GOLD", "2020-10-24", "2020-10-25"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.size()", is(emotionList.size())));
	}

}
