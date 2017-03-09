package ${pack};

import java.util.*;
import java.sql.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

${import}

/**
 * @author ${annotation.authorName}
 * @data ${annotation.date}
 *
 * @version ${annotation.version}
 */

@Service("${className}Service")
public class ${ClassName}Service implements I${ClassName}Service{

	@Autowired
	private I${ClassName}Dao ${className}Dao;
	
	public int insert (Map<String,Object> map) throws Exception{
		return ${className}Dao.insert(map);
	}
 	
 	public int del(String ${Pk}) throws Exception{
 		return ${className}Dao.del(${Pk});
 	}
 	
 	public int modify(Map<String,Object> map) throws Exception{
 		return ${className}Dao.modify(map);
 	}
 	
 	public List<Map<String,Object>> pageList(int pageIndex,int pageSize,String nameKey) throws Exception{
 		return ${className}Dao.pageList(pageIndex,pageSize,nameKey);
 	}
	
	public long pageTotalCount() throws Exception{
		return ${className}Dao.pageTotalCount();
	}
	
	public Map<String,Object> findById(String ${Pk}) throws Exception{
		return ${className}Dao.findById(${Pk});
	}

}