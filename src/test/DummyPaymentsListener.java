import org.brownsocks.payments.PaymentResult;
import org.brownsocks.payments.PaymentsListener;


public class DummyPaymentsListener implements PaymentsListener {

	@Override
	public void paymentProcessing(PaymentResult result) {
		System.out.println("Received payment processing event!");
		printResult(result);
	}

	@Override
	public void paymentReceived(PaymentResult result) {
		System.out.println("Received payment received event!");
		printResult(result);
	}

	private void printResult(PaymentResult result) {
		System.out.println(result.getResultType());
		if (result.isError())
			System.out.println("Error message: " + result.getErrorMessage());

		System.out
				.println("Confirmation code: " + result.getConfirmationCode());

		for (String key : result.getMetas().keySet()) {
			String val = result.getMetas().get(key);

			System.out.println(key + ": " + val);

		}
	}

	
}
