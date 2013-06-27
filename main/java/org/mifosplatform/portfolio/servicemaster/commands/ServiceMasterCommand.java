package org.mifosplatform.portfolio.servicemaster.commands;

import java.util.Set;

import javax.persistence.Column;

public class ServiceMasterCommand {


	private Long id;
	private String serviceCode;
	private String serviceDescription;
	private Long serviceType;
	private final Set<String> modifiedParameters;
	public ServiceMasterCommand(String serviceCode,String serviceDescription,Long serviceType,Set<String> modifiedParameters)
	{

		this.serviceCode=serviceCode;
		this.serviceDescription=serviceDescription;
		this.serviceType=serviceType;
		this.modifiedParameters=modifiedParameters;
	}
	public String getServiceCode() {
		return serviceCode;
	}
	public String getServiceDescription() {
		return serviceDescription;
	}
	public Long getServiceType() {
		return serviceType;
	}
	public Set<String> getModifiedParameters() {
		return modifiedParameters;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public boolean isServiceCodeChanged() {
		return this.modifiedParameters.contains("serviceCode");
	}

	public boolean isServiceDescriptionChanged() {
		return this.modifiedParameters.contains("serviceDescription");
	}

	public boolean isServiceTypeChanged() {
		return this.modifiedParameters.contains("serviceType");
	}



}
