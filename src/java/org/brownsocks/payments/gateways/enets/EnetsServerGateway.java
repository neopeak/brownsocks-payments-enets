/*
 * Copyright Neopeak Internet Solutions inc.
 * 
 * This file is part of the Brown Socks project. See accompanying
 * LICENSE file for legal restrictions.
 */

package org.brownsocks.payments.gateways.enets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.brownsocks.payments.PaymentOperation;
import org.brownsocks.payments.PaymentRequest;
import org.brownsocks.payments.PaymentResult;
import org.brownsocks.payments.PaymentResultType;
import org.brownsocks.payments.Secure3DResponse;
import org.brownsocks.payments.gateways.GatewayInitializationException;
import org.brownsocks.payments.gateways.PaymentsListenerSupport;
import org.brownsocks.payments.gateways.ServerSubmissionGateway;
import org.brownsocks.payments.gateways.UnsupportedGatewayRequestException;
import org.brownsocks.payments.gateways.enets.data.CreditTxnReq;
import org.brownsocks.payments.gateways.enets.data.CreditTxnRes;
import org.brownsocks.payments.gateways.enets.data.CustomAttribute;
import org.brownsocks.payments.gateways.enets.data.EzProtect;
import org.brownsocks.payments.gateways.enets.data.S3DTxnRes;
import org.brownsocks.payments.gateways.enets.data.TxnReq;
import org.brownsocks.payments.gateways.enets.data.TxnRes;
import org.brownsocks.payments.gateways.enets.pgp.BCPGPProvider;
import org.brownsocks.payments.gateways.enets.pgp.PGPProvider;
import org.brownsocks.payments.gateways.enets.xml.CastorXMLSerializer;
import org.brownsocks.payments.gateways.enets.xml.XMLSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * <p>eNets Server Submission Gateway.</p>
 * 
 * <p>This is a clean, from-scratch, implementation of the eNets
 * server submission gateway.</p>
 * *
 * @author cveilleux
 *
 */
public class EnetsServerGateway extends PaymentsListenerSupport implements ServerSubmissionGateway<EnetsMerchantAccount> {

	private static final Logger logger = LoggerFactory.getLogger(EnetsServerGateway.class); 
	
	private static final NumberFormat _monthFormat = new DecimalFormat("00");
	
	private EnetsMerchantAccount _enetsMerchantAccount;
	private PGPProvider _pgpProvider;
	private XMLSerializer _xmlSerializer;
	
	private static Properties _defaults;
	
	@Override
	public void setGatewayAccount(EnetsMerchantAccount gatewayAccount) {
		_enetsMerchantAccount = gatewayAccount;
	}

	@Override
	public void initialize() throws GatewayInitializationException {
		/* MAKE SURE MERCHANT ACCOUNT CFG IS SET */
		if (_enetsMerchantAccount == null)
			throw new GatewayInitializationException("gatewayAccount was not set.");

		/* INIT PGP SUB-SYSTEM */
		try {
			_pgpProvider = new BCPGPProvider();
			
			_pgpProvider.initialize(_enetsMerchantAccount.getMerchantPrivateKey(), 
					_enetsMerchantAccount.getMerchantPrivateKeyPassphrase(), 
					_enetsMerchantAccount.getNetsPublicKey());
		} catch (Exception e) {
			throw new GatewayInitializationException("Failed to initialize PGPProvider", e);
		}
		
		/* INIT XML SUB-SYSTEM */
		try {
			_xmlSerializer = new CastorXMLSerializer();
			_xmlSerializer.initialize();
			
		} catch (Exception e) {
			throw new GatewayInitializationException("Failed to initialize XMLSerializer", e);
			
		}
	}
	
	@Override
	public PaymentResult lookup(String merchantTxnID) throws UnsupportedGatewayRequestException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("mid", _enetsMerchantAccount.getMerchantMID());
		params.put("merch_txn_ref", merchantTxnID);
		
		try {
	        String response = EnetsHttp.sendAndReceive(_enetsMerchantAccount.getQueryURL(), params);
	        
	        System.out.println(response);
	        
	        /* TODO: Parse response code */
	        

		} catch (IOException e) {
			e.printStackTrace();
		}
		
