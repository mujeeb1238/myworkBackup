/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.commands.domain;

public class CommandWrapper {

    private final Long commandId;
    @SuppressWarnings("unused")
    private final Long officeId;
    private final Long groupId;
    private final Long clientId;
    private final Long loanId;
    private final Long savingsId;
    private final String actionName;
    private final String entityName;
    private final String taskPermissionName;
    private final Long entityId;
    private final Long subentityId;
    private final String href;
    private final String json;
    private final Long codeId;
    private final String transactionId;
    private final String supportedEntityType;
    private final Long supportedEntityId;

    public static CommandWrapper wrap(final String actionName, final String entityName, final Long resourceId, final Long subresourceId) {
        return new CommandWrapper(null, actionName, entityName, resourceId, subresourceId, null);
    }

    public static CommandWrapper fromExistingCommand(final Long commandId, final String actionName, final String entityName,
            final Long resourceId, final Long subresourceId, final String resourceGetUrl) {
        return new CommandWrapper(commandId, actionName, entityName, resourceId, subresourceId, resourceGetUrl);
    }

    private CommandWrapper(final Long commandId, final String actionName, final String entityName, final Long resourceId,
            final Long subresourceId, final String resourceGetUrl) {
        this.commandId = commandId;
        this.officeId = null;
        this.groupId = null;
        this.clientId = null;
        this.loanId = null;
        this.savingsId = null;
        this.actionName = actionName;
        this.entityName = entityName;
        this.taskPermissionName = actionName + "_" + entityName;
        this.entityId = resourceId;
        this.subentityId = subresourceId;
        this.codeId = null;
        this.supportedEntityType = null;
        this.supportedEntityId = null;
        this.href = resourceGetUrl;
        this.json = null;
        this.transactionId = null;

    }

    public CommandWrapper(final Long officeId, final Long groupId, final Long clientId, final Long loanId, final Long savingsId,
            final String actionName, final String entityName, final Long entityId, final Long subentityId, final Long codeId,
            final String supportedEntityType, final Long supportedEntityId, final String href, final String json, final String transactionId) {
        this.commandId = null;
        this.officeId = officeId;
        this.groupId = groupId;
        this.clientId = clientId;
        this.loanId = loanId;
        this.savingsId = savingsId;
        this.actionName = actionName;
        this.entityName = entityName;
        this.taskPermissionName = actionName + "_" + entityName;
        this.entityId = entityId;
        this.subentityId = subentityId;
        this.codeId = codeId;
        this.supportedEntityType = supportedEntityType;
        this.supportedEntityId = supportedEntityId;
        this.href = href;
        this.json = json;
        this.transactionId = transactionId;
    }

    public Long commandId() {
        return this.commandId;
    }

    public String actionName() {
        return this.actionName;
    }

    public String entityName() {
        return this.entityName;
    }

    public Long resourceId() {
        return this.entityId;
    }

    public Long subresourceId() {
        return this.subentityId;
    }

    public String taskPermissionName() {
        return this.actionName + "_" + this.entityName;
    }

    public boolean isCreate() {
        return this.actionName.equalsIgnoreCase("CREATE");
    }

    public String getTaskPermissionName() {
        return this.taskPermissionName;
    }

    public Long getCodeId() {
        return this.codeId;
    }

    public String getHref() {
        return this.href;
    }

    public String getJson() {
        return this.json;
    }

    public String getTransactionId() {
        return this.transactionId;
    }

    public String getEntityName() {
        return this.entityName;
    }

    public Long getEntityId() {
        return this.entityId;
    }

    public Long getSubentityId() {
        return this.subentityId;
    }

    public Long getGroupId() {
        return this.groupId;
    }

    public Long getClientId() {
        return this.clientId;
    }

    public Long getLoanId() {
        return this.loanId;
    }

    public Long getSavingsId() {
        return this.savingsId;
    }

    public Long getSupportedEntityId() {
        return this.supportedEntityId;
    }

    public String getSupportedEntityType() {
        return this.supportedEntityType;
    }

