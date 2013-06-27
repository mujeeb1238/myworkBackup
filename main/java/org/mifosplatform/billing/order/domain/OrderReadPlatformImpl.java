package org.mifosplatform.billing.order.domain;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.joda.time.LocalDate;
import org.mifosplatform.billing.plan.data.PlanData;
import org.mifosplatform.billing.plan.data.ServiceData;
import org.mifosplatform.billing.pricing.data.PriceData;
import org.mifosplatform.infrastructure.core.domain.JdbcSupport;
import org.mifosplatform.infrastructure.security.service.PlatformSecurityContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class OrderReadPlatformImpl {

	private final JdbcTemplate jdbcTemplate;
	private final PlatformSecurityContext context;



	public OrderReadPlatformImpl(PlatformSecurityContext context2,
			JdbcTemplate jdbcTemplate2) {

		this.context=context2;
		this.jdbcTemplate=jdbcTemplate2;

	}



	public List<ServiceData> retrieveAllServices(Long plan_code) {


		PlanMapper mapper = new PlanMapper();

		String sql = "select " + mapper.schema()+" and da.plan_id = '"+plan_code+"'" ;
		return this.jdbcTemplate.query(sql, mapper, new Object[] {});

	}

	private static final class PlanMapper implements RowMapper<ServiceData> {

		public String schema() {
			return "da.id as id,se.id as serviceId, da.service_code as service_code, da.plan_id as plan_code"
					+" from b_plan_detail da,b_service se where da.service_code = se.service_code";

		}

		@Override
		public ServiceData mapRow(final ResultSet rs,
				@SuppressWarnings("unused") final int rowNum)
				throws SQLException {
			Long id = rs.getLong("id");
			String serviceCode = rs.getString("service_code");
			String planCode = rs.getString("plan_code");
			Long serviceid = rs.getLong("serviceId");




			return new ServiceData(id,serviceid,serviceCode, planCode,null,null);

		}
	}
		public List<PriceData> retrieveAllPrices(Long plan_code,String billingFreq) {


			PriceMapper mapper1 = new PriceMapper();

			String sql = "select " + mapper1.schema()+" and da.plan_id = '"+plan_code+"' and (c.billfrequency_code='"+billingFreq+"'  or c.billfrequency_code='Once')";
			return this.jdbcTemplate.query(sql, mapper1, new Object[] {});

		}

		private static final class PriceMapper implements RowMapper<PriceData> {

			public String schema() {
				return "da.id as id, se.id as serviceId,da.service_code as service_code, da.charge_code as charge_code,da.charging_variant as charging_variant,"
                      +" c.charge_type as charge_type,c.charge_duration as charge_duration,c.duration_type as duration_type, da.discount_id as discountId,"
					  +" da.price as price from b_plan_pricing da,b_charge_codes c,b_service se where da.charge_code = c.charge_code and da.service_code=se.service_code and da.is_deleted='n' ";

			}

			@Override
			public PriceData mapRow(final ResultSet rs,
					@SuppressWarnings("unused") final int rowNum)
					throws SQLException {

				Long id = rs.getLong("id");
				String serviceCode = rs.getString("service_code");
				String chargeCode = rs.getString("charge_code");
				String chargingVariant = rs.getString("charging_variant");
				BigDecimal price=rs.getBigDecimal("price");
				String chargeType = rs.getString("charge_type");
				String chargeDuration = rs.getString("charge_duration");
				String durationType = rs.getString("duration_type");
				Long serviceid = rs.getLong("serviceId");
				Long discountId=rs.getLong("discountId");
				return new PriceData(id, serviceCode, chargeCode,chargingVariant,price,chargeType,chargeDuration,durationType,serviceid,discountId);

			}
	}
/*public PlanData retrievePlanData(Long id) {


	PlanMapper1 mapper2 = new PlanMapper1();

			String sql = "select " + mapper2.schema()+" where da.id = "+id ;
			return this.jdbcTemplate.queryForObject(sql, mapper2, new Object[] {});

		}

		private static final class PlanMapper1 implements RowMapper<PlanData> {

			public String schema() {
				return "da.id as id, da.plan_code as plan_code, da.start_date as start_date,da.end_date as end_date,"
						+ "da.plan_status as plan_status, da.contract_period as contract_period from b_plan_master da";

			}

			@Override
			public PlanData mapRow(final ResultSet rs,
					@SuppressWarnings("unused") final int rowNum)
					throws SQLException {

				Long id = rs.getLong("id");
				String planCode = rs.getString("plan_code");
				Long chargeCode =rs.getLong("plan_status");
				String contractPeriod = rs.getString("contract_period");
				LocalDate startDate=JdbcSupport.getLocalDate(rs,"start_date");
				LocalDate endDate=JdbcSupport.getLocalDate(rs,"end_date");



				return new PlanData(id, planCode, chargeCode,contractPeriod,startDate,endDate);

			}*/
	}



