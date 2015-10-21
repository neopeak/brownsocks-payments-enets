/*
 * Copyright Neopeak Internet Solutions inc.
 * 
 * This file is part of the Brown Socks project. See accompanying
 * LICENSE file for legal restrictions.
 */
package org.brownsocks.payments.gateways.enets;

/**
 * Indicates that the signature of the PGP message could not be
 * verified.
 * @author cveilleux
 */
public class SignatureVerificationException extends Exception {
	private static final long serialVersionUID = 1L;

	public SignatureVerificationException() {
		super();
	}

	public SignatureVerificationException(String message, Throwable cause) {
		super(message, cause);
	}

	public SignatureVerificationException(String message) {
		super(message);
	}

	public SignatureVerificationException(Throwable cause) {
		super(cause);
	}

}