    public boolean isUpdate() {
        // permissions resource has special update which involves no resource.
        return (isPermissionResource() && isUpdateOperation()) || (isCurrencyResource() && isUpdateOperation())
                || (isUpdateOperation() && this.entityId != null);
    }

    public boolean isUpdateOperation() {
        return this.actionName.equalsIgnoreCase("UPDATE");
    }

    public boolean isDelete() {
        return isDeleteOperation() && this.entityId != null;
    }

    private boolean isDeleteOperation() {
        return this.actionName.equalsIgnoreCase("DELETE");
    }

    public boolean isUpdateRolePermissions() {
        return this.actionName.equalsIgnoreCase("PERMISSIONS") && this.entityId != null;
    }

    public boolean isConfigurationResource() {
        return this.entityName.equalsIgnoreCase("CONFIGURATION");
    }

    public boolean isPermissionResource() {
        return this.entityName.equalsIgnoreCase("PERMISSION");
    }

    public boolean isRoleResource() {
        return this.entityName.equalsIgnoreCase("ROLE");
    }

    public boolean isUserResource() {
        return this.entityName.equalsIgnoreCase("USER");
    }

    public boolean isCurrencyResource() {
        return this.entityName.equalsIgnoreCase("CURRENCY");
    }

    public boolean isCodeResource() {
        return this.entityName.equalsIgnoreCase("CODE");
    }

    public boolean isCodeValueResource() {
        return this.entityName.equalsIgnoreCase("CODEVALUE");
    }

    public boolean isStaffResource() {
        return this.entityName.equalsIgnoreCase("STAFF");
    }

    public boolean isGuarantorResource() {
        return this.entityName.equalsIgnoreCase("GUARANTOR");
    }

    public boolean isGLAccountResource() {
        return this.entityName.equalsIgnoreCase("GLACCOUNT");
    }

    public boolean isGLClosureResource() {
        return this.entityName.equalsIgnoreCase("GLCLOSURE");
    }

    public boolean isJournalEntryResource() {
        return this.entityName.equalsIgnoreCase("JOURNALENTRY");
    }

    public boolean isRevertJournalEntry() {
        return this.actionName.equalsIgnoreCase("REVERSE") && this.entityName.equalsIgnoreCase("JOURNALENTRY");
    }

    public boolean isFundResource() {
        return this.entityName.equalsIgnoreCase("FUND");
    }

    public boolean isOfficeResource() {
        return this.entityName.equalsIgnoreCase("OFFICE");
    }

    public boolean isOfficeTransactionResource() {
        return this.entityName.equalsIgnoreCase("OFFICETRANSACTION");
    }

    public boolean isChargeDefinitionResource() {
        return this.entityName.equalsIgnoreCase("CHARGE");
    }

    public boolean isLoanProductResource() {
        return this.entityName.equalsIgnoreCase("LOANPRODUCT");
    }

    public boolean isClientResource() {
        return this.entityName.equalsIgnoreCase("CLIENT");
    }

    public boolean isClientActivation() {
        return this.actionName.equalsIgnoreCase("ACTIVATE") && this.entityName.equalsIgnoreCase("CLIENT");
    }

    public boolean isGroupActivation() {
        return this.actionName.equalsIgnoreCase("ACTIVATE") && this.entityName.equalsIgnoreCase("GROUP");
    }

    public boolean isCenterActivation() {
        return this.actionName.equalsIgnoreCase("ACTIVATE") && this.entityName.equalsIgnoreCase("CENTER");
    }

    public boolean isClientIdentifierResource() {
        return this.entityName.equals("CLIENTIDENTIFIER");
    }

    public boolean isClientNoteResource() {
        return this.entityName.equals("CLIENTNOTE");
    }

    public boolean isLoanResource() {
        return this.entityName.equalsIgnoreCase("LOAN");
    }

    public boolean isLoanChargeResource() {
        return this.entityName.equalsIgnoreCase("LOANCHARGE");
    }

    public boolean isCollateralResource() {
        return this.entityName.equalsIgnoreCase("COLLATERAL");
    }

    public boolean isApproveLoanApplication() {
        return this.actionName.equalsIgnoreCase("APPROVE") && this.entityName.equalsIgnoreCase("LOAN");
    }

