package org.mifosplatform.billing.service;

import java.util.Collection;
import java.util.List;

import org.mifosplatform.billing.servicemaster.data.SericeMasterOptionsData;
import org.mifosplatform.billing.servicemaster.data.ServiceMasterData;

public interface ServiceMasterReadPlatformService {
	 Collection<ServiceMasterData> retrieveAllServiceMasterData() ;

	List<SericeMasterOptionsData> retrieveServices();

	SericeMasterOptionsData retrieveIndividualService(Long serviceId);
}
