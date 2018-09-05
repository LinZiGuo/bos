package cn.itcast.bos.web.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.Function;
import cn.itcast.bos.domain.Role;
import cn.itcast.bos.service.IRoleService;
import cn.itcast.bos.web.action.base.BaseAction;
@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role> {
	@Autowired
	private IRoleService roleService;
	private String functionIds;
	private String roleid;
	
	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public void setFunctionIds(String functionIds) {
		this.functionIds = functionIds;
	}

	/**
	 * 添加角色
	 * @return
	 */
	@RequiresPermissions(value= {"role-add","role-edit"},logical=Logical.OR)
	public String add() {
		if(StringUtils.isNotBlank(roleid)) {
			Role role = roleService.findById(roleid);
			role.setCode(model.getCode());
			role.setDescription(model.getDescription());
			role.setName(model.getName());
			role.setFunctions(model.getFunctions());
			role.setUsers(model.getUsers());
			roleService.update(role, functionIds);
		} else {
			roleService.save(model, functionIds);
		}
		return LIST;
	}
	
	/**
	 * 分页查询
	 * @return
	 */
	@RequiresPermissions("role-list")
	public String pageQuery() {
		roleService.pageQuery(pageBean);
		this.WriteObject2Json(pageBean, new String[] {"currentPage","pageSize","detachedCriteria","functions","users"});
		return NONE;
	}
	
	/**
	 * 查询所有角色
	 * @return
	 */
	public String listajax() {
		List<Role> list = roleService.findAll();
		this.WriteObject2Json(list, new String[] {"functions","users"});
		return NONE;
	}
	
	public String editUI() {
		model = roleService.findById(roleid);
		List<Function> list = new ArrayList<>(model.getFunctions());
		this.WriteObject2Json(list, new String[] {"parentFunction","roles"});
		return "edit";
	}
	
	public String findFunctionByRoleid() {
		model = roleService.findById(roleid);
		List<Function> list = new ArrayList<>(model.getFunctions());
		this.WriteObject2Json(list, new String[] {"parentFunction","roles"});
		return NONE;
	}
}