    public boolean isUndoApprovalOfLoanApplication() {
        return this.actionName.equalsIgnoreCase("APPROVALUNDO") && this.entityName.equalsIgnoreCase("LOAN");
    }

    public boolean isApplicantWithdrawalFromLoanApplication() {
        return this.actionName.equalsIgnoreCase("WITHDRAW") && this.entityName.equalsIgnoreCase("LOAN");
    }

    public boolean isRejectionOfLoanApplication() {
        return this.actionName.equalsIgnoreCase("REJECT") && this.entityName.equalsIgnoreCase("LOAN");
    }

    public boolean isDisbursementOfLoan() {
        return this.actionName.equalsIgnoreCase("DISBURSE") && this.entityName.equalsIgnoreCase("LOAN");
    }

    public boolean isUndoDisbursementOfLoan() {
        return this.actionName.equalsIgnoreCase("DISBURSALUNDO") && this.entityName.equalsIgnoreCase("LOAN");
    }

    public boolean isLoanRepayment() {
        return this.actionName.equalsIgnoreCase("REPAYMENT") && this.entityName.equalsIgnoreCase("LOAN");
    }

    public boolean isLoanRepaymentAdjustment() {
        return this.actionName.equalsIgnoreCase("ADJUST") && this.entityName.equalsIgnoreCase("LOAN");
    }

    public boolean isWaiveInterestPortionOnLoan() {
        return this.actionName.equalsIgnoreCase("WAIVEINTERESTPORTION") && this.entityName.equalsIgnoreCase("LOAN");
    }

    public boolean isLoanWriteOff() {
        return this.actionName.equalsIgnoreCase("WRITEOFF") && this.entityName.equalsIgnoreCase("LOAN");
    }

    public boolean isCloseLoanAsObligationsMet() {
        return this.actionName.equalsIgnoreCase("CLOSE") && this.entityName.equalsIgnoreCase("LOAN");
    }

    public boolean isCloseLoanAsRescheduled() {
        return this.actionName.equalsIgnoreCase("CLOSEASRESCHEDULED") && this.entityName.equalsIgnoreCase("LOAN");
    }

    public boolean isAddLoanCharge() {
        return this.actionName.equalsIgnoreCase("CREATE") && this.entityName.equalsIgnoreCase("LOANCHARGE");
    }

    public boolean isDeleteLoanCharge() {
        return isDeleteOperation() && this.entityName.equalsIgnoreCase("LOANCHARGE");
    }

    public boolean isUpdateLoanCharge() {
        return this.actionName.equalsIgnoreCase("UPDATE") && this.entityName.equalsIgnoreCase("LOANCHARGE");
    }

    public boolean isWaiveLoanCharge() {
        return this.actionName.equalsIgnoreCase("WAIVE") && this.entityName.equalsIgnoreCase("LOANCHARGE");
    }

    public boolean isUpdateLoanOfficer() {
        return this.actionName.equalsIgnoreCase("UPDATELOANOFFICER") && this.entityName.equalsIgnoreCase("LOAN");
    }

    public boolean isRemoveLoanOfficer() {
        return this.actionName.equalsIgnoreCase("REMOVELOANOFFICER") && this.entityName.equalsIgnoreCase("LOAN");
    }

    public boolean isBulkUpdateLoanOfficer() {
        return this.actionName.equalsIgnoreCase("BULKREASSIGN") && this.entityName.equalsIgnoreCase("LOAN");
    }

    public boolean isDatatableResource() {
        return this.href.startsWith("/datatables/");
    }

    public boolean isDeleteOneToOne() {
        /* also covers case of deleting all of a one to many */
        return isDatatableResource() && isDeleteOperation() && this.subentityId == null;
    }

    public boolean isDeleteMultiple() {
        return isDatatableResource() && isDeleteOperation() && this.subentityId != null;
    }

    public boolean isUpdateOneToOne() {
        return isDatatableResource() && isUpdateOperation() && this.subentityId == null;
    }

    public boolean isUpdateMultiple() {
        return isDatatableResource() && isUpdateOperation() && this.subentityId != null;
    }

