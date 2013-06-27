package org.mifosplatform.billing.plan.data;

import java.util.List;

import org.joda.time.LocalDate;

public class PlanCodeData {
	private Long id;
	private String planCode;
	private List<ServiceData> availableServices;
	private LocalDate start_date;
	public PlanCodeData(final Long id,final String planCode,final List<ServiceData> data)
	{
		this.id=id;
		this.planCode=planCode;
		this.availableServices=data;
		this.start_date=new LocalDate();

	}

	public Long getId() {
		return id;
	}

	public String getPlanCode() {
		return planCode;
	}

	public List<ServiceData> getData() {
		return availableServices;
	}

	public LocalDate getStartDate() {
		return start_date;
	}


}
