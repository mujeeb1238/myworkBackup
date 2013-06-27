package org.mifosplatform.billing.scheduledjobs.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.codehaus.jettison.json.JSONObject;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.mifosplatform.billing.billingmaster.api.BillingMasterApiResourse;
import org.mifosplatform.billing.billingorder.service.InvoiceClient;
import org.mifosplatform.billing.billmaster.service.BillMasterWritePlatformService;
import org.mifosplatform.billing.billmaster.service.BillWritePlatformService;
import org.mifosplatform.billing.processscheduledjobs.service.SheduleJobReadPlatformService;
import org.mifosplatform.billing.processscheduledjobs.service.SheduleJobWritePlatformService;
import org.mifosplatform.billing.scheduledjobs.data.ScheduleJobData;
import org.mifosplatform.billing.scheduledjobs.domain.ScheduleJobs;
import org.mifosplatform.billing.scheduledjobs.domain.ScheduledJobRepository;
import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;
import org.mifosplatform.infrastructure.core.serialization.FromJsonHelper;
import org.mifosplatform.infrastructure.core.service.DataSourcePerTenantService;
import org.mifosplatform.infrastructure.security.service.TenantDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonElement;

@Service
public class SheduleJobWritePlatformServiceImpl  implements SheduleJobWritePlatformService{

	
	  private final TenantDetailsService tenantDetailsService;
	  private final DataSourcePerTenantService dataSourcePerTenantService;
	  private final SheduleJobReadPlatformService sheduleJobReadPlatformService;
	  private final InvoiceClient  invoiceClient;
	  private final ScheduledJobRepository scheduledJobRepository;
	  private final BillMasterWritePlatformService billMasterWritePlatformService;
	  private final FromJsonHelper fromApiJsonHelper;
	  private  final BillingMasterApiResourse billingMasterApiResourse;  
	  private final  BillWritePlatformService billWritePlatformService;
	    
	

	    @Autowired
	    public SheduleJobWritePlatformServiceImpl(final DataSourcePerTenantService dataSourcePerTenantService,final InvoiceClient invoiceClient,
	            final TenantDetailsService tenantDetailsService,final SheduleJobReadPlatformService sheduleJobReadPlatformService,
	            final ScheduledJobRepository scheduledJobRepository,final BillMasterWritePlatformService billMasterWritePlatformService,
	            final FromJsonHelper fromJsonHelper,final BillingMasterApiResourse billingMasterApiResourse,final BillWritePlatformService billWritePlatformService) {
	            this.dataSourcePerTenantService = dataSourcePerTenantService;
	            this.tenantDetailsService = tenantDetailsService;
	            this.sheduleJobReadPlatformService=sheduleJobReadPlatformService;
	            this.invoiceClient=invoiceClient;
	            this.scheduledJobRepository=scheduledJobRepository;
	           this.billMasterWritePlatformService=billMasterWritePlatformService;
	            this.fromApiJsonHelper=fromJsonHelper;
	            this.billingMasterApiResourse=billingMasterApiResourse;
	            this.billWritePlatformService=billWritePlatformService;
	    }


           @Transactional
		@Override
		public void runSheduledJobs() {
         
        	   
        	   List<ScheduleJobData> sheduleDatas=this.sheduleJobReadPlatformService.retrieveSheduleJobDetails();
              
        	   for(ScheduleJobData scheduleJobData : sheduleDatas){
        		
        		   List<Long> clientIds=this.sheduleJobReadPlatformService.getClientIds(scheduleJobData.getQuery());
            	 
            		   //Get the Client Ids
                	   for(Long clientId : clientIds){
                		   try{

                			   if(scheduleJobData.getProcessType().equalsIgnoreCase("Invoice")){
            		            this.invoiceClient.invoicingSingleClient(clientId, new LocalDate());
                			   }else if(scheduleJobData.getProcessType().equalsIgnoreCase("Statement")){
                			       JSONObject jsonobject = new JSONObject();
                			     
                			       LocalDate date=new LocalDate();
                			        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd MMMM yyyy");
                			        String formattedDate = formatter.print(date);
                			      //  System.out.println(formattedDate);
                			        
                			       jsonobject.put("dueDate",formattedDate.toString());
                			       jsonobject.put("locale", "en");
                	               jsonobject.put("dateFormat","dd MMMM YYYY");	
                		           jsonobject.put("message","Statment");
                		           final JsonElement parsedCommand = this.fromApiJsonHelper.parse(jsonobject.toString());
                		             
                		           final JsonCommand command = JsonCommand.from(jsonobject.toString(),parsedCommand,
                		        		   this.fromApiJsonHelper, "BILLMASTER",clientId,null,null,clientId,
                							null,null,null,null,null,null);
                		           final String result=this.billingMasterApiResourse.retrieveBillingProducts(clientId,jsonobject.toString());
                		             JSONObject object=new JSONObject(result);
                		             long billid=object.getLong("resourceId");
                		           //  this.billWritePlatformService.ireportPdf(billid);
                		             
                		             
                		             
                		             
                		           
                		           
                				   
                			   }
                			   
                		   }
                			   
                			   catch (Exception dve) {
                					 handleCodeDataIntegrityIssues(null, dve);
                					//return  CommandProcessingResult.empty();
                				}
                        	   
                          
                	   
        		 }
            	//   ScheduleJobs scheduleJob=this.scheduledJobRepository.findOne(scheduleJobData.getId()); 
            	      //  scheduleJob.setStatus('Y');
            	       // this.scheduledJobRepository.saveAndFlush(scheduleJob);
            	   
        	   }   
        	 
          
           
		}


		private void handleCodeDataIntegrityIssues(Object object,
				Exception dve) {
			// TODO Auto-generated method stub
			
		}
		}

	


