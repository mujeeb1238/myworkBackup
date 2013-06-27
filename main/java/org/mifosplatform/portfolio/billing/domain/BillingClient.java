package org.mifosplatform.portfolio.billing.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name ="client_balance")
public class BillingClient extends AbstractPersistable<Long>
 {
	@Column(name = "client_id")
	private Long client_id;

	public Long getClient_id() {
		return client_id;
	}
	public void setClient_id(Long client_id) {
		this.client_id = client_id;
	}
	public Long getBalance_amount() {
		return balance_amount;
	}
	public void setBalance_amount(Long balance_amount) {
		this.balance_amount = balance_amount;
	}
	@Column(name = "balance_amount")
	private Long balance_amount;
	
	public BillingClient()
	{
		
	}
	public BillingClient(final Long client_id,final Long balance_amount )
	{
		this.client_id=client_id;
		this.balance_amount=balance_amount;
		
	}

	
	}

