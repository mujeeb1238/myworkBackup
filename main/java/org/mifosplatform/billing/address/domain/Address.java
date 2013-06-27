package org.mifosplatform.billing.address.domain;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
import org.mifosplatform.infrastructure.core.api.JsonCommand;

@Entity
@Table(name = "b_client_address")
public class Address{


	@Id
	@GeneratedValue
	@Column(name="address_id")
	private Long id;

	@Column(name = "client_id", length = 65536)
	private Long clientId;

	@Column(name = "address_no")
	private String addressNo;
	
	@Column(name = "address_key")
	private String addressKey;

	@Column(name = "street")
	private String street;
	@Column(name = "city")
	private String city;

	@Column(name = "state")
	private String state;

	@Column(name = "country")
	private String country;

	@Column(name = "zip")
	private String zip;

	@Column(name = "is_deleted", nullable = false)
	private char deleted='n';

	

	public Address() {
         
          
	}



	public Address(Long clientId,String addressKey, String addressNo, String street,
			String city, String state, String country, String zip) {
		
		
		 this.clientId=clientId;
		 this.addressKey=addressKey;
         this.addressNo=addressNo;
         this.street=street;
         this.addressKey="addr1";
         this.city=city;
         this.state=state;
         this.country=country;
         this.zip=zip;
	}

		public static Address fromJson(Long clientId, JsonCommand command) {
			 final String addressKey = command.stringValueOfParameterNamed("addressKey");
			    final String addressNo = command.stringValueOfParameterNamed("addressNo");
			    final String street = command.stringValueOfParameterNamed("street");
			    final String city = command.stringValueOfParameterNamed("city");
			    final String zip = command.stringValueOfParameterNamed("zipCode");
			    final String state = command.stringValueOfParameterNamed("state");
			    final String country = command.stringValueOfParameterNamed("country");
			    return new Address(clientId, addressKey, addressNo, street, city, state, country, zip);
			}



		public Long getId() {
			return id;
		}



		public Map<String, Object> update(JsonCommand command) {

			  final Map<String, Object> actualChanges = new LinkedHashMap<String, Object>(1);
			  final String firstnameParamName = "addressKey";
		        if (command.isChangeInStringParameterNamed(firstnameParamName, this.addressKey)) {
		            final String newValue = command.stringValueOfParameterNamed(firstnameParamName);
		            actualChanges.put(firstnameParamName, newValue);
		            this.addressKey = StringUtils.defaultIfEmpty(newValue, null);
		        }

		        final String addressNoParamName = "addressNo";
		        if (command.isChangeInStringParameterNamed(addressNoParamName, this.addressNo)) {
		            final String newValue = command.stringValueOfParameterNamed(addressNoParamName);
		            actualChanges.put(addressNoParamName, newValue);
		            this.addressNo = StringUtils.defaultIfEmpty(newValue, null);
		        }
		        final String streetParamName = "street";
				if (command.isChangeInStringParameterNamed(streetParamName,	this.street)) {
					final String newValue = command.stringValueOfParameterNamed(streetParamName);
					actualChanges.put(streetParamName, newValue);
					this.street=StringUtils.defaultIfEmpty(newValue, null);
				}

		  final String zipParamName = "zipCode";
	        if (command.isChangeInStringParameterNamed(zipParamName, this.zip)) {
	            final String newValue = command.stringValueOfParameterNamed(zipParamName);
	            actualChanges.put(zipParamName, newValue);
	            this.zip = StringUtils.defaultIfEmpty(newValue, null);
	        }

	        final String cityParamName = "city";
	        if (command.isChangeInStringParameterNamed(cityParamName, this.city)) {
	            final String newValue = command.stringValueOfParameterNamed(cityParamName);
	            actualChanges.put(cityParamName, newValue);
	            this.city = StringUtils.defaultIfEmpty(newValue, null);
	        }
	        final String stateParamName = "state";
			if (command.isChangeInStringParameterNamed(stateParamName,this.state)) {
				final String newValue = command.stringValueOfParameterNamed(stateParamName);
				actualChanges.put(stateParamName, newValue);
				this.state=StringUtils.defaultIfEmpty(newValue,null);
			}
	        final String countryParamName = "country";
			if (command.isChangeInStringParameterNamed(countryParamName,this.country)) {
				final String newValue = command.stringValueOfParameterNamed(countryParamName);
				actualChanges.put(countryParamName, newValue);
				this.country=StringUtils.defaultIfEmpty(newValue,null);
			}

	        return actualChanges;


		}
	
	}
	
	
			

	


