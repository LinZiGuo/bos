package cn.itcast.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.ISubareaDao;
import cn.itcast.bos.domain.Subarea;
import cn.itcast.bos.service.ISubareaService;
import cn.itcast.bos.utils.PageBean;
@Service
@Transactional
public class SubareaServiceImpl implements ISubareaService {
	@Autowired
	private ISubareaDao subareaDao;

	/**
	 * 保存分区
	 */
	public void save(Subarea model) {
		subareaDao.save(model);
	}

	/**
	 * 分页查询
	 */
	public void pageQuery(PageBean pageBean) {
		subareaDao.pageQuery(pageBean);
	}

}
