package org.mifosplatform.billing.eventmaster.serialization;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.mifosplatform.billing.eventmaster.domain.EventMaster;
import org.mifosplatform.infrastructure.core.data.ApiParameterError;
import org.mifosplatform.infrastructure.core.data.DataValidatorBuilder;
import org.mifosplatform.infrastructure.core.exception.InvalidJsonException;
import org.mifosplatform.infrastructure.core.exception.PlatformApiDataValidationException;
import org.mifosplatform.infrastructure.core.serialization.FromJsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.reflect.TypeToken;
import com.google.gson.JsonElement;



/**
 * {@link Component} Class for Validating Json for {@link EventMaster}
 * 
 * @author pavani
 *
 */
@Component
@SuppressWarnings("serial")
public class EventMasterFromApiJsonDeserializer {

	private final Set<String> supportedParameters = new HashSet<String> (Arrays.asList("eventName","chargeCode","eventStartDate","eventDescription","status","eventEndDate","allowCancellation","eventValidity","mediaData","locale","dateFormat"));
	private final FromJsonHelper fromApiJsonHelper;
	
	@Autowired
	public EventMasterFromApiJsonDeserializer (final FromJsonHelper fromApiJsonHelper) {
		this.fromApiJsonHelper = fromApiJsonHelper;
	}
	
	
	public void validateForCreate(final String json) {
		if(StringUtils.isBlank(json)) { 
			throw new InvalidJsonException();
		}
		
		final Type typeOfMap = new TypeToken<Map<String,Object>>() {}.getType(); 
		fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json, supportedParameters);
		
		 final List<ApiParameterError> dataValidationErrors = new ArrayList<ApiParameterError>();
	     final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors).resource("eventmaster");
	     
	     final JsonElement element = fromApiJsonHelper.parse(json);
	     
	     final String eventName = fromApiJsonHelper.extractStringNamed("eventName", element);
	     baseDataValidator.reset().parameter("eventName").value(eventName).notBlank().notExceedingLengthOf(100);
	     final String chargeCode = fromApiJsonHelper.extractStringNamed("chargeCode", element);
	     baseDataValidator.reset().parameter("chargeCode").value(chargeCode).notBlank().notExceedingLengthOf(100);
	     
	     throwExceptionIfValidationWarningsExist(dataValidationErrors);
	     
	}
	private void throwExceptionIfValidationWarningsExist(final List<ApiParameterError> dataValidationErrors) {
        if (!dataValidationErrors.isEmpty()) { throw new PlatformApiDataValidationException("validation.msg.validation.errors.exist",
                "Validation errors exist.", dataValidationErrors); }
    }

}
