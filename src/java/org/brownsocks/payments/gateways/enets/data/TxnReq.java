package org.brownsocks.payments.gateways.enets.data;

import java.io.Serializable;

public class TxnReq implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer mid;
	private String netsTxnRef;
	private String svcCode;
	private String netsTxnDtm;
	private String netsMid;
	private String tid;
	private String clientType;
	private String paymentMode;
	private String txnAmount;
	private String currencyCode;
	private String merchantTxnRef;
	private String merchantTxnDtm;
	private String submissionMode;
	private String merchantCertId;
	private String successUrl;
	private String successUrlParams;
	private String failureUrl;
	private String failureUrlParams;
	private String notifyUrl;
	private String notifyUrlParams;
	private CreditTxnReq creditTxnReq;
	private DebitTxnReq debitTxnReq;

	public Integer getMid() {
		return mid;
	}

	public void setMid(Integer mid) {
		this.mid = mid;
	}

	public String getNetsTxnRef() {
		return netsTxnRef;
	}

	public void setNetsTxnRef(String netsTxnRef) {
		this.netsTxnRef = netsTxnRef;
	}

	public String getSvcCode() {
		return svcCode;
	}

	public void setSvcCode(String svcCode) {
		this.svcCode = svcCode;
	}

	public String getNetsTxnDtm() {
		return netsTxnDtm;
	}

	public void setNetsTxnDtm(String netsTxnDtm) {
		this.netsTxnDtm = netsTxnDtm;
	}

	public String getNetsMid() {
		return netsMid;
	}

	public void setNetsMid(String netsMid) {
		this.netsMid = netsMid;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getTxnAmount() {
		return txnAmount;
	}

	public void setTxnAmount(String txnAmount) {
		this.txnAmount = txnAmount;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getMerchantTxnRef() {
		return merchantTxnRef;
	}

	public void setMerchantTxnRef(String merchantTxnRef) {
		this.merchantTxnRef = merchantTxnRef;
	}

	public String getMerchantTxnDtm() {
		return merchantTxnDtm;
	}

	public void setMerchantTxnDtm(String merchantTxnDtm) {
		this.merchantTxnDtm = merchantTxnDtm;
	}

	public String getSubmissionMode() {
		return submissionMode;
	}

	public void setSubmissionMode(String submissionMode) {
		this.submissionMode = submissionMode;
	}

	public String getMerchantCertId() {
		return merchantCertId;
	}

	public void setMerchantCertId(String merchantCertId) {
		this.merchantCertId = merchantCertId;
	}

	public String getSuccessUrl() {
		return successUrl;
	}

	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}

	public String getSuccessUrlParams() {
		return successUrlParams;
	}

	public void setSuccessUrlParams(String successUrlParams) {
		this.successUrlParams = successUrlParams;
	}

	public String getFailureUrl() {
		return failureUrl;
	}

	public void setFailureUrl(String failureUrl) {
		this.failureUrl = failureUrl;
	}

	public String getFailureUrlParams() {
		return failureUrlParams;
	}

	public void setFailureUrlParams(String failureUrlParams) {
		this.failureUrlParams = failureUrlParams;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getNotifyUrlParams() {
		return notifyUrlParams;
	}

	public void setNotifyUrlParams(String notifyUrlParams) {
		this.notifyUrlParams = notifyUrlParams;
	}

	public CreditTxnReq getCreditTxnReq() {
		return creditTxnReq;
	}

	public void setCreditTxnReq(CreditTxnReq creditTxnReq) {
		this.creditTxnReq = creditTxnReq;
	}

	public DebitTxnReq getDebitTxnReq() {
		return debitTxnReq;
	}

	public void setDebitTxnReq(DebitTxnReq debitTxnReq) {
		this.debitTxnReq = debitTxnReq;
	}

}
