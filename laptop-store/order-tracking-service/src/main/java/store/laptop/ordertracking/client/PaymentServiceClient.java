package store.laptop.ordertracking.client;

import org.springframework.cloud.openfeign.FeignClient;
import store.laptop.payment.shared.model.web.api.PaymentAPI;

@FeignClient(name = "payments",
	url = "#{serviceCommonConfig['payment-service'].url}/payments")
public interface PaymentServiceClient extends PaymentAPI {

}