    public boolean isUnassignStaff() {
        return this.actionName.equalsIgnoreCase("UNASSIGNSTAFF") && this.entityName.equalsIgnoreCase("GROUP");
    }

    public String commandName() {
        return this.actionName + "_" + this.entityName;
    }

    public boolean isUpdateOfOwnUserDetails(final Long loggedInUserId) {
        return this.isUserResource() && isUpdate() && loggedInUserId.equals(this.entityId);
    }

    public boolean isDepositProductResource() {
        return this.entityName.equalsIgnoreCase("DEPOSITPRODUCT");
    }

    public boolean isDepositAccountResource() {
        return this.entityName.equalsIgnoreCase("DEPOSITACCOUNT");
    }

    public boolean isApprovalOfDeposit() {
        return this.actionName.equalsIgnoreCase("APPROVE") && this.entityName.equalsIgnoreCase("DEPOSITACCOUNT");
    }

    public boolean isWithdrawDepositAmount() {
        return this.actionName.equalsIgnoreCase("WITHDRAWAL") && this.entityName.equalsIgnoreCase("DEPOSITACCOUNT");
    }

    public boolean isWithdrawInterest() {
        return this.actionName.equalsIgnoreCase("INTEREST") && this.entityName.equalsIgnoreCase("DEPOSITACCOUNT");
    }

    public boolean isRenewOfDepositApplicaion() {
        return this.actionName.equalsIgnoreCase("RENEW") && this.entityName.equalsIgnoreCase("DEPOSITACCOUNT");
    }

    public boolean isRejectionOfDepositApplicaion() {
        return this.actionName.equalsIgnoreCase("REJECT") && this.entityName.equalsIgnoreCase("DEPOSITACCOUNT");
    }

    public boolean isWithdrawByApplicant() {
        return this.actionName.equalsIgnoreCase("WITHDRAW") && this.entityName.equalsIgnoreCase("DEPOSITACCOUNT");
    }

    public boolean isUndoApprovalOfDepositApplication() {
        return this.actionName.equalsIgnoreCase("APPROVALUNDO") && this.entityName.equalsIgnoreCase("DEPOSITACCOUNT");
    }

    public boolean isSavingsProductResource() {
        return this.entityName.equalsIgnoreCase("SAVINGSPRODUCT");
    }

    public boolean isSavingsAccountResource() {
        return this.entityName.equalsIgnoreCase("SAVINGSACCOUNT");
    }

    public boolean isSavingsAccountActivation() {
        return this.actionName.equalsIgnoreCase("ACTIVATE") && this.entityName.equalsIgnoreCase("SAVINGSACCOUNT");
    }

    public boolean isSavingsAccountDeposit() {
        return this.actionName.equalsIgnoreCase("DEPOSIT") && this.entityName.equalsIgnoreCase("SAVINGSACCOUNT");
    }

    public boolean isSavingsAccountWithdrawal() {
        return this.actionName.equalsIgnoreCase("WITHDRAWAL") && this.entityName.equalsIgnoreCase("SAVINGSACCOUNT");
    }

    public boolean isSavingsAccountInterestCalculation() {
        return this.actionName.equalsIgnoreCase("CALCULATEINTEREST") && this.entityName.equalsIgnoreCase("SAVINGSACCOUNT");
    }

    public boolean isSavingsAccountInterestPosting() {
        return this.actionName.equalsIgnoreCase("POSTINTEREST") && this.entityName.equalsIgnoreCase("SAVINGSACCOUNT");
    }

    public boolean isCalendarResource() {
        return this.entityName.equalsIgnoreCase("CALENDAR");
    }

    public boolean isNoteResource() {
        boolean isnoteResource = false;
        if (this.entityName.equalsIgnoreCase("CLIENTNOTE") || this.entityName.equalsIgnoreCase("LOANNOTE")
                || this.entityName.equalsIgnoreCase("LOANTRANSACTIONNOTE") || this.entityName.equalsIgnoreCase("SAVINGNOTE")
                || this.entityName.equalsIgnoreCase("GROUPNOTE")) {
            isnoteResource = true;
        }
        return isnoteResource;
    }

    public boolean isGroupResource() {
        return this.entityName.equalsIgnoreCase("GROUP");
    }

