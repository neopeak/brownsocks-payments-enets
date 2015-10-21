/*
 * Copyright Neopeak Internet Solutions inc.
 * 
 * This file is part of the Brown Socks project. See accompanying
 * LICENSE file for legal restrictions.
 */

package org.brownsocks.payments.gateways.enets;

import java.io.Serializable;

import org.brownsocks.payments.gateways.GatewayAccount;

public class EnetsMerchantAccount implements GatewayAccount, Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * The ASCII-Armored private key string.
	 */
	private String _merchantPrivateKey;
	
	/**
	 * Passphrase for the private key.
	 */
	private String _merchantPrivateKeyPassphrase;
	
	/**
	 * The ASCII-Armored public key supplied by NETS.
	 */
	private String _netsPublicKey;

	/**
	 * PaymentListener.do URL.
	 */
	private String _payURL;
	
	/**
	 * QueryTxnStatus.do URL.
	 */
	private String _queryURL;
	
	/**
	 * Merchand MID.
	 */
	private String _merchantMID;
	
	/**
	 * Terminal ID.
	 */
	private String _terminalID;
	
	/**
	 * Certificate ID, as configured in the enets admin.
	 */
	private String _merchantCertId;

	private String _postURL;
	private String _cancelURL;
	private String _successURL;
	private String _failureURL;
	private String _notifyURL;
	
	private boolean _testAccount;
	
	public boolean isTestAccount() {
		return _testAccount;
	}

	public void setTestAccount(boolean testAccount) {
		_testAccount = testAccount;
	}

	public String getMerchantPrivateKey() {
		return _merchantPrivateKey;
	}

	public void setMerchantPrivateKey(String merchantPrivateKey) {
		_merchantPrivateKey = merchantPrivateKey;
	}

	public String getMerchantPrivateKeyPassphrase() {
		return _merchantPrivateKeyPassphrase;
	}

	public void setMerchantPrivateKeyPassphrase(String merchantPrivateKeyPassphrase) {
		_merchantPrivateKeyPassphrase = merchantPrivateKeyPassphrase;
	}

	public String getNetsPublicKey() {
		return _netsPublicKey;
	}

	public void setNetsPublicKey(String netsPublicKey) {
		_netsPublicKey = netsPublicKey;
	}

	public String getPayURL() {
		return _payURL;
	}

	public void setPayURL(String payURL) {
		_payURL = payURL;
	}

	public String getQueryURL() {
		return _queryURL;
	}

	public void setQueryURL(String queryURL) {
		_queryURL = queryURL;
	}

	public String getMerchantMID() {
		return _merchantMID;
	}

	public void setMerchantMID(String merchantMID) {
		_merchantMID = merchantMID;
	}

	public String getTerminalID() {
		return _terminalID;
	}

	public void setTerminalID(String terminalID) {
		_terminalID = terminalID;
	}

	public String getMerchantCertId() {
		return _merchantCertId;
	}

	public void setMerchantCertId(String merchantCertId) {
		_merchantCertId = merchantCertId;
	}

	public String getNotifyURL() {
		return _notifyURL;
	}

	public void setNotifyURL(String notifyURL) {
		_notifyURL = notifyURL;
	}
 
	public String getPostURL() {
		return _postURL;
	}

	public void setPostURL(String postURL) {
		_postURL = postURL;
	}

	public String getCancelURL() {
		return _cancelURL;
	}

	public void setCancelURL(String cancelURL) {
		_cancelURL = cancelURL;
	}

	public String getSuccessURL() {
		return _successURL;
	}

	public void setSuccessURL(String successURL) {
		_successURL = successURL;
	}

	public String getFailureURL() {
		return _failureURL;
	}

	public void setFailureURL(String failureURL) {
		_failureURL = failureURL;
	}
	
	
}
