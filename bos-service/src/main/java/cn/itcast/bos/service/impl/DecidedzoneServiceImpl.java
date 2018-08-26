package cn.itcast.bos.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.IDecidedzoneDao;
import cn.itcast.bos.dao.ISubareaDao;
import cn.itcast.bos.domain.Decidedzone;
import cn.itcast.bos.domain.Subarea;
import cn.itcast.bos.service.IDecidedzoneService;
import cn.itcast.bos.utils.PageBean;
@Service
@Transactional
public class DecidedzoneServiceImpl implements IDecidedzoneService {

	@Autowired
	private IDecidedzoneDao decidedzoneDao;
	@Autowired
	private ISubareaDao subareaDao;
	/**
	 * 分页查询
	 */
	public void pageQuery(PageBean pageBean) {
		decidedzoneDao.pageQuery(pageBean);
	}
	
	/**
	 * 添加分区
	 */
	public void save(Decidedzone model) {
		decidedzoneDao.save(model);
	}

	/**
	 * 根据定区id查询定区
	 */
	public Decidedzone findById(String id) {
		return decidedzoneDao.findById(id);
	}

	/**
	 * 修改分区
	 */
	public void update(Decidedzone decidedzone) {
		decidedzoneDao.update(decidedzone);
	}

	/**
	 * 保存定区，同时关联分区
	 */
	public void save(Decidedzone model, String[] subareaid) {
		decidedzoneDao.save(model);
		for (String id : subareaid) {
			Subarea subarea = subareaDao.findById(id);
			subarea.setDecidedzone(model);
		}
	}

	/**
	 * 批量删除定区，同时删除关联分区
	 */
	public void batch(String ids) {
		if (StringUtils.isNotBlank(ids)) {
			String[] decidedzoneid = ids.split(",");
			for (String id : decidedzoneid) {
				Decidedzone decidedzone = decidedzoneDao.findById(id);
				subareaDao.executeUpdate("subarea.deleteDecidedzone", id);
				decidedzoneDao.delete(decidedzone);
			}
		}
	}

}
