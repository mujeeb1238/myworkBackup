package org.mifosplatform.portfolio.billing.command;

public class BillCommand {

	private Long client_id;

	public Long getClient_id() {
		return client_id;
	}

	public void setClient_id(Long client_id) {
		this.client_id = client_id;
	}
	
     public  BillCommand()
     {
    	 
     }
     public BillCommand(Long client_id)
     {
    	 this.client_id=client_id;
     }
	}


