package cn.itcast.bos.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.IRegionDao;
import cn.itcast.bos.domain.Region;
import cn.itcast.bos.service.IRegionService;
import cn.itcast.bos.utils.PageBean;
@Service
@Transactional
public class RegionServiceImpl implements IRegionService {
	@Autowired
	private IRegionDao regionDao;

	/**
	 * 批量保存
	 */
	public void saveBatch(List<Region> list) {
		for (Region region : list) {
			regionDao.saveOrUpdate(region);
		}
	}

	/**
	 * 分页查询
	 */
	public void pageQuery(PageBean pageBean) {
		regionDao.pageQuery(pageBean);
	}

	public List<Region> findAll() {
		return regionDao.findAll();
	}

	/**
	 * 根据Q进行模糊查询
	 */
	public List<Region> findByQ(String q) {
		return regionDao.findByQ(q);
	}

	/**
	 * 批量删除区域
	 */
	public void batch(String ids) {
		if(StringUtils.isNotBlank(ids)) {
			String[] regionId = ids.split(",");
			for (String id : regionId) {
				Region region = regionDao.findById(id);
				regionDao.delete(region);
			}
		}
	}

	
}
