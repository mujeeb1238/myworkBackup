package org.mifosplatform.billing.servicemaster.data;

import java.util.Collection;


public class SericeMasterOptionsData {
private Collection<ServiceMasterData> serviceMasterOptions;
private Long id;
private String serviceCode;
private String serviceDescription;
private String serviceType;

public SericeMasterOptionsData(Collection<ServiceMasterData> serviceMasterOptions)
{
	this.serviceMasterOptions=serviceMasterOptions;
}

public SericeMasterOptionsData(Long id, String serviceCode,
		String serviceDescription,String serviceType) {
	this.id=id;
	this.serviceDescription=serviceDescription;
	this.serviceCode=serviceCode;
	this.serviceType=serviceType;

}

public Collection<ServiceMasterData> getServiceMasterOptions() {
	return serviceMasterOptions;
}

public Long getId() {
	return id;
}

public String getServiceCode() {
	return serviceCode;
}

public String getserviceDescription() {
	return serviceDescription;
}

public String getServiceType() {
	return serviceType;
}



}
