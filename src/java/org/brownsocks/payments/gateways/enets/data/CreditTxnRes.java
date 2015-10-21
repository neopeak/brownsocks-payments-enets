package org.brownsocks.payments.gateways.enets.data;

import java.io.Serializable;

public class CreditTxnRes implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String netsAmountDeducted;
	private String netsRiskScore;
	private String bankAuthId;
	private S3DTxnRes s3dTxnRes;

	public String getNetsAmountDeducted() {
		return netsAmountDeducted;
	}

	public void setNetsAmountDeducted(String netsAmountDeducted) {
		this.netsAmountDeducted = netsAmountDeducted;
	}

	public String getNetsRiskScore() {
		return netsRiskScore;
	}

	public void setNetsRiskScore(String netsRiskScore) {
		this.netsRiskScore = netsRiskScore;
	}

	public String getBankAuthId() {
		return bankAuthId;
	}

	public void setBankAuthId(String bankAuthId) {
		this.bankAuthId = bankAuthId;
	}

	public S3DTxnRes getS3dTxnRes() {
		return s3dTxnRes;
	}

	public void setS3dTxnRes(S3DTxnRes s3dTxnRes) {
		this.s3dTxnRes = s3dTxnRes;
	}

}
