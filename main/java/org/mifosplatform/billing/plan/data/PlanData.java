package org.mifosplatform.billing.plan.data;

import java.util.List;

import org.joda.time.LocalDate;
import org.mifosplatform.billing.contract.data.SubscriptionData;
import org.mifosplatform.infrastructure.core.data.EnumOptionData;

public class PlanData {

	private  Long id;
	private  Long billRule;

	private  String planCode;
	private  String planDescription;
	private LocalDate startDate;
	private  LocalDate endDate;
	private  Long status;
	private EnumOptionData planstatus;
	private  String serviceDescription;
	private  List<ServiceData> services;
	private  List<ServiceData> selectedServices;
	private List<String> contractPeriod;
	private List<SubscriptionData> subscriptiondata;
	private List<BillRuleData> billRuleDatas;
	private List<EnumOptionData> planStatus;
	private  String Period;

	private PlanData datas;
	private long statusname;
	private List<SystemData> provisionSysData;
	private String provisionSystem;

	
	public PlanData(List<ServiceData> data, List<BillRuleData> billData,List<SubscriptionData> contractPeriod, List<EnumOptionData> status,
			PlanData datas, List<ServiceData> selectedservice,List<SystemData> provisionSysData) {
		if(datas!=null){
		this.id = datas.getId();
		this.planCode = datas.getplanCode();
		this.subscriptiondata = contractPeriod;
		this.startDate = datas.getStartDate();
		this.status = datas.getStatus();
		this.billRule = datas.getBillRule();
		this.endDate = datas.getEndDate();
		this.planDescription = datas.getplanDescription();
		this.provisionSystem=datas.getProvisionSystem();
		}
		this.services = data;
        this.provisionSysData=provisionSysData;
		this.selectedServices = selectedservice;
		this.billRuleDatas = billData;
		this.planStatus = status;
		this.serviceDescription = null;
		this.Period = null;
		this.datas = datas;
		this.datas = null;

	}

	
	public PlanData(Long id, String planCode, LocalDate startDate,LocalDate endDate, Long bill_rule, String contractPeriod,
			long status, String planDescription, long status1,String provisionSys,EnumOptionData enumstatus) {

		this.id = id;
		this.planCode = planCode;
		this.serviceDescription = null;
		this.startDate = startDate;
		this.status = status;
		this.billRule = bill_rule;
		this.endDate = endDate;
		this.planDescription = planDescription;
		this.services = null;
		this.billRuleDatas = null;
		this.Period = contractPeriod;
        this.provisionSystem=provisionSys;  
		this.selectedServices = null;
		this.statusname = status1;
		this.planstatus = enumstatus;
	}

	

	public String getProvisionSystem() {
		return provisionSystem;
	}


	public EnumOptionData getPlanstatus() {
		return planstatus;
	}

	public PlanData getDatas() {
		return datas;
	}

	public List<ServiceData> getSelectedServices() {
		return selectedServices;
	}

	public long getStatusname() {
		return statusname;
	}

	public List<EnumOptionData> getPlanStatus() {
		return planStatus;
	}

	public String getPeriod() {
		return Period;
	}

	public void setContractPeriod(List<String> contractPeriod) {
		this.contractPeriod = contractPeriod;
	}

	public List<BillRuleData> getBillRuleData() {
		return billRuleDatas;
	}

	public Long getId() {
		return id;
	}

	public String getplanCode() {
		return planCode;
	}

	public String getplanDescription() {
		return planDescription;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public Long getStatus() {
		return status;
	}

	public List<ServiceData> getServicedata() {
		return services;
	}

	public Long getBillRule() {
		return billRule;
	}

	public List<String> getContractPeriod() {
		return contractPeriod;
	}

	public String getserviceDescription() {
		return serviceDescription;
	}

	public List<SubscriptionData> getSubscriptiondata() {
		return subscriptiondata;
	}

}
