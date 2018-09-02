package cn.itcast.bos.dao;

import java.util.List;

import cn.itcast.bos.dao.base.IBaseDao;
import cn.itcast.bos.domain.Subarea;

public interface ISubareaDao extends IBaseDao<Subarea> {

	public List<Subarea> findGroupedSubareas();
	
}
