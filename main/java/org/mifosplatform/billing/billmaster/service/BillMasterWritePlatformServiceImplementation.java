package org.mifosplatform.billing.billmaster.service;

import java.math.BigDecimal;
import java.util.List;

import org.joda.time.LocalDate;
import org.mifosplatform.billing.adjustment.domain.AdjustmentRepository;
import org.mifosplatform.billing.billingorder.data.BillDetailsData;
import org.mifosplatform.billing.billingorder.domain.BillingOrderRepository;
import org.mifosplatform.billing.billingorder.domain.InvoiceRepository;
import org.mifosplatform.billing.billingorder.domain.InvoiceTaxRepository;
import org.mifosplatform.billing.billingorder.exceptions.BillingOrderNoRecordsFoundException;
import org.mifosplatform.billing.billmaster.domain.BillDetail;
import org.mifosplatform.billing.billmaster.domain.BillDetailRepository;
import org.mifosplatform.billing.billmaster.domain.BillMaster;
import org.mifosplatform.billing.billmaster.domain.BillMasterRepository;
import org.mifosplatform.billing.billmaster.serialize.BillMasterCommandFromApiJsonDeserializer;
import org.mifosplatform.billing.clientbalance.domain.ClientBalanceRepository;
import org.mifosplatform.billing.financialtransaction.data.FinancialTransactionsData;
import org.mifosplatform.billing.payments.domain.PaymentRepository;
import org.mifosplatform.billing.servicemaster.service.ServiceMasterWritePlatformServiceImpl;
import org.mifosplatform.billing.transactionhistory.service.TransactionHistoryWritePlatformService;
import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;
import org.mifosplatform.infrastructure.core.service.TenantAwareRoutingDataSource;
import org.mifosplatform.infrastructure.security.service.PlatformSecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BillMasterWritePlatformServiceImplementation implements
		BillMasterWritePlatformService {
	
	 private final static Logger logger = LoggerFactory.getLogger(ServiceMasterWritePlatformServiceImpl.class);
		private final PlatformSecurityContext context;
	    private final BillMasterCommandFromApiJsonDeserializer fromApiJsonDeserializer;
		private final BillMasterRepository billMasterRepository;
		private final BillDetailRepository billDetailRepository;
		private final PaymentRepository paymentRepository;
		private final AdjustmentRepository adjustmentRepository;
		private final BillingOrderRepository billingOrderRepository;
		private final InvoiceTaxRepository invoiceTaxRepository;
		private final InvoiceRepository invoiceRepository;
		private final ClientBalanceRepository clientBalanceRepository;
		private final JdbcTemplate jdbcTemplate;
		private final TenantAwareRoutingDataSource dataSource;
		private final BillMasterReadPlatformService billMasterReadPlatformService;
		private final BillWritePlatformService billWritePlatformService;
		private final TransactionHistoryWritePlatformService transactionHistoryWritePlatformService;
		//private final OneTimeSaleRepository oneTimeSaleRepository;
	@Autowired
	 public BillMasterWritePlatformServiceImplementation(final PlatformSecurityContext context,final BillMasterCommandFromApiJsonDeserializer fromApiJsonDeserializer,
				final BillMasterRepository billMasterRepository,final BillDetailRepository billDetailRepository,final PaymentRepository paymentRepository,
				final AdjustmentRepository adjustmentRepository,final BillingOrderRepository billingOrderRepository,final InvoiceTaxRepository invoiceTaxRepository,
				final InvoiceRepository invoiceRepository,final ClientBalanceRepository clientBalanceRepository,final TenantAwareRoutingDataSource dataSource,
				final BillMasterReadPlatformService billMasterReadPlatformService,final BillWritePlatformService billWritePlatformService,
				final TransactionHistoryWritePlatformService transactionHistoryWritePlatformService) {

			this.context = context;
			this.billMasterRepository = billMasterRepository;
			this.billDetailRepository = billDetailRepository;
			this.adjustmentRepository = adjustmentRepository;
			this.billingOrderRepository = billingOrderRepository;
			this.invoiceTaxRepository = invoiceTaxRepository;
			this.paymentRepository = paymentRepository;
			this.clientBalanceRepository = clientBalanceRepository;
			this.invoiceRepository=invoiceRepository;
			this.jdbcTemplate = new JdbcTemplate(dataSource);
			this.dataSource = dataSource;
			this.fromApiJsonDeserializer=fromApiJsonDeserializer;
			this.billMasterReadPlatformService=billMasterReadPlatformService;
			this.billWritePlatformService=billWritePlatformService;
			this.transactionHistoryWritePlatformService = transactionHistoryWritePlatformService;
			
	}
	
	@Transactional
	@Override
	public CommandProcessingResult createBillMaster(JsonCommand command,Long clientId) {
		try
		{
		context.authenticatedUser();
		fromApiJsonDeserializer.validateForCreate(command.json());		
		List<FinancialTransactionsData> financialTransactionsDatas = billMasterReadPlatformService.retrieveFinancialData(clientId);
		if (financialTransactionsDatas.size() == 0) {
			String msg = "no Bills to Generate";
			throw new BillingOrderNoRecordsFoundException(msg);
		}
	//	BillMaster billMaster = null;
		BigDecimal previousBal = BigDecimal.ZERO;
		List<BillMaster> billMasters = this.billMasterRepository.findAll();
		for (BillMaster data : billMasters) {
			if (data.getClientId().compareTo(clientId)==0) {
				previousBal = this.billMasterReadPlatformService.retrieveClientBalance(clientId);
			}
		}
		LocalDate billDate = new LocalDate();
		BigDecimal previousBalance = BigDecimal.ZERO;
		BigDecimal chargeAmount = BigDecimal.ZERO;
		BigDecimal adjustmentAmount = BigDecimal.ZERO;
		BigDecimal taxAmount = BigDecimal.ZERO;
		BigDecimal paidAmount = BigDecimal.ZERO;
		BigDecimal dueAmount = BigDecimal.ZERO;
		final LocalDate dueDate = command.localDateValueOfParameterNamed("dueDate");
		final String message = command.stringValueOfParameterNamed("message");
		BillMaster  billMaster = new BillMaster(clientId, clientId,billDate.toDate(), null, null, dueDate.toDate(),
		previousBalance, chargeAmount, adjustmentAmount, taxAmount,paidAmount, dueAmount, null,message);
		billMaster = this.billMasterRepository.save(billMaster);
		
		List<BillDetail> billDetail = billWritePlatformService.createBillDetail(financialTransactionsDatas, billMaster);
		billWritePlatformService.updateBillMaster(billDetail, billMaster,previousBal);
		billWritePlatformService.updateBillId(financialTransactionsDatas,billMaster.getId());
		BillDetailsData billDetails = this.billMasterReadPlatformService.retrievebillDetails(billMaster.getId());
		
		transactionHistoryWritePlatformService.saveTransactionHistory(billMaster.getClientId(), "Statement", billMaster.getBillDate(),"DueAmount:"+billMaster.getDueAmount(),
				"AmountPaid:"+billMaster.getPaidAmount(),"AdjustmentAmount:"+billMaster.getAdjustmentAmount(),"PromotionDescription:"+billMaster.getPromotionDescription(),"BillNumber:"+billMaster.getBillNumber(),"StatementID:"+billMaster.getId());
        this.billWritePlatformService.generatePdf(billDetails,financialTransactionsDatas);
        return new CommandProcessingResult(billMaster.getId());
	}   catch (DataIntegrityViolationException dve) {
		 handleCodeDataIntegrityIssues(command, dve);
		return  CommandProcessingResult.empty();
	}

	
}

	private void handleCodeDataIntegrityIssues(JsonCommand command,
			DataIntegrityViolationException dve) {
		// TODO Auto-generated method stub
		
	}
}