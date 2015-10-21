package org.brownsocks.payments.gateways.enets.xml;

import java.io.IOException;

import org.brownsocks.payments.gateways.enets.data.TxnReq;
import org.brownsocks.payments.gateways.enets.data.TxnRes;

public interface XMLSerializer {
	
	public void initialize() throws Exception;
	
	public String serializeTxnReq(TxnReq txnReq) throws IOException;
	
	public TxnRes unserializeTxnRes(String xml) throws IOException;
	
}
