package org.util;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class TableData {
	
	private long totalCount;
	
	private JSONArray rows;
	
	public TableData(){
		rows = new JSONArray();
	}
	
	public void setTotalCount(long totalCount){
		this.totalCount= totalCount;
	}
	
	public void addRows(List<?> list){
		rows.addAll(list);
	}
	
	public String toString(){
		JSONObject json = new JSONObject();
		json.accumulate("total", totalCount);
		json.accumulate("rows", rows);
		return json.toString();
	}

}
