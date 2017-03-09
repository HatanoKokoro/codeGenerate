package org.util;

public class StringUtils {
	
	//转换驼峰
	public static String getHumpName(String column){
		
		StringBuffer buf = new StringBuffer();
		if(column.indexOf("_")>-1){
			String[] columns = column.split("_");
			buf.append(columns[0]);
			for(int i=1;i<columns.length;i++){
				buf.append(upperCaseFirstOne(columns[i]));
			}
			return buf.toString();
		}
		return column;
	}
	
	//首字母大写
	public static String upperCaseFirstOne(String str){
		
		if(Character.isUpperCase(str.charAt(0)))
			return str;
		else
			return (new StringBuilder()).append(Character.toUpperCase(str.charAt(0))).append(str.substring(1)).toString();
	}
	
	public static void main(String[] args) {
		System.out.println(getHumpName("ss_qq"));
	}

}
