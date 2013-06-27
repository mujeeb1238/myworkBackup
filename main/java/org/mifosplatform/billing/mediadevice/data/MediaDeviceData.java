package org.mifosplatform.billing.mediadevice.data;

public class MediaDeviceData {
	
	
	final private  Long deviceId;
	final private  Long clientId;
	final private  String clientType;
	private int clientTypeId;

	public MediaDeviceData(Long deviceId, Long clientId, String clientType, int clientTypeId) {
           this.deviceId=deviceId;
           this.clientId=clientId;
           this.clientType=clientType;
           this.clientTypeId=clientTypeId;
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public Long getClientId() {
		return clientId;
	}

	public String getClientType() {
		return clientType;
	}

	public int getClientTypeId() {
		return clientTypeId;
	}

	
	
}
