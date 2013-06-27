package org.mifosplatform.billing.order.data;



import org.mifosplatform.billing.plan.domain.StatusTypeEnum;
import org.mifosplatform.infrastructure.core.data.EnumOptionData;

public class SavingStatusEnumaration {

	public static EnumOptionData interestCompoundingPeriodType(final int id) {
		return interestCompoundingPeriodType(StatusTypeEnum.fromInt(id));
	}

	public static EnumOptionData interestCompoundingPeriodType(final StatusTypeEnum type) {
		final String codePrefix = "deposit.interest.compounding.period.";
		EnumOptionData optionData = null;
		switch (type) {
		case ACTIVE:
			optionData = new EnumOptionData(StatusTypeEnum.ACTIVE.getValue().longValue(), codePrefix + StatusTypeEnum.ACTIVE.getCode(), "ACTIVE");
			break;
		case INACTIVE:
			optionData = new EnumOptionData(StatusTypeEnum.INACTIVE.getValue().longValue(), codePrefix + StatusTypeEnum.INACTIVE.getCode(), "INACTIVE");
			break;

		case DISCONNECTED:
			optionData = new EnumOptionData(StatusTypeEnum.DISCONNECTED.getValue().longValue(), codePrefix + StatusTypeEnum.DISCONNECTED.getCode(), "DISCONNECTED");
			break;
			
		case NEW:
			optionData = new EnumOptionData(StatusTypeEnum.NEW.getValue().longValue(), codePrefix + StatusTypeEnum.NEW.getCode(), "NEW");
			break;

		default:
			optionData = new EnumOptionData(StatusTypeEnum.INVALID.getValue().longValue(), StatusTypeEnum.INVALID.getCode(), "INVALID");
			break;
		}
		return optionData;
	}

}
