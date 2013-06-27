package org.mifosplatform.billing.payterms.data;

public class PaytermData {

	private Long id;
	private String paytermtype;
public PaytermData(final Long id,final String paytermtype)
{
this.id=id;
this.paytermtype=paytermtype;
}
public Long getId() {
	return id;
}
public String getPaytermtype() {
	return paytermtype;
}


}
