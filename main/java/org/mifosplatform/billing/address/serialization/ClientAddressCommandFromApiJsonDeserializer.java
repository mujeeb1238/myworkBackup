package org.mifosplatform.billing.address.serialization;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.mifosplatform.infrastructure.core.data.ApiParameterError;
import org.mifosplatform.infrastructure.core.data.DataValidatorBuilder;
import org.mifosplatform.infrastructure.core.exception.InvalidJsonException;
import org.mifosplatform.infrastructure.core.exception.PlatformApiDataValidationException;
import org.mifosplatform.infrastructure.core.serialization.FromJsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.reflect.TypeToken;
import com.google.gson.JsonElement;

@Component
public class ClientAddressCommandFromApiJsonDeserializer {

	final private Set<String> supportedParameters = new HashSet<String>(Arrays.asList("entityCode","entityName","parentEntityId"));
	final private Set<String> supportedParameters2 = new HashSet<String>(Arrays.asList("addressNo","city","country","entityCode","entityName","parentEntityId","state","street","zipCode"));

	private final FromJsonHelper fromApiJsonHelper;  
	
	@Autowired
	public ClientAddressCommandFromApiJsonDeserializer(final FromJsonHelper fromApiJsonHelper){
		this.fromApiJsonHelper = fromApiJsonHelper;
	}
	
	
	public void validaForCreate(String json){
		if(StringUtils.isBlank(json)){
			throw new InvalidJsonException();
		}
		
		
		 final Type typeOfMap = new TypeToken<Map<String, Object>>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;}.getType();
			
		 fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json, supportedParameters2);
		 
		 final List<ApiParameterError> dataValidationErrors = new ArrayList<ApiParameterError>();
	     final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors).resource("address");

	     final JsonElement element = fromApiJsonHelper.parse(json);


	     final String addressNo = fromApiJsonHelper.extractStringNamed("addressNo", element);
	     baseDataValidator.reset().parameter("addressNo").value(addressNo).notBlank().notExceedingLengthOf(150);
	     
	     final String street = fromApiJsonHelper.extractStringNamed("street", element);
	     baseDataValidator.reset().parameter("street").value(street).notBlank().notExceedingLengthOf(150);
	     
	     final String city = fromApiJsonHelper.extractStringNamed("city", element);
	     baseDataValidator.reset().parameter("city").value(city).notBlank().notExceedingLengthOf(200);
	     
	     final String state = fromApiJsonHelper.extractStringNamed("state", element);
	     baseDataValidator.reset().parameter("state").value(state).notBlank().notExceedingLengthOf(200);
	     
	     final String country = fromApiJsonHelper.extractStringNamed("country", element);
	     baseDataValidator.reset().parameter("country").value(country).notBlank().notExceedingLengthOf(200);
	     
	     final String zipCode = fromApiJsonHelper.extractStringNamed("zipCode", element);
	     baseDataValidator.reset().parameter("zipCode").value(zipCode).notBlank().notExceedingLengthOf(20);
	     
	     
	     throwExceptionIfValidationWarningsExist(dataValidationErrors);

	}

	
	
	
	
	public void validaForCreateCity(String json){
		if(StringUtils.isBlank(json)){
			throw new InvalidJsonException();
		}
		
		
		 final Type typeOfMap = new TypeToken<Map<String, Object>>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;}.getType();
			
		 fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json, supportedParameters);
		 
		 final List<ApiParameterError> dataValidationErrors = new ArrayList<ApiParameterError>();
	     final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors).resource("chargecode");

	     final JsonElement element = fromApiJsonHelper.parse(json);


	     final String entityCode = fromApiJsonHelper.extractStringNamed("entityCode", element);
	     baseDataValidator.reset().parameter("entityCode").value(entityCode).notBlank().notExceedingLengthOf(10);
	     
	     final String entityName = fromApiJsonHelper.extractStringNamed("entityName", element);
	     baseDataValidator.reset().parameter("entityName").value(entityName).notBlank().notExceedingLengthOf(100);

	     
	     final String parentEntityId = fromApiJsonHelper.extractStringNamed("parentEntityId", element);
	     if(parentEntityId.contains("-1"))
	    	 baseDataValidator.reset().parameter("parentEntityId").value(parentEntityId).notBlank().zeroOrPositiveAmount();
	     else
	    	 baseDataValidator.reset().parameter("parentEntityId").value(parentEntityId).notBlank().notExceedingLengthOf(11);
	     
	     
	     throwExceptionIfValidationWarningsExist(dataValidationErrors);

	}
	

	public void validaForCreateState(String json){
		if(StringUtils.isBlank(json)){
			throw new InvalidJsonException();
		}
		
		
		 final Type typeOfMap = new TypeToken<Map<String, Object>>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;}.getType();
			
		 fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json, supportedParameters);
		 
		 final List<ApiParameterError> dataValidationErrors = new ArrayList<ApiParameterError>();
	     final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors).resource("chargecode");

	     final JsonElement element = fromApiJsonHelper.parse(json);


	     final String entityCode = fromApiJsonHelper.extractStringNamed("entityCode", element);
	     baseDataValidator.reset().parameter("entityCode").value(entityCode).notBlank().notExceedingLengthOf(10);
	     
	     final String entityName = fromApiJsonHelper.extractStringNamed("entityName", element);
	     baseDataValidator.reset().parameter("entityName").value(entityName).notBlank().notExceedingLengthOf(100);

	     
	     final String parentEntityId = fromApiJsonHelper.extractStringNamed("parentEntityId", element);
	     if(parentEntityId.contains("-1"))
	    	 baseDataValidator.reset().parameter("parentEntityId").value(parentEntityId).notBlank().zeroOrPositiveAmount();
	     else
	    	 baseDataValidator.reset().parameter("parentEntityId").value(parentEntityId).notBlank().notExceedingLengthOf(11);
	     
	     
	     throwExceptionIfValidationWarningsExist(dataValidationErrors);

	}


	public void validaForCreateCountry(String json){
		if(StringUtils.isBlank(json)){
			throw new InvalidJsonException();
		}
		
		
		 final Type typeOfMap = new TypeToken<Map<String, Object>>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;}.getType();
			
		 fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json, supportedParameters);
		 
		 final List<ApiParameterError> dataValidationErrors = new ArrayList<ApiParameterError>();
	     final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors).resource("chargecode");

	     final JsonElement element = fromApiJsonHelper.parse(json);


	     final String entityCode = fromApiJsonHelper.extractStringNamed("entityCode", element);
	     baseDataValidator.reset().parameter("entityCode").value(entityCode).notBlank().notExceedingLengthOf(4);
	     
	     final String entityName = fromApiJsonHelper.extractStringNamed("entityName", element);
	     baseDataValidator.reset().parameter("entityName").value(entityName).notBlank().notExceedingLengthOf(100);

	     
	     final String parentEntityId = fromApiJsonHelper.extractStringNamed("parentEntityId", element);
	     if(parentEntityId.contains("-1"))
	    	 baseDataValidator.reset().parameter("parentEntityId").value(parentEntityId).notBlank().zeroOrPositiveAmount();
	     else
	    	 baseDataValidator.reset().parameter("parentEntityId").value(parentEntityId).notBlank().notExceedingLengthOf(11);
	     
	     
	     throwExceptionIfValidationWarningsExist(dataValidationErrors);

	}

	private void throwExceptionIfValidationWarningsExist(final List<ApiParameterError> dataValidationErrors) {
        if (!dataValidationErrors.isEmpty()) { throw new PlatformApiDataValidationException("validation.msg.validation.errors.exist",
                "Validation errors exist.", dataValidationErrors); }
    }
}

