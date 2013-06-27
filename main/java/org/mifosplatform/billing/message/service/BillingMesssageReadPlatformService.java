package org.mifosplatform.billing.message.service;

import java.util.List;

import org.mifosplatform.billing.message.data.BillingMessageData;
import org.mifosplatform.infrastructure.core.api.JsonCommand;

public interface BillingMesssageReadPlatformService {
	
BillingMessageData retrieveMessageTemplate(Long id);

List<BillingMessageData> retrieveAllMessageTemplates();

List<BillingMessageData> retrieveAllMessageTemplateParams();


List<BillingMessageData> retrieveMessageParams(Long entityId);

List<BillingMessageData> retrieveData(JsonCommand command, BillingMessageData templateData, List<BillingMessageData> messageparam);






}
