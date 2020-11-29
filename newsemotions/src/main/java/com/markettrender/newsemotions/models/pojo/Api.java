package com.markettrender.newsemotions.models.pojo;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Api implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;

	private long numberOfPetitions;

	private long maxPetitions;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createdAt;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastPetition;

	public Api() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getNumberOfPetitions() {
		return numberOfPetitions;
	}

	public void setNumberOfPetitions(long numberOfPetitions) {
		this.numberOfPetitions = numberOfPetitions;
	}

	public long getMaxPetitions() {
		return maxPetitions;
	}

	public void setMaxPetitions(long maxPetitions) {
		this.maxPetitions = maxPetitions;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getLastPetition() {
		return lastPetition;
	}

	public void setLastPetition(Date lastPetition) {
		this.lastPetition = lastPetition;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Api [name=" + name + ", numberOfPetitions=" + numberOfPetitions + ", maxPetitions=" + maxPetitions
				+ ", createdAt=" + createdAt + ", lastPetition=" + lastPetition + "]";
	}

}
