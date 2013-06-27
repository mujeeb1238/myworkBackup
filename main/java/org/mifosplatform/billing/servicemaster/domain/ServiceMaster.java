package org.mifosplatform.billing.servicemaster.domain;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang.StringUtils;
import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "b_service", uniqueConstraints = @UniqueConstraint(name = "service_code_key", columnNames = { "service_code" }))
public class ServiceMaster extends AbstractPersistable<Long> {

	@Column(name = "service_code", nullable = false, length = 20)
	private String serviceCode;

	@Column(name = "service_description", nullable = false, length = 100)
	private String serviceDescription;

	@Column(name = "type", nullable = false, length = 100)
	private Long serviceType;

	@Column(name = "is_deleted")
	private String isDeleted="n";


public ServiceMaster()
{}


public static ServiceMaster fromJson(final JsonCommand command) {
    final String serviceCode = command.stringValueOfParameterNamed("serviceCode");
    final String serviceDescription = command.stringValueOfParameterNamed("serviceDescription");
    final Long serviceType = command.longValueOfParameterNamed("serviceType");
    return new ServiceMaster(serviceCode,serviceDescription,serviceType);
}

	public ServiceMaster(String serviceCode, String serviceDescription,
			Long serviceType) {
		this.serviceCode = serviceCode;
		this.serviceDescription = serviceDescription;
		this.serviceType = serviceType;
	}

	public static ServiceMaster create(String serviceCode,
			String serviceDescription, Long serviceType) {
		return new ServiceMaster(serviceCode, serviceDescription, serviceType);
					}

	public String getServiceCode() {
		return this.serviceCode;
	}

	public String getServiceDescription() {
		return this.serviceDescription;
	}

	public Long getServiceType() {
		return this.serviceType;
	}


	public String getIsDeleted() {
		return this.isDeleted;
	}


	public  Map<String, Object> update(JsonCommand command) {
		
		  final Map<String, Object> actualChanges = new LinkedHashMap<String, Object>(1);
		  final String firstnameParamName = "serviceCode";
	        if (command.isChangeInStringParameterNamed(firstnameParamName, this.serviceCode)) {
	            final String newValue = command.stringValueOfParameterNamed(firstnameParamName);
	            actualChanges.put(firstnameParamName, newValue);
	            this.serviceCode = StringUtils.defaultIfEmpty(newValue, null);
	        }
	        
	        final String servicedescParamName = "serviceDescription";
	        if (command.isChangeInStringParameterNamed(servicedescParamName, this.serviceDescription)) {
	            final String newValue = command.stringValueOfParameterNamed(servicedescParamName);
	            actualChanges.put(firstnameParamName, newValue);
	            this.serviceDescription = StringUtils.defaultIfEmpty(newValue, null);
	        }
	        final String officeIdParamName = "serviceType";
			if (command.isChangeInLongParameterNamed(officeIdParamName,
					this.serviceType)) {
				final Long newValue = command
						.longValueOfParameterNamed(officeIdParamName);
				actualChanges.put(officeIdParamName, newValue);
			}
	        
	        return actualChanges;

	}

	public void delete() {
		if(isDeleted.equalsIgnoreCase("y"))
		{}
		else
		{
			this.serviceCode=this.serviceCode+"_"+this.getId();
			isDeleted="y";
		}
	}



}
