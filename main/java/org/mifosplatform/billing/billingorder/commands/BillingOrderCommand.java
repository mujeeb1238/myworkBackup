package org.mifosplatform.billing.billingorder.commands;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.mifosplatform.billing.billingorder.domain.InvoiceTax;

public class BillingOrderCommand {

	private final Long clientOrderId;
	private final Long orderPriceId;
	private final Long clientId;
	private final Date startDate;
	private final Date nextBillableDate;
	private final Date endDate;
	private final String billingFrequency;
	private final String chargeCode;
	private final String chargeType;
	private final Integer chargeDuration;
	private final String durationType;
	private final Date invoiceTillDate;
	private final BigDecimal price;
	private final String billingAlign;
	private final List<InvoiceTax> listOfTax;
	private final Date billStartDate;
	private  Date billEndDate;



	public BillingOrderCommand(Long clientOrderId, Long oderPriceId,
			Long clientId, Date startDate, Date nextBillableDate, Date endDate,
			String billingFrequency, String chargeCode, String chargeType,
			Integer chargeDuration,String durationType, Date invoiceTillDate, BigDecimal price,
			String billingAlign,final List<InvoiceTax> listOfTax,final Date billStartDate,final Date billEndDate) {
		this.clientOrderId = clientOrderId;
		this.orderPriceId = oderPriceId;
		this.clientId = clientId;
		this.startDate = startDate;
		this.nextBillableDate = nextBillableDate;
		this.endDate = endDate;
		this.billingFrequency = billingFrequency;
		this.chargeCode = chargeCode;
		this.chargeType = chargeType;
		this.chargeDuration = chargeDuration;
		this.durationType = durationType;
		this.invoiceTillDate = invoiceTillDate;
		this.price = price;
		this.billingAlign = billingAlign;
		this.billStartDate = billStartDate;
		this.billEndDate = billEndDate;
		this.listOfTax =listOfTax;
	}

	public Long getClientId() {
		return clientId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getNextBillableDate() {
		return nextBillableDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public String getBillingFrequency() {
		return billingFrequency;
	}

	public String getChargeCode() {
		return chargeCode;
	}

	public String getChargeType() {
		return chargeType;
	}

	public Integer getChargeDuration() {
		return chargeDuration;
	}

	public String getDurationType() {
		return durationType;
	}

	public Date getInvoiceTillDate() {
		return invoiceTillDate;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public String getBillingAlign() {
		return billingAlign;
	}

	public Long getClientOrderId() {
		return clientOrderId;
	}

	public Long getOrderPriceId() {
		return orderPriceId;
	}

	public List<InvoiceTax> getListOfTax() {
		return listOfTax;
	}

	public Date getBillStartDate() {
		return billStartDate;
	}

	public Date getBillEndDate() {
		return billEndDate;
	}

	public void setBillEndDate(Date billEndDate) {
		this.billEndDate = billEndDate;
	}
	
	
	
}
