package com.markettrender.newsemotions.models.responses;

import java.io.Serializable;

public class ImportDailyEmotionsResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private int saved;
	
	private int updated;

	private int successfullyProcessed;
	
	private int processedWithErrors;
	
	public ImportDailyEmotionsResponse() {}

	public ImportDailyEmotionsResponse(int saved, int updated, int successfullyProcessed, int processedWithErrors) {
		super();
		this.saved = saved;
		this.updated = updated;
		this.successfullyProcessed = successfullyProcessed;
		this.processedWithErrors = processedWithErrors;
	}

	public int getNumSaved() {
		return saved;
	}

	public void setNumSaved(int saved) {
		this.saved = saved;
	}

	public int getNumUpdated() {
		return updated;
	}

	public void setNumUpdated(int updated) {
		this.updated = updated;
	}

	public int getNumSuccessful() {
		return successfullyProcessed;
	}

	public void setNumSuccessful(int successfullyProcessed) {
		this.successfullyProcessed = successfullyProcessed;
	}

	public int getNumErrors() {
		return processedWithErrors;
	}

	public void setNumErrors(int processedWithErrors) {
		this.processedWithErrors = processedWithErrors;
	}
	
}
