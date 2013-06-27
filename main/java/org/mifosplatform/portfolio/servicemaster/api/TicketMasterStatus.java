package org.mifosplatform.portfolio.servicemaster.api;


public enum TicketMasterStatus {
	

		ACTIVE(1, "CategoryType.active"), //
		RESOLVED(2, "CategoryType.resolved "),
		CLOSED(3, "CategoryType.closed"),
		WORKING(3, "CategoryType.working "),
		TESTING(3, "CategoryType.tesing"),		
		 INVALID(4, "CategoryType.invalid");


	    private final Integer value;
		private final String code;

	    private TicketMasterStatus(final Integer value, final String code) {
	        this.value = value;
			this.code = code;
	    }

	    public Integer getValue() {
	        return this.value;
	    }

		public String getCode() {
			return code;
		}

		public static TicketMasterStatus fromInt(final Integer frequency) {

			TicketMasterStatus masterStatus = TicketMasterStatus.INVALID;
			switch (frequency) {
			case 1:
				masterStatus = TicketMasterStatus.ACTIVE;
				break;
			case 2:
				masterStatus = TicketMasterStatus.RESOLVED;
				break;

			case 3:
				masterStatus = TicketMasterStatus.CLOSED;
				break;
				
			case 4:
				masterStatus = TicketMasterStatus.TESTING;
				break;
				
			case 5:
				masterStatus = TicketMasterStatus.WORKING;
				break;


			default:
				masterStatus = TicketMasterStatus.INVALID;
				break;
			}
			return masterStatus;
		}
	}



