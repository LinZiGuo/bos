package cn.itcast.bos.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import cn.itcast.bos.domain.Region;
import cn.itcast.bos.domain.Subarea;
import cn.itcast.bos.utils.PageBean;

public interface ISubareaService {

	public void save(Subarea model);

	public void pageQuery(PageBean pageBean);

	public List<Subarea> findAll();

	public Subarea findById(String id);

	public void update(Subarea subarea);

	public void batch(String ids);

	public void saveBatch(List<Subarea> list);

	public List<Subarea> findListNotAssociation();

	public List<Subarea> findListByDecidedzoneId(String decidedzoneId);

	public List<Subarea> findGroupedSubareas();

}
