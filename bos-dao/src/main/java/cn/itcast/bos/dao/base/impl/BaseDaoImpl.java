package cn.itcast.bos.dao.base.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import cn.itcast.bos.dao.base.IBaseDao;
/**
 * 持久层通用实现
 * @author 郭子灵
 *
 * @param <T>
 */
public class BaseDaoImpl<T> extends HibernateDaoSupport implements IBaseDao<T> {

	//代表的是某个实体的类型
	private Class<T> entityClass;
	
	@Resource//根据类型注入spring工厂中的会话工厂对象sessionFactory
	public void setMySessionFactory(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	//在父类（BaseDaoImpl）的构造方法中动态获得entityClass
	public BaseDaoImpl() {
		ParameterizedType superclass = (ParameterizedType) this.getClass().getGenericSuperclass();
		//获得父类上声明的泛型数组
		Type[] actualTypeArguments = superclass.getActualTypeArguments();
		entityClass = (Class<T>) actualTypeArguments[0];
	}


	public void save(T entity) {
		this.getHibernateTemplate().save(entity);

	}

	
	public void delete(T entity) {
		this.getHibernateTemplate().delete(entity);

	}

	
	public void update(T entity) {
		this.getHibernateTemplate().update(entity);

	}

	
	public T findById(Serializable id) {
		this.getHibernateTemplate().get(entityClass, id);
		return null;
	}

	
	public List<T> findAll() {
		String hql = "FRIM "+entityClass.getSimpleName();
		return (List<T>) this.getHibernateTemplate().find(hql);
	}

	//执行更新
	public void executeUpdate(String queryName, Object... objects) {
		Session session = this.getSessionFactory().getCurrentSession();
		Query query = session.getNamedQuery(queryName);
		int i = 0;
		for (Object object : objects) {
			//为HQL语句中的？赋值
			query.setParameter(i++, object);
		}
		//执行更新
		query.executeUpdate();
	}

}
