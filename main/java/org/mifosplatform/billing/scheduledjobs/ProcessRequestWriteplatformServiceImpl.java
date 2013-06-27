package org.mifosplatform.billing.scheduledjobs;

import java.util.Date;
import java.util.List;

import org.mifosplatform.billing.eventorder.domain.PrepareRequest;
import org.mifosplatform.billing.eventorder.domain.PrepareRequsetRepository;
import org.mifosplatform.billing.order.domain.OrderRepository;
import org.mifosplatform.billing.order.service.OrderReadPlatformService;
import org.mifosplatform.billing.preparerequest.data.PrepareRequestData;
import org.mifosplatform.billing.preparerequest.service.PrepareRequestReadplatformService;
import org.mifosplatform.billing.processrequest.domain.ProcessRequest;
import org.mifosplatform.billing.processrequest.domain.ProcessRequestDetails;
import org.mifosplatform.billing.processrequest.domain.ProcessRequestRepository;
import org.mifosplatform.infrastructure.core.domain.MifosPlatformTenant;
import org.mifosplatform.infrastructure.core.service.DataSourcePerTenantService;
import org.mifosplatform.infrastructure.core.service.ThreadLocalContextUtil;
import org.mifosplatform.infrastructure.security.service.TenantDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;




@Service(value = "processRequestWriteplatformService")
public class ProcessRequestWriteplatformServiceImpl implements ProcessRequestWriteplatformService{

	
	  private final TenantDetailsService tenantDetailsService;
	  private final DataSourcePerTenantService dataSourcePerTenantService;
	  private final PrepareRequestReadplatformService prepareRequestReadplatformService;
	  private final OrderReadPlatformService orderReadPlatformService;
	  private final OrderRepository orderRepository;
	  private final ProcessRequestRepository processRequestRepository;
	  private final PrepareRequsetRepository prepareRequsetRepository;
	  

	    @Autowired
	    public ProcessRequestWriteplatformServiceImpl(final DataSourcePerTenantService dataSourcePerTenantService,final TenantDetailsService tenantDetailsService,
	    		final PrepareRequestReadplatformService prepareRequestReadplatformService,final OrderReadPlatformService orderReadPlatformService,
	    		final OrderRepository orderRepository,final ProcessRequestRepository processRequestRepository,final PrepareRequsetRepository prepareRequsetRepository) {
	            this.dataSourcePerTenantService = dataSourcePerTenantService;
	            this.tenantDetailsService = tenantDetailsService;
	            this.prepareRequestReadplatformService=prepareRequestReadplatformService;
	            this.orderReadPlatformService=orderReadPlatformService;
	            this.orderRepository=orderRepository;
	            this.processRequestRepository=processRequestRepository;
	            this.prepareRequsetRepository=prepareRequsetRepository;
	             
	    }

	    @Transactional
	    @Override
		public void ProcessingRequestDetails() {
	        
	        final MifosPlatformTenant tenant = this.tenantDetailsService.loadTenantById("default");
	        ThreadLocalContextUtil.setTenant(tenant);
	        
	   //   JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSourcePerTenantService.retrieveTenantAwareDataSource());
	        
	        //Retrieve the data from Prepare Request 
            List<PrepareRequestData> data=this.prepareRequestReadplatformService.retrieveDataForProcessing();
            
         //   System.out.println(data.get(0).getRequestId());
            for(PrepareRequestData requestData:data){
            	
                       //Get the Order details
                     final List<Long> clientOrderIds = this.prepareRequestReadplatformService.retrieveRequestClientOrderDetails(requestData.getClientId());
                              //Processing the request
                            if(clientOrderIds!=null){
                                     this.processingClientDetails(clientOrderIds,requestData);
                                    //Update RequestData
                                     PrepareRequest prepareRequest=this.prepareRequsetRepository.findOne(requestData.getRequestId());
                                     prepareRequest.updateProvisioning();
                                     this.prepareRequsetRepository.save(prepareRequest);
                            }
            }
	    }
                    
                    
          

		

		private void processingClientDetails(List<Long> clientOrderIds,PrepareRequestData requestData) {
			
			
			
			for(Long orderId:clientOrderIds){
				
				 final MifosPlatformTenant tenant = this.tenantDetailsService.loadTenantById("default");
			        ThreadLocalContextUtil.setTenant(tenant);
			        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSourcePerTenantService.retrieveTenantAwareDataSource());
			        
			/*	Order order=this.orderRepository.findOne(orderId);
				
				ProcessRequest processRequest=new ProcessRequest(order.getClientId(),order.getId(),"NONE",requestData.getHardwareId(),order.getStartDate(),
						order.getEndDate(),new Date(),new Date(),"None",'N');*/
				
			        ProcessRequest processRequest=new ProcessRequest(1l,2l,"NONE","jhgj",new Date(),
							new Date(),new Date(),new Date(),"None",'N');
			        
				/*List<OrderLine> orderLineData=order.getServices();
				  for(OrderLine orderLine:orderLineData){
					  
					  ProcessRequestDetails processRequestDetails=new ProcessRequestDetails(orderLine.getId(),orderLine.getServiceId(),"Sent","Recieved",'N');
					  processRequest.add(processRequestDetails);
					  
				  }*/
			       
						  
						  ProcessRequestDetails processRequestDetails=new ProcessRequestDetails(1l,2l,"Sent","Recieved",'N');
						  processRequest.add(processRequestDetails);
						  
					
				
                       this.processRequestRepository.save(processRequest);				
				
				
			}
              
			
			
			
		}

		
}
