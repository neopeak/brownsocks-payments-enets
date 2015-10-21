package org.brownsocks.payments.gateways.enets.data;

import java.io.Serializable;

public class CreditTxnReq implements Serializable {
	private static final long serialVersionUID = 1L;

	private String pan;
	private String expiryDate;
	private String cvv;
	private String paymentType;
	private String postUrl;
	private String postUrlParams;
	private String cancelUrl;
	private String cancelUrlParams;
	private String cardBrand;
	private String cardHolderName;
	private String stan;
	private Secure3D s3d;
	private EzProtect ezProtect;
	private String invoice;
	private String param1;
	private String param2;
	private String param3;
	private String param4;
	private String param5;

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getPostUrl() {
		return postUrl;
	}

	public void setPostUrl(String postUrl) {
		this.postUrl = postUrl;
	}

	public String getPostUrlParams() {
		return postUrlParams;
	}

	public void setPostUrlParams(String postUrlParams) {
		this.postUrlParams = postUrlParams;
	}

	public String getCancelUrl() {
		return cancelUrl;
	}

	public void setCancelUrl(String cancelUrl) {
		this.cancelUrl = cancelUrl;
	}

	public String getCancelUrlParams() {
		return cancelUrlParams;
	}

	public void setCancelUrlParams(String cancelUrlParams) {
		this.cancelUrlParams = cancelUrlParams;
	}

	public String getCardBrand() {
		return cardBrand;
	}

	public void setCardBrand(String cardBrand) {
		this.cardBrand = cardBrand;
	}

	public String getCardHolderName() {
		return cardHolderName;
	}

	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}

	public String getStan() {
		return stan;
	}

	public void setStan(String stan) {
		this.stan = stan;
	}

	public Secure3D getS3d() {
		return s3d;
	}

	public void setS3d(Secure3D s3d) {
		this.s3d = s3d;
	}

	public EzProtect getEzProtect() {
		return ezProtect;
	}

	public void setEzProtect(EzProtect ezProtect) {
		this.ezProtect = ezProtect;
	}

	public String getInvoice() {
		return invoice;
	}

	public void setInvoice(String invoice) {
		this.invoice = invoice;
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

}
