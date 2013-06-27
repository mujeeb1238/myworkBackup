package org.mifosplatform.billing.inventory.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.mifosplatform.billing.inventory.domain.InventoryGrn;
import org.mifosplatform.billing.inventory.domain.InventoryGrnRepository;
import org.mifosplatform.billing.inventory.domain.InventoryItemDetails;
import org.mifosplatform.billing.inventory.domain.InventoryItemDetailsAllocation;
import org.mifosplatform.billing.inventory.domain.InventoryItemDetailsAllocationRepository;
import org.mifosplatform.billing.inventory.domain.InventoryItemDetailsRepository;
import org.mifosplatform.billing.inventory.domain.ItemDetailsRepository;
import org.mifosplatform.billing.inventory.exception.AbstractInventoryItemDetailsExist;
import org.mifosplatform.billing.inventory.exception.InventoryItemDetailsExist;
import org.mifosplatform.billing.inventory.serialization.InventoryItemAllocationCommandFromApiJsonDeserializer;
import org.mifosplatform.billing.inventory.serialization.InventoryItemCommandFromApiJsonDeserializer;
import org.mifosplatform.billing.onetimesale.domain.OneTimeSale;
import org.mifosplatform.billing.onetimesale.domain.OneTimeSaleRepository;
import org.mifosplatform.billing.onetimesale.service.OneTimeSaleReadPlatformService;
import org.mifosplatform.billing.transactionhistory.service.TransactionHistoryWritePlatformService;
import org.mifosplatform.billing.uploadstatus.domain.UploadStatus;
import org.mifosplatform.billing.uploadstatus.domain.UploadStatusRepository;
import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResultBuilder;
import org.mifosplatform.infrastructure.core.exception.PlatformDataIntegrityException;
import org.mifosplatform.infrastructure.core.serialization.FromJsonHelper;
import org.mifosplatform.infrastructure.security.service.PlatformSecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class InventoryItemDetailsWritePlatformServiceImp implements InventoryItemDetailsWritePlatformService{
	
	
	private PlatformSecurityContext context;
	private InventoryItemDetailsRepository inventoryItemDetailsRepository;
	private InventoryGrnRepository inventoryGrnRepository;
	private ItemDetailsRepository itemDetailsRepository;
	private FromJsonHelper fromJsonHelper;
	private UploadStatusRepository uploadStatusRepository;
	private TransactionHistoryWritePlatformService transactionHistoryWritePlatformService;
	
	
	private InventoryItemCommandFromApiJsonDeserializer inventoryItemCommandFromApiJsonDeserializer;
	private InventoryItemAllocationCommandFromApiJsonDeserializer inventoryItemAllocationCommandFromApiJsonDeserializer;
	private InventoryItemDetailsAllocationRepository inventoryItemDetailsAllocationRepository; 
	private InventoryItemDetailsReadPlatformService inventoryItemDetailsReadPlatformService;
	//private OneTimeSaleReadPlatformServiceImpl oneTimeSaleReadPlatformServiceImpl;
	private OneTimeSaleRepository oneTimeSaleRepository;
	
	
	@Autowired
	public InventoryItemDetailsWritePlatformServiceImp(final InventoryItemDetailsReadPlatformService inventoryItemDetailsReadPlatformService, final PlatformSecurityContext context, final InventoryGrnRepository inventoryitemRopository, final ItemDetailsRepository itemDetailsRepository, final InventoryItemCommandFromApiJsonDeserializer inventoryItemCommandFromApiJsonDeserializer, final InventoryItemAllocationCommandFromApiJsonDeserializer inventoryItemAllocationCommandFromApiJsonDeserializer, final InventoryItemDetailsAllocationRepository inventoryItemDetailsAllocationRepository,final OneTimeSaleReadPlatformService oneTimeSaleReadPlatformService,OneTimeSaleRepository oneTimeSaleRepository,InventoryItemDetailsRepository inventoryItemDetailsRepository, final FromJsonHelper fromJsonHelper,UploadStatusRepository uploadStatusRepository, final TransactionHistoryWritePlatformService transactionHistoryWritePlatformService) {

		this.inventoryItemDetailsReadPlatformService = inventoryItemDetailsReadPlatformService;
		this.context=context;
		this.inventoryItemDetailsRepository=inventoryItemDetailsRepository;
		this.inventoryGrnRepository=inventoryitemRopository;
		this.itemDetailsRepository = itemDetailsRepository;
		this.inventoryItemCommandFromApiJsonDeserializer = inventoryItemCommandFromApiJsonDeserializer;
		this.inventoryItemAllocationCommandFromApiJsonDeserializer = inventoryItemAllocationCommandFromApiJsonDeserializer;
		this.inventoryItemDetailsAllocationRepository = inventoryItemDetailsAllocationRepository;
		//this.oneTimeSaleReadPlatformServiceImpl = oneTimeSaleReadPlatformServiceImpl;
		this.oneTimeSaleRepository = oneTimeSaleRepository;
		this.fromJsonHelper=fromJsonHelper;
		this.uploadStatusRepository=uploadStatusRepository;
		this.transactionHistoryWritePlatformService = transactionHistoryWritePlatformService;
	}
	
	private final static Logger logger = (Logger) LoggerFactory.getLogger(InventoryItemDetailsWritePlatformServiceImp.class);
	
	
	
	@SuppressWarnings("unused")
	@Transactional
	@Override
	public CommandProcessingResult addItem(JsonCommand command,String uploadStatus,Long orderId) {
		InventoryItemDetails inventoryItemDetails=null;
		LocalDate currentDate = new LocalDate();
		currentDate.toDate();
		try{
			
			context.authenticatedUser();
			
			this.context.authenticatedUser();
			
			inventoryItemCommandFromApiJsonDeserializer.validateForCreate(command);
			
			inventoryItemDetails = InventoryItemDetails.fromJson(command,fromJsonHelper);
			
			InventoryGrn inventoryGrn = inventoryGrnRepository.findOne(inventoryItemDetails.getGrnId());
			List<Long> itemMasterId = this.inventoryItemDetailsReadPlatformService.retriveSerialNumberForItemMasterId(inventoryItemDetails.getSerialNumber());
			if(itemMasterId.contains(inventoryItemDetails.getItemMasterId())){
				throw new InventoryItemDetailsExist("validation.error.msg.inventory.item.duplicate.serialNumber", "validation.error.msg.inventory.item.duplicate.serialNumber", "validation.error.msg.inventory.item.duplicate.serialNumber","validation.error.msg.inventory.item.duplicate.serialNumber");
			}
			 
			
			if(inventoryGrn != null){
				inventoryItemDetails.setOfficeId(inventoryGrn.getOfficeId());
				if(inventoryGrn.getReceivedQuantity() <= inventoryGrn.getOrderdQuantity()){
					inventoryGrn.setReceivedQuantity(inventoryGrn.getReceivedQuantity()+1);
					this.inventoryGrnRepository.save(inventoryGrn);
				}else{
					throw new InventoryItemDetailsExist("received.quantity.is.nill.hence.your.item.details.will.not.be.saved","received.quantity.is.nill.hence.your.item.details.will.not.be.saved","received.quantity.is.nill.hence.your.item.details.will.not.be.saved","received.quantity.is.nill.hence.your.item.details.will.not.be.saved");
				}
			}else{
				throw new InventoryItemDetailsExist("grnid.does.not.exist", "grnid.does.not.exist","grnid.does.not.exist","grnid.does.not.exist");
			}
			this.inventoryItemDetailsRepository.save(inventoryItemDetails);
			 
			/*++processRecords;
             processStatus="Processed";*/
			
		} catch (DataIntegrityViolationException dve){
			
			handleDataIntegrityIssues(command,dve);
			return new CommandProcessingResult(Long.valueOf(-1));
		}
			
		return new CommandProcessingResultBuilder().withEntityId(inventoryItemDetails.getId()).build();
	}
	
	
	
		private void handleDataIntegrityIssues(final JsonCommand element, final DataIntegrityViolationException dve) {

	         Throwable realCause = dve.getMostSpecificCause();
	        if (realCause.getMessage().contains("serial_no_constraint")){
	        	throw new PlatformDataIntegrityException("validation.error.msg.inventory.item.duplicate.serialNumber", "validation.error.msg.inventory.item.duplicate.serialNumber", "validation.error.msg.inventory.item.duplicate.serialNumber","");
	        	
	        }


	        logger.error(dve.getMessage(), dve);   	
	}




		@Override
		public CommandProcessingResult allocateHardware(JsonCommand command) {
			InventoryItemDetailsAllocation inventoryItemDetailsAllocation=null;
			InventoryItemDetails inventoryItemDetails = null;
			try{
				context.authenticatedUser();
				
				this.context.authenticatedUser();
				inventoryItemAllocationCommandFromApiJsonDeserializer.validateForCreate(command.json());
				
				/*
				 * data comming from the client side is stored in inventoryItemAllocation
				 * */
				inventoryItemDetailsAllocation = InventoryItemDetailsAllocation.fromJson(command);
				
				/*
				 * trying to get Data(Id) from  b_item_detail where b_item_detail.serial_no=?";
				 */
				inventoryItemDetails = inventoryItemDetailsReadPlatformService.retriveInventoryItemDetail(inventoryItemDetailsAllocation.getSerialNumber(),inventoryItemDetailsAllocation.getItemMasterId());
				
				/**
				 * getting data from item_detail table based on item_master_id
				 */
				inventoryItemDetails = inventoryItemDetailsRepository.findOne(inventoryItemDetails.getItemMasterId());
				inventoryItemDetails.setItemMasterId(inventoryItemDetailsAllocation.getItemMasterId());
				inventoryItemDetails.setClientId(inventoryItemDetailsAllocation.getClientId());
				
				//InventoryItemDetailsAllocation serialNumbers = inventoryItemDetailsAllocationRepository.findOne(inventoryItemDetailsAllocation.getItemMasterId());
				
				
				this.inventoryItemDetailsRepository.save(inventoryItemDetails);
				this.inventoryItemDetailsAllocationRepository.save(inventoryItemDetailsAllocation);
				//this.inventoryItemDetailsRepository.save(inventoryItemDetails);
				
				//OneTimeSaleData otsd = this.oneTimeSaleReadPlatformServiceImpl.retrieveSingleOneTimeSaleDetails(inventoryItemDetailsAllocation.getOrderId());
				
				OneTimeSale ots = this.oneTimeSaleRepository.findOne(inventoryItemDetailsAllocation.getOrderId());
				ots.setHardwareAllocated("ALLOCATED");
				this.oneTimeSaleRepository.save(ots);
				this.transactionHistoryWritePlatformService.saveTransactionHistory(+ots.getClientId(), "HardwareAllocation", inventoryItemDetailsAllocation.getAllocationDate(),"ClientId:"+ots.getClientId(),"Units:"+ots.getUnits(),"ChargeCode:"+ots.getChargeCode(),"Quantity:"+ots.getQuantity(),"ItemId:"+ots.getItemId(),"HardwareAllocated:"+ots.getHardwareAllocated(),"Id:"+ots.getId(),"UnitPrice:"+ots.getUnitPrice(),"TotalPrice:"+ots.getTotalPrice(),"SaleDate:"+ots.getSaleDate(),"SerialNumber:"+inventoryItemDetails.getSerialNumber());						

			}catch(DataIntegrityViolationException dve){
				handleDataIntegrityIssues(command, dve); 
				return new CommandProcessingResult(Long.valueOf(-1));
			}
			return new CommandProcessingResultBuilder().withCommandId(1L).withEntityId(inventoryItemDetailsAllocation.getId()).build();
			/*command is has to be changed to command.commandId() in the above code*/
		}
		


		
		/*private void handleDataIntegrityIssues(JsonElement element,DataIntegrityViolationException dve) {
			 Throwable realCause = dve.getMostSpecificCause();
		        if (realCause.getMessage().contains("serial_no_constraint")){
		        	throw new PlatformDataIntegrityException("validation.error.msg.inventory.item.duplicate.serialNumber", "Item Details with SerialNumber" + element.stringValueOfParameterNamed("serialNumber")+ " already exists", "SerialNumber", element.stringValueOfParameterNamed("serialNumber"));
		        }


		        logger.error(dve.getMessage(), dve);
			
		}*/

		
		public void genarateException(String uploadStatus,Long orderId,Long processRecords){
			String processStatus="New Unprocessed";
			String errormessage="item details already exist";
			LocalDate currentDate = new LocalDate();
			currentDate.toDate();
			if(uploadStatus.equalsIgnoreCase("UPLOADSTATUS")){
				UploadStatus uploadStatusObject = this.uploadStatusRepository.findOne(orderId);
				uploadStatusObject.update(currentDate,processStatus,processRecords,null,errormessage);
				this.uploadStatusRepository.save(uploadStatusObject);
			}
			
			 throw new PlatformDataIntegrityException("received.quantity.is.nill.hence.your.item.details.will.not.be.saved","","");
		}
}



