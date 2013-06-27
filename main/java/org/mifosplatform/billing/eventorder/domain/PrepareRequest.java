package org.mifosplatform.billing.eventorder.domain;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.mifosplatform.infrastructure.core.domain.AbstractAuditableCustom;
import org.mifosplatform.useradministration.domain.AppUser;

@Entity
@Table(name = "b_prepare_request")
public class PrepareRequest extends AbstractAuditableCustom<AppUser, Long>{

	




	@Column(name = "client_id")
	private Long clientId;

	@Column(name = "order_id")
	private Long orderId;

	@Column(name = "status")
	private String status;

	@Column(name = "request_type")
	private String requestType;

	@Column(name = "gen_request")
	private String generateRequest;

	@Column(name = "is_provisioning")
	private char isProvisioning;
	
	
	 public  PrepareRequest() {
		// TODO Auto-generated constructor stub
	}


	public PrepareRequest(Long clientId, Long orderId, String requstStatus,	String requestType, char isProvisioning, String generateRequest) {
		this.clientId=clientId;
		this.orderId=orderId;
		this.status=requstStatus;
		this.isProvisioning=requestType.equalsIgnoreCase("NONE")?'Y':'N';
		this.requestType=requestType;
		this.generateRequest=generateRequest;
              
	}



	public Long getClientId() {
		return clientId;
	}


	public Long getOrderId() {
		return orderId;
	}


	public String getStatus() {
		return status;
	}


	public String getRequestType() {
		return requestType;
	}


	public String getGenerateRequest() {
		return generateRequest;
	}


	public char getIsProvisioning() {
		return isProvisioning;
	}


	public void updateProvisioning() {
		this.isProvisioning='Y';
		
	}

			
	}
 
	
	
