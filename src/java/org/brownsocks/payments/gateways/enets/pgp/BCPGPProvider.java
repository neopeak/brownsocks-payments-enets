/*
 * Copyright Neopeak Internet Solutions inc.
 * 
 * This file is part of the Brown Socks project. See accompanying
 * LICENSE file for legal restrictions.
 */

package org.brownsocks.payments.gateways.enets.pgp;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.io.IOUtils;
import org.bouncycastle.bcpg.ArmoredOutputStream;
import org.bouncycastle.bcpg.BCPGOutputStream;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.PGPEncryptedDataGenerator;
import org.bouncycastle.openpgp.PGPEncryptedDataList;
import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPLiteralData;
import org.bouncycastle.openpgp.PGPLiteralDataGenerator;
import org.bouncycastle.openpgp.PGPObjectFactory;
import org.bouncycastle.openpgp.PGPOnePassSignature;
import org.bouncycastle.openpgp.PGPOnePassSignatureList;
import org.bouncycastle.openpgp.PGPPrivateKey;
import org.bouncycastle.openpgp.PGPPublicKey;
import org.bouncycastle.openpgp.PGPPublicKeyEncryptedData;
import org.bouncycastle.openpgp.PGPPublicKeyRing;
import org.bouncycastle.openpgp.PGPPublicKeyRingCollection;
import org.bouncycastle.openpgp.PGPSecretKey;
import org.bouncycastle.openpgp.PGPSecretKeyRing;
import org.bouncycastle.openpgp.PGPSignature;
import org.bouncycastle.openpgp.PGPSignatureGenerator;
import org.bouncycastle.openpgp.PGPSignatureList;
import org.bouncycastle.openpgp.PGPUtil;
import org.bouncycastle.util.encoders.Base64;
import org.brownsocks.payments.gateways.enets.SignatureVerificationException;

/**
 * Bouncy-Castle based PGP provider.
 * @author cveilleux
 */
public class BCPGPProvider implements PGPProvider {
	
	private PGPSecretKeyRing _secretKeyRing;
	private String _passphrase;
	private PGPPublicKeyRingCollection _remotePublicKeyRing;
	
	private BouncyCastleProvider _provider;
	
	public static void main(String[] args) throws NoSuchProviderException, IOException, SignatureVerificationException, PGPException {
		BCPGPProvider provider = new BCPGPProvider();
		
		String rawPrivateKey = readFile("merchant-test-priv.pgp.asc");
		String rawPubKey = readFile("nets-test-pub.pgp.asc");
		provider.initialize(rawPrivateKey, "password1", rawPubKey);

		String rawMessage = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<txn-req><txn-amount>1000</txn-amount><credit-txn-req><expiry-date>1502</expiry-date><payment-type>SALE</payment-type><post-url>https://test.enets.sg/umapi/notify.jsp</post-url><cvv>232</cvv><pan>4111111111111111</pan><card-holder-name>Cedric Veilleux</card-holder-name><cancel-url>https://test.enets.sg/umapi/post.jsp</cancel-url></credit-txn-req><currency-code>SGD</currency-code><tid>termID</tid><submission-mode>S</submission-mode><merchant-cert-id>1</merchant-cert-id><payment-mode>CC</payment-mode><nets-mid>968158000</nets-mid><merchant-txn-ref>1318475086339</merchant-txn-ref><success-url>https://test.enets.sg/umapi/success.jsp</success-url><failure-url>https://test.enets.sg/umapi/fail.jsp</failure-url><notify-url>https://test.enets.sg/umapi/notify.jsp</notify-url></txn-req>\n";
		String crypted = provider.signAndEncrypt(rawMessage);
		System.out.println(crypted);
		
		String cryptedData = "LS0tLS1CRUdJTiBQR1AgTUVTU0FHRS0tLS0tClZlcnNpb246IEJDUEcgdjEuNDMKCmhRRU9BOWhua1VsOGV4R0hFQVFBeW9ncUJhelE3a1hkWXRwZzFXbE1VQmNsb2VRcllIdk4yK1FyYkhNbDBrVDcKaWNQUlpVcjB6b0MzTHZNTm5tcVpPT1BOenpjOFlBWnUrcUMrci9GSnN6c3dSUGRrVzFPaGFSVzk1SGNONjV4YgpTSkdrRjZtM1dTWnFVbkIzOWhkZDNsZjBGbWVGMHVPZlFuVXZpejIwNEVycEF3aUhkRjBHd0JobkNzUzlYTHdFCkFJSjVWdjZQNFJVRS9rWjN0U1VVcENYdVVtbDhIaHZxVFNWeVh3eWJRN0RBWktESVdqYVJlaDVYNFhtSDJ4YUcKM29mdUxETzBBTDloR0kxOUtJM3BCMFFRS3NlZTIrazM2MlUzZThMSStpVlVQV2lVbHQ2RDgxV2ROaXlBcW13RgpmV2JkcDk4NnhTUWNrczFhUVUxRUdKY0JBcDBmR2ZsSnNkUXJpcWp2VGdWVHljR080WW5YTTc3S1lYMFlSTVNFCnE3NXEzYWpWd3BpVTMzSUdmSGp3YVlCREp6WVRjVTRjWXJJZ1BZZ3ZxODVzbFZnUFRIZHpLMlpHUTZZaDlQbEEKQ2c0YUN0RDlFSzkvSEdGR20zU015TnpzdWpJOWFvM2gzVW03cFNpRFF3cWY4L0ZnMDFHUU5oSjFRd3doM1FWSwo2NVZrS0lWdzQ4Ynp2Y2dMNHJGT2tVVUo4MlVwR2FCMHYrUUpqWENFU1pxbURCaFliMWZITU5xWnBhQ0pTZUl6CnZjZzNtVGpsYk56YzFOQkFlRzNZdHY1WWpZSi85TCtEdkVueGpVQmpzQ0MzRTlsSnJsZXVNSlE4QWtaTzBXTW4KZzdwbjhxWXNrUy9vUk5rKzJVaVpqSDRod0J5Y2h0WjRDZ3VXd1pGVStBMU9JbDBHaFZKLzQ5YlR4SzRQSENGMwpsYlVINFVuQVZJSUU4OFQwV1dQakFWb25WSEFKKzdPaWczOEl6NzVHVG13MWhKbnY4enduWG9DcVVtSGFTMFJnClUveGtqNE5TVWhoSDNFdEk0bzVGTmNxME1CZ1MwR21TMVVmS1V6ZFJpN0FEWUFhNEhSWldmRUxSR1BWOGxVTmkKUVg1YWR3UmR5eTFLZUg0YTFXM0FWUlY2cjQwRnpVV2dPcGY2OTFiTGZOWkp3WTdxRDc5NGxvRFZuOHhROU5oNwpIQklDNFBnRkM4eHRSOFpaeEI2Uzd2UVlseVpMZ0xVWk1icFpyTHppYVRMM2tsMzhkOEtDbWVoWnBSUlo4cC9vCmQyYjhBR1E0a1pBbjNiQUtJWkJ1cEd0NHpqV0JKZmdRSmgycVc4dzN6OXpzSHVBcUNvbi9SODRLVTVxd0JpbzYKTEh3d0ZwNG9DRzNNcnRGdERuVlMxOFFwTHR3WEVaT2RtRmZhNlJ4VDR2SklwMlVaeXhLQ3MycjhEdzUzMlkrZgpVMTJrMDAySXphVUg4MmN1V29oelBXdEJCbWoxM0NNbVFwc0hlUTNVUXNKM2kzTzJZMWJOUmRiME5CY1B3aTBhCnFOST0KPW53dDEKLS0tLS1FTkQgUEdQIE1FU1NBR0UtLS0tLQo=";
		System.out.println(provider.decryptAndVerify(cryptedData));
	}
	
