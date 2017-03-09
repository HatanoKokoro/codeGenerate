package ${pack};

import java.util.*;

/**
 * @author ${annotation.authorName}
 * @data ${annotation.date}
 *
 * @version ${annotation.version}
 */
 

 public interface I${ClassName}Service {
 
 	public int insert (Map<String,Object> map) throws Exception;
 	
 	public int del(String ${Pk}) throws Exception;
 	
 	public int modify(Map<String,Object> map) throws Exception;
 	
 	public List<Map<String,Object>> pageList(int pageIndex,int pageSize,String nameKey) throws Exception;
	
	public long pageTotalCount() throws Exception;
	
	public Map<String,Object> findById(String ${Pk}) throws Exception;
 
 }
	
	
	
	

	
