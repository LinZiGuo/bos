<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	function doAdd(){
		//alert("增加...");
		$('#addStaffWindow').window("open");
	}
	
	function doView(){
		//alert("查看...");
		$('#searchWindow').window("open");
	}
	
	//批量删除按钮对应的处理函数
	function doDelete(){
		//alert("删除...");
		//获取所选中的行
		var rows = $("#grid").datagrid("getSelections");
		if(rows.length == 0){
			//没有选中记录，弹出提示
			$.messager.alert("提示信息","请选择需要删除的取派员！","info");
		} else{
			//弹出确认框
			$.messager.confirm("提示信息","确定删除当前选中的取派员？",function(r){
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
					location.href = "${pageContext.request.contextPath }/staffAction_delete.action?ids=" + ids;
				}
			});
		}
	}
	
	function doRestore(){
		//alert("将取派员还原...");
		//获取所选中的行
		var rows = $("#grid").datagrid("getSelections");
		if(rows.length == 0){
			//没有选中记录，弹出提示
			$.messager.alert("提示信息","请选择需要还原的取派员！","info");
		} else{
			//弹出确认框
			$.messager.confirm("提示信息","确定还原当前选中的取派员？",function(r){
				if(r){
					var ids = "";
					var array = new Array();
					//用户点击的是确定按钮，需要还原
					//获取当前选中记录的id
					for(var i=0;i<rows.length;i++){
						//取派员id
						var id = rows[i].id;
						array.push(id);
					}
					ids = array.join(",");
					//发送请求，将ids提交到action
					location.href = "${pageContext.request.contextPath }/staffAction_restore.action?ids=" + ids;
				}
			});
		}
	}
	//工具栏
	var toolbar = [ {
		id : 'button-view',	
		text : '查询',
		iconCls : 'icon-search',
		handler : doView
	}, {
		id : 'button-add',
		text : '增加',
		iconCls : 'icon-add',
		handler : doAdd
	}, {
		id : 'button-delete',
		text : '作废',
		iconCls : 'icon-cancel',
		handler : doDelete
	},{
		id : 'button-save',
		text : '还原',
		iconCls : 'icon-save',
		handler : doRestore
	}];
	// 定义列
	var columns = [ [ {
		field : 'id',
		checkbox : true,
	},{
		field : 'name',
		title : '姓名',
		width : 120,
		align : 'center'
	}, {
		field : 'telephone',
		title : '手机号',
		width : 120,
		align : 'center'
	}, {
		field : 'haspda',
		title : '是否有PDA',
		width : 120,
		align : 'center',
		formatter : function(data,row, index){
			if(data=="1"){
				return "有";
			}else{
				return "无";
			}
		}
	}, {
		field : 'deltag',
		title : '是否作废',
		width : 120,
		align : 'center',
		formatter : function(data,row, index){
			if(data=="0"){
				return "正常使用"
			}else{
				return "已作废";
			}
		}
	}, {
		field : 'standard',
		title : '取派标准',
		width : 120,
		align : 'center'
	}, {
		field : 'station',
		title : '所谓单位',
		width : 200,
		align : 'center'
	} ] ];
	
	$(function(){
		// 先将body隐藏，再显示，不会出现页面刷新效果
		$("body").css({visibility:"visible"});
		
		// 取派员信息表格
		$('#grid').datagrid( {
			iconCls : 'icon-forward',
			fit : true,//自适应
			border : false,//边框
			rownumbers : true,//行号
			striped : true,//纹理效果
			pageList: [30,50,100],
			pagination : true,//分页条
			toolbar : toolbar,//工具栏
			url : "${pageContext.request.contextPath }/staffAction_pageQuery.action",//ajax请求地址
			idField : 'id',//标识字段
			columns : columns,//定义表头
			onDblClickRow : doDblClickRow//双击事件
		});
		
		// 添加取派员窗口
		$('#addStaffWindow').window({
	        title: '添加取派员',
	        width: 400,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false
	    });
		
		// 修改取派员窗口
		$('#editStaffWindow').window({
	        title: '修改取派员',
	        width: 400,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false
	    });
		
		// 查询取派员窗口
		$('#searchWindow').window({
	        title: '查询取派员',
	        width: 400,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 350,
	        resizable:false
	    });
		
		//定义一个工具方法，用于将指定的form表单中所有的输入项转为json数据{key:value,key:value}
		$.fn.serializeJson=function(){  
            var serializeObj={};  
            var array=this.serializeArray();
            $(array).each(function(){  
                if(serializeObj[this.name]){  
                    if($.isArray(serializeObj[this.name])){  
                        serializeObj[this.name].push(this.value);  
                    }else{  
                        serializeObj[this.name]=[serializeObj[this.name],this.value];  
                    }  
                }else{  
                    serializeObj[this.name]=this.value;   
                }  
            });
            return serializeObj;  
        }; 
		
		$("#btn").click(function(){
			//定义一个工具方法，用于将指定的form表单中所有的输入项转为json数据{key:value,key:value}
			var f = $("#searchForm").serializeJson();
			//调用数据表格的load方法，重新发送一次Ajax请求，并且提交参数
			$("#grid").datagrid("load",f);
			//关闭查询窗口
			$("#searchWindow").window("close");
		});
	});

	function doDblClickRow(rowIndex, rowData){
		//alert("双击表格数据...");
		//打开修改取派员窗口
		$('#editStaffWindow').window("open");
		//使用form表单对象的load方法回显数据
		$("#editStaffForm").form("load",rowData);
	}
