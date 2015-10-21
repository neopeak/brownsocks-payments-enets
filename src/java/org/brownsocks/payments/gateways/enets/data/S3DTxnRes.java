package org.brownsocks.payments.gateways.enets.data;

import java.io.Serializable;

public class S3DTxnRes implements Serializable {
	private static final long serialVersionUID = 1L;

	private String PAreq;
	private String md;
	private String termUrl;
	private String acsUrl;
	private String errorCode;

	public String getPAreq() {
		return PAreq;
	}

	public void setPAreq(String pAreq) {
		PAreq = pAreq;
	}

	public String getMd() {
		return md;
	}

	public void setMd(String md) {
		this.md = md;
	}

	public String getTermUrl() {
		return termUrl;
	}

	public void setTermUrl(String termUrl) {
		this.termUrl = termUrl;
	}

	public String getAcsUrl() {
		return acsUrl;
	}

	public void setAcsUrl(String acsUrl) {
		this.acsUrl = acsUrl;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

}
