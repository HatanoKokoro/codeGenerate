package org.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.codeGenerator.MarkerDbConnection;

public class Common {
	
	public static String getDate(){
		
		try{
			DateFormat dft = new SimpleDateFormat("YYYY-MM-dd");
			Date date = new Date();
			return dft.format(date);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getSysKey(String tableName){
		String date = getDate().replaceAll("-", "");
		
		Map<String,Object>  map= MarkerDbConnection.selectBaseMap(tableName);
		String key_prefix = map.get("key_prefix").toString();
		String key_date = map.get("key_date").toString();
		String table_name = map.get("table_name").toString();
		int serial_val = Integer.parseInt(map.get("serial_val").toString())+1;
		String serial_valstr = String.format("%05d", serial_val);
		if(date.equals(key_date)){
			if(MarkerDbConnection.updateSequence(key_date, serial_valstr, tableName)<0){
				System.out.println("数据库表[BASE_KEY_SEQUENCE]执行修改时报错");
			}
			return key_prefix+key_date+String.format("%05d", serial_val);
		}else{
			if(MarkerDbConnection.updateSequence(key_date,"00001",table_name)<0){
				System.out.println("数据库表[BASE_KEY_SEQUENCE]执行修改时报错");
			}
			return key_prefix+key_date+"00001";
		}
		
	}

}