        throw new UnsupportedGatewayRequestException();		
	}

	@Override
	public PaymentResult authorize(PaymentRequest request)
			throws UnsupportedGatewayRequestException {
		return processTransaction(request, PaymentOperation.AUTH);
	}

	@Override
	public PaymentResult capture(PaymentRequest request)
			throws UnsupportedGatewayRequestException {
		return processTransaction(request, PaymentOperation.CAPTURE);
	}

	@Override
	public PaymentResult credit(PaymentRequest request)
			throws UnsupportedGatewayRequestException {
		return processTransaction(request, PaymentOperation.CREDIT);
	}

	@Override
	public PaymentResult sale(PaymentRequest request)
			throws UnsupportedGatewayRequestException {
		return processTransaction(request, PaymentOperation.SALE);
	}
	
	/**
	 * Unpacks the "message" sent to the postURL (servlet).
	 * This contains a Crypted/XML "TxnRes" bean.
	 * 
	 * We give 'friendly' access to this method so the servlet
	 * sitting in the same package may access it but not end-user
	 * of the library.
	 * 
	 * @param message
	 * @return
	 */
	PaymentResult handlePostCallback(String message) {
		
		PaymentResult result = new PaymentResult();
		String txnResXml = decryptAndVerifyResponse(message, result);
		if (txnResXml == null)
			return result;
		
		TxnRes responseBean = unserializeTxnRes(txnResXml, result);
		if (result.isCompleted()) // error occurred..
			return result;
		
		if (responseBean == null) {
			result.setCompleted(true);
			result.setResultType(PaymentResultType.ERROR_UNKNOWN);
			result.setErrorMessage("Empty response received from eNets. Transaction status should be verified manually with eNets.");
			result.markCompletion();
			
			return result;
		}
		
		convertToBrownSocks(responseBean, result);
		
		sendPaymentReceivedEvent(result);
		
		return result;
	}
	
	private PaymentResult processTransaction(PaymentRequest request, PaymentOperation operation) throws UnsupportedGatewayRequestException {
		
		PaymentResult result = new PaymentResult(operation);
		result.setMerchantTxnID(request.getMerchantTxnID());
		
		// enets does not give the currency in the response message, so we copy it from the request
		result.setCurrency(request.getCurrency());
		
		TxnReq txnReq = convertToUMAPI(request, operation);
		
		/* CONVERT TO XML STRING */
		String xmlTxnReq = serializeTxnReq(txnReq, result);
		if (xmlTxnReq == null)
			return result;

		/* SIGN AND ENCRYPT USING PGP */
		String cryptedRequest = signAndEncrypt(xmlTxnReq, result);
		if (cryptedRequest == null)
			return result;
		
		/* TRANSMIT OVER HTTP POST REQUEST */
		String responseCrypted = httpPost(cryptedRequest, result);
		if (responseCrypted == null)
			return result;
		
		/* DECRYPT AND VERIFY SIGNATURE */
		String responseXML = decryptAndVerifyResponse(responseCrypted, result);
		if (responseXML == null)
			return result;
		
		/* PARSE XML */
		TxnRes responseBean = unserializeTxnRes(responseXML, result);
		if (result.isCompleted()) // error occurred..
			return result;
		
		/* ANALYZE RESULT */
		if (responseBean == null) {
			result.setCompleted(true);
			result.setResultType(PaymentResultType.ERROR_UNKNOWN);
			result.setErrorMessage("Empty response received from eNets. Transaction status should be verified manually with eNets.");
			result.markCompletion();
			
			return result;
		}
		
		convertToBrownSocks(responseBean, result);
		
		if (PaymentResultType.REQUIRES_3D_SECURE_VERIF.equals(result.getResultType()))
			sendPaymentProcessingEvent(result);
		else
			sendPaymentReceivedEvent(result);
		
		return result;
	}
	
	private String serializeTxnReq(TxnReq txnReq, PaymentResult result) {
		String xmlTxnReq = null;
		try {
			xmlTxnReq = _xmlSerializer.serializeTxnReq(txnReq);
			
		} catch (IOException e) {
			logger.warn("Could not convert eNets request to XML string. Transaction aborted.", e);
			
			result.setResultType(PaymentResultType.ERROR_INTERNAL);
			result.setErrorMessage("eNets library error: Could not convert eNets request to XML string. Transaction aborted. Check configuration.");
			result.markCompletion();
			return null;
		}
		
		return xmlTxnReq;
	}
	
	private String signAndEncrypt(String xmlTxnReq, PaymentResult result) {
		String cryptedRequest = null;
		try {
			cryptedRequest = _pgpProvider.signAndEncrypt(xmlTxnReq);
			
		} catch (IOException e) {
			logger.warn("Could not sign and encrypt eNets request using PGP subsystem. Transaction aborted.", e);
			
			result.setResultType(PaymentResultType.ERROR_INTERNAL);
			result.setErrorMessage("eNets library error: Could not sign and encrypt eNets request using PGP subsystem. Transaction aborted. Check configuration.");
			result.markCompletion();
			return null;
		}
		
		return cryptedRequest;
	}
	
	private String httpPost(String cryptedRequest, PaymentResult result) {
		String responseCrypted = null;
		try {
			responseCrypted = EnetsHttp.sendAndReceive(_enetsMerchantAccount.getPayURL(), "message", cryptedRequest);
			
		} catch (IOException e) {
			logger.warn("A network error occured transmitting the transaction request to eNets gateway. Transaction should be verified manually with eNets.", e);
			
			result.setResultType(PaymentResultType.ERROR_NETWORK);
			result.setErrorMessage("A network error occured transmitting the transaction request to eNets gateway. Transaction status should be verified manually with eNets. Source message: " + e.getMessage());
			result.markCompletion();
			return null;
		}
		
		return responseCrypted;
	}
	
	private String decryptAndVerifyResponse(String responseCrypted, PaymentResult result) {
		String responseXML = null;
		try {
			responseXML = _pgpProvider.decryptAndVerify(responseCrypted);
			
		} catch (SignatureVerificationException sve) {
			logger.warn("Failed to verify eNets signature. Authenticity of response cannot be validated. Transaction status should be verified manually with eNets.", sve);
			
			result.setResultType(PaymentResultType.ERROR_SUSPECT);
			result.setErrorMessage("Failed to verify eNets signature. Authenticity of response cannot be validated. Transaction status should be verified manually with eNets. Source message: " + sve.getMessage());
			result.markCompletion();
			return null;
			
		} catch (IOException e) {
			logger.warn("Error occured during decryption of response from eNets. Transaction status should be verified manually with eNets.", e);
			
			result.setResultType(PaymentResultType.ERROR_INTERNAL);
			result.setErrorMessage("Failed to decrypt response from eNets (PGP subsystem error). Transaction status should be verified manually with eNets. Source message: " + e.getMessage());
			result.markCompletion();
			return null;
		}
		
		return responseXML;
	}
	
	private TxnRes unserializeTxnRes(String responseXML, PaymentResult result) {
		TxnRes responseBean = null;
		try {
			responseBean = _xmlSerializer.unserializeTxnRes(responseXML);
			
		} catch (IOException e) {
			logger.warn("Failed to parse XML response from eNets. Transaction status should be verified manually with eNets.", e);
			
			result.setResultType(PaymentResultType.ERROR_INTERNAL);
			result.setErrorMessage("Failed to parse XML response from eNets. Transaction status should be verified manually with eNets. Source message: " + e.getMessage());
			result.markCompletion();
			return null;
		}
		
		return responseBean;
	}
	
	/**
	 * Convert from the brown socks API payment request bean to the UMAPI payment request bean.
	 * @param request
	 * @param operation
	 * @return
	 * @throws UnsupportedGatewayRequestException
	 */
	private TxnReq convertToUMAPI(PaymentRequest request, PaymentOperation operation) throws UnsupportedGatewayRequestException {
		TxnReq txnReq = new TxnReq();
		CreditTxnReq credReq = new CreditTxnReq();
		
		txnReq.setNetsMid(_enetsMerchantAccount.getMerchantMID());
		txnReq.setTid(_enetsMerchantAccount.getTerminalID());
		txnReq.setPaymentMode("CC");
		txnReq.setTxnAmount(new Integer(request.getAmount()).toString());
		txnReq.setCurrencyCode(request.getCurrency());
		txnReq.setMerchantTxnRef(request.getMerchantTxnID());
		txnReq.setSubmissionMode("S");
		txnReq.setMerchantCertId(_enetsMerchantAccount.getMerchantCertId());
		
		credReq.setPan(request.getCardNumber());
		credReq.setExpiryDate(convertExpYear(request.getCardExpiryYear()) + _monthFormat.format(request.getCardExpiryMonth()));
		credReq.setCvv(request.getCcv());
		
		credReq.setPaymentType(operationToPaymentType(operation));
		credReq.setCardHolderName(request.getCustomerInfo().getFullName());
		
		EzProtect ezProtect = new EzProtect();
		ezProtect.setBillAddr1(request.getCustomerInfo().getAddressLine1());
		ezProtect.setBillAddr2(request.getCustomerInfo().getAddressLine2());
		ezProtect.setBillCity(request.getCustomerInfo().getCity());
		ezProtect.setBillCountry(request.getCustomerInfo().getCountry());
		ezProtect.setBillEmail(request.getCustomerInfo().getEmail());
		ezProtect.setBillFirstName(request.getCustomerInfo().getFirstName());
		ezProtect.setBillLastName(request.getCustomerInfo().getLastName());
		ezProtect.setBillInitial(request.getCustomerInfo().getMiddleName());
		ezProtect.setBillPhoneNum(request.getCustomerInfo().getPhone());
		ezProtect.setBillState(request.getCustomerInfo().getState());
		ezProtect.setBillZipCode(request.getCustomerInfo().getZipcode());
		credReq.setEzProtect(ezProtect);
		
		credReq.setPostUrl(_enetsMerchantAccount.getPostURL());
		credReq.setCancelUrl(_enetsMerchantAccount.getCancelURL());
		
		txnReq.setSuccessUrl(_enetsMerchantAccount.getSuccessURL());
		txnReq.setFailureUrl(_enetsMerchantAccount.getFailureURL());
		txnReq.setNotifyUrl(_enetsMerchantAccount.getNotifyURL());

		txnReq.setCreditTxnReq(credReq);
		
		return txnReq;
	}
	
	/* eNets use 2 digits card expiration years */
	private String convertExpYear(int expYear) {
		int secondDigit = expYear % 10;
		int firstDigit = (expYear/10) % 10; 
		return new String("" + firstDigit + "" + secondDigit);
	}
	
	private String operationToPaymentType(PaymentOperation operation) throws UnsupportedGatewayRequestException {
		switch (operation) {
		case AUTH:
			return "AUTH";
		case CAPTURE:
			return "CAPT";
		case SALE:
			return "SALE";
		case CREDIT:
			return "CRED";
		}
		throw new UnsupportedGatewayRequestException("Unknow PaymentOperation: " + operation);
	}
	
	/* ******************************************************************/
	/* RESPONSE PARSING METHODS BELOW                                   */
	/* ******************************************************************/
	
	private void convertToBrownSocks(TxnRes txnRes, PaymentResult result) {
		
		// basic info
		result.setConfirmationCode(txnRes.getNetsTxnRef());
		result.setMerchantTxnID(txnRes.getMerchantTxnRef());
		if (txnRes.getCreditTxnRes().getNetsAmountDeducted() == null)
			result.setAmount(0);
		else
			result.setAmount(new Integer(txnRes.getCreditTxnRes().getNetsAmountDeducted()).intValue());
		
		// convert error to brownsocks standard error types
		result.setResultType(getPaymentResultType(txnRes));
		
		// set raw error code / msg
		result.setErrorCode(txnRes.getNetsTxnRespCode());
		result.setErrorMessage(txnRes.getNetsTxnMsg());
		
		// handle 3D secure stuff
		if (PaymentResultType.REQUIRES_3D_SECURE_VERIF.equals(result.getResultType())) {
			result.setSecure3dResponse(convert3DResonse(txnRes.getCreditTxnRes().getS3dTxnRes()));
		}
		
		// add in the meta data
		addMetas(txnRes, result);
		
		result.markCompletion();
		
	}
	
	private PaymentResultType getPaymentResultType(TxnRes txnRes) {
		
		String netsTxnStatus = txnRes.getNetsTxnStatus();
		if (StringUtils.isEmpty(netsTxnStatus))
			return PaymentResultType.ERROR_UNKNOWN;
		
		int status = -1;
		try {
			status = Integer.parseInt(netsTxnStatus);
			
		} catch (NumberFormatException e) {
			return PaymentResultType.ERROR_UNKNOWN;
		}
		
		switch(status) {
			case -1:
				return PaymentResultType.ERROR_UNKNOWN;
			
			case 0:
				return PaymentResultType.SUCCESS;
				
			case 1:
				return parseErrorCode(txnRes);
				
			case 2:
			case 3:
				return PaymentResultType.ERROR_DUPLICATE_TRANSACTION;
				
			case 5:
				return PaymentResultType.REQUIRES_3D_SECURE_VERIF;
				
			case 9:
				return PaymentResultType.ERROR_TRANSACTION_CANCELLED;
				
			default:
				return PaymentResultType.ERROR_UNKNOWN;
		}
		
	}
	
	private PaymentResultType parseErrorCode(TxnRes txnRes) {
		String responseCode = txnRes.getNetsTxnRespCode();
		
		if (StringUtils.isEmpty(responseCode))
			return PaymentResultType.ERROR_UNKNOWN;
		
		if (responseCode.length() == 2)
			return ISO8583ErrorCodes.convertErrorCode(responseCode);
		
		if (responseCode.charAt(0) == '-') {
			int code = Integer.parseInt(responseCode);
			code = code * -1;
			return EnetsErrorCodes.convertErrorCode(code);
		}
		
		return PaymentResultType.ERROR_UNKNOWN;
		
	}
	
	private Secure3DResponse convert3DResonse(S3DTxnRes s3dTxnRes) {
		Secure3DResponse response = new Secure3DResponse(s3dTxnRes.getAcsUrl());
		response.addAcsField("PaReq", s3dTxnRes.getPAreq());
		response.addAcsField("TermUrl", s3dTxnRes.getTermUrl());
		response.addAcsField("MD", s3dTxnRes.getMd());
		response.addAcsField("ErrorCode", s3dTxnRes.getErrorCode());
		return response;
	}
	
	private void addMetas(TxnRes txnRes, PaymentResult result) {
		result.addMeta("merchantCertiId", txnRes.getMerchantCertId());
		result.addMeta("merchantTxnRef", txnRes.getMerchantTxnRef());
		result.addMeta("netsMid", txnRes.getNetsMid());
		result.addMeta("netsTxnDtm", txnRes.getNetsTxnDtm());
		result.addMeta("netsTxnMsg", txnRes.getNetsTxnMsg());
		result.addMeta("netsTxnRef", txnRes.getNetsTxnRef());
		result.addMeta("netsTxnRespCode", txnRes.getNetsTxnRespCode());
		result.addMeta("netsTxnStatus", txnRes.getNetsTxnStatus());
		result.addMeta("paymentMode", txnRes.getPaymentMode());
		result.addMeta("tid", txnRes.getTid());
		
		if (txnRes.getCreditTxnRes() != null) {
			CreditTxnRes creditTxnRes = txnRes.getCreditTxnRes();
			result.addMeta("creditTxnRes.bankAuthId", creditTxnRes.getBankAuthId());
			result.addMeta("creditTxnRes.netsAmountDeducted", creditTxnRes.getNetsAmountDeducted());
			result.addMeta("creditTxnRes.netsRiskScore", creditTxnRes.getNetsRiskScore());
			
			if (creditTxnRes.getS3dTxnRes() != null) {
				S3DTxnRes s3dTxnRes = creditTxnRes.getS3dTxnRes();
				
				result.addMeta("creditTxnRes.S3DTxnRes.acsUrl", s3dTxnRes.getAcsUrl());
				result.addMeta("creditTxnRes.S3DTxnRes.errorCode", s3dTxnRes.getErrorCode());
				result.addMeta("creditTxnRes.S3DTxnRes.md", s3dTxnRes.getMd());
				result.addMeta("creditTxnRes.S3DTxnRes.PAreq", s3dTxnRes.getPAreq());
				result.addMeta("creditTxnRes.S3DTxnRes.termUrl", s3dTxnRes.getTermUrl());
			}
		}
		
		if (txnRes.getCustomAttribute() != null) {
			List<CustomAttribute> attrs = txnRes.getCustomAttribute();
			for (CustomAttribute customAttribute : attrs) {
				result.addMeta("customAttributes." + customAttribute.getKey(), customAttribute.getValue());
			}
		}
	}
	
	
	/* ******************************************************************/
	/* UTILITY METHODS FOR LOADING DEFAULT MERCHANT CONFIGURATION BELOW */
	/* ******************************************************************/
	
	public static EnetsMerchantAccount buildDefaultLiveAccount() throws IOException {
		EnetsMerchantAccount liveAccount = buildDefaultAccount("live");
		liveAccount.setTestAccount(false);
		return liveAccount;
	}

	public static EnetsMerchantAccount buildDefaultTestAccount() throws IOException {
		EnetsMerchantAccount testAccount = buildDefaultAccount("test");
		testAccount.setTestAccount(true);
		return testAccount;
	}
	
	private static EnetsMerchantAccount buildDefaultAccount(String type) throws IOException {
		loadDefaults();
		
		EnetsMerchantAccount account = new EnetsMerchantAccount();
		
		// private key
		String privateKeyName = getDefaultValue(type, "merchantPrivateKey");
		account.setMerchantPrivateKey(loadBundledKey(privateKeyName));
		account.setMerchantPrivateKeyPassphrase(getDefaultValue(type, "merchantPrivateKeyPassphrase"));
		
		// pub key
		String netPublicKeyName = getDefaultValue(type, "netsPublicKey");
		account.setNetsPublicKey(loadBundledKey(netPublicKeyName));
		
		// URL's
		account.setPayURL(getDefaultValue(type, "payURL"));
		account.setQueryURL(getDefaultValue(type, "queryURL"));
		
		// Callbacks URL's
		account.setCancelURL(getDefaultValue(type, "cancelURL"));
		account.setSuccessURL(getDefaultValue(type, "successURL"));
		account.setPostURL(getDefaultValue(type, "postURL"));
		account.setNotifyURL(getDefaultValue(type, "notifyURL"));
		account.setFailureURL(getDefaultValue(type, "failureURL"));

		// Merchant info
		account.setMerchantMID(getDefaultValue(type, "merchantMID"));
		account.setMerchantCertId(getDefaultValue(type, "merchantCertId"));
		
		return account;
	}
	
	private static String getDefaultValue(String type, String key) {
		return _defaults.getProperty("enets." + type + "." + key);
	}
	
	private static void loadDefaults() throws IOException {
		if (_defaults != null)
			return;
		
		InputStream fileStream = EnetsServerGateway.class.getClassLoader().getResourceAsStream("org/brownsocks/payments/gateways/enets/defaults.properties");
		try {
			Properties properties = new Properties();
			properties.load(fileStream);
			_defaults = properties;
			
		} finally {
			IOUtils.closeQuietly(fileStream);
		}
	}
	
	private static String loadBundledKey(String filename) throws IOException {
		if (StringUtils.isEmpty(filename))
			return null;
		
		InputStream fileStream = EnetsServerGateway.class.getClassLoader().getResourceAsStream("org/brownsocks/payments/gateways/enets/" + filename);
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(fileStream));
			StringBuilder builder = new StringBuilder();
			
			String line = null;
			do {
				line = reader.readLine();
				if (line != null) {
					builder.append(line);
					builder.append("\n");
				}
			} while (line != null);
			
			return builder.toString();
			
		} finally {
			IOUtils.closeQuietly(fileStream);
		}
	}

}
