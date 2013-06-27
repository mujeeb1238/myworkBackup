package org.mifosplatform.billing.processrequest.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.mifosplatform.infrastructure.core.domain.AbstractAuditableCustom;
import org.mifosplatform.useradministration.domain.AppUser;

@Entity
@Table(name = "b_process_request_detail")
public class ProcessRequestDetails extends AbstractAuditableCustom<AppUser, Long>  {


    @ManyToOne
    @JoinColumn(name="processrequest_id")
	private ProcessRequest processRequest;

	@Column(name = "orderlinbe_id")
	private Long orderlinId;

	@Column(name = "service_id")
	private Long serviceId;

	@Column(name = "sent_message")
	private String sentMessage;
	
	@Column(name = "receive_message")
	private String receiveMessage;
	
	@Column(name = "is_deleted")
	private char isDeleted;

	public ProcessRequestDetails(){
		
	}
	

	public ProcessRequestDetails(Long orderlinId, Long serviceId, String sentMessage,
			String recievedMessage, char isDeleted) {
         
	          this.orderlinId=orderlinId;
	          this.serviceId=serviceId;
	          this.sentMessage=sentMessage;
	          this.receiveMessage=recievedMessage;
	          this.isDeleted=isDeleted;
	
	}


	public void update(ProcessRequest processRequest) {
      
		this.processRequest=processRequest;
		
	}




}
