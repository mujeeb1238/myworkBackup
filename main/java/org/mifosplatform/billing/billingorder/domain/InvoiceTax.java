package org.mifosplatform.billing.billingorder.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "b_charge_tax")
public class InvoiceTax {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "charge_id")
	private Long invoiceChargeId;

	@Column(name = "invoice_id")
	private Long invoiceId;

	@Column(name = "tax_code")
	private String taxCode;

	@Column(name = "tax_value")
	private BigDecimal taxValue;

	@Column(name = "tax_percentage")
	private BigDecimal taxPercentage;

	@Column(name = "tax_amount")
	private BigDecimal taxAmount;
	
	@Column(name = "bill_id")
	private Long billId;

	private InvoiceTax() {

	}

	public InvoiceTax(final Long invoiceChargeId, final Long invoiceId,
			final String taxCode, final BigDecimal taxValue,
			final BigDecimal taxPercentage, final BigDecimal taxAmount) {

		this.invoiceChargeId = invoiceChargeId;
		this.invoiceId = invoiceId;
		this.taxCode = taxCode;
		this.taxValue = taxValue;
		this.taxPercentage = taxPercentage;
		this.taxAmount = taxAmount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getInvoiceChargeId() {
		return invoiceChargeId;
	}

	public void setInvoiceChargeId(Long invoiceChargeId) {
		this.invoiceChargeId = invoiceChargeId;
	}

	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getTaxCode() {
		return taxCode;
	}

	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}

	public BigDecimal getTaxValue() {
		return taxValue;
	}

	public void setTaxValue(BigDecimal taxValue) {
		this.taxValue = taxValue;
	}

	public BigDecimal getTaxPercentage() {
		return taxPercentage;
	}

	public void setTaxPercentage(BigDecimal taxPercentage) {
		this.taxPercentage = taxPercentage;
	}

	public BigDecimal getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}

	public void updateBillId(Long billId) {
		this.billId=billId;
		
	}

}
