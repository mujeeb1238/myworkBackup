package org.mifosplatform.billing.eventorder.service;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.mifosplatform.billing.onetimesale.data.OneTimeSaleData;
import org.mifosplatform.infrastructure.core.service.TenantAwareRoutingDataSource;
import org.mifosplatform.infrastructure.security.service.PlatformSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class EventOrderReadplatformServieImpl implements EventOrderReadplatformServie {

	private final JdbcTemplate jdbcTemplate;
	private final PlatformSecurityContext context;

	@Autowired
	public EventOrderReadplatformServieImpl(final PlatformSecurityContext context,final TenantAwareRoutingDataSource dataSource) {
		this.context = context;
		this.jdbcTemplate = new JdbcTemplate(dataSource);

	}

	

	@Override
	public List<OneTimeSaleData> retrieveEventOrderData(Long clientId) {
		EventOrderDataMapper mapper = new EventOrderDataMapper();

		String sql = "select " + mapper.schema()
				+ " where e.client_id = ? ";

		return this.jdbcTemplate.query(sql, mapper, new Object[] { clientId });
	}

	private static final class EventOrderDataMapper implements
			RowMapper<OneTimeSaleData> {

		public String schema() {
			return " e.id as orderid,e.client_id as clientId,e.event_id as eventId,e.eventprice_id as eventpriceId,0 as movieLink,e.booked_price as bookedPrice,"
			     +" e.charge_code as chargeCode,e.is_invoiced as isInvoiced from b_eventorder e ";

		}

		@Override
		public OneTimeSaleData mapRow(ResultSet rs, int rowNum)
				throws SQLException {

			Long orderid = rs.getLong("orderid");
			Long clientId = rs.getLong("clientId");
			String chargeCode = rs.getString("chargeCode");
			BigDecimal bookedPrice = rs.getBigDecimal("bookedPrice");
			String isInvoiced = rs.getString("isInvoiced");
			return new OneTimeSaleData(orderid,clientId, null, chargeCode, null,null, bookedPrice,isInvoiced,orderid);

		}
	}
}

