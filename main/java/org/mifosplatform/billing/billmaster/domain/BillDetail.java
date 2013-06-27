package org.mifosplatform.billing.billmaster.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "b_bill_details")
public class BillDetail {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "bill_id")
	private Long billId;

	@Column(name ="transaction_id")
	private  Long transactionId;

	@Column(name="Transaction_date")
	private Date transactionDate;

	@Column(name = "Transaction_type")
	private String transactionType;

	@Column(name = "Amount")
	private BigDecimal amount;

	protected BillDetail() {

	}

	public BillDetail(final Long billId,final Long transactionId ,final Date transactionDate, final String transactionType,
			final BigDecimal amount) {

		this.billId = billId;
		this.transactionId = transactionId;
		this.transactionDate = transactionDate;
		this.transactionType = transactionType;
		this.amount = amount;

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBillId() {
		return billId;
	}

	public void setBillId(Long billId) {
		this.billId = billId;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}


}
