package org.mifosplatform.billing.preparerequest.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.mifosplatform.billing.eventorder.domain.PrepareRequsetRepository;
import org.mifosplatform.billing.order.domain.Order;
import org.mifosplatform.billing.order.domain.OrderLine;
import org.mifosplatform.billing.order.domain.OrderRepository;
import org.mifosplatform.billing.preparerequest.data.PrepareRequestData;
import org.mifosplatform.billing.processrequest.domain.ProcessRequest;
import org.mifosplatform.billing.processrequest.domain.ProcessRequestDetails;
import org.mifosplatform.billing.processrequest.domain.ProcessRequestRepository;
import org.mifosplatform.infrastructure.core.domain.MifosPlatformTenant;
import org.mifosplatform.infrastructure.core.service.DataSourcePerTenantService;
import org.mifosplatform.infrastructure.core.service.ThreadLocalContextUtil;
import org.mifosplatform.infrastructure.security.service.TenantDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class PrepareRequestReadplatformServiceImpl  implements PrepareRequestReadplatformService{

	
	  private final TenantDetailsService tenantDetailsService;
	  private final DataSourcePerTenantService dataSourcePerTenantService;
	  private final OrderRepository orderRepository;
	  private final ProcessRequestRepository processRequestRepository;
	  private final PrepareRequsetRepository prepareRequsetRepository;
	

	    @Autowired
	    public PrepareRequestReadplatformServiceImpl(final DataSourcePerTenantService dataSourcePerTenantService,
	            final TenantDetailsService tenantDetailsService,final OrderRepository orderRepository,
		   final ProcessRequestRepository processRequestRepository,
		   final PrepareRequsetRepository prepareRequsetRepository) {
	            this.dataSourcePerTenantService = dataSourcePerTenantService;
	            this.tenantDetailsService = tenantDetailsService;
	            this.orderRepository=orderRepository;
	            this.processRequestRepository=processRequestRepository;
	            this.prepareRequsetRepository=prepareRequsetRepository;
	        
	    }

	
	@Override
	public List<PrepareRequestData> retrieveDataForProcessing() {
		try {
			
			  
	        final MifosPlatformTenant tenant = this.tenantDetailsService.loadTenantById("default");
	        ThreadLocalContextUtil.setTenant(tenant);
	        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSourcePerTenantService.retrieveTenantAwareDataSource());
			final ClientOrderMapper mapper = new ClientOrderMapper();

			final String sql = "select " + mapper.clientOrderLookupSchema();

			return jdbcTemplate.query(sql, mapper, new Object[] { });
			} catch (EmptyResultDataAccessException e) {
			return null;
			}

			}

			private static final class ClientOrderMapper implements RowMapper<PrepareRequestData> {

			public String clientOrderLookupSchema() {
			return " p.id AS id,p.client_id AS clientId,p.order_id AS orderId,p.request_type AS requestType,p.gen_request AS generateRequest,"
				  +" a.serial_no as hardwareId FROM b_prepare_request p,b_allocation a WHERE p.is_provisioning = 'N'  and p.client_id = a.client_id";
			}

			@Override
			public PrepareRequestData mapRow(final ResultSet rs, @SuppressWarnings("unused") final int rowNum) throws SQLException {

			final Long id = rs.getLong("id");
			final Long clientId = rs.getLong("clientId");
			final Long orderId = rs.getLong("orderId");
			final String requestType = rs.getString("requestType");
			final String generateRequest = rs.getString("generateRequest");
			final String hardWareId=rs.getString("hardwareId");
			
			return new PrepareRequestData(id, clientId,orderId, requestType, generateRequest,hardWareId);
			}
			}
			
			@Override
			public List<Long> retrieveRequestClientOrderDetails(Long clientId) {
				try {
				
				  final MifosPlatformTenant tenant = this.tenantDetailsService.loadTenantById("default");
			        ThreadLocalContextUtil.setTenant(tenant);
			        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSourcePerTenantService.retrieveTenantAwareDataSource());
			        
				OrderIdMapper planIdMapper = new OrderIdMapper();
				String sql = "select" + planIdMapper.planIdSchema();
				return jdbcTemplate.query(sql, planIdMapper,new Object[] { clientId });
				
			
			} catch (EmptyResultDataAccessException e) {
				return null;
				}
			}
			private static final class OrderIdMapper implements RowMapper<Long> {

				@Override
				public Long  mapRow(ResultSet resultSet, int rowNum) throws SQLException {
//					Long orderId = resultSet.getLong("orderId");
//					String durationType = resultSet.getString("durationType");
//					Date billStartDate = resultSet.getDate("billStartDate");
					return resultSet.getLong("orderId");
				}
				

				public String planIdSchema() {
					return "  os.id as orderId FROM b_orders os where os.client_id=?";
							
				}
				
				
			}

			@Override
			public void processingClientDetails(List<Long> clientOrderIds,
					PrepareRequestData requestData) {

				
				for(Long orderId:clientOrderIds){
					
					 final MifosPlatformTenant tenant = this.tenantDetailsService.loadTenantById("default");
				        ThreadLocalContextUtil.setTenant(tenant);
				        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSourcePerTenantService.retrieveTenantAwareDataSource());
					Order order=this.orderRepository.findOne(orderId);
					
					ProcessRequest processRequest=new ProcessRequest(order.getClientId(),order.getId(),"NONE",requestData.getHardwareId(),order.getStartDate(),
							order.getEndDate(),new Date(),new Date(),"None",'N');
				        
					List<OrderLine> orderLineData=order.getServices();
					  for(OrderLine orderLine:orderLineData){
						  
						  ProcessRequestDetails processRequestDetails=new ProcessRequestDetails(orderLine.getId(),orderLine.getServiceId(),"Sent","Recieved",'N');
						  processRequest.add(processRequestDetails);
						 
					  }
							  
							
							  
						
					
	                       this.processRequestRepository.save(processRequest);				
					
					
				}
	              
				
				
				
			
				
			}

}
