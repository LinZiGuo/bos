package cn.itcast.bos.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.ISubareaDao;
import cn.itcast.bos.domain.Region;
import cn.itcast.bos.domain.Subarea;
import cn.itcast.bos.service.ISubareaService;
import cn.itcast.bos.utils.PageBean;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
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

	@Override
	public List<Subarea> findAll() {
		return subareaDao.findAll();
	}

	@Override
	public Subarea findById(String id) {
		return subareaDao.findById(id);
	}

	@Override
	public void update(Subarea subarea) {
		subareaDao.update(subarea);
	}

	/**
	 * 批量删除分区
	 */
	public void batch(String ids) {
		if (StringUtils.isNotBlank(ids)) {
			String[] subareaid = ids.split(",");
			for (String id : subareaid) {
				subareaDao.delete(subareaDao.findById(id));
			}
		}
	}

	/**
	 * 批量保存
	 */
	public void saveBatch(List<Subarea> list) {
		for (Subarea subarea : list) {
			subareaDao.save(subarea);
		}
	}

	/**
	 * 查询未关联的分区
	 */
	public List<Subarea> findListNotAssociation() {
		DetachedCriteria dc = DetachedCriteria.forClass(Subarea.class);
		dc.add(Restrictions.isNull("decidedzone"));
		return subareaDao.findByCriteria(dc);
	}

	/**
	 * 根据定区id查询关联的分区
	 */
	public List<Subarea> findListByDecidedzoneId(String decidedzoneId) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Subarea.class);
		//添加过滤条件
		detachedCriteria.add(Restrictions.eq("decidedzone.id", decidedzoneId));
		return subareaDao.findByCriteria(detachedCriteria);
	}

}
