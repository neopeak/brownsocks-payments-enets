package org.brownsocks.payments.gateways.enets.data;

import java.io.Serializable;

public class DebitTxnReq implements Serializable {
	private static final long serialVersionUID = 1L;

	private String merchantTxnDate;
	private String merchantTxnTime;
	private String merchantTimeZone;
	private String txnType;
	private String merchantCountryCode;
	private String retry;
	private String version;
	private String param1;
	private String param2;
	private String param3;
	private String param4;
	private String param5;
	private String merchantCertID;
	private String merchantSignature;
	private String strTxnEndParam;
	private String strNotifyParam;
	private String strTxnEndParam2;
	private String txnId;

	public String getMerchantTxnDate() {
		return merchantTxnDate;
	}

	public void setMerchantTxnDate(String merchantTxnDate) {
		this.merchantTxnDate = merchantTxnDate;
	}

	public String getMerchantTxnTime() {
		return merchantTxnTime;
	}

	public void setMerchantTxnTime(String merchantTxnTime) {
		this.merchantTxnTime = merchantTxnTime;
	}

	public String getMerchantTimeZone() {
		return merchantTimeZone;
	}

	public void setMerchantTimeZone(String merchantTimeZone) {
		this.merchantTimeZone = merchantTimeZone;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public String getMerchantCountryCode() {
		return merchantCountryCode;
	}

	public void setMerchantCountryCode(String merchantCountryCode) {
		this.merchantCountryCode = merchantCountryCode;
	}

	public String getRetry() {
		return retry;
	}

	public void setRetry(String retry) {
		this.retry = retry;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getParam1() {
		return param1;
	}

	public void setParam1(String param1) {
		this.param1 = param1;
	}

	public String getParam2() {
		return param2;
	}

	public void setParam2(String param2) {
		this.param2 = param2;
	}

	public String getParam3() {
		return param3;
	}

	public void setParam3(String param3) {
		this.param3 = param3;
	}

	public String getParam4() {
		return param4;
	}

	public void setParam4(String param4) {
		this.param4 = param4;
	}

	public String getParam5() {
		return param5;
	}

	public void setParam5(String param5) {
		this.param5 = param5;
	}

	public String getMerchantCertID() {
		return merchantCertID;
	}

	public void setMerchantCertID(String merchantCertID) {
		this.merchantCertID = merchantCertID;
	}

	public String getMerchantSignature() {
		return merchantSignature;
	}

	public void setMerchantSignature(String merchantSignature) {
		this.merchantSignature = merchantSignature;
	}

	public String getStrTxnEndParam() {
		return strTxnEndParam;
	}

	public void setStrTxnEndParam(String strTxnEndParam) {
		this.strTxnEndParam = strTxnEndParam;
	}

	public String getStrNotifyParam() {
		return strNotifyParam;
	}

	public void setStrNotifyParam(String strNotifyParam) {
		this.strNotifyParam = strNotifyParam;
	}

	public String getStrTxnEndParam2() {
		return strTxnEndParam2;
	}

	public void setStrTxnEndParam2(String strTxnEndParam2) {
		this.strTxnEndParam2 = strTxnEndParam2;
	}

	public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

}
