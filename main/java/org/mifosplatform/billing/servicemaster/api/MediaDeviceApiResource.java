package org.mifosplatform.billing.servicemaster.api;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.mifosplatform.billing.mediadetails.exception.NoMediaDeviceFoundException;
import org.mifosplatform.billing.mediadevice.data.MediaDeviceData;
import org.mifosplatform.billing.mediadevice.service.MediaDeviceReadPlatformService;
import org.mifosplatform.commands.service.PortfolioCommandSourceWritePlatformService;
import org.mifosplatform.infrastructure.core.api.ApiRequestParameterHelper;
import org.mifosplatform.infrastructure.core.serialization.ApiRequestJsonSerializationSettings;
import org.mifosplatform.infrastructure.core.serialization.DefaultToApiJsonSerializer;
import org.mifosplatform.infrastructure.security.service.PlatformSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Path("/mediadevices")
@Component
@Scope("singleton")
public class MediaDeviceApiResource {
	private  final Set<String> RESPONSE_DATA_PARAMETERS=new HashSet<String>(Arrays.asList("deviceId","clientId","clientType"));
    private final String resourceNameForPermissions = "MEDIADEVICE";
	  private final PlatformSecurityContext context;
	    private final DefaultToApiJsonSerializer<MediaDeviceData> toApiJsonSerializer;
	    private final ApiRequestParameterHelper apiRequestParameterHelper;
	    private final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService;
	    private final MediaDeviceReadPlatformService mediaDeviceReadPlatformService;
		
		 @Autowired
	    public MediaDeviceApiResource(final PlatformSecurityContext context, 
	   final DefaultToApiJsonSerializer<MediaDeviceData> toApiJsonSerializer, final ApiRequestParameterHelper apiRequestParameterHelper,
	   final PortfolioCommandSourceWritePlatformService commandsSourceWritePlatformService,final MediaDeviceReadPlatformService mediaDeviceReadPlatformService){
		        this.context = context;
		        this.toApiJsonSerializer = toApiJsonSerializer;
		        this.apiRequestParameterHelper = apiRequestParameterHelper;
		        this.commandsSourceWritePlatformService = commandsSourceWritePlatformService;
		        this.mediaDeviceReadPlatformService=mediaDeviceReadPlatformService;
		    }		
		
	
	 @GET
		@Path("{deviceId}")
		@Consumes({MediaType.APPLICATION_JSON})
		@Produces({MediaType.APPLICATION_JSON})
		public String retrieveSingleDeviceDetails(@PathParam("deviceId") final String deviceId, @Context final UriInfo uriInfo) {
			MediaDeviceData data = this.mediaDeviceReadPlatformService.retrieveDeviceDetails(deviceId);
			if(data == null){
				throw new NoMediaDeviceFoundException();
			}
	        final ApiRequestJsonSerializationSettings settings = apiRequestParameterHelper.process(uriInfo.getQueryParameters());
	        return this.toApiJsonSerializer.serialize(settings, data, RESPONSE_DATA_PARAMETERS);
		}
	 	 
}
