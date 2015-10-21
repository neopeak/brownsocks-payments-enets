package org.brownsocks.payments.gateways.enets.data;

import java.io.Serializable;
import java.util.List;

public class TxnRes implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer mid;
	private String tid;
	private String netsMid;
	private String paymentMode;
	private String merchantTxnRef;
	private String netsTxnRef;
	private String netsTxnDtm;
	private String netsTxnStatus;
	private String netsTxnRespCode;
	private String netsTxnMsg;
	private String merchantCertId;
	private List<CustomAttribute> customAttribute;
	private CreditTxnRes creditTxnRes;
	private DebitTxnRes debitTxnRes;

	public Integer getMid() {
		return mid;
	}

	public void setMid(Integer mid) {
		this.mid = mid;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getNetsMid() {
		return netsMid;
	}

	public void setNetsMid(String netsMid) {
		this.netsMid = netsMid;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getMerchantTxnRef() {
		return merchantTxnRef;
	}

	public void setMerchantTxnRef(String merchantTxnRef) {
		this.merchantTxnRef = merchantTxnRef;
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

	public String getNetsTxnRespCode() {
		return netsTxnRespCode;
	}

	public void setNetsTxnRespCode(String netsTxnRespCode) {
		this.netsTxnRespCode = netsTxnRespCode;
	}

	public String getNetsTxnMsg() {
		return netsTxnMsg;
	}

	public void setNetsTxnMsg(String netsTxnMsg) {
		this.netsTxnMsg = netsTxnMsg;
	}

	public String getMerchantCertId() {
		return merchantCertId;
	}

	public void setMerchantCertId(String merchantCertId) {
		this.merchantCertId = merchantCertId;
	}

	public List<CustomAttribute> getCustomAttribute() {
		return customAttribute;
	}

	public void setCustomAttribute(List<CustomAttribute> customAttribute) {
		this.customAttribute = customAttribute;
	}

	public CreditTxnRes getCreditTxnRes() {
		return creditTxnRes;
	}

	public void setCreditTxnRes(CreditTxnRes creditTxnRes) {
		this.creditTxnRes = creditTxnRes;
	}

	public DebitTxnRes getDebitTxnRes() {
		return debitTxnRes;
	}

	public void setDebitTxnRes(DebitTxnRes debitTxnRes) {
		this.debitTxnRes = debitTxnRes;
	}

}
