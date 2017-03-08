package ${pack};

import java.util.*;
import java.sql.*;

/**
 * @author ${annotation.authorName}
 * @data ${annotation.date}
 *
 * @version ${annotation.version}
 */

public class ${ClassName}{
<#list list as p>

	private ${p.data_type} ${p.humpColumnName}; // ${p.column_comment}
</#list>
<#list list as p>	

	public ${p.data_type} get${p.methodName}(){
		return ${p.humpColumnName};
	}
	
	public void set${p.methodName}(${p.data_type} ${p.humpColumnName}){
		this.${p.humpColumnName} = ${p.humpColumnName};
	}
</#list>
}