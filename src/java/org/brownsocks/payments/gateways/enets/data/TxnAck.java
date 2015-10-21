package org.brownsocks.payments.gateways.enets.data;

import java.io.Serializable;

public class TxnAck implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer mid;
	private String netsMid;
	private String merchantTxnRef;
	private String merchantTxnStatus;
	private String merchantCertId;
	private String netsTxnRef;
	private CreditAck creditAck;
	private DebitAck debitAck;

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

	public String getMerchantTxnRef() {
		return merchantTxnRef;
	}

	public void setMerchantTxnRef(String merchantTxnRef) {
		this.merchantTxnRef = merchantTxnRef;
	}

	public String getMerchantTxnStatus() {
		return merchantTxnStatus;
	}

	public void setMerchantTxnStatus(String merchantTxnStatus) {
		this.merchantTxnStatus = merchantTxnStatus;
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

	public CreditAck getCreditAck() {
		return creditAck;
	}

	public void setCreditAck(CreditAck creditAck) {
		this.creditAck = creditAck;
	}

	public DebitAck getDebitAck() {
		return debitAck;
	}

	public void setDebitAck(DebitAck debitAck) {
		this.debitAck = debitAck;
	}

}
