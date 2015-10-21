package org.brownsocks.payments.gateways.enets.data;

import java.io.Serializable;

public class TxnNotify implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer mid;
	private String netsMid;
	private String tid;
	private String paymentMode;
	private String txnAmount;
	private String currencyCode;
	private String merchantTxnRef;
	private String merchantTxnDtm;
	private String submissionMode;
	private String merchantCertId;
	private String netsTxnRef;
	private String netsTxnDtm;
	private String netsTxnStatus;
	private CreditNotify creditNotify;
	private DebitNotify debitNotify;

	public Integer getMid() {
		return mid;
	}

	public void setMid(Integer mid) {
		this.mid = mid;
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

	public String getNetsTxnRef() {
		return netsTxnRef;
	}

	public void setNetsTxnRef(String netsTxnRef) {
		this.netsTxnRef = netsTxnRef;
	}

	public String getNetsTxnDtm() {
		return netsTxnDtm;
	}

	public void setNetsTxnDtm(String netsTxnDtm) {
		this.netsTxnDtm = netsTxnDtm;
	}

	public String getNetsTxnStatus() {
		return netsTxnStatus;
	}

	public void setNetsTxnStatus(String netsTxnStatus) {
		this.netsTxnStatus = netsTxnStatus;
	}

	public CreditNotify getCreditNotify() {
		return creditNotify;
	}

	public void setCreditNotify(CreditNotify creditNotify) {
		this.creditNotify = creditNotify;
	}

	public DebitNotify getDebitNotify() {
		return debitNotify;
	}

	public void setDebitNotify(DebitNotify debitNotify) {
		this.debitNotify = debitNotify;
	}

}
