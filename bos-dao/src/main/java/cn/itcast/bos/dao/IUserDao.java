package cn.itcast.bos.dao;

import cn.itcast.bos.dao.base.IBaseDao;
import cn.itcast.bos.domain.User;

/**
 * 用户DAO
 * @author 郭子灵
 *
 */
public interface IUserDao extends IBaseDao<User> {

	/**
	 * 根据用户名和密码查询用户
	 * @param username
	 * @param password
	 * @return
	 */
	public User findUserByUsernameAndPassword(String username, String password);

	public User findUserByUsername(String username);
}
