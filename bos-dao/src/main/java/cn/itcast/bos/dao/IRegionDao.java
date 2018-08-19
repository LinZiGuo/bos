package cn.itcast.bos.dao;

import java.util.List;

import cn.itcast.bos.dao.base.IBaseDao;
import cn.itcast.bos.domain.Region;

public interface IRegionDao extends IBaseDao<Region> {

	public List<Region> findByQ(String q);

}
