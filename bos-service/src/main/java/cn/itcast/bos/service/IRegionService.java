package cn.itcast.bos.service;

import java.util.List;

import cn.itcast.bos.domain.Region;
import cn.itcast.bos.utils.PageBean;

public interface IRegionService {

	public void saveBatch(List<Region> list);

	public void pageQuery(PageBean pageBean);

	public List<Region> findAll();

	public List<Region> findByQ(String q);

	public void batch(String ids);

}
