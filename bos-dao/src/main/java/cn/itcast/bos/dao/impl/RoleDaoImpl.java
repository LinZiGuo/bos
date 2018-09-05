package cn.itcast.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.itcast.bos.dao.IRoleDao;
import cn.itcast.bos.dao.base.impl.BaseDaoImpl;
import cn.itcast.bos.domain.Function;
import cn.itcast.bos.domain.Role;
@Repository
public class RoleDaoImpl extends BaseDaoImpl<Role> implements IRoleDao {

	/**
	 * 根据角色ID查询关联的功能
	 */
	public List<Function> findFunctionsByRoleid(String roleid) {
		String hql = "SELECT DISTINCT f FROM Function f LEFT OUTER JOIN f.roles"
				+ " r WHERE r.id = ?";
		List<Function> list = (List<Function>) this.getHibernateTemplate().find(hql, roleid);
		return list;
	}

}
