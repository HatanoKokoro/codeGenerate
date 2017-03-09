package ${pack};

import java.util.*;

import org.apache.ibatis.annotations.Param;

/**
 * @author ${annotation.authorName}
 * @data ${annotation.date}
 *
 * @version ${annotation.version}
 */
 

 public interface I${ClassName}Dao{
 
 	public int insert (Map<String,Object> map) throws Exception;
 	
 	public int del(String ${Pk}) throws Exception;
 	
 	public int modify(Map<String,Object> map) throws Exception;
 	
 	public List<Map<String,Object>> pageList(@Param("pageIndex")int pageIndex,@Param("pageSize")int pageSize,@Param("nameKey")String nameKey) throws Exception;
	
	public long pageTotalCount() throws Exception;
	
	public Map<String,Object> findById(String ${Pk}) throws Exception;
 
 }
	
	
	
	

	
