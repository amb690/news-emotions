package com.markettrender.newsemotions.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.markettrender.newsemotions.models.entity.Asset;
import com.markettrender.newsemotions.service.AssetService;

@WebMvcTest(controllers = AssetController.class)
public class AssetControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private AssetService assetService;
	
	@Test
	void getAssetByTickerTest() throws Exception {
		
		Mockito.when(assetService.findByTicker1(Mockito.anyString())).thenReturn(new Asset());
		
		this.mockMvc.perform(get("/asset/{ticker}", "GOLD", new Date()))
			.andExpect(status().isOk());
	}
}
