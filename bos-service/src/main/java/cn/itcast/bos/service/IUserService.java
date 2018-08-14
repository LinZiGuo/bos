package cn.itcast.bos.service;

import cn.itcast.bos.domain.User;

public interface IUserService {

	public User login(User model);

	public void editPassword(User user);

}
