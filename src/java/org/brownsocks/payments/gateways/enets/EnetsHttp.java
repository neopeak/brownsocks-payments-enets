package org.brownsocks.payments.gateways.enets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for sending requests to eNets server. 
 * @author cveilleux
 */
public class EnetsHttp {
	
	private static final Logger _log = LoggerFactory.getLogger(EnetsHttp.class);
	
	public static final int SO_TIMEOUT = 60 * 5 * 1000; // 2 minutes
	public static final int CONNECTION_TIMEOUT = 60 * 1 * 1000; // 1 minute
	
	public static String sendAndReceive(String url, String key, String val) throws HttpException, IOException {
		Map<String,String> params = new HashMap<String, String>();
		params.put(key, val);
		return sendAndReceive(url, params);		
	}

	public static String sendAndReceive(String url, Map<String,String> parameters) throws HttpException, IOException {
		
		PostMethod postmethod = new PostMethod(url);
		
		NameValuePair pairs[] = new NameValuePair[parameters.size()];
		
		int i = 0;
		for (String key : parameters.keySet()) {
			String val = parameters.get(key);
			pairs[i] = new NameValuePair(key, val);
			i++;
		}

		try {
			_log.info("Posting to " + url);
			
			HttpClient client = getDefaultHttpClient();
			postmethod.setRequestBody(pairs);
			int resultCode = client.executeMethod(postmethod);
			
			if (resultCode != 200) {
				_log.error("eNets gateway returned non 200 HTTP response code. (" + resultCode + ")");
				throw new HttpException("eNets gateway returned non 200 HTTP response code. (" + resultCode + ")");
				
			} else {
				_log.info("Got result code " + resultCode + " (success)");
				
			}
			
			return postmethod.getResponseBodyAsString();

		} finally {
			if (postmethod != null)
				postmethod.releaseConnection();
		}

	}	

	/**
	 * Returns an HTTP client configured with default timeouts.
	 * @return
	 */
	public static HttpClient getDefaultHttpClient() {
		/*
		// HTTP CLIENT 3.0 version:
		HttpClient httpclient = new HttpClient();
		HttpConnectionParams httpconnectionparams = new HttpConnectionParams();
		httpconnectionparams.setConnectionTimeout(CONNECTION_TIMEOUT);
		httpconnectionparams.setSoTimeout(SO_TIMEOUT);
		HttpClientParams httpclientparams = new HttpClientParams(httpconnectionparams);
		httpclient.setParams(httpclientparams);
		*/
		
		// HTTP CLIENT 2.0 version:
		HttpClient httpclient = new HttpClient();
		httpclient.setConnectionTimeout(CONNECTION_TIMEOUT);
		httpclient.setTimeout(SO_TIMEOUT);
		return httpclient;
	}
	
}
