package org.mifosplatform.billing.preparerequest.data;

public class PrepareRequestData {
	private final Long requestId;
	private final Long clientId;
	private final Long orderId;
	private final String requestType;
	private final String generateRequest;
	private String hardwareId;

	public PrepareRequestData(Long id, Long clientId, Long orderId,
			String requestType, String generateRequest, String hardWareId) {

		this.requestId = id;
		this.clientId = clientId;
		this.orderId = orderId;
		this.requestType = requestType;
		this.generateRequest = generateRequest;
		this.hardwareId=hardWareId;
	}

	
	public Long getRequestId() {
		return requestId;
	}


	public Long getClientId() {
		return clientId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public String getRequestType() {
		return requestType;
	}

	public String getGenerateRequest() {
		return generateRequest;
	}


	public String getHardwareId() {
		return hardwareId;
	}

}
