package org.mifosplatform.billing.service;

import java.math.BigDecimal;
import java.util.Date;

public class DiscountMasterData {
	private long id;
	private String discountCode;
	private String discountDescription;
	private String discounType;
	private BigDecimal discountValue;
	private Date discountStartDate;
	private Date discountEndDate;
	private String isDeleted;
	private Long orderPriceId;
	private Long discountMasterId;
	

	public DiscountMasterData(long id, long discountCode,
			String discountDescription, String discounType, BigDecimal discountValue) {
		this.id = id;
		this.discountDescription = discountDescription;
		this.discounType = discounType;
		this.discountValue = discountValue;

	}

	public DiscountMasterData(Long id, String discountcode, String discountdesc) {
		this.id = id;
		this.discountCode = discountcode;
		this.discountDescription = discountdesc;
		this.discounType = null;
		// this.discountValue=;
	}

	// discount master manoj
	public DiscountMasterData(Long discountMasterid, Long orderPriceId,
			Long discountMasterId, Date discountStartDate,
			Date discountEndDate, String discountType, BigDecimal discountRate,
			String isDeleted) {
		this.id = discountMasterid;
		this.orderPriceId = orderPriceId;
		this.discountMasterId = discountMasterid;
		this.discountStartDate = discountStartDate;
		this.discountEndDate = discountEndDate;
		this.discounType = discountType;
		this.discountValue = discountRate;
		this.isDeleted = isDeleted;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDiscountCode() {
		return discountCode;
	}

	public void setDiscountCode(String discountCode) {
		this.discountCode = discountCode;
	}

	public String getDiscountDescription() {
		return discountDescription;
	}

	public void setDiscountDescription(String discountDescription) {
		this.discountDescription = discountDescription;
	}

	public String getDiscounType() {
		return discounType;
	}

	public void setDiscounType(String discounType) {
		this.discounType = discounType;
	}

	public BigDecimal getDiscountValue() {
		return discountValue;
	}

	public void setDiscountValue(BigDecimal discountValue) {
		this.discountValue = discountValue;
	}

	public Date getDiscountStartDate() {
		return discountStartDate;
	}

	public void setDiscountStartDate(Date discountStartDate) {
		this.discountStartDate = discountStartDate;
	}

	public Date getDiscountEndDate() {
		return discountEndDate;
	}

	public void setDiscountEndDate(Date discountEndDate) {
		this.discountEndDate = discountEndDate;
	}

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Long getOrderPriceId() {
		return orderPriceId;
	}

	public void setOrderPriceId(Long orderPriceId) {
		this.orderPriceId = orderPriceId;
	}

	public Long getDiscountMasterId() {
		return discountMasterId;
	}

	public void setDiscountMasterId(Long discountMasterId) {
		this.discountMasterId = discountMasterId;
	}
	
	
}
