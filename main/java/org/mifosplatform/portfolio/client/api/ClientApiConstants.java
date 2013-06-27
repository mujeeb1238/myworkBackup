package org.mifosplatform.portfolio.client.api;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.mifosplatform.portfolio.client.data.ClientData;

public class ClientApiConstants {

    public static final String CLIENT_RESOURCE_NAME = "client";

    // general
    public static final String localeParamName = "locale";
    public static final String dateFormatParamName = "dateFormat";

    // request parameters
    public static final String idParamName = "id";
    public static final String groupIdParamName = "groupId";
    public static final String accountNoParamName = "accountNo";
    public static final String externalIdParamName = "externalId";
    public static final String firstnameParamName = "firstname";
    public static final String middlenameParamName = "middlename";
    public static final String lastnameParamName = "lastname";
    public static final String fullnameParamName = "fullname";
    public static final String officeIdParamName = "officeId";
    public static final String activeParamName = "active";
    public static final String activationDateParamName = "activationDate";
    public static final String clientCategoryParamName ="clientCategory";
    
    // response parameters
    public static final String statusParamName = "status";
    public static final String hierarchyParamName = "hierarchy";
    public static final String displayNameParamName = "displayName";
    public static final String officeNameParamName = "officeName";
    public static final String imageKeyParamName = "imageKey";
    public static final String imagePresentParamName = "imagePresent";

    // associations related part of response
    public static final String groupsParamName = "groups";

    // template related part of response
    public static final String officeOptionsParamName = "officeOptions";
    public static final String clientCategoryDatas="clientCategoryDatas";

    public static final Set<String> CLIENT_CREATE_REQUEST_DATA_PARAMETERS = new HashSet<String>(Arrays.asList(localeParamName,
            dateFormatParamName, groupIdParamName, accountNoParamName, externalIdParamName, firstnameParamName, middlenameParamName,
            lastnameParamName, fullnameParamName, officeIdParamName, activeParamName, activationDateParamName,clientCategoryParamName));

    public static final Set<String> CLIENT_UPDATE_REQUEST_DATA_PARAMETERS = new HashSet<String>(Arrays.asList(localeParamName,
            dateFormatParamName, accountNoParamName, externalIdParamName, firstnameParamName, middlenameParamName,clientCategoryParamName,
            lastnameParamName, fullnameParamName, activeParamName, activationDateParamName,clientCategoryParamName));

    /**
     * These parameters will match the class level parameters of
     * {@link ClientData}. Where possible, we try to get response parameters to
     * match those of request parameters.
     */
    public static final Set<String> CLIENT_RESPONSE_DATA_PARAMETERS = new HashSet<String>(Arrays.asList(idParamName, accountNoParamName,
            externalIdParamName, statusParamName, activeParamName, activationDateParamName, firstnameParamName, middlenameParamName,
            lastnameParamName, fullnameParamName, displayNameParamName, officeIdParamName, officeNameParamName, hierarchyParamName,
            imageKeyParamName, imagePresentParamName, groupsParamName, officeOptionsParamName,clientCategoryDatas));

    public static final Set<String> ACTIVATION_REQUEST_DATA_PARAMETERS = new HashSet<String>(Arrays.asList(localeParamName,
            dateFormatParamName, activationDateParamName));
}