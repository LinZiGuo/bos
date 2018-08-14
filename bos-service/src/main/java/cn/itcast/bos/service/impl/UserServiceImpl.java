package cn.itcast.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.IUserDao;
import cn.itcast.bos.domain.User;
import cn.itcast.bos.service.IUserService;
import cn.itcast.bos.utils.MD5Utils;
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

}
