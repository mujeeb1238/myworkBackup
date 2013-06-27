package org.mifosplatform.billing.message.service;


import java.util.List;

import org.mifosplatform.billing.message.data.BillingMessageData;
import org.mifosplatform.billing.message.serialization.MessageDataCommandFromApiJsonDeserializer;
import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResultBuilder;
import org.mifosplatform.infrastructure.core.serialization.FromJsonHelper;
import org.mifosplatform.infrastructure.security.service.PlatformSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BillingMessageDataWritePlatformServiceImpl implements BillingMessageDataWritePlatformService {
	
	private final PlatformSecurityContext context;
    private final FromJsonHelper fromApiJsonHelper;
    private final BillingMesssageReadPlatformService billingMesssageReadPlatformService;
    private final MessageDataCommandFromApiJsonDeserializer messageDataCommandFromApiJsonDeserializer;
	
    @Autowired
    public BillingMessageDataWritePlatformServiceImpl(PlatformSecurityContext context,
    	    FromJsonHelper fromApiJsonHelper,BillingMesssageReadPlatformService billingMesssageReadPlatformService,
    		MessageDataCommandFromApiJsonDeserializer messageDataCommandFromApiJsonDeserializer)
    {
    	this.context=context;
    	this.fromApiJsonHelper=fromApiJsonHelper;
    	this.billingMesssageReadPlatformService=billingMesssageReadPlatformService;
    	this.messageDataCommandFromApiJsonDeserializer=messageDataCommandFromApiJsonDeserializer;
    	
    }
    
    
	@Override
	public CommandProcessingResult createMessageData(JsonCommand command) {
		// TODO Auto-generated method stub
		context.authenticatedUser();
		this.messageDataCommandFromApiJsonDeserializer.validateForCreate(command.json());
		BillingMessageData templateData=this.billingMesssageReadPlatformService.retrieveMessageTemplate(command.entityId());
		List<BillingMessageData> messageparam=this.billingMesssageReadPlatformService.retrieveMessageParams(command.entityId());
		List<BillingMessageData> clientData=this.billingMesssageReadPlatformService.retrieveData(command,templateData,messageparam);
		
		/*ArrayList<BillingMessageData> param=new ArrayList<BillingMessageData>();
		for(BillingMessageData params:messageparam){
			param.add(params);
		}
		int no=0;
		for(BillingMessageData data:clientData){
			
			String header=templateData.getHeader();
			String footer=templateData.getFooter();
			String body=templateData.getBody();
			String subject=templateData.getSubject();
			String messageTo=command.stringValueOfParameterNamed("messageTo");
			String status=command.stringValueOfParameterNamed("status");
			String messageFrom=command.stringValueOfParameterNamed("messageFrom");
			int n=param.size();*/
			/*ArrayList<ArrayList<String>> dynamicData=new ArrayList<ArrayList<String>>();
			int h=dynamicData.size();
			
			ArrayList<String> dynaData=new ArrayList<String>();
			dynaData=dynamicData.get(no);
			ArrayList<String> client=new ArrayList<String>();
			for(int i=0;i<h;i++){
			client.add(i, );
			}
			for(int i=0;i<n;i++){
			header=header.replaceAll((param.get(i)).toString(),(client.get(i)).toString());
			body=body.replaceAll((param.get(i)).toString(),(client.get(i)).toString());								
			footer=footer.replaceAll((param.get(i)).toString(),(client.get(i)).toString());
			}*/
			/*header=header.replaceAll("param1", name);
			body=body.replaceAll("param2", balanceAmount.toString());
			footer=footer.replaceAll("param3", "OBS");*/
			/*ArrayList<String> client=new ArrayList<String>();
			client.add(0, data.getName());
			client.add(1, data.getBalanceAmount().toString());
			client.add(2, "OBS");
			Iterator<BillingMessageData> userIterator = param.iterator();
			 while (userIterator.hasNext()) {
		        BillingMessageData username = userIterator.next();
		        
		        }
			int j=client.size();
			  for(int i=0,k=0;i<n&k<j;i++,k++){
				
				 
				  String name=param.get(1).getParameter();
				  String value=client.get(k).toString();
				header=header.replaceAll(name,value);
				body=body.replaceAll(name,value);								
				footer=footer.replaceAll(name,value);
				}
			 
			BillingMessageTemplate billingMessageTemplate=this.messageTemplateRepository.findOne(command.entityId());
			BillingMessage billingMessage=new BillingMessage(header, body, footer, messageFrom, messageTo, subject, status,billingMessageTemplate);
		//	this..setMessageData(billingMessage);
			this.messageDataRepository.save(billingMessage);
		
		    no=no+1;
		*/
		 //return new CommandProcessingResultBuilder().withCommandId(command.commandId()).withEntityId(billingMessage.getId()).build();
		
		
		return new CommandProcessingResultBuilder().withCommandId(command.commandId()).withEntityId(command.entityId()).build();

	}

}