    public boolean isCollectionSheetResource() {
        return this.entityName.equals("COLLECTIONSHEET");
    }

    public boolean isCenterResource() {
        return this.entityName.equalsIgnoreCase("CENTER");
    }

    public boolean isReportResource() {
        return this.entityName.equalsIgnoreCase("REPORT");
    }
    
    public boolean isServiceResource() {
        return this.entityName.equalsIgnoreCase("SERVICE");
    }

	public boolean isContractResource() {
		 return this.entityName.equalsIgnoreCase("CONTRACT");

	}

	public boolean isPlanResource() {
		 return this.entityName.equalsIgnoreCase("PLAN");
	}

	public boolean isPriceResource() {
		 return this.entityName.equalsIgnoreCase("PRICE");
	}

	public boolean isOrderResource() {
		return this.entityName.equalsIgnoreCase("ORDER");
	}

	public boolean isOneTimeSaleResource() {
		return this.entityName.equalsIgnoreCase("ONETIMESALE");
	}

	public boolean isItemResource() {
		return this.entityName.equalsIgnoreCase("ITEM");
	}

	public boolean isUpdatePrice() {
		 return this.actionName.equalsIgnoreCase("UPDATE");
	}

	public boolean isAddressResource() {
		return this.entityName.equalsIgnoreCase("ADDRESS") || this.entityName.equalsIgnoreCase("city") ||
				this.entityName.equalsIgnoreCase("state") || this.entityName.equalsIgnoreCase("country");
	}

	public boolean isOrderPriceResource() {
		 return this.entityName.equalsIgnoreCase("ORDERPRICE");
	}

	public boolean isInvoiceResource() {
		 return this.entityName.equalsIgnoreCase("INVOICE");
	}

	public boolean isAdjustmentResource() {
		return this.entityName.equalsIgnoreCase("ADJUSTMENT");
	}

	public boolean isPaymentResource() {
		return this.entityName.equalsIgnoreCase("PAYMENT");
	}

	public boolean isPaymodeResource() {
		return this.entityName.equalsIgnoreCase("PAYMODE");
	}

	public boolean isBillMasterResource() {
		return this.entityName.equalsIgnoreCase("BILLMASTER");
	}

	public boolean isCalculatePrice() {
		return this.entityName.equalsIgnoreCase("ONETIMESALE");
	}
	public boolean isInventoryResource() {
		return this.entityName.equalsIgnoreCase("INVENTORY");
	}
	public boolean isAllocateHardwareResource(){
		return this.entityName.equalsIgnoreCase("ALLOCATION");
	}
	public boolean isUploadStatusResource() {
		return this.entityName.equalsIgnoreCase("UPLOADSTATUS");
	}
	 public boolean isTicketResource() {
	    	return this.entityName.equalsIgnoreCase("TICKET");
	    }
	 
	    public boolean isCreateTicket() {
	    	return this.actionName.equalsIgnoreCase("Create") && this.entityName.equalsIgnoreCase("TICKET");
	    }
	    
	    public boolean isUpdateTicket() {
	    	return this.actionName.equalsIgnoreCase("UPDATE") && this.entityName.equalsIgnoreCase("TICKET"); 
	    }
     public boolean isCloseTicket() {
        return this.actionName.equalsIgnoreCase("CLOSE") && this.entityName.equalsIgnoreCase("TICKET");
       }
       
       public boolean isEventResource() {
        return this.entityName.equalsIgnoreCase("EVENT");
       }
       
       public boolean isCreateEvent () {
        return this.actionName.equalsIgnoreCase("CREATE") && this.entityName.equalsIgnoreCase("EVENT");
       }
       
       public boolean isUpdateEvent () {
        return this.actionName.equalsIgnoreCase("UPDATE") && this.entityName.equalsIgnoreCase("EVENT");
       }
       public boolean isCloseEvent () {
        return this.actionName.equalsIgnoreCase("DELETE") && this.entityName.equalsIgnoreCase("EVENT");
       }
       
       public boolean isEventPricingResource() {
        return this.entityName.equalsIgnoreCase("EVENTPRICE");
       }
       
