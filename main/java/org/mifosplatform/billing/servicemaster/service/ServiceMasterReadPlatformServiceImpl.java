package org.mifosplatform.billing.servicemaster.service;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.mifosplatform.billing.service.ServiceMasterReadPlatformService;
import org.mifosplatform.billing.servicemaster.data.SericeMasterOptionsData;
import org.mifosplatform.billing.servicemaster.data.ServiceMasterData;
import org.mifosplatform.infrastructure.core.service.TenantAwareRoutingDataSource;
import org.mifosplatform.infrastructure.security.service.PlatformSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
@Service
public class ServiceMasterReadPlatformServiceImpl implements  ServiceMasterReadPlatformService{

	private final JdbcTemplate jdbcTemplate;
	private final PlatformSecurityContext context;

	@Autowired
	public  ServiceMasterReadPlatformServiceImpl(final PlatformSecurityContext context, final TenantAwareRoutingDataSource dataSource) {
		this.context = context;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public Collection<ServiceMasterData> retrieveAllServiceMasterData() {
		this.context.authenticatedUser();

		ServiceMasterMapper mapper = new ServiceMasterMapper();
		String sql = "select " + mapper.schema();

		return this.jdbcTemplate.query(sql, mapper, new Object[] {});

	}

	protected static final class ServiceMasterMapper implements RowMapper<ServiceMasterData> {

		@Override
		public ServiceMasterData mapRow(final ResultSet rs, @SuppressWarnings("unused") final int rowNum)
				throws SQLException {
			String discounType = rs.getString("serviceType");
			String discountValue=rs.getString("categoryType");


			return new ServiceMasterData(discounType,discountValue);

		}


		public String schema() {
			return "d.servicetype as servicetype , d.categorytype as categorytype from m_servicemaster_type d";
		}
	}

	@Override
	public List<SericeMasterOptionsData> retrieveServices() {
		this.context.authenticatedUser();

		ServiceMapper mapper = new ServiceMapper();
		String sql = "select " + mapper.schema()+" where d.is_deleted='n' ";

		return this.jdbcTemplate.query(sql, mapper, new Object[] {});

	}

	protected static final class ServiceMapper implements RowMapper<SericeMasterOptionsData> {

		@Override
		public SericeMasterOptionsData mapRow(final ResultSet rs, @SuppressWarnings("unused") final int rowNum)
				throws SQLException {
			Long id=rs.getLong("id");
			String serviceCode = rs.getString("serviceCode");
			String serviceDescription=rs.getString("serviceDescription");


			return new SericeMasterOptionsData(id,serviceCode,serviceDescription,null);

		}


		public String schema() {
			return "d.id as id,d.service_code as serviceCode , d.service_description as serviceDescription,d.type as serviceType from b_service d";
		}

}

	@Override
	public SericeMasterOptionsData retrieveIndividualService(Long serviceId) {
		ServicesMapper mapper = new ServicesMapper();
		String sql = "select " + mapper.schema()+" where d.id="+serviceId;

		return this.jdbcTemplate.queryForObject(sql, mapper, new Object[] {});

	}

	protected static final class ServicesMapper implements RowMapper<SericeMasterOptionsData> {

		@Override
		public SericeMasterOptionsData mapRow(final ResultSet rs, @SuppressWarnings("unused") final int rowNum)
				throws SQLException {
			Long id=rs.getLong("id");
			String serviceCode = rs.getString("serviceCode");
			String serviceDescription=rs.getString("serviceDescription");
			String serviceType=rs.getString("serviceType");


			return new SericeMasterOptionsData(id,serviceCode,serviceDescription,serviceType);

		}


		public String schema() {
			return "d.id as id,d.service_code as serviceCode , d.service_description as serviceDescription,d.type as serviceType from b_service d";
		}
}
}
