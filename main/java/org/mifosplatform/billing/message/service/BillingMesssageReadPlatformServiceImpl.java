package org.mifosplatform.billing.message.service;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.mifosplatform.billing.message.data.BillingMessageData;
import org.mifosplatform.billing.message.domain.BillingMessage;
import org.mifosplatform.billing.message.domain.BillingMessageTemplate;
import org.mifosplatform.billing.message.domain.BillingMessageTemplateRepository;
import org.mifosplatform.billing.message.domain.MessageDataRepository;
import org.mifosplatform.infrastructure.core.api.JsonCommand;
import org.mifosplatform.infrastructure.core.serialization.FromJsonHelper;
import org.mifosplatform.infrastructure.core.service.TenantAwareRoutingDataSource;
import org.mifosplatform.infrastructure.security.service.PlatformSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.google.gson.JsonElement;

@Service
public class BillingMesssageReadPlatformServiceImpl implements BillingMesssageReadPlatformService
{
	private final JdbcTemplate jdbcTemplate;
	private final PlatformSecurityContext context;
	private final FromJsonHelper fromApiJsonHelper;
	private static List<BillingMessageData> messageparam;
	private static BillingMessageData templateData;
	private static JsonCommand command;
    private static MessageDataRepository messageDataRepository;
    private static BillingMessageTemplateRepository messageTemplateRepository;
	
	@Autowired
   public BillingMesssageReadPlatformServiceImpl( final PlatformSecurityContext context,final TenantAwareRoutingDataSource dataSource,
		 MessageDataRepository messageDataRepository,final FromJsonHelper fromApiJsonHelper,
  		 BillingMessageTemplateRepository messageTemplateRepository)
	{
		this.context=context;
		this.jdbcTemplate=new JdbcTemplate(dataSource);
		this.fromApiJsonHelper = fromApiJsonHelper;
		this.messageDataRepository=messageDataRepository;
		this.messageTemplateRepository=messageTemplateRepository;
	}
	
	
	//for mesage template
	
	@Override
	public List<BillingMessageData> retrieveAllMessageTemplates() {
		// TODO Auto-generated method stub
		
		context.authenticatedUser();
		BillingAllMessageTemplateMapper mapper=new BillingAllMessageTemplateMapper();
		String sql = "select " + mapper.schema();
		
		return this.jdbcTemplate.query(sql, mapper, new Object[] {});
		
	}


	
	private static final class BillingAllMessageTemplateMapper implements RowMapper<BillingMessageData> {

		public String schema() {
			
		return	"mt.id ,mt.template_description, mt.subject ,mt.header,mt.body , mt.footer from  b_message_template mt";
		}
			

		@Override
		public BillingMessageData mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			
			int id1=rs.getInt("id");
			String templateDescription=rs.getString("template_description");
			String subject = rs.getString("subject");
		    String header=rs.getString("header");
			String body=rs.getString("body");
		    String footer=rs.getString("footer");
			Long id=new Long(id1);
			return new BillingMessageData(id,templateDescription, subject, header, body, footer,null);
		}
	}
	
	@Override
	public BillingMessageData retrieveMessageTemplate(Long command)
	{
		context.authenticatedUser();
		BillingMessageTemplateMapper mapper = new BillingMessageTemplateMapper();
		

		String sql = "select " + mapper.schema()+command;

		return this.jdbcTemplate.queryForObject(sql, mapper, new Object[] {});
	}
	private static final class BillingMessageTemplateMapper implements RowMapper<BillingMessageData> {

		public String schema() {
			
		return	"mt.id ,mt.template_description, mt.subject ,mt.header,mt.body , mt.footer from  b_message_template mt where mt.id=";
		}
			

		@Override
		public BillingMessageData mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			int id1=rs.getInt("id");
			String templateDescription=rs.getString("template_description");
			String subject = rs.getString("subject");
		    String header=rs.getString("header");
			String body=rs.getString("body");
		    String footer=rs.getString("footer");
		    Long id=new Long(id1);
		    
			return new BillingMessageData(id,templateDescription, subject, header, body, footer,null);
		}
	}
	
	
	//for message params
	@Override
	public List<BillingMessageData> retrieveMessageParams(Long entityId) {
		// TODO Auto-generated method stub
		context.authenticatedUser();
		BillingMessageParamMapper mapper = new BillingMessageParamMapper();
		String sql = "select " + mapper.schema()+entityId;

		return this.jdbcTemplate.query(sql, mapper, new Object[] {});
	}
		
		



private static final class BillingMessageParamMapper implements RowMapper<BillingMessageData> {

	public String schema() {
		
	return "mp.msgtemplate_id as msgTemplateId,mp.parameter_name as parameterName,mp.sequence_no as sequenceNo " +
			"from b_message_params mp where mp.msgtemplate_id=";
	}
		
