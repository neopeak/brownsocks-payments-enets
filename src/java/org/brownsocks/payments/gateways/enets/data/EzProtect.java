package org.brownsocks.payments.gateways.enets.data;

import java.io.Serializable;
import java.util.List;

public class EzProtect implements Serializable {
	private static final long serialVersionUID = 1L;

	private String billFirstName;
	private String billLastName;
	private String billInitial;
	private String billAddr1;
	private String billAddr2;
	private String billCoyName;
	private String billCity;
	private String billState;
	private String billZipCode;
	private String billCountry;
	private String billMobileNum;
	private String billPhoneNum;
	private String billFaxNum;
	private String billEmail;
	private String shipFirstName;
	private String shipLastName;
	private String shipInitial;
	private String shipAddr1;
	private String shipAddr2;
	private String shipCoyName;
	private String shipCity;
	private String shipState;
	private String shipZipCode;
	private String shipCountry;
	private String shipMobileNum;
	private String shipPhoneNum;
	private String shipFaxNum;
	private String shipEmail;
	private String shipAmt;
	private String shopperIpAddr;
	private String productFormat;
	private List<ProductDetails> productDetails;
	private Integer riskScore;

	public String getBillFirstName() {
		return billFirstName;
	}

	public void setBillFirstName(String billFirstName) {
		this.billFirstName = billFirstName;
	}

	public String getBillLastName() {
		return billLastName;
	}

	public void setBillLastName(String billLastName) {
		this.billLastName = billLastName;
	}

	public String getBillInitial() {
		return billInitial;
	}

	public void setBillInitial(String billInitial) {
		this.billInitial = billInitial;
	}

	public String getBillAddr1() {
		return billAddr1;
	}

	public void setBillAddr1(String billAddr1) {
		this.billAddr1 = billAddr1;
	}

	public String getBillAddr2() {
		return billAddr2;
	}

	public void setBillAddr2(String billAddr2) {
		this.billAddr2 = billAddr2;
	}

	public String getBillCoyName() {
		return billCoyName;
	}

	public void setBillCoyName(String billCoyName) {
		this.billCoyName = billCoyName;
	}

	public String getBillCity() {
		return billCity;
	}

	public void setBillCity(String billCity) {
		this.billCity = billCity;
	}

	public String getBillState() {
		return billState;
	}

	public void setBillState(String billState) {
		this.billState = billState;
	}

	public String getBillZipCode() {
		return billZipCode;
	}

	public void setBillZipCode(String billZipCode) {
		this.billZipCode = billZipCode;
	}

	public String getBillCountry() {
		return billCountry;
	}

	public void setBillCountry(String billCountry) {
		this.billCountry = billCountry;
	}

	public String getBillMobileNum() {
		return billMobileNum;
	}

	public void setBillMobileNum(String billMobileNum) {
		this.billMobileNum = billMobileNum;
	}

	public String getBillPhoneNum() {
		return billPhoneNum;
	}

	public void setBillPhoneNum(String billPhoneNum) {
		this.billPhoneNum = billPhoneNum;
	}

	public String getBillFaxNum() {
		return billFaxNum;
	}

	public void setBillFaxNum(String billFaxNum) {
		this.billFaxNum = billFaxNum;
	}

	public String getBillEmail() {
		return billEmail;
	}

	public void setBillEmail(String billEmail) {
		this.billEmail = billEmail;
	}

	public String getShipFirstName() {
		return shipFirstName;
	}

	public void setShipFirstName(String shipFirstName) {
		this.shipFirstName = shipFirstName;
	}

	public String getShipLastName() {
		return shipLastName;
	}

	public void setShipLastName(String shipLastName) {
		this.shipLastName = shipLastName;
	}

	public String getShipInitial() {
		return shipInitial;
	}

	public void setShipInitial(String shipInitial) {
		this.shipInitial = shipInitial;
	}

	public String getShipAddr1() {
		return shipAddr1;
	}

	public void setShipAddr1(String shipAddr1) {
		this.shipAddr1 = shipAddr1;
	}

	public String getShipAddr2() {
		return shipAddr2;
	}

	public void setShipAddr2(String shipAddr2) {
		this.shipAddr2 = shipAddr2;
	}

	public String getShipCoyName() {
		return shipCoyName;
	}

	public void setShipCoyName(String shipCoyName) {
		this.shipCoyName = shipCoyName;
	}

	public String getShipCity() {
		return shipCity;
	}

	public void setShipCity(String shipCity) {
		this.shipCity = shipCity;
	}

	public String getShipState() {
		return shipState;
	}

	public void setShipState(String shipState) {
		this.shipState = shipState;
	}

	public String getShipZipCode() {
		return shipZipCode;
	}

	public void setShipZipCode(String shipZipCode) {
		this.shipZipCode = shipZipCode;
	}

	public String getShipCountry() {
		return shipCountry;
	}

	public void setShipCountry(String shipCountry) {
		this.shipCountry = shipCountry;
	}

	public String getShipMobileNum() {
		return shipMobileNum;
	}

	public void setShipMobileNum(String shipMobileNum) {
		this.shipMobileNum = shipMobileNum;
	}

	public String getShipPhoneNum() {
		return shipPhoneNum;
	}

	public void setShipPhoneNum(String shipPhoneNum) {
		this.shipPhoneNum = shipPhoneNum;
	}

	public String getShipFaxNum() {
		return shipFaxNum;
	}

	public void setShipFaxNum(String shipFaxNum) {
		this.shipFaxNum = shipFaxNum;
	}

	public String getShipEmail() {
		return shipEmail;
	}

	public void setShipEmail(String shipEmail) {
		this.shipEmail = shipEmail;
	}

	public String getShipAmt() {
		return shipAmt;
	}

	public void setShipAmt(String shipAmt) {
		this.shipAmt = shipAmt;
	}

	public String getShopperIpAddr() {
		return shopperIpAddr;
	}

	public void setShopperIpAddr(String shopperIpAddr) {
		this.shopperIpAddr = shopperIpAddr;
	}

	public String getProductFormat() {
		return productFormat;
	}

	public void setProductFormat(String productFormat) {
		this.productFormat = productFormat;
	}

	public List<ProductDetails> getProductDetails() {
		return productDetails;
	}

	public void setProductDetails(List<ProductDetails> productDetails) {
		this.productDetails = productDetails;
	}

	public Integer getRiskScore() {
		return riskScore;
	}

	public void setRiskScore(Integer riskScore) {
		this.riskScore = riskScore;
	}

}
