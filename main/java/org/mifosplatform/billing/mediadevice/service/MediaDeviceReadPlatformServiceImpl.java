package org.mifosplatform.billing.mediadevice.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.mifosplatform.billing.mediadevice.data.MediaDeviceData;
import org.mifosplatform.infrastructure.core.service.TenantAwareRoutingDataSource;
import org.mifosplatform.infrastructure.security.service.PlatformSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;


@Service
public class MediaDeviceReadPlatformServiceImpl implements MediaDeviceReadPlatformService{
	
	private final PlatformSecurityContext context;
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public MediaDeviceReadPlatformServiceImpl (final PlatformSecurityContext context,final TenantAwareRoutingDataSource dataSource) {
		this.context = context;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	

	@Override
	public MediaDeviceData retrieveDeviceDetails(String deviceId) {
		try {
			final EventMasterMapper eventMasterMapper = new EventMasterMapper();
			
			final String sql = "SELECT " + eventMasterMapper.eventMasterSchema()+"and a.serial_no=?";
			return jdbcTemplate.queryForObject(sql, eventMasterMapper,new Object[] { deviceId });
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	private static final class EventMasterMapper implements RowMapper<MediaDeviceData> {
		public String eventMasterSchema() {
			return "  a.id AS deviceId,a.client_id AS clientId,mc.code_value AS clientType,mc.id as clientTypeId"
					+" FROM b_allocation a, m_client c,m_code_value mc WHERE a.client_id = c.id AND	 mc.id=c.category_type "; 
  
		}
		@Override
		public MediaDeviceData mapRow(final ResultSet rs, final int rowNum) throws SQLException {
			
			final Long deviceId = rs.getLong("deviceId");
			final Long clientId = rs.getLong("clientId");
			final String clientType = rs.getString("clientType");
			final int clientTypeId = rs.getInt("clientTypeId");
			return new MediaDeviceData(deviceId,clientId,clientType,clientTypeId);
		}
	}

}
