import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.brownsocks.payments.CardType;
import org.brownsocks.payments.CustomerInfo;
import org.brownsocks.payments.PaymentRequest;
import org.brownsocks.payments.PaymentResult;
import org.brownsocks.payments.gateways.GatewayInitializationException;
import org.brownsocks.payments.gateways.UnsupportedGatewayRequestException;
import org.brownsocks.payments.gateways.enets.EnetsMerchantAccount;
import org.brownsocks.payments.gateways.enets.EnetsPostServlet;
import org.brownsocks.payments.gateways.enets.EnetsServerGateway;

public class TestLive {
	
	private static final String PRIVATE_KEY_FILENAME = "angkor2011-priv.pgp.asc";
	private static final String PRIVATE_KEY_PASSPHRASE = "Password1$";
	private static final String MID = "937773000";
	
	public static void main(String[] args) throws IOException, GatewayInitializationException, UnsupportedGatewayRequestException {
		EnetsMerchantAccount liveAccount = EnetsServerGateway.buildDefaultLiveAccount();
		liveAccount.setMerchantPrivateKey(new String(IOUtils.toByteArray(new FileReader(PRIVATE_KEY_FILENAME))));
		liveAccount.setMerchantPrivateKeyPassphrase(PRIVATE_KEY_PASSPHRASE);
		liveAccount.setMerchantMID(MID);
		liveAccount.setCancelURL("http://office.neopeak.com:8181/enets/cancel");
		liveAccount.setNotifyURL("http://office.neopeak.com:8181/enets/noti");
		liveAccount.setFailureURL("http://office.neopeak.com:8181/enets/failure");
		liveAccount.setSuccessURL("http://office.neopeak.com:8181/enets/success");
		liveAccount.setPostURL("http://office.neopeak.com:8181/enets/post");
		
		EnetsServerGateway enets = new EnetsServerGateway();
		enets.setGatewayAccount(liveAccount);
		enets.initialize();

		PaymentRequest request = new PaymentRequest();
		request.setAmount(1000);
		request.setCurrency("USD");
		/*
		request.setCardExpiryMonth(8);
		request.setCardExpiryYear(12);
		request.setCardNumber("5581587000419483");
		request.setCardType(CardType.MC);
		request.setCcv("814");
		*/
		request.setCardExpiryMonth(3);
		request.setCardExpiryYear(13);
		request.setCardNumber("4530912404579010");
		request.setCardType(CardType.VISA);
		request.setCcv("938");
		
		request.setMerchantTxnID(new Long(System.currentTimeMillis())
				.toString());

		CustomerInfo customer = new CustomerInfo();
		customer.setFirstName("Cedric");
		customer.setLastName("Veilleux");

		request.setCustomerInfo(customer);

		PaymentResult result = enets.sale(request);
		
	}
	
	
}
