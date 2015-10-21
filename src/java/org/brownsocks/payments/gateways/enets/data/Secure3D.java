package org.brownsocks.payments.gateways.enets.data;

import java.io.Serializable;

public class Secure3D implements Serializable {
	private static final long serialVersionUID = 1L;

	private String purchaseXid;
	private String txCavv;
	private String txEci;
	private String txStatus;
	private String ucafind;
	private Integer merchId;
	private String merchRef;

	public String getPurchaseXid() {
		return purchaseXid;
	}

	public void setPurchaseXid(String purchaseXid) {
		this.purchaseXid = purchaseXid;
	}

	public String getTxCavv() {
		return txCavv;
	}

	public void setTxCavv(String txCavv) {
		this.txCavv = txCavv;
	}

	public String getTxEci() {
		return txEci;
	}

	public void setTxEci(String txEci) {
		this.txEci = txEci;
	}

	public String getTxStatus() {
		return txStatus;
	}

	public void setTxStatus(String txStatus) {
		this.txStatus = txStatus;
	}

	public String getUcafind() {
		return ucafind;
	}

	public void setUcafind(String ucafind) {
		this.ucafind = ucafind;
	}

	public Integer getMerchId() {
		return merchId;
	}

	public void setMerchId(Integer merchId) {
		this.merchId = merchId;
	}

	public String getMerchRef() {
		return merchRef;
	}

	public void setMerchRef(String merchRef) {
		this.merchRef = merchRef;
	}

}
