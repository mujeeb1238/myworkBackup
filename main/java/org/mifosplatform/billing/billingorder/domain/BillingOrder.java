package org.mifosplatform.billing.billingorder.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "b_charge")
public class BillingOrder  extends AbstractPersistable<Long>{

	@Column(name = "client_id")
	private Long clientId;

	@Column(name = "order_id")
	private Long orderId;

	@Column(name = "priceline_id")
	private Long orderlineId;

	@Column(name = "charge_code")
	private String chargeCode;

	@Column(name = "charge_type")
	private String chargeType;

	@Column(name = "discount_code")
	private String discountCode;

	@Column(name = "charge_amount")
	private BigDecimal chargeAmount;

	@Column(name = "discount_amount")
	private BigDecimal discountAmount;

	@Column(name = "netcharge_amount")
	private BigDecimal netChargeAmount;

	@Column(name = "charge_start_date")
	private Date startDate;

	@Column(name = "charge_end_date")
	private Date entDate;

	@Column(name = "invoice_id")
	private Long invoiceId;
	
	@Column(name="bill_id")
	private Long billId;

	public BillingOrder() {
	}

	public BillingOrder(final Long clientId, final Long orderId,
			final Long orderlineId, final String chargeCode,
			final String chargeType, final String discountCode,
			final BigDecimal chargeAmount, final BigDecimal discountAmount,
			final BigDecimal netChargeAmount, final Date startDate,
			final Date entDate, final long l) {

		this.clientId = clientId;
		this.orderId = orderId;
		this.orderlineId = orderlineId;
		this.chargeCode = chargeCode;
		this.chargeType = chargeType;
		this.discountCode = discountCode;
		this.chargeAmount = chargeAmount;
		this.discountAmount = discountAmount;
		this.netChargeAmount = netChargeAmount;
		this.startDate = startDate;
		this.entDate = entDate;
		this.invoiceId = 1l;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getOrderlineId() {
		return orderlineId;
	}

	public void setOrderlineId(Long orderlineId) {
		this.orderlineId = orderlineId;
	}

	public String getChargeCode() {
		return chargeCode;
	}

	public void setChargeCode(String chargeCode) {
		this.chargeCode = chargeCode;
	}

	public String getChargeType() {
		return chargeType;
	}

	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	public String getDiscountCode() {
		return discountCode;
	}

	public void setDiscountCode(String discountCode) {
		this.discountCode = discountCode;
	}

	public BigDecimal getChargeAmount() {
		return chargeAmount;
	}

	public void setChargeAmount(BigDecimal chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}

	public BigDecimal getNetChargeAmount() {
		return netChargeAmount;
	}

	public void setNetChargeAmount(BigDecimal netChargeAmount) {
		this.netChargeAmount = netChargeAmount;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEntDate() {
		return entDate;
	}

	public void setEntDate(Date entDate) {
		this.entDate = entDate;
	}

	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public void updateBillId(Long billId) {
		this.billId=billId;
		
	}

}