	@Override
	public BillingMessageData mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		 String parameterName=rs.getString("parameterName");
		return new BillingMessageData(parameterName);
	}
  }


//for messageData
@Override
public List<BillingMessageData> retrieveData(JsonCommand command,
		BillingMessageData templateData, List<BillingMessageData> messageparam) {
	// TODO Auto-generated method stub
	context.authenticatedUser();
	this.messageparam=messageparam;
	this.templateData=templateData;
	this.command=command;
	String json=command.json();
	final JsonElement element = fromApiJsonHelper.parse(json);
	final String clientquery = fromApiJsonHelper.extractStringNamed("query", element);
	BillingMessageDataMapper mapper = new BillingMessageDataMapper();

	String sql = "select "+clientquery;

	return this.jdbcTemplate.query(sql, mapper, new Object[] {});
	
}

private static final class BillingMessageDataMapper implements RowMapper<BillingMessageData> {

	@Override
	public BillingMessageData mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		 ArrayList<String> rowdata=new ArrayList<String>(); 
		 ArrayList<String> columndata=new ArrayList<String>();
		 rs.last();
		 int Rows=rs.getRow();
		 rs.beforeFirst(); 
		 ResultSetMetaData rsmd = rs.getMetaData();
		 int columnCount = rsmd.getColumnCount();
		
		 //System.out.println(rsmd.getColumnName(0));
		 for(int j=1;j<=Rows;j++){
			 rs.next();
			 
		    for(int i=1;i<=columnCount;i++){
			//String columnname = rsmd.getColumnName(i);
			String name=rs.getString(i);
			columndata.add(name);
		}
		   
		   rowdata.addAll(columndata);
		   // columndata.removeAll(columndata);
		   
		   //my new data 13/06/2013
		   
		    BillingMessageData clientdata=new BillingMessageData(rowdata);
		    ArrayList<BillingMessageData> param=new ArrayList<BillingMessageData>();
			for(BillingMessageData params:messageparam){
				param.add(params);
			   }
			    ArrayList<String> client1=new ArrayList<String>();
			    client1=clientdata.getArraydata();
				
				String header=templateData.getHeader();
				String footer=templateData.getFooter();
				String body=templateData.getBody();
				String subject=templateData.getSubject();
				String status=command.stringValueOfParameterNamed("status");
				String messageFrom=command.stringValueOfParameterNamed("messageFrom");
				int n=param.size();
				
				if(n>0){
				ArrayList<String> client=new ArrayList<String>();
				
				for(int i=0;i<client1.size();i++){
				client.add(i, client1.get(i).toString());
				}
			
				for(int i=0,k=0;i<n-1&k<client.size();i++,k++){
					String name=param.get(i).getParameter();
					String value=client.get(k).toString();
					header=header.replaceAll(name,value);
					body=body.replaceAll(name,value);								
				}
				  String messageTo=client.get(n-1).toString();
				  BillingMessageTemplate billingMessageTemplate=messageTemplateRepository.findOne(command.entityId());
				  BillingMessage billingMessage=new BillingMessage(header, body, footer, messageFrom, messageTo, subject, status,billingMessageTemplate);
				  messageDataRepository.save(billingMessage);
				  rowdata.removeAll(rowdata);
				  columndata.removeAll(columndata);
				 
				}//if
				}//for Rows
		return new BillingMessageData(command.commandId());
		
}
}

@Override
public List<BillingMessageData> retrieveAllMessageTemplateParams() {
	// TODO Auto-generated method stub
	
			context.authenticatedUser();
			BillingAllMessageTemplateParamMapper mapper=new BillingAllMessageTemplateParamMapper();
			String sql = "select " + mapper.schema();
			
			return this.jdbcTemplate.query(sql, mapper, new Object[] {});
			
		}


		
		private static final class BillingAllMessageTemplateParamMapper implements RowMapper<BillingMessageData> {

			public String schema() {
				
			return	"mt.id ,mt.template_description, mt.subject ,mt.header,mt.body , mt.footer, group_concat(mp.parameter_name separator ', ') as messageParameters " +
					"from b_message_params mp, b_message_template mt where mt.id=mp.msgtemplate_id group by mt.id ";
			}
				

			@Override
			public BillingMessageData mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				
				int id1=rs.getInt("id");
				String templateDescription=rs.getString("template_description");
				String subject = rs.getString("subject");
			    String header=rs.getString("header");
				String body=rs.getString("body");
			    String footer=rs.getString("footer");
			    String messageParameters=rs.getString("messageParameters");
				Long id=new Long(id1);
				return new BillingMessageData(id,templateDescription, subject, header, body, footer,messageParameters);
			}
		}



}



