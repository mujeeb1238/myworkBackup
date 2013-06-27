package org.mifosplatform.billing.mediadevice.service;

import org.mifosplatform.billing.mediadevice.data.MediaDeviceData;


public interface MediaDeviceReadPlatformService {

	MediaDeviceData retrieveDeviceDetails(String deviceId);

}
