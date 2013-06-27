package org.mifosplatform.billing.processrequest.domain;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "b_process_request")
public class ProcessRequest   {

	@Id
	@GeneratedValue
	@Column(name="id")
	private Long id;
	
	@Column(name = "client_id")
	private Long clientId;

	@Column(name = "order_id")
	private Long orderId;

	@Column(name = "hardware_id")
	private String hardwareId;

	@Column(name = "service_class")
	private String serviceClass;

	@Column(name = "sent_date")
	private Date sentDate;

	@Column(name = "received_date")
	private Date receivedDate;
	
	@Column(name = "start_date")
	private Date startDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_date")
	private Date endDate;

	@Column(name = "provisioing_system")
	private String provisioingSystem;

	@Column(name = "is_deleted")
	private char isDeleted;



	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "processRequest", orphanRemoval = true)
	private List<ProcessRequestDetails> processRequestDetails = new ArrayList<ProcessRequestDetails>();

	

	 public ProcessRequest() {
		// TODO Auto-generated constructor stub
			
	}



	public ProcessRequest(Long clientId, Long orderId, String serviceClass,String hardwareId, Date startDate,
			Date endDate, Date sentDate,Date recievedDate, String provisioningSystem, char isDeleted) {
            this.clientId=clientId;
            this.orderId=orderId;
            this.serviceClass=serviceClass;
            this.hardwareId=hardwareId;
            this.startDate=startDate;
            this.endDate=endDate;
            this.sentDate=sentDate;
            this.receivedDate=recievedDate;
            this.provisioingSystem=provisioningSystem;
            this.isDeleted=isDeleted;
	
	
	}



	public void add(ProcessRequestDetails processRequestDetails) {
	     processRequestDetails.update(this);
	     this.processRequestDetails.add(processRequestDetails);
		
		
	}
 
	

}
