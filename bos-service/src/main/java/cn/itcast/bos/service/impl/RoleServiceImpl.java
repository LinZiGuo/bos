package cn.itcast.bos.service.impl;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.IRoleDao;
import cn.itcast.bos.domain.Function;
import cn.itcast.bos.domain.Role;
import cn.itcast.bos.service.IRoleService;
import cn.itcast.bos.utils.PageBean;
@Service
@Transactional
public class RoleServiceImpl implements IRoleService {
	@Autowired
	private IRoleDao roleDao;

	/**
	 * 添加一个角色，同时需要关联权限
	 */
	public void save(Role model, String functionIds) {
		if(StringUtils.isNotBlank(functionIds)) {
			String[] ids = functionIds.split(",");
			for (String id : ids) {
				Function function = new Function();
				function.setId(id);//托管对象
				model.getFunctions().add(function);//角色关联权限
			}
		}
		roleDao.save(model);//持久状态
	}

	/**
	 * 分页查询
	 */
	public void pageQuery(PageBean pageBean) {
		roleDao.pageQuery(pageBean);
	}

	/**
	 * 查询角色
	 */
	public List<Role> findAll() {
		return roleDao.findAll();
	}

	/**
	 * 根据id查询角色
	 */
	public Role findById(String roleid) {
		Role role = roleDao.findById(roleid);
		List<Function> functions = roleDao.findFunctionsByRoleid(roleid);
		for (Function function : functions) {
			role.getFunctions().add(function);
		}
		return role;
	}

	/**
	 * 修改角色
	 */
	public void update(Role role, String functionIds) {
		roleDao.update(role);//持久状态
		if(StringUtils.isNotBlank(functionIds)) {
			String[] ids = functionIds.split(",");
			for (String id : ids) {
				Function function = new Function();
				function.setId(id);//托管对象
				role.getFunctions().add(function);//角色关联权限
			}
		}
	}
}
