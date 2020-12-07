package com.markettrender.newsemotions.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "asset_id", "published_at", "apiName" }) })
public class NewsEmotion implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	private Asset asset;

	@Column(name = "emotion_score")
	private Double emotionScore;

	private Integer positive;
	
	private Integer neutral;
	
	private Integer negative;

	@Column(name = "emotion_label")
	private String emotionLabel;

	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createdAt;

	@Column(name = "published_at")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date publishedAt;
	
	@NotBlank
	private String apiName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getEmotionScore() {
		return emotionScore;
	}

	public void setEmotionScore(Double emotionScore) {
		this.emotionScore = emotionScore;
	}

	public String getEmotionLabel() {
		return emotionLabel;
	}

	public void setEmotionLabel(String emotionLabel) {
		this.emotionLabel = emotionLabel;
	}

	@PrePersist
	public void prePersist() {
		this.createdAt = new Date();
	}

	public Date getPublishedAt() {
		return publishedAt;
	}

	public void setPublishedAt(Date publishedAt) {
		this.publishedAt = publishedAt;
	}

	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}

	public Integer getPositive() {
		return positive;
	}

	public void setPositive(Integer positive) {
		this.positive = positive;
	}

	public Integer getNeutral() {
		return neutral;
	}

	public void setNeutral(Integer neutral) {
		this.neutral = neutral;
	}

	public Integer getNegative() {
		return negative;
	}

	public void setNegative(Integer negative) {
		this.negative = negative;
	}

	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	@Override
	public String toString() {
		return "Emotion [asset=" + asset + ", emotionScore=" + emotionScore + ", positive=" + positive + ", neutral="
				+ neutral + ", negative=" + negative + ", emotionLabel=" + emotionLabel + ", createdAt=" + createdAt
				+ ", publishedAt=" + publishedAt + "]";
	}

}
