import java.io.IOException;

import org.brownsocks.payments.CardType;
import org.brownsocks.payments.CustomerInfo;
import org.brownsocks.payments.PaymentRequest;
import org.brownsocks.payments.PaymentResult;
import org.brownsocks.payments.gateways.GatewayInitializationException;
import org.brownsocks.payments.gateways.UnsupportedGatewayRequestException;
import org.brownsocks.payments.gateways.enets.EnetsMerchantAccount;
import org.brownsocks.payments.gateways.enets.EnetsServerGateway;

public class Test {
	public static void main(String[] args) throws IOException, GatewayInitializationException, UnsupportedGatewayRequestException {
		
		EnetsMerchantAccount testAccount = EnetsServerGateway.buildDefaultTestAccount();
		
		EnetsServerGateway enets = new EnetsServerGateway();
		// TODO: Configure account url's here
		enets.setGatewayAccount(testAccount);
		enets.initialize();
		
		PaymentRequest request = new PaymentRequest();
		request.setAmount(1000);
		request.setCardExpiryMonth(2);
		request.setCardExpiryYear(14);
		request.setCardNumber("4111111111111111");
		request.setCardType(CardType.VISA);
		request.setCcv("232");
		request.setCurrency("SGD");
		request.setMerchantTxnID(new Long(System.currentTimeMillis()).toString());
		
		CustomerInfo customer = new CustomerInfo();
		customer.setFirstName("Cedric");
		customer.setLastName("Veilleux");
		
		request.setCustomerInfo(customer);
		
		PaymentResult result = enets.sale(request);
		
		System.out.println(result.getResultType());
		if (result.isError())
			System.out.println("Error message: " + result.getErrorMessage());
		
		System.out.println("Confirmation code: " + result.getConfirmationCode());
		
		for (String key : result.getMetas().keySet()) {
			String val = result.getMetas().get(key);
			
			System.out.println(key + ": " + val);
			
		}
		
	}
}
