package com.markettrender.newsemotions.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.markettrender.newsemotions.models.entity.Asset;
import com.markettrender.newsemotions.models.entity.NewsEmotion;
import com.markettrender.newsemotions.repositories.AssetRepository;
import com.markettrender.newsemotions.service.AssetService;

@SpringBootTest
class AssetServiceTest {

	@Autowired
	private AssetService assetService;

	@MockBean
	private AssetRepository assetRepo;
	
	@Test
	void saveNewsEmotionTest() {

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
		apple.setEmotions(new ArrayList<NewsEmotion>());
		apple.prePersist();

		when(assetRepo.save(Mockito.any(Asset.class))).thenReturn(apple);

		assetService.save(apple);

		assertThat(apple.getTicker()).isEqualTo("APPL");
		assertThat(apple.getCountry()).isEqualTo("EEUU");
		assertThat(apple.getCreatedAt()).isNotNull();
		assertThat(apple.getExchange()).isEqualTo("NASDAQ100");
		assertThat(apple.getHasNews()).isEqualTo(true);
		assertThat(apple.getId()).isEqualTo(5L);
		assertThat(apple.getIndustry()).isEqualTo("Technology");
		assertThat(apple.getIpoDate()).isNotNull();
		assertThat(apple.getName()).isEqualTo("Apple INC");
		assertThat(apple.getSector()).isEqualTo("Technology");
		assertThat(apple.getEmotions()).isNotNull();
		assertThat(apple.getCreatedAt()).isNotNull();
		
	}
	
	@Test
	void findByTickerTest() {

		Asset asset = new Asset();
		asset.setCountry("EEUU");

		when(assetRepo.findbyName(Mockito.anyString())).thenReturn(asset);

		assetService.findByTicker1("APPL");

		assertThat(asset.getCountry()).isEqualTo("EEUU");
	}
	
}
