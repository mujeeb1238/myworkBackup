package org.mifosplatform.billing.message.service;

import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;

public interface BillingMessageDataWritePlatformService {

	CommandProcessingResult createMessageData(JsonCommand command);
	
	
	

}
