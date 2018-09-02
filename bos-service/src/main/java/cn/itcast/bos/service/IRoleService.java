package cn.itcast.bos.service;

import java.util.List;

import cn.itcast.bos.domain.Role;
import cn.itcast.bos.utils.PageBean;

public interface IRoleService {

	public void save(Role model, String functionIds);

	public void pageQuery(PageBean pageBean);

	public List<Role> findAll();

}
