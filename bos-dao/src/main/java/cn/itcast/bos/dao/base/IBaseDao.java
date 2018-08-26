package cn.itcast.bos.dao.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import cn.itcast.bos.domain.Staff;
import cn.itcast.bos.utils.PageBean;

/**
 * 持久层通用接口
 * @author 郭子灵
 *
 * @param <T>
 */
public interface IBaseDao<T> {
	public void save(T entity);
	public void delete(T entity);
	public void update(T entity);
	public T findById(Serializable id);
	public List<T> findAll();
	public void executeUpdate(String queryName, Object... objects);
	public void pageQuery(PageBean pageBean);
	public void saveOrUpdate(T entity);
	public List<T> findByCriteria(DetachedCriteria detachedCriteria);
}
