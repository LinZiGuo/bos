<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- 导入jquery核心类库 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<!-- 导入easyui类库 -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/ext/portal.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/default.css">	
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/ext/jquery.portal.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/ext/jquery.cookie.js"></script>
<script
	src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"
	type="text/javascript"></script>
<script type="text/javascript">
	$(function(){
		$("#grid").datagrid({
			toolbar : [
				<shiro:hasPermission name="function-add">
				{
					id : 'add',
					text : '添加权限',
					iconCls : 'icon-add',
					handler : function(){
						location.href='${pageContext.request.contextPath}/page_admin_function_add.action';
					}
				},
				</shiro:hasPermission>
				<shiro:hasPermission name="function-delete">
				{
					id : 'delete',
					text : '删除权限',
					iconCls : 'icon-cancel',
					handler : doDelete
				}
				</shiro:hasPermission>
			],
			fit : true,
			pageList : [10,20,30],
			pagination : true,//分页条
			url : '${pageContext.request.contextPath}/functionAction_pageQuery.action',
			columns : [[
			  {
				  field : 'id',
				  title : '编号',
				  width : 200,
				  checkbox : true
			  },
			  {
				  field : 'name',
				  title : '名称',
				  width : 200
			  },  
			  {
				  field : 'description',
				  title : '描述',
				  width : 200
			  },  
			  {
				  field : 'generatemenu',
				  title : '是否生成菜单',
				  width : 150,
				  formatter : function(data, row, index){
					  if(data == '1'){
						  return '是';
					  } else {
						  return '否';
					  }
				  }
			  },  
			  {
				  field : 'zindex',
				  title : '优先级',
				  width : 200
			  },  
			  {
				  field : 'page',
				  title : '路径',
				  width : 200
			  }
			]]
		});
	});
	
	function doDelete(){
		//获取所选中的行
		var rows = $("#grid").datagrid("getSelections");
		if(rows.length == 0){
			//没有选中记录，弹出提示
			$.messager.alert("提示信息","请选择需要删除的权限！","info");
		} else{
			//弹出确认框
			$.messager.confirm("提示信息","确定删除当前选中的权限？",function(r){
				if(r){
					var ids = "";
					var array = new Array();
					//用户点击的是确定按钮，需要删除
					//获取当前选中记录的id
					for(var i=0;i<rows.length;i++){
						//取派员id
						var id = rows[i].id;
						array.push(id);
					}
					ids = array.join(",");
					//发送请求，将ids提交到action
					location.href = "${pageContext.request.contextPath }/functionAction_delete.action?ids=" + ids;
				}
			});
		}
	}
</script>	
</head>
<body class="easyui-layout">
<div data-options="region:'center'">
	<table id="grid"></table>
</div>
</body>
</html>