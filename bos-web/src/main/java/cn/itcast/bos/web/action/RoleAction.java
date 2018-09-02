package cn.itcast.bos.web.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.Role;
import cn.itcast.bos.service.IRoleService;
import cn.itcast.bos.web.action.base.BaseAction;
@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role> {
	@Autowired
	private IRoleService roleService;
	private String functionIds;
	
	public void setFunctionIds(String functionIds) {
		this.functionIds = functionIds;
	}

	/**
	 * 添加角色
	 * @return
	 */
	public String add() {
		roleService.save(model, functionIds);
		return LIST;
	}
	
	/**
	 * 分页查询
	 * @return
	 */
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
}
