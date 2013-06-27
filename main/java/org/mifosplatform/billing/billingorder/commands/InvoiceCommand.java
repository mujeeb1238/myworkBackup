package org.mifosplatform.billing.billingorder.commands;

import java.math.BigDecimal;
import java.util.Date;

public class InvoiceCommand {

	private final Long clientId;
	private final Date invoiceDate;
	private final BigDecimal invoiceAmount;
	private final BigDecimal netChargeAmount;
	private final BigDecimal taxAmount;
	private final String invoiceStatus;
	private final Long createdBy;
	private final Date createdDate;
	private final Long lastModifiedId;
	private final Date lastModifiedDate;

	public InvoiceCommand(final Long clientId, final Date invoiceDate,
			final BigDecimal invoiceAmount, final BigDecimal netChargeAmount,
			final BigDecimal taxAmount, final String invoiceStatus,
			final Long createdBy, final Date createdDate,
			final Long lastModifiedId, final Date lastModifiedDate) {

		this.clientId = clientId;
		this.invoiceDate = invoiceDate;
		this.invoiceAmount = invoiceAmount;
		this.netChargeAmount = netChargeAmount;
		this.taxAmount = taxAmount;
		this.invoiceStatus = invoiceStatus;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.lastModifiedId = lastModifiedId;
		this.lastModifiedDate = lastModifiedDate;

	}

	public Long getClientId() {
		return clientId;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public BigDecimal getInvoiceAmount() {
		return invoiceAmount;
	}

	public BigDecimal getNetChargeAmount() {
		return netChargeAmount;
	}

	public BigDecimal getTaxAmount() {
		return taxAmount;
	}

	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public Long getLastModifiedId() {
		return lastModifiedId;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

}
