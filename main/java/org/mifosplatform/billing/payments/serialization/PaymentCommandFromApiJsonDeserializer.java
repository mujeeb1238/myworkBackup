package org.mifosplatform.billing.payments.serialization;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.mifosplatform.infrastructure.core.data.ApiParameterError;
import org.mifosplatform.infrastructure.core.data.DataValidatorBuilder;
import org.mifosplatform.infrastructure.core.exception.InvalidJsonException;
import org.mifosplatform.infrastructure.core.exception.PlatformApiDataValidationException;
import org.mifosplatform.infrastructure.core.serialization.FromJsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

/**
 * Deserializer for code JSON to validate API request.
 */
@Component
public class PaymentCommandFromApiJsonDeserializer {

	/**
	 * The parameters supported for this command.
	 */
	private final Set<String> supportedParameters = new HashSet<String>(
			Arrays.asList("id", "clientId", "paymentDate", "paymentCode",
					"amountPaid", "statmentId", "externalId", "dateFormat",
					"locale", "remarks"));
	
	private final FromJsonHelper fromApiJsonHelper;

	@Autowired
	public PaymentCommandFromApiJsonDeserializer(
			final FromJsonHelper fromApiJsonHelper) {
		this.fromApiJsonHelper = fromApiJsonHelper;
	}

	public void validateForCreate(String json) {

		if (StringUtils.isBlank(json)) {
			throw new InvalidJsonException();
		}

		final Type typeOfMap = new TypeToken<Map<String, Object>>() {
		}.getType();
		fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json,
				supportedParameters);

		final List<ApiParameterError> dataValidationErrors = new ArrayList<ApiParameterError>();
		final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(
				dataValidationErrors).resource("payment");

		final JsonElement element = fromApiJsonHelper.parse(json);
		
		//final LocalDate paymentDate = fromApiJsonHelper.extractLocalDateNamed("paymentDate", element);
		final String paymentCode = fromApiJsonHelper.extractStringNamed("paymentCode", element);
		final BigDecimal amountPaid = fromApiJsonHelper.extractBigDecimalWithLocaleNamed("amountPaid", element);
		//final String remarks = fromApiJsonHelper.extractStringNamed("remarks", element);

	
	//	baseDataValidator.reset().parameter("paymentDate").value(paymentDate)
		//.notBlank().notExceedingLengthOf(100);
		baseDataValidator.reset().parameter("paymentCode").value(paymentCode)
		.notBlank().notExceedingLengthOf(100);
		baseDataValidator.reset().parameter("amountPaid").value(amountPaid)
		.notBlank().notExceedingLengthOf(100);
		//baseDataValidator.reset().parameter("remarks").value(remarks)
		//.notBlank().notExceedingLengthOf(100);
		

		throwExceptionIfValidationWarningsExist(dataValidationErrors);
	}

	private void throwExceptionIfValidationWarningsExist(
			final List<ApiParameterError> dataValidationErrors) {
		if (!dataValidationErrors.isEmpty()) {
			throw new PlatformApiDataValidationException(
					"validation.msg.validation.errors.exist",
					"Validation errors exist.", dataValidationErrors);
		}
	}

}
