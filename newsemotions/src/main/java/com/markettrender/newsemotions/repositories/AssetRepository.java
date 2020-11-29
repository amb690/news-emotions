package com.markettrender.newsemotions.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.markettrender.newsemotions.models.entity.Asset;

@Repository
public interface AssetRepository extends CrudRepository<Asset, Long> {

	@Query("SELECT a FROM Asset a WHERE a.ticker LIKE ?1")
	Asset findbyName(String ticker);
}
