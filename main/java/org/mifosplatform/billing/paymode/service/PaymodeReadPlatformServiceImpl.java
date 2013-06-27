package org.mifosplatform.billing.paymode.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import org.mifosplatform.billing.paymode.data.McodeData;
import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.service.TenantAwareRoutingDataSource;
import org.mifosplatform.infrastructure.security.service.PlatformSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class PaymodeReadPlatformServiceImpl implements
		PaymodeReadPlatformService {

	private final JdbcTemplate jdbcTemplate;
	private final PlatformSecurityContext context;

	@Autowired
	public PaymodeReadPlatformServiceImpl(
			final PlatformSecurityContext context,
			final TenantAwareRoutingDataSource dataSource) {
		this.context = context;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private static final class PaymodeMapper implements RowMapper<McodeData> {

		public String codeScheme() {
			return "b.id,code_value from m_code a, m_code_value b where a.id = b.code_id ";
		}
		
		@Override
		public McodeData mapRow(ResultSet rs, int rowNum) throws SQLException {
			Long id = rs.getLong("id");
			String paymodeCode = rs.getString("code_value");

			return  McodeData.instance(id, paymodeCode);
		}

	}

	@Transactional
	@Override
	public Collection<McodeData> retrievemCodeDetails(String codeName) {
		PaymodeMapper mapper = new PaymodeMapper();
		String sql = "select " + mapper.codeScheme()+" and code_name=?";

		return this.jdbcTemplate.query(sql, mapper, new Object[] { codeName });
	}

	@Override
	public McodeData retrieveSinglePaymode(Long paymodeId) {
		PaymodeMapper mapper = new PaymodeMapper();
		String sql = "select " + mapper.codeScheme() + " and b.id="+ paymodeId;

		return this.jdbcTemplate.queryForObject(sql, mapper, new Object[] {});
	}

@Override
public McodeData retrievePaymodeCode(JsonCommand command) {
	PaymodeMapper1 mapper = new PaymodeMapper1();
	String sql = "select id from m_code where code_name='" +command.stringValueOfParameterNamed("code_id")+"'";

	return this.jdbcTemplate.queryForObject(sql, mapper, new Object[] {});
}
private static final class PaymodeMapper1 implements RowMapper<McodeData> {

	@Override
	public McodeData mapRow(ResultSet rs, int rowNum) throws SQLException {
		Long id = rs.getLong("id");
		return  McodeData.instance1(id);
	}

}
}
