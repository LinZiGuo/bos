package cn.itcast.bos.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
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
	
	/**
	 * 获取新增的ID
	 */
	public String getNewId() {
		String hql = "FROM Region r ORDER BY r.id DESC";
		List list = this.getHibernateTemplate().find(hql);
		if(list.size() > 0) {
			Region region = (Region) list.get(0);
			String last = region.getId();
			int num = Integer.valueOf(last.substring(last.length()-3));
			String id = "QY" + String.format("%03d", num+1);
			return id;
		}
		return "QY001";
	}
}
