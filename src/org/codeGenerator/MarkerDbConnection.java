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

import org.util.Common;
import org.util.StringUtils;

import com.mysql.jdbc.PreparedStatement;

public class MarkerDbConnection {
	
	static Logger log = Logger.getLogger(MarkerDbConnection.class.getName());
	
	public static final String URL="com/config/jdbc.properties";
	public static final String JDBC = System.getProperty("user.dir")+"/src/"+URL;
	public static final int TOMCAT = 1;
	public static final int JAVA = 2;
	public static Connection Connection(int type){
		Connection conn = null;
		Map map = new HashMap();
		InputStream in;
		try{
			if(type==JAVA){
				in = new BufferedInputStream(new FileInputStream(JDBC));
				MyFreeMarker.getPropertiesEles(map);
			}else{
				in = new MarkerDbConnection().getClass().getClassLoader().getResourceAsStream(URL);
				MyFreeMarker.getPropertiesElesTomcat(map,in);
			}
			
			
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
		Connection conn = Connection(JAVA);
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
	
	
	public static int insertSequence(String tableName,String prefix){
		
		String date = Common.getDate().replaceAll("-", "");
		int size=-1;
		String sql = "insert into base_key_sequence (table_name,key_prefix,key_date,serial_val ) "
				+ "values (?,?,?,?)";
		Connection conn = Connection(TOMCAT);
		PreparedStatement pstm = null;
		try{
			pstm = (PreparedStatement) conn.prepareStatement(sql);
			pstm.setString(1, tableName);
			pstm.setString(2, prefix);
			pstm.setString(3, date);
			pstm.setString(4, "00000");
			size = pstm.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(pstm!=null) pstm.close();
				if(conn!=null) conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return size;
	}
	
public static int updateSequence(String key_date,String serial_val,String tableName){
		
		int size=-1;
		String sql = "update  base_key_sequence  set key_date= ?,serial_val=? where table_name = ? ";
		Connection conn = Connection(TOMCAT);
		PreparedStatement pstm = null;
		try{
			pstm = (PreparedStatement) conn.prepareStatement(sql);
			pstm.setString(1, key_date);
			pstm.setString(2, serial_val);
			pstm.setString(3, tableName);
			size = pstm.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(pstm!=null) pstm.close();
				if(conn!=null) conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return size;
	}
	
	public static int createBaseSeq(){
		
		int size=-1;
		String sql = "create table base_key_sequence ("
				+ "table_name varchar(50) not null comment '表明',"
				+ "key_prefix varchar(10) default null comment '前缀',"
				+ "key_date varchar(18) default null comment '进行	日期',"
				+ "serial_val varchar(5) default null comment '流水号(5位)',"
				+ "primary key (table_name)"
				+ ") engine=innodb default charset=utf8 comment='主键信息表'";
		Connection conn = Connection(JAVA);
		PreparedStatement pstm = null;
		try{
			pstm = (PreparedStatement) conn.prepareStatement(sql);
			size = pstm.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(pstm!=null) pstm.close();
				if(conn!=null) conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return size;
	}
	
public static Map<String,Object> selectBaseMap(String tableName){
		
		Map<String,Object> map = new HashMap<String,Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("select table_name,key_prefix,key_date,serial_val from base_key_sequence where table_name=?");
		ResultSet result=null;
		Connection conn = Connection(TOMCAT);
		PreparedStatement pstm  = null;
		try{
			pstm = (PreparedStatement) conn.prepareStatement(sql.toString());
			pstm.setString(1, tableName);
			result = pstm.executeQuery();
			while(result.next()){
				map.put("table_name", result.getString("table_name"));
				map.put("key_prefix", result.getString("key_prefix"));
				map.put("key_date", result.getString("key_date"));
				map.put("serial_val", result.getString("serial_val"));
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
		return map;
	}
	
	public static int selectBaseSeq(Map map){
		
		int size =-1;
		String sql = "select count(*) count from information_schema.columns where table_schema = ? and table_name = ?";
		Connection conn = Connection(JAVA);
		ResultSet result =null;
		PreparedStatement pstm = null;
		try{
			pstm = (PreparedStatement) conn.prepareStatement(sql);
			pstm.setString(1, (String)map.get("table_schema"));
			pstm.setString(2, "base_key_sequence");
			result = pstm.executeQuery();
			while(result.next()){
				size = result.getInt("count");
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
		return size;
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
