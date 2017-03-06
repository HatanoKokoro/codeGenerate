package org.codeGenerator;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import org.util.Annotation;
import org.util.StringUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class MyFreeMarker {
	
	static Logger log = Logger.getLogger(MyFreeMarker.class.getName());
	
	private static Configuration configuration;
	private static Template template;
	private static Writer writer;
	
	public static final String ANNOTATION = "annotation";
	public static final String ANNOTATION_AUTHOR_NAME = "Artoria";
	public static final String ANNOTATION_AUTHOR_EMAIL = "wym98188623@163.com";
	public static final String ANNOTATION_VERSION = "1.0";
	public static final String DATE_FROMATE = "yyyy-MM-dd HH:mm:ss";
	
	public static final String FTLS_PATH = "src/org/ftls";
	public static final String ENTITY = "entity.ftl";
	
	public static Map<String, Object> map=new HashMap<String,Object>();
	
	
	
	
	public static void CreateFile(String tableName,String createUser,String packageUrl,
			String prefix)throws Exception{
		map.put("tableName",tableName);
		map.put("createUser",createUser) ;
		map.put("url",packageUrl) ;
		map.put("prefix",prefix) ;
		
		getPropertiesEles(map);
		
		List<Map<String,String>> list = MarkerDbConnection.select(map);
		if(list!=null && list.size()>0){
			configuration = new Configuration();
			configuration.setClassForTemplateLoading(new MyFreeMarker().getClass(), "/org/ftls/");
			
			Annotation annotation = new Annotation();
			annotation.setAuthorEmail(ANNOTATION_AUTHOR_EMAIL);
			annotation.setAuthorName(createUser);
			annotation.setVersion(ANNOTATION_VERSION);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FROMATE);
			annotation.setDate(simpleDateFormat.format(new Date()));
			String className = StringUtils.getHumpName(tableName);
			String pk ="id";
			for(int i=0;i<list.size();i++){
				if(list.get(i).get("key")!=null && list.get(i).get("key").equals("PRI"))
					pk = list.get(i).get("humpColumnName");
			}
			map.put("pk", pk);
			
			CreateEntity(className, list, annotation, map);
			
		}
		
	}
	
	public static void CreateEntity(String className,List list,Annotation annotation,Map map){
		
		try{
			String packageUrl = map.get("url").toString();
			template = configuration.getTemplate(ENTITY);
			
			Map<String,Object> root = new HashMap<String,Object>();
			packageUrl = packageUrl + ".entity";
			root.put("pack", packageUrl);
			
			String ClassName = StringUtils.upperCaseFirstOne(className);
			String beanPath = map.get("workspace")+"/src/"+packageUrl.replace(".", "/")+"/";
			
			File filePath = new File(beanPath);
			if(!filePath.exists()){
				filePath.mkdirs();
			}
			
			String beanFilePath = beanPath+ClassName+".java";
			File file = new File(beanFilePath);
			if(!file.exists()){
				file.createNewFile();
			}
			
			root.put("annotation", annotation );
			root.put("ClassName", ClassName);
			root.put("className", className);
			root.put("list", list);
			
			writer = new FileWriter(file); 
			template.process(root, writer);
			writer.flush();
			writer.close();
			System.out.println("[创建实体类成功!]");
		}catch(Exception e){
			log.info("实体创建异常!"+e.getMessage());
			System.out.println("实体创建异常!"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void getPropertiesEles(Map map)throws Exception{
		InputStream in = new BufferedInputStream(new FileInputStream(MarkerDbConnection.JDBC_URL));
		Properties pro = new Properties();
		pro.load(in);
		Iterator it = pro.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, String> entry= (Map.Entry<String, String>)it.next();
			String key = entry.getKey();
			String value = entry.getValue();
			map.put(key,value);
		}
	}
	
}
