package org.brownsocks.payments.gateways.enets.data;

import java.io.Serializable;

public class DebitAck implements Serializable {
	private static final long serialVersionUID = 1L;

	private String retry;
	private String version;
	private String param1;
	private String param2;
	private String param3;
	private String param4;
	private String param5;
	private String merchantSig;
	private String certId;

	public String getRetry() {
		return retry;
	}

	public void setRetry(String retry) {
		this.retry = retry;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getParam1() {
		return param1;
	}

	public void setParam1(String param1) {
		this.param1 = param1;
	}

	public String getParam2() {
		return param2;
	}

	public void setParam2(String param2) {
		this.param2 = param2;
	}

	public String getParam3() {
		return param3;
	}

	public void setParam3(String param3) {
		this.param3 = param3;
	}

	public String getParam4() {
		return param4;
	}

	public void setParam4(String param4) {
		this.param4 = param4;
	}

	public String getParam5() {
		return param5;
	}

	public void setParam5(String param5) {
		this.param5 = param5;
	}

	public String getMerchantSig() {
		return merchantSig;
	}

	public void setMerchantSig(String merchantSig) {
		this.merchantSig = merchantSig;
	}

	public String getCertId() {
		return certId;
	}

	public void setCertId(String certId) {
		this.certId = certId;
	}

}
