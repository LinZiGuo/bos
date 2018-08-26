package cn.itcast.bos.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import cn.itcast.bos.domain.Region;
import cn.itcast.bos.utils.PageBean;

public interface IRegionService {

	public void saveBatch(List<Region> list);

	public void pageQuery(PageBean pageBean);

	public List<Region> findAll();

	public List<Region> findByQ(String q);

	public void batch(String ids);

	public void save(Region model);
	
	public String getNewId();

	public Region findById(String id);

	public void update(Region region);

	public Region findByCriteria(DetachedCriteria dc);

}
