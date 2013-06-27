package org.mifosplatform.portfolio.servicemaster.commands;

public class ServicesCommand {

	private final String serviceCode;
	private final String serrviceDescription;
	private final Long serviceType;



	public ServicesCommand(final String serviceCode,final String serviceDescription,
		final	Long serviceType) {

		this.serrviceDescription=serviceDescription;
		this.serviceCode=serviceCode;
		this.serviceType=serviceType;
	}



	public String getServiceCode() {
		return serviceCode;
	}



	public String getSerrviceDescription() {
		return serrviceDescription;
	}



	public Long getServiceType() {
		return serviceType;
	}



}
