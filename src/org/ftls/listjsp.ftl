
<!DOCTYPE html>
<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="bpt" value="${r'${pageContext.request.contextPath}'}"/>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!--> <html lang="en"> <!--<![endif]-->
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
    	.bootstrap-table{
		    margin-left: 20px;
		    margin-right: 20px;
    	}
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
          <div class="row-fluid">
       		<div class="input-group pagebody">
				<input type="text" placeholder="请输入关键词"  class="form-control" id="searchContent"/>
				<span class="input-group-btn">
					<button type="button" class="btn btn-primary" id="searchButton" style="margin-bottom: 9px;">搜索</button>
				</span>
				<div style="float:right;">
				<a class="btn btn-success " href="${className}/add" style="margin-right:20px">添加</a>
				</div>
			</div>
              <table class="table table-hover no-margins table-striped text-center"  id="table"></table>
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
		var nameKey;		
		$("#searchButton").click(function(){
			nameKey = $("#searchContent").val(); 
			$("#table").bootstrapTable('refresh', {url: '${className}/list'});
	    });
	    
		var operateEvents = {
			"click .edit": function(e, value, row, index) {
				// 编辑
				window.location.href = "${r'${bpt}'}/${className}/modify?${pk}="+row.${pk};
			},
			"click .detail": function(e, value, row, index) {
				// 详情
				window.location.href = "${r'${bpt}'}/${className}/detail?${pk}="+row.${pk};
			},
			"click .remove": function(e, value, row, index) {
				// 删除
				layer.confirm('确定要删除吗？', {
	                  title: ['提示', 'font-size:18px;color:#fff'],
	                  shift: 5 ,//动画类型
	                  btn: ['确定', '取消', ] //可以无限个按钮
					}, function(index, layero){
					  //按钮【按钮一】的回调
						$.ajax({
						url: '${className}/del',
						type: 'POST',
						data: {
							id: row.id
						},
						success: function(data) {
							layer.alert(data.msg, {
				            	//skin: 'layui-layer-molv', //样式类名         layui-layer-lan   layui-layer-molv
					       		closeBtn: 0,                //0:不显示；1：窗口里 2：窗口外
					         	icon: 1,               //1：成功 2：错误 3：问号 4：锁 5：哭脸 6：笑脸
					         	shift: 5 //动画类型
				                },function(index){
				         			$("#table").bootstrapTable('refresh', {});
				         			layer.close(index);
			            		}
		            		);
						}
					});
					
					}, function(index){
					  //按钮【按钮二】的回调
					});
				
			}
		};
		
		$("#table").bootstrapTable({
			pageNumber: 1,
			pageSize: 10,
			pageList: [10, 25, 50, 100],
			pagination: true,
			classes: 'table-no-bordered',
			sidePagination: 'server',
			queryParamsType: 'limit',
			queryParams: function(params) {
				var submitData = {
					pageSize: params.limit,
					pageIndex: params.offset,
					nameKey: nameKey
				};
				return submitData;
			},
			rowStyle: function(value, row, index) {
				return {
					classes: 'text-center text-nowrap',
					css: {"color": "blue", "font-size": "13px"}
				};
			}, 
			onLoadSuccess: function(data) {
			},
			dataType: "json",
			url: '${className}/list',
			columns: [
						{
							field: 'index',
							title: '序号',
							halign: 'center',
							formatter: function(value, row, index) {
								if(row.${pk} == "") {
									return "";
								} else {
									return index + 1;
								}
							}
						},
						<#list list as p>
						{
							field: '${p.humpColumnName}',
							title: '${p.column_comment}',
							class: 'text-center'
						},
						</#list>
						{
							title: "操作",
							formatter: function(value, row, index) {
								if(row.${pk}!=''){
					        		return  '<a href="javascript:;" class="edit" title="编辑"><i class="icon-edit"></i></a>' + '&nbsp;&nbsp;&nbsp;'+
					        		 '<a href="javascript:;" class="detail" title="详情"><i class="icon-search"></i></a>' + '&nbsp;&nbsp;&nbsp;'+
					        		 '<a href="javascript:;" class="remove" title="删除"><i class="icon-remove">' + '&nbsp;&nbsp;&nbsp;';
					        	}
							},
							class: 'text-center',
	 						events: operateEvents
						}
				]
			});
		});
	
	//点击回车查询
	$("#searchContent").keydown(function(e) {
	    if (e.keyCode == 13) {//keyCode=13是回车键
	        $('#searchButton').click();
	    }
	});
   </script>
</body>
</html>
