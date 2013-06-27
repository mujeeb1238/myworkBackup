package org.mifosplatform.billing.address.service;

import java.util.List;

import org.mifosplatform.billing.address.data.AddressData;

public interface AddressReadPlatformService {


	List<AddressData> retrieveSelectedAddressDetails(String selectedname);

	List<AddressData> retrieveAddressDetails(Long clientId);

	List<AddressData> retrieveAddressDetails();

	List<String> retrieveCountryDetails();

	List<String> retrieveStateDetails();

	List<String> retrieveCityDetails();

	List<AddressData> retrieveCityDetails(String selectedname);

	

	
	

}

