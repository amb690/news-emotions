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

	public int getSaved() {
		return saved;
	}

	public void setSaved(int saved) {
		this.saved = saved;
	}

	public int getUpdated() {
		return updated;
	}

	public void setUpdated(int updated) {
		this.updated = updated;
	}

	public int getSuccessfullyProcessed() {
		return successfullyProcessed;
	}

	public void setSuccessfullyProcessed(int successfullyProcessed) {
		this.successfullyProcessed = successfullyProcessed;
	}

	public int getProcessedWithErrors() {
		return processedWithErrors;
	}

	public void setProcessedWithErrors(int processedWithErrors) {
		this.processedWithErrors = processedWithErrors;
	}
	
}
