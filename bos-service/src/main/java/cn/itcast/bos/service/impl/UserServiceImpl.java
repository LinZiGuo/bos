package cn.itcast.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.IUserDao;
import cn.itcast.bos.domain.Role;
import cn.itcast.bos.domain.User;
import cn.itcast.bos.service.IUserService;
import cn.itcast.bos.utils.MD5Utils;
import cn.itcast.bos.utils.PageBean;
@Service
@Transactional
public class UserServiceImpl implements IUserService {
	@Autowired
	private IUserDao userDao;
	/**
	 * 用户登录
	 */
	public User login(User model) {
		//使用MD5的算法进行加密密码
		String password = MD5Utils.md5(model.getPassword());
		return userDao.findUserByUsernameAndPassword(model.getUsername(), password);
	}
	
	/**
	 * 当前用户修改密码
	 */
	public void editPassword(User user) {
		userDao.executeUpdate("user.editPassword",user.getPassword(),user.getId());
	}

	/**
	 * 添加用户，同时关联角色
	 */
	public void save(User model, String[] roleIds) {
		String password = MD5Utils.md5(model.getPassword());
		model.setPassword(password);
		userDao.save(model);//持久状态
		if (roleIds != null && roleIds.length > 0) {
			for (String roleId : roleIds) {
				Role role = new Role(roleId);//托管对象
				model.getRoles().add(role);//用户关联角色
			}
		}
	}

	/**
	 * 分页查询
	 */
	public void pageQuery(PageBean pageBean) {
		userDao.pageQuery(pageBean);
	}

}
