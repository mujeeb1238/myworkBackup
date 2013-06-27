package org.mifosplatform.billing.address.data;

import java.util.List;

public class AddressData {
	
	private Long id;
	private Long clientId;
	private String addressKey;
	private String addressNo;
	private String street;
	private String zip;
	private String city;
	private String state;
	private String country;
	private List<AddressData> datas;
	private List<String> countryData,stateData,cityData;
	private String data; 
	
	

	public AddressData(Long addressId, Long clientId, String addressKey,
			String addressNo, String street, String zip, String city,
			String state, String country) {
     
		this.id=addressId;
		this.addressKey=addressKey;
		this.clientId=clientId;
		this.addressNo=addressNo;
		this.street=street;
		this.zip=zip;
		this.city=city;
		this.state=state;
		this.country=country;
	
	
	}



	public AddressData(List<AddressData> addressdata, List<String> countryData, List<String> statesData, List<String> citiesData) {
		if(addressdata!=null && addressdata.size()!=0){
		this.id=addressdata.get(0).getAddressId();
		this.addressKey=addressdata.get(0).getAddressKey();
		this.clientId=addressdata.get(0).getClientId();
		this.addressNo=addressdata.get(0).getAddressNo();
		this.street=addressdata.get(0).getStreet();
		this.zip=addressdata.get(0).getZip();
		this.city=addressdata.get(0).getCity();
		this.state=addressdata.get(0).getState();
		this.country=addressdata.get(0).getCountry();
		}
	this.datas=addressdata;
	this.countryData=countryData;
	this.stateData=statesData;
	this.cityData=citiesData;
	}



	public AddressData(Long id, String data) {

	this.id=id;
	this.data=data;
	
	}



	public Long getAddressId() {
		return id;
	}



	public Long getClientId() {
		return clientId;
	}



	public String getAddressKey() {
		return addressKey;
	}



	public String getAddressNo() {
		return addressNo;
	}



	public String getStreet() {
		return street;
	}



	public String getZip() {
		return zip;
	}



	public String getCity() {
		return city;
	}



	public String getState() {
		return state;
	}



	public String getCountry() {
		return country;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public List<AddressData> getDatas() {
		return datas;
	}



	public void setDatas(List<AddressData> datas) {
		this.datas = datas;
	}



	public List<String> getCountryData() {
		return countryData;
	}



	public void setCountryData(List<String> countryData) {
		this.countryData = countryData;
	}



	public List<String> getStateData() {
		return stateData;
	}



	public void setStateData(List<String> stateData) {
		this.stateData = stateData;
	}



	public List<String> getCityData() {
		return cityData;
	}



	public void setCityData(List<String> cityData) {
		this.cityData = cityData;
	}



	public String getData() {
		return data;
	}



	public void setData(String data) {
		this.data = data;
	}



	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}



	public void setAddressKey(String addressKey) {
		this.addressKey = addressKey;
	}



	public void setAddressNo(String addressNo) {
		this.addressNo = addressNo;
	}



	public void setStreet(String street) {
		this.street = street;
	}



	public void setZip(String zip) {
		this.zip = zip;
	}



	public void setCity(String city) {
		this.city = city;
	}



	public void setState(String state) {
		this.state = state;
	}



	public void setCountry(String country) {
		this.country = country;
	}
	
	

}
