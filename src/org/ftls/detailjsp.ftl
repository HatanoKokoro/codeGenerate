<!DOCTYPE html>
<html lang="zh-cn">
<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="bpt" value="${r'${pageContext.request.contextPath}'}"/>
<!-- BEGIN HEAD -->
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
	+ request.getServerName() + ":" + request.getServerPort()
	+ path + "/";
%>
<base href="<%=basePath%>">
<head>
   <meta charset="utf-8" />
   <meta content="width=device-width, initial-scale=1.0" name="viewport" />
   <meta content="" name="description" />
   <meta content="Mosaddek" name="author" />
   <link href="static/assets/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
   <link href="static/assets/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet" />
   <link href="static/assets/font-awesome/css/font-awesome.css" rel="stylesheet" />
   <link href="static/css/style.css" rel="stylesheet" />
   <link href="static/css/style-responsive.css" rel="stylesheet" />
   <link href="static/css/style-default.css" rel="stylesheet" id="style_color" />
   <link href="static/assets/fullcalendar/bootstrap-fullcalendar.css" rel="stylesheet" />
   <link href="static/assets/bootstrap-table/bootstrap-table.min.css" rel="stylesheet" />
   
   <style type="text/css">
    	.pagebody{
    		margin-left: 20px;
		    margin-right: 20px;
    	}
    </style>
   
</head>

<body class="fixed-top">
   
   <jsp:include page="../../pages/common/header.jsp"></jsp:include>
   
   <!-- BEGIN CONTAINER -->
   <div id="container" class="row-fluid">
      
      <jsp:include page="../../pages/common/left.jsp"></jsp:include>
      
      <!-- BEGIN PAGE -->  
      <div id="main-content">
         <!-- BEGIN PAGE CONTAINER-->
         <div class="container-fluid">
            <!-- BEGIN PAGE HEADER-->   
            <div class="row-fluid">
               <div class="span12">
                  <!-- BEGIN PAGE TITLE & BREADCRUMB-->
                   <h3 class="page-title">  控制台</h3>
                   <ul class="breadcrumb">
                       <li>
                           <a href="/">首页</a>
                           <span class="divider">/</span>
                       </li>                      
                       <li class="active">  控制台</li>
                   </ul>
                   <!-- END PAGE TITLE & BREADCRUMB-->
               </div>
            </div>
            <!-- END PAGE HEADER-->
         </div>
         <!-- END PAGE CONTAINER-->
         <!-- 你添加的内容  -->
          <div class="row-fluid pagebody">
             <form id="formMenu">
             
	             <#list list as p>
	             	<div class="form-group">
		             	<label class="col-sm-2 control-label" >${p.column_comment}</label>
						<div class="col-sm-4">
							<input class="form-control col-sm-3" disabled="disabled" type="text" name="${p.humpColumnName}" id="${p.humpColumnName}" value="${r'${data.'}${p.humpColumnName}${r'}'}">
						</div>
					</div>	
					
	             </#list>
	             <div class="form-group">
						<input class="btn btn-info" type="button" id="rtn" value="返回" >
				</div>
			</form>
			
         </div>
         <!-- 你添加的内容end  -->
      </div>
      <!-- END PAGE -->  
   </div>
   <!-- END CONTAINER -->

   <!-- BEGIN FOOTER -->
   <div id="footer">
       test
   </div>
   <!-- END FOOTER -->

   <script src="static/js/jquery-1.8.3.min.js"></script>
   <script src="static/assets/bootstrap/js/bootstrap.min.js"></script>
   <script src="static/assets/bootstrap-table/bootstrap-table.min.js"></script>
   <script src="static/js/common-scripts.js"></script>
   <script src="static/assets/layer/layer.js"></script>
   <script>
   		$(function(){
   			
   			
   			
   			$("#rtn").click(function(){
   				window.location="${className}/toList"
   			})
   		
   		})
   </script>
</body>
</html>
