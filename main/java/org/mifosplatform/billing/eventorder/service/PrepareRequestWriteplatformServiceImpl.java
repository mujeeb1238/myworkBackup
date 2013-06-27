package org.mifosplatform.billing.eventorder.service;



import java.util.Date;

import org.mifosplatform.billing.eventorder.domain.PrepareRequest;
import org.mifosplatform.billing.eventorder.domain.PrepareRequsetRepository;
import org.mifosplatform.billing.order.data.SavingStatusEnumaration;
import org.mifosplatform.billing.order.domain.Order;
import org.mifosplatform.billing.plan.domain.StatusTypeEnum;
import org.mifosplatform.billing.transactionhistory.service.TransactionHistoryWritePlatformService;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;
import org.mifosplatform.infrastructure.core.data.EnumOptionData;
import org.mifosplatform.infrastructure.security.service.PlatformSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class PrepareRequestWriteplatformServiceImpl implements PrepareRequestWriteplatformService{
	private final PlatformSecurityContext context;
	private final PrepareRequsetRepository prepareRequsetRepository; 
	private final TransactionHistoryWritePlatformService transactionHistoryWritePlatformService;

	@Autowired
	public PrepareRequestWriteplatformServiceImpl(final PlatformSecurityContext context,
			final PrepareRequsetRepository prepareRequsetRepository,
			TransactionHistoryWritePlatformService transactionHistoryWritePlatformService) {
		this.context = context;
		this.prepareRequsetRepository=prepareRequsetRepository;
		this.transactionHistoryWritePlatformService=transactionHistoryWritePlatformService;

	}

	@Override
	public CommandProcessingResult prepareNewRequest(Order eventOrder,String provisioningStatus) {
  
		try{
			this.context.authenticatedUser();
			String requstStatus= SavingStatusEnumaration.interestCompoundingPeriodType(StatusTypeEnum.ACTIVE).getValue();;
			if(provisioningStatus.equalsIgnoreCase("NONE")){
			 requstStatus = SavingStatusEnumaration.interestCompoundingPeriodType(StatusTypeEnum.ACTIVE).getValue();
		
			}
			PrepareRequest prepareRequest=new PrepareRequest(eventOrder.getClientId(),eventOrder.getId(),requstStatus,provisioningStatus,'N',"NONE");
			
			this.prepareRequsetRepository.save(prepareRequest);
			transactionHistoryWritePlatformService.saveTransactionHistory(eventOrder.getClientId(), "EventOrder PrepareRequest", new Date(),"IsProvisioning:"+prepareRequest.getIsProvisioning(),
					"orderId:"+prepareRequest.getOrderId(),"Status:"+prepareRequest.getStatus(),"PrepareRequestID:"+prepareRequest.getId());
		} catch (DataIntegrityViolationException dve) {
			return CommandProcessingResult.empty();
		
	}
		
		
		
		
		return null;
	}

	

}
