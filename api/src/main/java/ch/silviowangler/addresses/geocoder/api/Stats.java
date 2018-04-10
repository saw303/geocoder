package ch.silviowangler.addresses.geocoder.api;

import java.io.Serializable;

/**
 * @author Silvio Wangler
 */
public class Stats implements Serializable {

	private long unprocessed;
	private long processed;
	private long processedNoGeocodes;
	private long totalRecords;

	public long getUnprocessed() {
		return unprocessed;
	}

	public void setUnprocessed(long unprocessed) {
		this.unprocessed = unprocessed;
	}

	public long getProcessed() {
		return processed;
	}

	public void setProcessed(long processed) {
		this.processed = processed;
	}

	public long getProcessedNoGeocodes() {
		return processedNoGeocodes;
	}

	public void setProcessedNoGeocodes(long processedNoGeocodes) {
		this.processedNoGeocodes = processedNoGeocodes;
	}

	public long getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(long totalRecords) {
		this.totalRecords = totalRecords;
	}

	public double getProgress() {
		return Double.valueOf(100d) / this.totalRecords * (this.processed + this.processedNoGeocodes);
	}
}