	/**
	 * Utility method to read a complete file to a string (useful for reading keys!)
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public static String readFile(String filename) throws IOException {
		FileReader fileReader = new FileReader(new File(filename));
		BufferedReader reader = new BufferedReader(fileReader);
		
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
	}

	@Override
	public void initialize(String privateKey, String privateKeyPass, String publicKey) throws IOException, PGPException, NoSuchProviderException {
	
		_provider = new BouncyCastleProvider();
		
		_passphrase = privateKeyPass;
		_secretKeyRing = new PGPSecretKeyRing(PGPUtil.getDecoderStream(new ByteArrayInputStream(privateKey.getBytes())));
		_remotePublicKeyRing = new PGPPublicKeyRingCollection(PGPUtil.getDecoderStream(new ByteArrayInputStream(publicKey.getBytes())));
	}

	@Override
	public String signAndEncrypt(String message) throws IOException {
		
		try {
			/* Final < Armored < Crypted < Clear PGP */
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ArmoredOutputStream armoredOutput = new ArmoredOutputStream(out);
			PGPEncryptedDataGenerator crypter = new PGPEncryptedDataGenerator(PGPEncryptedDataGenerator.S2K_SHA1, new SecureRandom(), _provider);
			crypter.addMethod(getRemotePublicKey());
			BCPGOutputStream pgpOut = new BCPGOutputStream(crypter.open(armoredOutput, new byte[512]));
			
			/* Prepare for signing */
	        PGPSignatureGenerator signer = new PGPSignatureGenerator(getSigningPublicKey().getAlgorithm(), 
	                PGPUtil.SHA1, _provider
	                );
	        signer.initSign(PGPSignature.BINARY_DOCUMENT, getSigningPrivateKey());
	        
	        /* Output the standard header */
	        signer.generateOnePassVersion(false).encode(pgpOut);

	        /* Output the literal data */
	        PGPLiteralDataGenerator literalDataGenerator = new PGPLiteralDataGenerator(true);
	        literalDataGenerator.open(pgpOut, 'b', "bar", message.getBytes().length, new Date()).write(message.getBytes());
	        
	        /* Calculate signature and output it */
	        signer.update(message.getBytes());
	        signer.generate().encode(pgpOut);
	
