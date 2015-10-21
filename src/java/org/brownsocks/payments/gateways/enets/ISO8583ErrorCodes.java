/*
 * Copyright Neopeak Internet Solutions inc.
 * 
 * This file is part of the Brown Socks project. See accompanying
 * LICENSE file for legal restrictions.
 */

package org.brownsocks.payments.gateways.enets;

import org.brownsocks.payments.PaymentResultType;

/**
 * Convert ISO8583 response codes to brownsocks result code. 
 * @author cveilleux
 */
public class ISO8583ErrorCodes {

	public static PaymentResultType convertErrorCode(String errorCodeStr) {
		
		int errorCode = new Integer(errorCodeStr).intValue();
		
		switch (errorCode) {
			case 0: // Approval
			case 8: // Approved, verify ID & signature
				return PaymentResultType.SUCCESS;
			
			case 1: // Refer to Card Issuer
			case 2: // Refer to Card Issuer's special condition
				return PaymentResultType.ERROR_CARD;
				
			case 3: // Invalid Merchant
				return PaymentResultType.ERROR_INVALID_MERCHANT;
				
			case 4: // Pick-up
				return PaymentResultType.ERROR_SUSPECT;

			case 5: // Do not honour
				return PaymentResultType.ERROR_CARD_DECLINED;
				
			case 12: // Invalid transaction
			case 13: // Invalid amount
				return PaymentResultType.ERROR_INVALID_REQUEST;
				
			case 14: // Invalid card number
				return PaymentResultType.ERROR_CARD;
				
			case 19: // Re-enter transaction
				return PaymentResultType.ERROR_NOT_PROCESSED;
				
			case 21: // No Transaction
				return PaymentResultType.ERROR_INVALID_REQUEST;
				
			case 25: // Unable to locate record on file
			case 30: // Format Errror
			case 31: // Bank not supported by switch
				return PaymentResultType.ERROR_REMOTE;
				
			case 41: // Lost card 
			case 43: // Stolen card, pickup
				return PaymentResultType.ERROR_SUSPECT;
				
			case 51: // Not sufficient funds
			case 52: // No cheque account
			case 53: // No saving account
				return PaymentResultType.ERROR_CARD_DECLINED;
				
			case 54: // Expired card
			case 55: // Incorrect PIN
				return PaymentResultType.ERROR_CARD;
				
			case 56: // No card record
				return PaymentResultType.ERROR_INVALID_REQUEST;
				
			case 58: // Transaction not permitted to terminal
				return PaymentResultType.ERROR_REMOTE;
				
			case 61: // Exceeds withdrawal amount limit
				return PaymentResultType.ERROR_CARD_DECLINED;
				
			case 63: // Security violation
			case 75: // Allowable number of PIN tries exceeded
				return PaymentResultType.ERROR_SUSPECT;
				
			case 76: // Invalid product code
				return PaymentResultType.ERROR_INVALID_REQUEST;
				
			case 77: // Reconcile error
			case 78: // Trans. Number not found
				return PaymentResultType.ERROR_REMOTE;
				
			case 79: // Invalid CVV
				return PaymentResultType.ERROR_CARD;
				
			case 80: // Batch number not found
			case 85: // Batch not found
			case 88: // Approved, Have cm call AMEX
			case 89: // Bad terminal ID
			case 91: // Issuer or switch is inoperative
				return PaymentResultType.ERROR_REMOTE;
				
			case 94: // Duplicate transmission
				return PaymentResultType.ERROR_DUPLICATE_TRANSACTION;
				
			case 95: // Reconcile error. Batch upload started
			case 96: // System malfunction
				return PaymentResultType.ERROR_REMOTE;
		
				
			default:
				return PaymentResultType.ERROR_UNKNOWN;
			
		}
		
	}

	
}
