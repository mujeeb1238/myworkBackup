package org.mifosplatform.billing.eventorder.service;

import java.util.List;

import org.mifosplatform.billing.onetimesale.data.OneTimeSaleData;

public interface EventOrderReadplatformServie {
	
	List<OneTimeSaleData> retrieveEventOrderData(Long clientId);

}