       public boolean isCreateEventPrice () {
        return this.actionName.equalsIgnoreCase("CREATE") && this.entityName.equalsIgnoreCase("EVENTPRICE");
       }
       
       public boolean isUpdateEventPrice () {
        return this.actionName.equalsIgnoreCase("UPDATE") && this.entityName.equalsIgnoreCase("EVENTPRICE");
       }
       public boolean isCloseEventPrice () {
        return this.actionName.equalsIgnoreCase("DELETE") && this.entityName.equalsIgnoreCase("EVENTPRICE");
       }
		public boolean isNewRecord() {
			return this.actionName.equalsIgnoreCase("UPDATE") || this.entityName.equalsIgnoreCase("city") 
					||this.entityName.equalsIgnoreCase("state") || this.entityName.equalsIgnoreCase("country");
		}

		public boolean isEventOrderResource() {
			 return this.entityName.equalsIgnoreCase("EVENTORDER");
		}

		public boolean isCreateEventOrder() {
			  return this.actionName.equalsIgnoreCase("CREATE") && this.entityName.equalsIgnoreCase("EVENTORDER");
		} 
		public boolean isInventoryItemAllocatable(){
		  	return this.actionName.equalsIgnoreCase("INSERT") && this.entityName.equalsIgnoreCase("ALLOCATION");
		}
		public boolean isGrnResource(){
			return this.entityName.equalsIgnoreCase("GRN");
		}

		public boolean isMediaAssetResource() {
			 return this.entityName.equalsIgnoreCase("MEDIAASSET");
		}
		public boolean isCreateMediaAsset() {
			  return this.actionName.equalsIgnoreCase("CREATE") && this.entityName.equalsIgnoreCase("MEDIAASSET");
		}

		public boolean isUpdateMediaAsset() {
			 return this.actionName.equalsIgnoreCase("UPDATE") && this.entityName.equalsIgnoreCase("MEDIAASSET");
		}

		public boolean isCloseMediaAsset() {
			 return this.actionName.equalsIgnoreCase("DELETE" +	"" ) && this.entityName.equalsIgnoreCase("MEDIAASSET");

		}

		public boolean isRenewOrder() {
			  return this.actionName.equalsIgnoreCase("RENEWAL") && this.entityName.equalsIgnoreCase("ORDER");
		}

		public boolean isReconnectOrder() {
			  return this.actionName.equalsIgnoreCase("RECONNECT") && this.entityName.equalsIgnoreCase("ORDER");
		} 


		public boolean isCreateBillingMessage() {
			// TODO Auto-generated method stub
			 return this.actionName.equalsIgnoreCase("CREATE") && this.entityName.equalsIgnoreCase("BILLINGMESSAGE");
		}

		public boolean isCreateMessageData() {
			// TODO Auto-generated method stub
			return this.actionName.equalsIgnoreCase("CREATEDATA") && this.entityName.equalsIgnoreCase("BILLINGMESSAGE");
		}

		public boolean isMessageResource() {
			
			return this.entityName.equalsIgnoreCase("BILLINGMESSAGE");
		}

		public boolean isUpdateBillingMessage() {
			// TODO Auto-generated method stub
			return this.actionName.equalsIgnoreCase("UPDATE") && this.entityName.equalsIgnoreCase("BILLINGMESSAGE");
		}

		public boolean isTaxMapResource(){
			return this.entityName.equalsIgnoreCase("TAXMAPPING");
		}
		public boolean isChargeCodeResource(){
			return this.entityName.equalsIgnoreCase("CHARGECODE");
		}
		public boolean isUpdateChargeCode(){
			return this.actionName.equalsIgnoreCase("UPDATE") && this.entityName.equalsIgnoreCase("CHARGECODE");
		}
		public boolean isUpdateTaxMap(){
			return this.actionName.equalsIgnoreCase("UPDATE") && this.entityName.equalsIgnoreCase("TAXMAPPING");	
		}
		
		public boolean isBatch(){
			return this.entityName.equalsIgnoreCase("BATCH");
		}
		public boolean isSchedulling(){
			return this.entityName.equalsIgnoreCase("SCHEDULE");
		}
}