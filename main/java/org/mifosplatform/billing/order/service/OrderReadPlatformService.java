package org.mifosplatform.billing.order.service;

import java.util.List;

import org.mifosplatform.billing.order.data.DisconnectDetails;
import org.mifosplatform.billing.order.data.OrderData;
import org.mifosplatform.billing.order.data.OrderPriceData;
import org.mifosplatform.billing.payterms.data.PaytermData;
import org.mifosplatform.billing.plan.data.PlanCodeData;
import org.springframework.jdbc.core.JdbcTemplate;

public interface OrderReadPlatformService {

	List<PlanCodeData> retrieveAllPlatformData();

	List<PaytermData> retrieveAllPaytermData();

	// List<OrderData> retrieveOrderLineData(Long orderId);
	List<OrderPriceData> retrieveOrderPriceData(Long orderId);

	

	void retrieveInvoice(Long clientId);

	List<PaytermData> getChargeCodes(Long planCode);

	List<OrderPriceData> retrieveOrderPriceDetails(Long orderId, Long clientId);

	List<OrderData> retrieveClientOrderDetails(Long clientId);





}
