package org.codeGenerator;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.util.StringUtils;

import com.mysql.jdbc.PreparedStatement;

public class MarkerDbConnection {
	
	static Logger log = Logger.getLogger(MarkerDbConnection.class.getName());
	
	public static final String JDBC_URL = System.getProperty("user.dir")+"/src/com/config/jdbc.properties";
	
	
	public static Connection Connection(){
		Connection conn = null;
		Map map = new HashMap();
		try{
			InputStream in = new BufferedInputStream(new FileInputStream(JDBC_URL));
			MyFreeMarker.getPropertiesEles(map);
			String url = (String) map.get("jdbc.url");
			String username = (String) map.get("jdbc.username");
			String password = (String) map.get("jdbc.password");
			String diverClassName = (String) map.get("jdbc.driverClassName");
			Class.forName(diverClassName);
			conn = DriverManager.getConnection(url,username,password);
		}catch(ClassNotFoundException e){
			log.info("驱动未加载成功");
			e.printStackTrace();
		}catch (SQLException e) {
			log.info("sql异常");
			e.printStackTrace();
		}catch(Exception e){
		e.printStackTrace();
	}
		return conn;
	}
	
	public static List<Map<String,String>> select(Map map){
		
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		StringBuffer sql = new StringBuffer();
		sql.append("select column_name,data_type,column_key pk,column_comment,");
		sql.append("character_maximum_length length,column_type type, is_nullable nullable ");
		sql.append(" from information_schema.columns where table_schema = ? and table_name = ?");
		ResultSet result=null;
		Connection conn = Connection();
		PreparedStatement pstm  = null;
		try{
			pstm = (PreparedStatement) conn.prepareStatement(sql.toString());
			pstm.setString(1, (String) map.get("table_schema"));
			pstm.setString(2, (String) map.get("tableName"));
			result = pstm.executeQuery();
			while(result.next()){
				Map<String,String> maps =new HashMap<String,String>();
				maps.put("humpColumnName", StringUtils.getHumpName(result.getString("column_name")));
				maps.put("column_name", result.getString("column_name"));
				maps.put("methodName",StringUtils.upperCaseFirstOne(StringUtils.getHumpName(result.getString("column_name"))));
				maps.put("data_type",getType( result.getString("data_type")));
				maps.put("column_comment", result.getString("column_comment"));
				maps.put("length", result.getString("length"));
				maps.put("key", result.getString("pk"));
				maps.put("nullable", result.getString("nullable"));
				list.add(maps);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(result!=null) result.close();
				if(pstm!=null) pstm.close();
				if(conn!=null) conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return list;
	}
	
	public static String getType(String dataType){
		
		dataType = dataType.toLowerCase();
		if (dataType.contains("char")||dataType.contains("text")||dataType.contains("varchar"))
			dataType = "String";
		else if (dataType.contains("bit"))
			dataType = "Boolean";
		else if (dataType.contains("bigint"))
			dataType = "Long";
		else if (dataType.contains("int")||dataType.contains("integer")||dataType.contains("tinyint")||dataType.contains("smallint"))
			dataType = "Integer";
		else if (dataType.contains("float"))
			dataType = "Float";
		else if (dataType.contains("double"))
			dataType = "Double";
		else if (dataType.contains("decimal"))
			dataType = "BigDecimal";
		else if (dataType.contains("date"))
			dataType = "Date";
		else if (dataType.contains("time")||dataType.contains("timestamp"))
			dataType = "Timestamp";
		else if (dataType.contains("clob"))
			dataType = "Clob";
		else {
			dataType = "java.lang.Object";
		}
		return dataType;
	}
}
