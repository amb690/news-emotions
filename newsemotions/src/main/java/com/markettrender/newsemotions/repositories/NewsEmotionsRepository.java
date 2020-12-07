package com.markettrender.newsemotions.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.markettrender.newsemotions.models.entity.NewsEmotion;

@Repository
public interface NewsEmotionsRepository extends JpaRepository<NewsEmotion, Long> {

	@Query("SELECT e FROM NewsEmotion e LEFT JOIN FETCH e.asset a WHERE a.ticker LIKE ?1 AND e.publishedAt BETWEEN ?2 AND ?3")
	List<NewsEmotion> findbyAssetBetweenTwoDates(String asset, Date from, Date to);
	
	@Query("SELECT e FROM NewsEmotion e LEFT JOIN FETCH e.asset a WHERE a.ticker LIKE ?1")
	List<NewsEmotion> findbyAsset(String asset);

	@Query("SELECT e FROM NewsEmotion e LEFT JOIN FETCH e.asset a WHERE a.ticker LIKE ?1 AND e.publishedAt = ?2")
	NewsEmotion findbyAssetAndDate(String asset, Date date);
}
