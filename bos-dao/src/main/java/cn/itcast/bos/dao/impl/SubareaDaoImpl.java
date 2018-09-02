package cn.itcast.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.itcast.bos.dao.ISubareaDao;
import cn.itcast.bos.dao.base.impl.BaseDaoImpl;
import cn.itcast.bos.domain.Subarea;
@Repository
public class SubareaDaoImpl extends BaseDaoImpl<Subarea> implements ISubareaDao {

	/**
	 * 查询分组分区
	 */
	public List<Subarea> findGroupedSubareas() {
		String hql = "SELECT r.province ,count(*) FROM Subarea s LEFT OUTER JOIN s.region r Group BY r.province";
		return (List<Subarea>) this.getHibernateTemplate().find(hql);
	}

}
