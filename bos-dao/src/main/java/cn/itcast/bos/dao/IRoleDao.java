package cn.itcast.bos.dao;

import java.util.List;

import cn.itcast.bos.dao.base.IBaseDao;
import cn.itcast.bos.domain.Function;
import cn.itcast.bos.domain.Role;

public interface IRoleDao extends IBaseDao<Role> {

	public List<Function> findFunctionsByRoleid(String roleid);

}
