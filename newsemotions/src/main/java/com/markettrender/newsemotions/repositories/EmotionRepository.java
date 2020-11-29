package com.markettrender.newsemotions.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.markettrender.newsemotions.models.entity.Emotion;

@Repository
public interface EmotionRepository extends JpaRepository<Emotion, Long> {

	@Query("SELECT e FROM Emotion e LEFT JOIN FETCH e.asset a WHERE a.ticker LIKE ?1 AND e.publishedAt BETWEEN ?2 AND ?3")
	List<Emotion> findbyAssetBetweenTwoDates(String asset, Date from, Date to);
	
	@Query("SELECT e FROM Emotion e LEFT JOIN FETCH e.asset a WHERE a.ticker LIKE ?1")
	List<Emotion> findbyAsset(String asset);

	@Query("SELECT e FROM Emotion e LEFT JOIN FETCH e.asset a WHERE a.ticker LIKE ?1 AND e.publishedAt = ?2")
	Emotion findbyAssetAndDate(String asset, Date date);
}
