package org.mifosplatform.scheduledjobs;

import java.util.Date;
import java.util.List;

import org.mifosplatform.billing.eventorder.domain.PrepareRequest;
import org.mifosplatform.billing.eventorder.domain.PrepareRequsetRepository;
import org.mifosplatform.billing.preparerequest.data.PrepareRequestData;
import org.mifosplatform.billing.preparerequest.service.PrepareRequestReadplatformService;
import org.mifosplatform.billing.processscheduledjobs.service.SheduleJobWritePlatformService;
import org.mifosplatform.infrastructure.core.domain.MifosPlatformTenant;
import org.mifosplatform.infrastructure.core.service.ThreadLocalContextUtil;
import org.mifosplatform.infrastructure.security.service.TenantDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;





@Service
public class ProcessRequestSheduledJob  {

	
	  private final TenantDetailsService tenantDetailsService;
	  private final PrepareRequestReadplatformService prepareRequestReadplatformService;
	  private final PrepareRequsetRepository prepareRequsetRepository;
       
	  @Autowired
	  private  SheduleJobWritePlatformService sheduleJobWritePlatformService;	  
	  

	    @Autowired
	    public ProcessRequestSheduledJob(final TenantDetailsService tenantDetailsService,final PrepareRequestReadplatformService prepareRequestReadplatformService,
	    		final PrepareRequsetRepository prepareRequsetRepository) {
	          
	            this.tenantDetailsService = tenantDetailsService;
	            this.prepareRequestReadplatformService=prepareRequestReadplatformService;
	            this.prepareRequsetRepository=prepareRequsetRepository;
	         //   this.sheduleJobWritePlatformService=sheduleJobWritePlatformService;
	             
	    }

	   
		public void execute() {
			
		
			
			System.out.println("Running Job Processing Request"+new Date());
			
	        final MifosPlatformTenant tenant = this.tenantDetailsService.loadTenantById("default"); 
	        ThreadLocalContextUtil.setTenant(tenant);
	        //Retrieve the data from Prepare Request 
	        int processingJobs=0;
            List<PrepareRequestData> data=this.prepareRequestReadplatformService.retrieveDataForProcessing();
          
            for(PrepareRequestData requestData:data){
            	
                       //Get the Order details
                     final List<Long> clientOrderIds = this.prepareRequestReadplatformService.retrieveRequestClientOrderDetails(requestData.getClientId());
                     
                              //Processing the request
                            if(clientOrderIds!=null){
                                    // this.processingClientDetails(clientOrderIds,requestData);
                            	this.prepareRequestReadplatformService.processingClientDetails(clientOrderIds,requestData);
                            	
                                    //Update RequestData
                                     PrepareRequest prepareRequest=this.prepareRequsetRepository.findOne(requestData.getRequestId());
                                     prepareRequest.updateProvisioning();
                                     this.prepareRequsetRepository.save(prepareRequest);
                                     processingJobs=processingJobs+1;
                            }
                         
            }
            
          
           this.sheduleJobWritePlatformService.runSheduledJobs();
            
           
            System.out.println("Finishing Job Processing Request"+new Date());
            System.out.println("Processing Jobs are "+processingJobs);
           
           
	    }
                    
                    

		
			
			
			
		}

		

