package org.mifosplatform.billing.paymode.service;

import java.util.Collection;

import org.mifosplatform.billing.paymode.data.McodeData;
import org.mifosplatform.infrastructure.core.api.JsonCommand;

public interface PaymodeReadPlatformService {

	McodeData retrieveSinglePaymode(Long paymodeId);

	//Collection<PaymodeData> retrieveAllPaymodes();

	McodeData retrievePaymodeCode(JsonCommand command);

	Collection<McodeData> retrievemCodeDetails(String codeName);

}
