package ${pack};

import java.util.*;
import java.sql.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONObject;
import org.util.ReturnMsg;
import org.util.TableData;
import org.util.JsonUtils;
${import};

/**
 * @author ${annotation.authorName}
 * @data ${annotation.date}
 *
 * @version ${annotation.version}
 */
 /**
 * 
 * 在"mybatiesconfig.xml中加入內容
 *
 *在typeAliases标签中添加<typeAlias type="${alias}" alias="${className}"/>
 * 		
 * 
 * 在mappers标签中添加 <mapper resource="${mapper}" />
 *
 * 
 */

@Controller
@RequestMapping("${className}")
public class ${ClassName}Controller{

	@Autowired
	private I${ClassName}Service ${className}Service;
	
	private ReturnMsg msg;
	
	@RequestMapping(value = "/tolist", method = RequestMethod.GET)
	public ModelAndView toList (HttpServletRequest request,HttpServletResponse response){
		ModelAndView mv = new ModelAndView("/${className}/${className}_list");
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(HttpServletRequest request,HttpServletResponse response) {
		TableData pagingInfo = new TableData();
		try {
			int pageSize = Integer.parseInt(request.getParameter("pageSize"));
			int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
			String nameKey = request.getParameter("nameKey");
			List<Map<String,Object>> data = ${className}Service.pageList(pageIndex,pageSize,nameKey);
			long totalCount = ${className}Service.pageTotalCount();
			
			pagingInfo.setTotalCount(totalCount);
			pagingInfo.addRows(data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pagingInfo.toString();
	}
	
	@ResponseBody
	@RequestMapping(value="/del",method=RequestMethod.POST)
	public String del(@RequestParam int id, HttpServletRequest request,HttpServletResponse response){
		try{
			int size = ${className}Service.del(id);
			msg = new ReturnMsg(true, "操作成功!" );
		} catch (Exception e) {
			msg = new ReturnMsg(false, "操作失败!" );
			e.printStackTrace();
		}
		return JsonUtils.objectToString(msg);
	}
	

}
