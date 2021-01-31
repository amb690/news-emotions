package com.markettrender.newsemotions.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.markettrender.newsemotions.models.entity.Asset;

@Repository
public interface AssetPageableRepository extends PagingAndSortingRepository<Asset, Long>{
}
