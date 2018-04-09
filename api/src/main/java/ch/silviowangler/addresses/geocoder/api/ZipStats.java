package ch.silviowangler.addresses.geocoder.api;

import java.io.Serializable;

/**
 * @author Silvio Wangler
 */
public class ZipStats implements Serializable {

	private String zip;
	private long total;
	private long processed;
	private long unprocessed;

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public long getProcessed() {
		return processed;
	}

	public void setProcessed(long processed) {
		this.processed = processed;
	}

	public long getUnprocessed() {
		return unprocessed;
	}

	public void setUnprocessed(long unprocessed) {
		this.unprocessed = unprocessed;
	}
}
