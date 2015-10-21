/*
 * Copyright Neopeak Internet Solutions inc.
 * 
 * This file is part of the Brown Socks project. See accompanying
 * LICENSE file for legal restrictions.
 */
package org.brownsocks.payments.gateways.enets;

import org.brownsocks.payments.PaymentResultType;

/**
 * Based on the eNETS UMAPI documentation, this convert the error codes to 
 * the proper result type.
 * 
 * This handles the negative error codes. The error code must be converted to
 * a positive number.
 * 
 * @author cveilleux
 */
public class EnetsErrorCodes {
	public static PaymentResultType convertErrorCode(int errorCode) {
		switch (errorCode) {
			case 1000: // Missing NetsMid
			case 1001: // Invalid NetsMid
			case 1002: // Invalid Merchand Id
			case 1003: // Merchant not registered
			case 1004: // Merchant is not active
			case 1005: // Invalid terminal Id
				return PaymentResultType.ERROR_INVALID_MERCHANT;
				
			case 1006: // Invalid payment mode
			case 1007: // Missing txn amount
			case 1008: // Invalid txn amount
			case 1009: // Invalid currency code
			case 1010: // Missing merchant ref
			case 1011: // Invalid merchant ref
			case 1012: // Invalid Merchant txn Date time
			case 1013: // Invalid submission mode
			case 1014: // Invalid Merchant Cert ID
				return PaymentResultType.ERROR_INVALID_REQUEST;
				
			case 1015: // Invalid credit card number
			case 1016: // Missing credit card number
			case 1017: // Invalid expiry date
			case 1018: // Missing expiry date
			case 1019: // Invalid CVV
				return PaymentResultType.ERROR_CARD;

			case 1020: 	// Invalid card holder name
				return PaymentResultType.ERROR_CUSTOMER;
				
			case 1021: // Missing Payment Type
			case 1022: // Invalid Payment type
			case 1023: // Invalid success url
			case 1024: // Invalid Success url params
			case 1025: // Invalid failure url
			case 1026: // Missing failure url param
			case 1027: // Invalid notify url
			case 1028: // Invalid notify url params
			case 1029: // Invalid cancel url
			case 1030: // Invalid cancel url params
			case 1031: // Invalid post URL
			case 1032: // Invalid post url params
			case 1033: // Invalid Stan (credit note number)
				return PaymentResultType.ERROR_INVALID_REQUEST;
				
			case 1034: // Under construction
				return PaymentResultType.ERROR_REMOTE;
				
			case 1100: // Malformed XML input
			case 1101: // Invalid XML schema
			case 1102: // Schema validation error
			case 1103: // PGP error
				return PaymentResultType.ERROR_INVALID_REQUEST;
				
			case 1104: // Unable to sign with PGP
			case 1105: // Unable to encrypt with PGP
				return PaymentResultType.ERROR_REMOTE;
				
			case 1106: // Unable to decrypt with PGP
			case 1107: // Unable to verify signature with PGP
			case 1108: // Invalid signature
				return PaymentResultType.ERROR_INVALID_REQUEST;

			case 1109: // XML utility error
			case 1110: // Unable to generate transaction reference
				return PaymentResultType.ERROR_REMOTE;
				
			case 1111: // Merchant not subscribing to this service
				return PaymentResultType.ERROR_INVALID_MERCHANT;
				
			case 1112: // Payment mode not supported
				return PaymentResultType.ERROR_INVALID_REQUEST;

			case 1113: // Unable to decode
			case 1114: // Unable to encode
			case 1115: // Unable to format merchant message
				return PaymentResultType.ERROR_REMOTE;
				
			case 1116: // Invalid cert id
				return PaymentResultType.ERROR_INVALID_REQUEST;
				
				
			case 1200: // Invalid login
			case 1201: // Error retrieving consumer info
				return PaymentResultType.ERROR_REMOTE;
			
			case 1202: // Credit card number not allowed
			case 1203: // Credit card expired
				return PaymentResultType.ERROR_CARD;
				
			case 1204: // Transaction amount not within allowed range
			case 1205: // Reversal amount not equal to original amount
			case 1206: // Capture amount more than original auth amount
			case 1207: // Credit amount more than origninal sale/capture amount
			case 1208: // Transaction not allowed
				return PaymentResultType.ERROR_INVALID_REQUEST;
			
			case 1209: // Batch close in progress
				return PaymentResultType.ERROR_NOT_PROCESSED;
				
			case 1210: // Bank not active
			case 1211: // BITMAP ERROR
				return PaymentResultType.ERROR_REMOTE;
			
			case 1212: // Reversal not allowed in different batch
			case 1213: // No mapping found for Txn Type
			case 1214: // Unknown merchant type
				return PaymentResultType.ERROR_INVALID_REQUEST;

			case 1215: // Error getting HostMID
			case 1216: // No acquiring bank found
			case 1217: // No HostTID configured for merchant
			case 1218: // Host Mid not found in database
			case 1219: // Host Tid not found in database
			case 1220: // Error occurred during batch close
			case 1221: // Error instantiating processor
			case 1222: // Error instantiating acquirer TODO: Clarify, documentation seems wrong, there is two 1234 error code documented.. 
				return PaymentResultType.ERROR_REMOTE;
			
			case 1223: // Duplicate transaction
			case 1224: // Transaction being processed
				return PaymentResultType.ERROR_DUPLICATE_TRANSACTION;
				

			case 1225: // Transaction has already been reversed
			case 1226: // Transaction has already been credited
			case 1227: // Transaction has already been captured
			case 1228: // Transaction has already been charged back
			case 1229: // Transaction locked
			case 1230: // Transaction has already been cancelled
				return PaymentResultType.ERROR_INVALID_REQUEST;
				
			case 1231: // System error
			case 1232: // System error
			case 1233: // System error
				return PaymentResultType.ERROR_REMOTE;
				
			case 1234: // Transaction does not exist
				return PaymentResultType.ERROR_INVALID_REQUEST;
				
			case 1290: // Comm channel type not defined
			case 1291: // Comm channel info not defined
			case 1300: // Comms timeout
			case 1301: // Comms Error
			case 1302: // Merchant bank is in maintenance
				return PaymentResultType.ERROR_REMOTE;
				
			case 1401: // Stan does not tally with request
			case 1402: // Terminal ID does not tally with request
			case 1403: // MTID does not tally with request
			case 1404: // Missing response code
			case 1405: // Unable to parse response
				return PaymentResultType.ERROR_REMOTE; // Seems like errors with communicating with bank systems
				
			case 4035: // Transaction failed please try again
				return PaymentResultType.ERROR_NOT_PROCESSED;
			
				
			case 1501: // Cannot Notify Merchant. Contact eNETS
			case 1502: // Cannot Notify Merchant. Contact eNETS
			case 1503: // Cannot Notify Merchant. Contact eNETS
			case 1504: // Cannot Process Transaction. Contact Merchant.
			case 1505: // Reversal failed
				return PaymentResultType.ERROR_REMOTE;
			
			case 1600: // EzProtect parameters are required
				return PaymentResultType.ERROR_CUSTOMER;

				
			/* 3D secure related errors 
			 * Some of these errors could be considered REMOTE, but we all put them under 3D Secure verif failed,
			 * to make it clear that there was a 'general' problem with the 3D secure verif process. */
				
			case 1700: // Error in determining enrollment/3D transaction status
			case 1701: // Card not enrolled
			case 1702: // Authentication failed
			case 1703: // Unable to connect to ParesListener
			case 1704: // Card type not recognized
			case 1705: // Acquirer not 3D secure enrolled
			case 1706: // Merchant not 3D secure enrolled
			case 1707: // Acquirer's password missing
			case 1708: // Acquirer's password is invalid
			case 1709: // Invalid currency code
			case 1710: // Invalid transaction data
			case 1711: // PAReq not received by ACS
			case 1712: // Can not find serial number
			case 1713: // Invalid 3D merchant ID
			case 1714: // Invalid credit card number
			case 1715: // Invalid credit card expiry date
			case 1716: // Invalid order number
			case 1717: // Invalid purchase amount
			case 1718: // Reccurence frequency is invalid
			case 1719: // Authorization number is invalid
			case 1720: // Card not supported
			case 1721: // Validation Error
			case 1722: // 3D processing error. Kindly contact your issuing bank to investigate
			case 1723: // System error during Secure3D authentication
				return PaymentResultType.ERROR_3D_SECURE_VERIFICATION_FAILED;
				
			case 2000: // A system error occurred
			case 2001: // A database error occurred
			case 2002: // Invalid session. Please contact merchant to check your transaction status.
				return PaymentResultType.ERROR_REMOTE;
				
			default:
				return PaymentResultType.ERROR_UNKNOWN;
		}
	}
}
