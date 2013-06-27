package org.mifosplatform.billing.taxmapping.api;

import java.util.Arrays;
import java.util.List;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.mifosplatform.billing.chargecode.data.ChargeCodeData;
import org.mifosplatform.billing.taxmapping.data.TaxMapData;
import org.mifosplatform.billing.taxmapping.service.TaxMapReadPlatformService;
import org.mifosplatform.commands.domain.CommandSource;
import org.mifosplatform.commands.domain.CommandWrapper;
import org.mifosplatform.commands.service.CommandWrapperBuilder;
import org.mifosplatform.commands.service.PortfolioCommandSourceWritePlatformService;
import org.mifosplatform.infrastructure.core.api.ApiRequestParameterHelper;
import org.mifosplatform.infrastructure.core.data.CommandProcessingResult;
import org.mifosplatform.infrastructure.core.serialization.ApiRequestJsonSerializationSettings;
import org.mifosplatform.infrastructure.core.serialization.DefaultToApiJsonSerializer;
import org.mifosplatform.infrastructure.security.service.PlatformSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;




@Path("taxmaps")
@Component
@Scope("singleton")
public class TaxMapApiResource {

	private final Set<String> RESPONSE_TAXMAPPING_PARAMETERS = new HashSet<String>(Arrays.asList("id","chargeCode","taxCode","startDate","type","rate"));
	
	
	private String resourceNameForPermissions = "TAXMAPPING";
	private final ApiRequestParameterHelper apiRequestParameterHelper;
	private final PlatformSecurityContext context;
	private final DefaultToApiJsonSerializer<ChargeCodeData> apiJsonSerializer;
	private final DefaultToApiJsonSerializer<TaxMapData> apiJsonSerializerForTaxMap;
	private final PortfolioCommandSourceWritePlatformService portfolioCommandSourceWritePlatformService;
	private final TaxMapReadPlatformService taxMapReadPlatformService;
	
	@Autowired
	public TaxMapApiResource(ApiRequestParameterHelper apiRequestParameterHelper, PlatformSecurityContext context, DefaultToApiJsonSerializer<ChargeCodeData> apiJsonSerializer, PortfolioCommandSourceWritePlatformService portfolioCommandSourceWritePlatformService, TaxMapReadPlatformService taxMapReadPlatformService, DefaultToApiJsonSerializer<TaxMapData> apiJsonSerializerForTaxMap) {
	this.context = context;
	this.apiJsonSerializer = apiJsonSerializer;
	this.apiRequestParameterHelper = apiRequestParameterHelper;
	this.portfolioCommandSourceWritePlatformService = portfolioCommandSourceWritePlatformService;
	this.taxMapReadPlatformService = taxMapReadPlatformService;
	this.apiJsonSerializerForTaxMap = apiJsonSerializerForTaxMap;
	}
	
	@GET
	@Path("template")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public String retriveTaxTemplate(@Context final UriInfo uriInfo){
		context.authenticatedUser().validateHasReadPermission(resourceNameForPermissions);
		List<ChargeCodeData> ccd = taxMapReadPlatformService.retriveTemplateData();
		List<TaxMapData> tmd = taxMapReadPlatformService.retriveTaxMapTypeData();
		ChargeCodeData chargeCodeData  = new ChargeCodeData(ccd);
		chargeCodeData.setTaxMapData(tmd);
		final ApiRequestJsonSerializationSettings settings = apiRequestParameterHelper.process(uriInfo.getQueryParameters());
		return this.apiJsonSerializer.serialize(settings,chargeCodeData,RESPONSE_TAXMAPPING_PARAMETERS);
	}
	
	
	@GET
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public String retriveTaxDetails(@Context final UriInfo uriInfo){
		context.authenticatedUser().validateHasReadPermission(resourceNameForPermissions);
		List<TaxMapData> taxMapData = taxMapReadPlatformService.retriveTaxMapData();
		final ApiRequestJsonSerializationSettings settings = apiRequestParameterHelper.process(uriInfo.getQueryParameters());
		return this.apiJsonSerializerForTaxMap.serialize(settings,taxMapData,RESPONSE_TAXMAPPING_PARAMETERS);
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public String createTaxMap(final String jsonRequestBody){
		context.authenticatedUser().validateHasReadPermission(resourceNameForPermissions);
		CommandWrapper command = new CommandWrapperBuilder().createTaxMap().withJson(jsonRequestBody).build();
		CommandProcessingResult result = portfolioCommandSourceWritePlatformService.logCommandSource(command);
		return apiJsonSerializerForTaxMap.serialize(result);
	}
	
	@GET
	@Path("{taxMapId}")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public String getTaxMapForUpdate(@PathParam("taxMapId") final Long taxMapId, @Context final UriInfo uriInfo){
		context.authenticatedUser().validateHasReadPermission(resourceNameForPermissions);
		TaxMapData taxMapData = taxMapReadPlatformService.retriveTaxMapDataForUpdate(taxMapId);
		List<ChargeCodeData> ccd = taxMapReadPlatformService.retriveTemplateData();
		List<TaxMapData> tmd = taxMapReadPlatformService.retriveTaxMapTypeData();
		taxMapData.setChargeCodeForTax(ccd);
		taxMapData.setTaxMapData(tmd);
		final ApiRequestJsonSerializationSettings settings = apiRequestParameterHelper.process(uriInfo.getQueryParameters());
		return this.apiJsonSerializerForTaxMap.serialize(settings,taxMapData,RESPONSE_TAXMAPPING_PARAMETERS);
		
	}
	
	@PUT
	@Path("{taxMapId}")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public String updateTaxMapData(@PathParam("taxMapId") final Long taxMapId, final String jsonRequestBody){
		context.authenticatedUser().validateHasCheckerPermissionTo(resourceNameForPermissions);
		CommandWrapper commandRequest = new CommandWrapperBuilder().updateTaxMap(taxMapId).withJson(jsonRequestBody).build();
		CommandProcessingResult result = portfolioCommandSourceWritePlatformService.logCommandSource(commandRequest);	
		return apiJsonSerializerForTaxMap.serialize(result);
	}
}
