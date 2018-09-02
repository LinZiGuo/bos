package cn.itcast.bos.service;

import cn.itcast.bos.domain.User;
import cn.itcast.bos.utils.PageBean;

public interface IUserService {

	public User login(User model);

	public void editPassword(User user);

	public void save(User model, String[] roleIds);

	public void pageQuery(PageBean pageBean);

}
