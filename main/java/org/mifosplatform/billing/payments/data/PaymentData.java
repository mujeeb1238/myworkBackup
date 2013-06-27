package org.mifosplatform.billing.payments.data;

import java.util.Collection;

import org.joda.time.LocalDate;
import org.mifosplatform.billing.paymode.data.McodeData;

public class PaymentData {
    private Collection<McodeData> data;
	private LocalDate paymentDate;
	public PaymentData(Collection<McodeData> data){
		this.data= data;
		this.paymentDate=new LocalDate();
		
		
	}
}
