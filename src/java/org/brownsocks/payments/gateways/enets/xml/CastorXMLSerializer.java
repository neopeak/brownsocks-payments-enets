package org.brownsocks.payments.gateways.enets.xml;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.brownsocks.payments.gateways.enets.data.TxnReq;
import org.brownsocks.payments.gateways.enets.data.TxnRes;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.xml.sax.InputSource;

public class CastorXMLSerializer implements XMLSerializer {
	
	private static final String MAPPING_FILE_LOCATION = "org/brownsocks/payments/gateways/enets/xml/castor/umapi-castor-mapping.xml";
	
	private Mapping _mapping;
	
	public void initialize() throws Exception {
		_mapping = loadMapping();
	}
	
	@Override
	public String serializeTxnReq(TxnReq txnReq) throws IOException {
		try {
	        StringWriter stringwriter = new StringWriter();
	        Marshaller marshaller = new Marshaller(stringwriter);
	        marshaller.setMapping(_mapping);
	        marshaller.marshal(txnReq);
	        return stringwriter.toString();
	        
		} catch (Exception e) {
			throw new IOException("Exception occured during XML marshalling.", e);
		}
	}

	@Override
	public TxnRes unserializeTxnRes(String xml) throws IOException {
		try {
	        Unmarshaller unmarshaller = new Unmarshaller(_mapping);
	        return (TxnRes) unmarshaller.unmarshal(new InputSource(new ByteArrayInputStream(xml.getBytes())));
	        
		} catch (Exception e) {
			throw new IOException("Exception occured during XML unmarshalling.", e);
			
		}
	}
	
	private Mapping loadMapping() throws IOException, MappingException {
		InputStream inputstream = null;
		
		try {
			inputstream = CastorXMLSerializer.class.getClassLoader().getResourceAsStream(MAPPING_FILE_LOCATION);
			Mapping mapping = new Mapping();
			mapping.loadMapping(new InputSource(inputstream));
			return mapping;
			
		} finally {
			IOUtils.closeQuietly(inputstream);
			
		}
	}

}