	        pgpOut.close();
	        armoredOutput.close();
	        out.close();
	        
	        byte[] result = out.toByteArray();
	        
	        // brain dead UMAPI adds an extra base64 encoding on top of the ASCII armored string. Go figure.
			return new String(Base64.encode(result));
			
		} catch (PGPException pgpException) {
			throw new IOException("PGP subsystem problem.", pgpException);
			
		} catch (NoSuchAlgorithmException noSuchAlgorithmException) {
			throw new IOException("Missing algorithm. Are you running a compatible JVM/Bouncycastle version?", noSuchAlgorithmException);
			
		} catch (SignatureException signatureException) {
			throw new IOException("PGP subsystem problem.", signatureException);
			
		} catch (NoSuchProviderException noSuchProviderException) {
			throw new IOException("Missing provider. Are you running a compatible JVM/Bouncycastle version?", noSuchProviderException);
			
		}
		
		
	}
	
	@Override
	public String decryptAndVerify(String messageIn) throws IOException, SignatureVerificationException {
		
		try {
			/* Stage zero: Convert to ASCII armored format and open a decoding stream */
			InputStream is = new ByteArrayInputStream(Base64.decode(messageIn));
			InputStream decoderStream = PGPUtil.getDecoderStream(is);
			
			/* Stage one: Init a decrypting stream */
			PGPObjectFactory pgpFactory = new PGPObjectFactory(decoderStream);
			PGPEncryptedDataList cryptedDataList = (PGPEncryptedDataList) pgpFactory.nextObject();
			PGPPublicKeyEncryptedData cryptedData = (PGPPublicKeyEncryptedData) cryptedDataList.get(0);
			InputStream clearStream = cryptedData.getDataStream(getCryptingPrivateKey(), _provider);
			
			/* Stage two: Seperate the XML data from the signatures */
			PGPObjectFactory plainFact = new PGPObjectFactory(clearStream);
	
			PGPOnePassSignatureList onePassSignatureList = (PGPOnePassSignatureList) plainFact.nextObject();
			PGPLiteralData literalData = (PGPLiteralData) plainFact.nextObject();
			
			String xmlMessage = IOUtils.toString(literalData.getInputStream());
			PGPSignatureList signatureList = (PGPSignatureList) plainFact.nextObject();
			
			/* Stage three: Verify signature */
			PGPOnePassSignature ops = onePassSignatureList.get(0);
			PGPPublicKey key = _remotePublicKeyRing.getPublicKey(ops.getKeyID());	
			ops.initVerify(key, _provider);
			ops.update(xmlMessage.getBytes());
			
			if (!ops.verify(signatureList.get(0))) {
				throw new SignatureVerificationException("Failed to verify message signature. Message authenticity cannot be thrusted.");
			}
			
			return xmlMessage;
			
		} catch (PGPException pgpException) {
			throw new IOException("PGP subsystem problem.", pgpException);
			
		} catch (SignatureException signException) {
			throw new IOException("PGP subsystem problem.", signException);
			
		} catch (Throwable t) {
			throw new IOException("Unknown error occured in PGP subsystem: " + t.getMessage(), t);
			
		}
		
	}
	
	private PGPPublicKey getRemotePublicKey() throws IOException {
		Iterator it = _remotePublicKeyRing.getKeyRings();
		while (it.hasNext()) {
			PGPPublicKeyRing keyRing = (PGPPublicKeyRing) it.next();
			Iterator keyIt = keyRing.getPublicKeys();
			while (keyIt.hasNext()) {
				PGPPublicKey key = (PGPPublicKey) keyIt.next();
				if (key.isEncryptionKey())
					return key;
			}
		}
		
		throw new IOException("No suitable encryption key found.");
	}
	
	private PGPSecretKey getSigningSecretKey() throws IOException {
		Iterator it = _secretKeyRing.getSecretKeys();
		while (it.hasNext()) {
			PGPSecretKey secretKey = (PGPSecretKey) it.next();
			if (secretKey.isSigningKey())
				return secretKey;
		}
		throw new IOException("No signing key found.");
	}
	
	private PGPSecretKey getCryptingSecretKey() throws IOException {
		Iterator it = _secretKeyRing.getSecretKeys();
		while (it.hasNext()) {
			PGPSecretKey secretKey = (PGPSecretKey) it.next();
			if (!secretKey.isSigningKey())
				return secretKey;
		}
		throw new IOException("No crypting key found.");
	}
	
	private PGPPrivateKey getCryptingPrivateKey() throws IOException, PGPException {
		PGPSecretKey secret = getCryptingSecretKey();
		return secret.extractPrivateKey(_passphrase.toCharArray(), _provider);
	}
	
	private PGPPublicKey getSigningPublicKey() throws IOException {
		return getSigningSecretKey().getPublicKey();
	}
	
	private PGPPrivateKey getSigningPrivateKey() throws PGPException, IOException {
		PGPSecretKey secret = getSigningSecretKey();
		return secret.extractPrivateKey(_passphrase.toCharArray(), _provider);
	}

}
