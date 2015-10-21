/*
 * Copyright Neopeak Internet Solutions inc.
 * 
 * This file is part of the Brown Socks project. See accompanying
 * LICENSE file for legal restrictions.
 */

package org.brownsocks.payments.gateways.enets.pgp;

import java.io.IOException;

import org.brownsocks.payments.gateways.enets.SignatureVerificationException;

public interface PGPProvider {

	/**
	 * Initialize the PGP provider with the local private key and 
	 * the public key of the remote party.
	 * @param privateKey
	 * @param privateKeyPass 
	 * @param publicKey
	 */
	public void initialize(String privateKey, String privateKeyPass, String publicKey) throws Exception;
	
	/**
	 * Sign with our private key and encrypt with the remote party's public key.
	 * @param message
	 * @return
	 * @throws IOException 
	 */
	public String signAndEncrypt(String message) throws IOException;
	
	/**
	 * Decrypt a message with our private key and verify the signature with remote pub key.
	 * @param message
	 * @return
	 */
	public String decryptAndVerify(String message) throws SignatureVerificationException, IOException;
	
}
