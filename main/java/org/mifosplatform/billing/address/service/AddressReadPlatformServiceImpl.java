package org.mifosplatform.billing.address.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.mifosplatform.billing.address.data.AddressData;
import org.mifosplatform.infrastructure.core.service.TenantAwareRoutingDataSource;
import org.mifosplatform.infrastructure.security.service.PlatformSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;



@Service
public class AddressReadPlatformServiceImpl implements AddressReadPlatformService {
	
	private final JdbcTemplate jdbcTemplate;
	private final PlatformSecurityContext context;

	@Autowired
	public AddressReadPlatformServiceImpl(final PlatformSecurityContext context,
			final TenantAwareRoutingDataSource dataSource) {
		this.context = context;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}


	@Override
	public List<AddressData> retrieveAddressDetails(Long clientId) {

		try{
		context.authenticatedUser();
		AddressMapper mapper = new AddressMapper();

		String sql = "select " + mapper.schema()+" where is_deleted='n' and a.client_id="+clientId;

		return this.jdbcTemplate.query(sql, mapper, new Object[] {});
		}catch (final EmptyResultDataAccessException e) {
			return null;
		}
	}

	private static final class AddressMapper implements RowMapper<AddressData> {

		public String schema() {
			return "a.address_id as id,a.client_id as clientId,a.address_key as addressKey,"
				+"a.address_no as addressNo,a.street as street,a.zip as zip,a.city as city,"
				+"a.state as state,a.country as country from b_client_address a";

		}

		@Override
		public AddressData mapRow(final ResultSet rs,
				@SuppressWarnings("unused") final int rowNum)
				throws SQLException {

			Long id = rs.getLong("id");
			Long clientId = rs.getLong("clientId");
			String addressKey = rs.getString("addressKey");
			String addressNo = rs.getString("addressNo");
			String street = rs.getString("street");
			String zip = rs.getString("zip");
			String city = rs.getString("city");
			String state = rs.getString("state");
			String country = rs.getString("country");
			//String serviceDescription = rs.getString("service_description");
			return new AddressData(id, clientId, addressKey, addressNo, street, zip, city, state, country);

		}
	}

	@Override
	public List<AddressData> retrieveSelectedAddressDetails(String selectedname) {
		
		AddressMapper mapper = new AddressMapper();
		String sql = "select " + mapper.schema()+" where a.city=? or a.state =? or a.country =? and a.is_deleted='n'";

		return this.jdbcTemplate.query(sql, mapper, new Object[]  { selectedname,selectedname,selectedname });
	}
	@Override
	public List<AddressData> retrieveAddressDetails() {

		context.authenticatedUser();
		AddressMapper mapper = new AddressMapper();

		String sql = "select " + mapper.schema()+" where is_deleted='n'";

		return this.jdbcTemplate.query(sql, mapper, new Object[] {});

	}


	@Override
	public List<String> retrieveCountryDetails() {
		context.authenticatedUser();
		AddressMapper1 mapper = new AddressMapper1();

		String sql = "select " + mapper.sqlschema("country_name","country");

		return this.jdbcTemplate.query(sql, mapper, new Object[] {});

	}

	private static final class AddressMapper1 implements RowMapper<String> {

		public String sqlschema(String placeholder,String tablename) {
			return placeholder+" as data from b_"+tablename;

		}

		@Override
		public String mapRow(final ResultSet rs,
				@SuppressWarnings("unused") final int rowNum)
				throws SQLException {

			
			String country = rs.getString("data");
			return country;
		

		}

	
	}

	@Override
	public List<String> retrieveStateDetails() {
		context.authenticatedUser();
		AddressMapper1 mapper = new AddressMapper1();

		String sql = "select " + mapper.sqlschema("state_name","state");

		return this.jdbcTemplate.query(sql, mapper, new Object[] {});

	}


	@Override
	public List<String> retrieveCityDetails() {
		context.authenticatedUser();
		AddressMapper1 mapper = new AddressMapper1();

		String sql = "select " + mapper.sqlschema("city_name","city");

		return this.jdbcTemplate.query(sql, mapper, new Object[] {});

	}


	@Override
	public List<AddressData> retrieveCityDetails(String selectedname) {
		context.authenticatedUser();
		DataMapper mapper = new DataMapper();

		String sql = "select " + mapper.schema(selectedname);

		return this.jdbcTemplate.query(sql, mapper, new Object[] {});

	}

	private static final class DataMapper implements RowMapper<AddressData> {

		public String schema(String placeHolder) {
			return "id as id,"+placeHolder+"_name as data from b_"+placeHolder;

		}

		@Override
		public AddressData mapRow(final ResultSet rs,
				@SuppressWarnings("unused") final int rowNum)
				throws SQLException {

			Long id = rs.getLong("id");
			String data = rs.getString("data");
		
			//String serviceDescription = rs.getString("service_description");
			return new AddressData(id,data);

		}
	}
}