</script>	
</head>
<body class="easyui-layout" style="visibility:hidden;">
	<div region="center" border="false">
    	<table id="grid"></table>
	</div>
	<div class="easyui-window" title="对收派员进行添加或者修改" id="addStaffWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >保存</a>
			</div>
		</div>
		
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="addStaffForm" action="staffAction_add.action" method="post">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">收派员信息</td>
					</tr>
					<!-- TODO 这里完善收派员添加 table -->
					<tr>
						<td>姓名</td>
						<td><input type="text" name="name" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>手机</td>
						<script type="text/javascript">
						$(function(){
							//为保存按钮绑定事件
							$("#save").click(function(){
								//表单校验，如果通过，提交表单
								var v = $("addStaffForm").form("validate");
								if(v){
									$("#addStaffForm").submit();
								}
							});
							var reg = /^1[3|4|5|7|8][0-9]{9}$/;
							//扩展校验规则，对手机号进行校验
							$.extend($.fn.validatebox.defaults.rules,{
								telephone:{
									validator:function(value,param){
										return reg.test(value);
									},
									message:'手机号输入有误！'
									}
							});
						});
						</script>
						<td><input type="text" name="telephone" data-options="validType:'telephone'" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>单位</td>
						<td><input type="text" name="station" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td colspan="2">
						<input type="checkbox" name="haspda" value="1" />
						是否有PDA</td>
					</tr>
					<tr>
						<td>取派标准</td>
						<td>
							<input type="text" name="standard" class="easyui-validatebox" required="true"/>  
						</td>
					</tr>
					</table>
			</form>
		</div>
	</div>
	
	<!-- 修改取派员窗口 -->
	<div class="easyui-window" title="对收派员进行添加或者修改" id="editStaffWindow" collapsible="false" 
		minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="edit" icon="icon-edit" href="#" class="easyui-linkbutton" plain="true" >保存</a>
			</div>
		</div>
		
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="editStaffForm" action="staffAction_edit.action" method="post">
				<input type="hidden" name="id">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">收派员信息</td>
					</tr>
					<tr>
						<td>姓名</td>
						<td><input type="text" name="name" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>手机</td>
						<td>
							<script type="text/javascript">
								$(function(){
									//为保存按钮绑定事件
									$("#edit").click(function(){
										//表单校验，如果通过，提交表单
										var v = $("#editStaffForm").form("validate");
										if(v){
											//$("#addStaffForm").form("submit");
											$("#editStaffForm").submit();
										}
									});
									
									var reg = /^1[3|4|5|7|8][0-9]{9}$/;
									//扩展手机号校验规则
									$.extend($.fn.validatebox.defaults.rules, { 
										telephone: { 
											validator: function(value,param){ 
											return reg.test(value);
										}, 
											message: '手机号输入有误！' 
										}
										}); 
									});
							</script>
						<input type="text" data-options="validType:'telephone'" 
							name="telephone" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>单位</td>
						<td><input type="text" name="station" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td colspan="2">
						<input type="checkbox" name="haspda" value="1" />
						是否有PDA</td>
					</tr>
					<tr>
						<td>取派标准</td>
						<td>
							<input type="text" name="standard" class="easyui-validatebox" required="true"/>  
						</td>
					</tr>
					</table>
			</form>
		</div>
	</div>
	
	<!-- 查询取派员窗口 -->
	<div class="easyui-window" title="查询取派员" id="searchWindow" collapsible="false" 
		minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="searchForm">
				<input type="hidden" name="query" value="query" />
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">收派员信息</td>
					</tr>
					<tr>
						<td>姓名</td>
						<td><input type="text" name="name" class="easyui-textbox"/></td>
					</tr>
					<tr>
						<td>手机</td>
						<td>
						<input type="text" name="telephone" class="easyui-textbox"/></td>
					</tr>
					<tr>
						<td>单位</td>
						<td><input type="text" name="station" class="easyui-textbox"/></td>
					</tr>
					<tr>
						<td colspan="2">
						<input type="checkbox" name="haspda" value="1" />
						是否有PDA</td>
					</tr>
					<tr>
						<td>取派标准</td>
						<td>
							<input type="text" name="standard" class="easyui-textbox"/>  
						</td>
					</tr>
					<tr>
						<td colspan="2"><a id="btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> </td>
					</tr>
					</table>
			</form>
		</div>
	</div>
</body>
</html>	