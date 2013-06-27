package org.mifosplatform.billing.billingorder.service;

import java.math.BigDecimal;
import java.util.List;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.mifosplatform.billing.adjustment.service.AdjustmentReadPlatformService;
import org.mifosplatform.billing.billingorder.commands.BillingOrderCommand;
import org.mifosplatform.billing.billingorder.commands.InvoiceCommand;
import org.mifosplatform.billing.billingorder.data.BillingOrderData;
import org.mifosplatform.billing.billingorder.data.GenerateInvoiceData;
import org.mifosplatform.billing.billingorder.data.ProcessDate;
import org.mifosplatform.billing.billingorder.domain.BillingOrder;
import org.mifosplatform.billing.billingorder.domain.Invoice;
import org.mifosplatform.billing.billingorder.exceptions.BillingOrderNoRecordsFoundException;
import org.mifosplatform.billing.billingorder.serialization.BillingOrderCommandFromApiJsonDeserializer;
import org.mifosplatform.billing.clientbalance.data.ClientBalanceData;
import org.mifosplatform.billing.clientbalance.service.ClientBalanceReadPlatformService;
import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResultBuilder;
import org.mifosplatform.infrastructure.security.service.PlatformSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class InvoiceClient {

	private BillingOrderReadPlatformService billingOrderReadPlatformService;
	private GenerateBillingOrderService generateBillingOrderService;
	private BillingOrderWritePlatformService billingOrderWritePlatformService;
	private AdjustmentReadPlatformService adjustmentReadPlatformService;
	private ClientBalanceReadPlatformService clientBalanceReadPlatformService;
	private PlatformSecurityContext context;
	private BillingOrderCommandFromApiJsonDeserializer apiJsonDeserializer;
	
	
	
	
	@Autowired
	InvoiceClient(BillingOrderReadPlatformService billingOrderReadPlatformService,GenerateBillingOrderService generateBillingOrderService,
			BillingOrderWritePlatformService billingOrderWritePlatformService,AdjustmentReadPlatformService adjustmentReadPlatformService,
			ClientBalanceReadPlatformService clientBalanceReadPlatformService,
			final PlatformSecurityContext context, final BillingOrderCommandFromApiJsonDeserializer apiJsonDeserializer) {
		this.billingOrderReadPlatformService = billingOrderReadPlatformService;
		this.generateBillingOrderService = generateBillingOrderService;
		this.billingOrderWritePlatformService = billingOrderWritePlatformService;
		this.adjustmentReadPlatformService = adjustmentReadPlatformService;
		this.context = context;
		this.clientBalanceReadPlatformService = clientBalanceReadPlatformService;
		this.apiJsonDeserializer = apiJsonDeserializer;
		//this.invoiceClient = invoiceClient;
	}
	public BigDecimal invoicingSingleClient(Long clientId, LocalDate processDate) {

		// Get list of qualified orders
		//List<Long> orderIds = billingOrderReadPlatformService.retrieveOrderIds(clientId, processDate);
		List<Long> orderIds = billingOrderReadPlatformService.retrieveOrderIds(clientId, processDate);
		if (orderIds.size() == 0) {
			throw new BillingOrderNoRecordsFoundException();
		}
		BigDecimal invoiceAmount=BigDecimal.ZERO;
/*		for (BillingOrderData billingOrderId  : billingOrderIds) {
			LocalDate billStartDate = new LocalDate(billingOrderId.getStartDate());
			int qualifiedNumberOfTimes = getQualifiedNumberOfTimes(billStartDate, processDate, billingOrderId.getDurationType());
			int noOfTimes = 0;
			while(noOfTimes!=qualifiedNumberOfTimes){
	            BigDecimal invoicePrice=invoiceServices(billingOrderId.getOrderId(),clientId,processDate);
	               invoiceAmount=invoiceAmount.add(invoicePrice);
				noOfTimes++;
			}
		}*/
		
		for (Long orderId  : orderIds) {
			
	            BigDecimal invoicePrice=invoiceServices(orderId,clientId,processDate);
	            invoiceAmount=invoiceAmount.add(invoicePrice);
			
		}
		return invoiceAmount;
	}
	
	public BigDecimal invoiceServices(Long orderId,Long clientId,LocalDate processDate){
		
			
			// Charges
			List<BillingOrderData> products = this.billingOrderReadPlatformService.retrieveBillingOrderData(clientId, processDate,orderId);

			List<BillingOrderCommand> billingOrderCommands = this.generateBillingOrderService.generatebillingOrder(products);
			List<BillingOrder> listOfBillingOrders = billingOrderWritePlatformService.createBillingProduct(billingOrderCommands);
			
			System.out.println("---------------------"+billingOrderCommands.get(0).getNextBillableDate());

			// Invoice
			InvoiceCommand invoiceCommand = this.generateBillingOrderService.generateInvoice(billingOrderCommands);
			
			// Client Balance
			// List<ClientBalanceData> clientBalancesDatas = adjustmentReadPlatformService.retrieveAllAdjustments(clientId);
			List<ClientBalanceData> clientBalancesDatas = clientBalanceReadPlatformService.retrieveAllClientBalances(clientId);
			
			Invoice invoice = billingOrderWritePlatformService.createInvoice(invoiceCommand, clientBalancesDatas);

			// Update invoice-tax
			billingOrderWritePlatformService.updateInvoiceTax(invoice, billingOrderCommands, listOfBillingOrders);

			// Update charge
			billingOrderWritePlatformService.updateInvoiceCharge(invoice,
					listOfBillingOrders);
			
			 // Update order-price
			//billingOrderWritePlatformService.updateBillingOrder(billingOrderCommands);
			billingOrderWritePlatformService.updateOrderPrice(billingOrderCommands);
			
			System.out.println("---------------------"+billingOrderCommands.get(0).getNextBillableDate());
			// Update orders
			CommandProcessingResult entityIdentifier = billingOrderWritePlatformService.updateBillingOrder(billingOrderCommands);
               if(invoiceCommand.getInvoiceAmount() == null){
            	   return BigDecimal.ZERO;
               }
		return invoiceCommand.getInvoiceAmount();
	}
	
	public Integer getQualifiedNumberOfTimes(LocalDate billStartDate , LocalDate processDate,String durationType ){
		int qualifiedNumberOfTimes = 0;
		int qualifiedBillingDays = Days.daysBetween(billStartDate, processDate).getDays()+1;
		
		if(durationType.equals("Week(s)")){
			if(qualifiedBillingDays<=7){
				qualifiedNumberOfTimes = 1;
			}else{
				qualifiedNumberOfTimes = (qualifiedBillingDays / 7)+1;
			}
			
		}else if(durationType.equals("Month(s)")){
			if(qualifiedBillingDays<=30){
				qualifiedNumberOfTimes = 1;
			}else{
				qualifiedNumberOfTimes = (qualifiedBillingDays / 30)+1;
			}
		}
		return qualifiedNumberOfTimes;
	}
	
	
	
	public CommandProcessingResult createInvoiceBill(JsonCommand command) {
		try {
			context.authenticatedUser();
			this.apiJsonDeserializer.validateForCreate(command.json());
			LocalDate processDate = ProcessDate.fromJson(command);
	        List<GenerateInvoiceData> invoiceDatas = this.billingOrderReadPlatformService.retrieveClientsWithOrders(processDate);
			/*if (invoiceDatas == null || invoiceDatas.size() == 0) {
				throw new BillingOrderNoRecordsFoundException();
			} else {
				for (GenerateInvoiceData invoiceData : invoiceDatas) {
					this.invoicingSingleClient(invoiceData.getClientId(), processDate);
				}

			}*/
			
				BigDecimal invoiceAmount=this.invoicingSingleClient(command.entityId(), processDate);
	
			
			return new CommandProcessingResultBuilder().withCommandId(command.commandId()).withEntityId(1l).build();

		} catch (DataIntegrityViolationException dve) {
			return CommandProcessingResult.empty();
		}

	}

}
