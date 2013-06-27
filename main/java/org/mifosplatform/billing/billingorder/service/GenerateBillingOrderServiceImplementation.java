package org.mifosplatform.billing.billingorder.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.mifosplatform.billing.billingorder.commands.BillingOrderCommand;
import org.mifosplatform.billing.billingorder.commands.InvoiceCommand;
import org.mifosplatform.billing.billingorder.commands.InvoiceTaxCommand;
import org.mifosplatform.billing.billingorder.data.BillingOrderData;
import org.mifosplatform.billing.billingorder.domain.InvoiceTax;
import org.mifosplatform.billing.billingorder.exceptions.BillingOrderNoRecordsFoundException;
import org.mifosplatform.billing.service.DiscountMasterData;
import org.mifosplatform.billing.taxmaster.data.TaxMappingRateData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenerateBillingOrderServiceImplementation implements	GenerateBillingOrderService {

	private final GenerateBill generateBill;
	private final BillingOrderReadPlatformService billingOrderReadPlatformService;
	//private final OrderRepository orderRepository;

	@Autowired
	public GenerateBillingOrderServiceImplementation(GenerateBill generateBill,BillingOrderReadPlatformService billingOrderReadPlatformService) {
		this.generateBill = generateBill;
		this.billingOrderReadPlatformService = billingOrderReadPlatformService;
		//this.orderRepository = orderRepository;
	}

	@Override
	public List<BillingOrderCommand> generatebillingOrder(
			List<BillingOrderData> products) {
		

		
		BillingOrderCommand billingOrderCommand = null;
		List<BillingOrderCommand> billingOrderCommands = new ArrayList<BillingOrderCommand>();

		if (products.size() != 0) {

			for (BillingOrderData billingOrderData : products) {
				// discount master 
				DiscountMasterData discountMasterData = null;
				
				List<DiscountMasterData> discountMasterDatas = billingOrderReadPlatformService.retrieveDiscountOrders(billingOrderData.getClientOrderId(),billingOrderData.getOderPriceId());
				
				if(discountMasterDatas.size()!=0){
					discountMasterData = discountMasterDatas.get(0);	
				}
				

				if(billingOrderData.getOrderStatus() ==3){
					billingOrderCommand=generateBill.getCancelledOrderBill(billingOrderData,discountMasterData);	
					billingOrderCommands.add(billingOrderCommand);
				}
				
				else if (generateBill.isChargeTypeNRC(billingOrderData)) {
						
						System.out.println("---- NRC ---");
							billingOrderCommand = generateBill.getOneTimeBill(billingOrderData,discountMasterData);
							billingOrderCommands.add(billingOrderCommand);

					} else if (generateBill.isChargeTypeRC(billingOrderData)) {

						System.out.println("---- RC ----");

						// monthly
						if (billingOrderData.getDurationType().equalsIgnoreCase("month(s)") ) {
							if (billingOrderData.getBillingAlign().equalsIgnoreCase("N")) {

								billingOrderCommand = generateBill.getMonthyBill(billingOrderData,discountMasterData);
								billingOrderCommands.add(billingOrderCommand);

							} else if (billingOrderData.getBillingAlign().equalsIgnoreCase("Y")) {

								if (billingOrderData.getInvoiceTillDate() == null) {

									billingOrderCommand = generateBill.getProrataMonthlyFirstBill(billingOrderData,discountMasterData);
									billingOrderCommands.add(billingOrderCommand);

								} else if (billingOrderData.getInvoiceTillDate() != null) {

									billingOrderCommand = generateBill.getNextMonthBill(billingOrderData,discountMasterData);
									billingOrderCommands.add(billingOrderCommand);

								}
							}

						// weekly
						} else if (billingOrderData.getDurationType().equalsIgnoreCase("week(s)")) {

							if (billingOrderData.getBillingAlign().equalsIgnoreCase("N")) {
								billingOrderCommand = generateBill.getWeeklyBill(billingOrderData,discountMasterData);
								billingOrderCommands.add(billingOrderCommand);

							} else if (billingOrderData.getBillingAlign().equalsIgnoreCase("Y")) {

								if (billingOrderData.getInvoiceTillDate() == null) {

									billingOrderCommand = generateBill.getProrataWeeklyFirstBill(billingOrderData,discountMasterData);
									billingOrderCommands.add(billingOrderCommand);

								} else if (billingOrderData.getInvoiceTillDate() != null) {

									billingOrderCommand = generateBill.getNextWeeklyBill(billingOrderData,discountMasterData);
									billingOrderCommands.add(billingOrderCommand);
								}
							}

						// daily
						} else if (billingOrderData.getDurationType()
								.equalsIgnoreCase("daily")) {
							
							
							billingOrderCommand = generateBill.getDailyBill(billingOrderData,discountMasterData);
							billingOrderCommands.add(billingOrderCommand);
							
							
						}
					}
				
			}
		} else if (products.size() == 0) {
			throw new BillingOrderNoRecordsFoundException();
		}
		// return billingOrderCommand;
		return billingOrderCommands;
	}

	@Override
	public List<InvoiceTaxCommand> generateInvoiceTax(
			List<TaxMappingRateData> taxMappingRateDatas, BigDecimal price,
			Long clientId) {

		BigDecimal taxPercentage = null;
		String taxCode = null;
		BigDecimal taxAmount = null;
		List<InvoiceTaxCommand> invoiceTaxCommands = new ArrayList<InvoiceTaxCommand>();
		InvoiceTaxCommand invoiceTaxCommand = null;
		if (taxMappingRateDatas != null) {

			for (TaxMappingRateData taxMappingRateData : taxMappingRateDatas) {

				taxPercentage = taxMappingRateData.getRate();
				taxCode = taxMappingRateData.getTaxCode();
				taxAmount = price.multiply(taxPercentage.divide(new BigDecimal(
						100)));

				invoiceTaxCommand = new InvoiceTaxCommand(clientId, null, null,
						taxCode, null, taxPercentage, taxAmount);
				invoiceTaxCommands.add(invoiceTaxCommand);
			}

		}
		return invoiceTaxCommands;

	}
	@Override
	public InvoiceCommand generateInvoice(
			List<BillingOrderCommand> billingOrderCommands) {
		BigDecimal totalChargeAmountForServices = BigDecimal.ZERO;
		BigDecimal totalTaxAmountForServices = BigDecimal.ZERO;
		BigDecimal invoiceAmount = BigDecimal.ZERO;
		LocalDate invoiceDate = new LocalDate();
		for (BillingOrderCommand billingOrderCommand : billingOrderCommands) {
			totalChargeAmountForServices = billingOrderCommand.getPrice().add(
					totalChargeAmountForServices);
			List<InvoiceTax> listOfTaxes = billingOrderCommand.getListOfTax();
			BigDecimal netTaxForService = BigDecimal.ZERO;
			for (InvoiceTax invoiceTax : listOfTaxes) {
				netTaxForService = invoiceTax.getTaxAmount().add(
						netTaxForService);
			}
			totalTaxAmountForServices = totalTaxAmountForServices
					.add(netTaxForService);
		}
		invoiceAmount = totalChargeAmountForServices
				.add(totalTaxAmountForServices);

		return new InvoiceCommand(billingOrderCommands.get(0).getClientId(),
				invoiceDate.toDate(), invoiceAmount,
				totalChargeAmountForServices, totalTaxAmountForServices,
				"active", null, null, null, null);
	}
}
