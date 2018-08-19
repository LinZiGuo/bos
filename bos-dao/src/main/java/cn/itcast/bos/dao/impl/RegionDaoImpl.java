package cn.itcast.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.itcast.bos.dao.IRegionDao;
import cn.itcast.bos.dao.base.impl.BaseDaoImpl;
import cn.itcast.bos.domain.Region;
@Repository
public class RegionDaoImpl extends BaseDaoImpl<Region> implements IRegionDao {

	/**
	 * 根据q模糊查询
	 */
	public List<Region> findByQ(String q) {
		String hql = "FROM Region r WHERE r.shortcode LIKE ? OR r.citycode LIKE ? OR r.province LIKE ? OR r.city LIKE ? OR r.district LIKE ?";
		return (List<Region>) this.getHibernateTemplate().find(hql, "%" + q + "%", "%" + q + "%", "%" + q + "%", "%" + q + "%", "%" + q + "%");
	}

}
