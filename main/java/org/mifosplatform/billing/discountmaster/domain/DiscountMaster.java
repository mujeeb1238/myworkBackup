package org.mifosplatform.billing.discountmaster.domain;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "b_discount_master")
public class DiscountMaster extends AbstractPersistable<Long> {

	/*@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;*/

	@Column(name = "discount_code")
	private String discountCode;

	
	@Column(name = "discount_description")
	private String discountDescription;

	@Column(name = "discount_type")
	private String discountType;

	@Column(name = "start_date")
	private Date startDate;

	@Column(name = "discount_rate")
	private BigDecimal discountRate;

	@Column(name = "discount_status")
	private String discountStatus;


	

	

	 public DiscountMaster() {
		// TODO Auto-generated constructor stub
			
	}






	public String getDiscountCode() {
		return discountCode;
	}






	public String getDiscountDescription() {
		return discountDescription;
	}






	public String getDiscountType() {
		return discountType;
	}






	public Date getStartDate() {
		return startDate;
	}






	public BigDecimal getDiscountRate() {
		return discountRate;
	}






	public String getDiscountStatus() {
		return discountStatus;
	}
 


}
