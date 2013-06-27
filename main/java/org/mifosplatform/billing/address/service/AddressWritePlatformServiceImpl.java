package org.mifosplatform.billing.address.service;

import java.util.Map;

import org.mifosplatform.billing.address.domain.Address;
import org.mifosplatform.billing.address.domain.AddressRepository;
import org.mifosplatform.billing.address.serialization.ClientAddressCommandFromApiJsonDeserializer;
import org.mifosplatform.infrastructure.codes.exception.CodeNotFoundException;
import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResultBuilder;
import org.mifosplatform.infrastructure.security.service.PlatformSecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;


@Service
public class AddressWritePlatformServiceImpl implements AddressWritePlatformService {
	  private final static Logger logger = LoggerFactory.getLogger(AddressWritePlatformServiceImpl.class);
	private PlatformSecurityContext context;
	private AddressRepository addressRepository;
	private CityRepository cityRepository;
	private StateRepository stateRepository;
	private CountryRepository countryRepository;
	private ClientAddressCommandFromApiJsonDeserializer apiJsonDeserializer; 


	@Autowired
	public AddressWritePlatformServiceImpl(final PlatformSecurityContext context,final CityRepository cityRepository,
			final StateRepository stateRepository,final CountryRepository countryRepository,final AddressRepository addressRepository, final ClientAddressCommandFromApiJsonDeserializer apiJsonDeserializer) {
		this.context = context;
		this.addressRepository = addressRepository;
		this.cityRepository=cityRepository;
		this.stateRepository=stateRepository;
		this.countryRepository=countryRepository;
		this.apiJsonDeserializer = apiJsonDeserializer;
		

	}

	@Override
	public CommandProcessingResult createAddress(Long clientId,JsonCommand command) {
		try {
			context.authenticatedUser();
			apiJsonDeserializer.validaForCreate(command.json());
			final Address address = Address.fromJson(clientId,command);
			this.addressRepository.save(address);
			return new CommandProcessingResult(address.getId());
		} catch (DataIntegrityViolationException dve) {
			 handleCodeDataIntegrityIssues(command, dve);
			return  CommandProcessingResult.empty();
		}
	}

	private void handleCodeDataIntegrityIssues(JsonCommand command,
			DataIntegrityViolationException dve) {
		  logger.error(dve.getMessage(), dve);
		
	}

	@Override
	public CommandProcessingResult updateAddress(Long addrId,JsonCommand command) {
		try
		{
			  context.authenticatedUser();
	            //this.fromApiJsonDeserializer.validateForUpdate(command.json());
	            final Address address = retrieveAddressBy(addrId);
               final Map<String, Object> changes = address.update(command);
         if (!changes.isEmpty()) {
             this.addressRepository.save(address);
         }
         return new CommandProcessingResultBuilder() //
         .withCommandId(command.commandId()) //
         .withEntityId(addrId) //
         .with(changes) //
         .build();
	} catch (DataIntegrityViolationException dve) {
		 handleCodeDataIntegrityIssues(command, dve);
		return new CommandProcessingResult(Long.valueOf(-1));
	}
}

	private Address retrieveAddressBy(Long addrId) {
		Address address=this.addressRepository.findOne(addrId);
	    if(address== null){
		throw new CodeNotFoundException(addrId);
	    }
	return address;
	}

	@Override
	public CommandProcessingResult createNewRecord(JsonCommand command,String entityType) {
  try{
	  
	  this.context.authenticatedUser();
	 
	  if(entityType.equalsIgnoreCase("city")){
		//  City city=new City(command.getEntityCode(),command.getEntityName(),command.getParentEntityId());
		  apiJsonDeserializer.validaForCreateCity(command.json());
		  final City city = City.fromJson(command);
		  this.cityRepository.save(city);
		 return new CommandProcessingResult(Long.valueOf(city.getId()));
	  }else if(entityType.equalsIgnoreCase("state")){
		  
		  apiJsonDeserializer.validaForCreateState(command.json());
		  State state=State.fromJson(command); 
		  this.stateRepository.save(state);
		  
		  return new CommandProcessingResult(Long.valueOf(state.getId()));
	  }else{
		  apiJsonDeserializer.validaForCreateCountry(command.json());
		  Country country=Country.fromJson(command);
		  this.countryRepository.save(country);
		  return new CommandProcessingResult(Long.valueOf(country.getId()));
	  }
	  
		  
	  
  } catch (DataIntegrityViolationException dve) {
	  handleCodeDataIntegrityIssues(command, dve);
		return new CommandProcessingResult(Long.valueOf(-1));
	}

	}
}

