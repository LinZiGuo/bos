package cn.itcast.bos.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import cn.itcast.bos.dao.IWorkordermanageDao;
import cn.itcast.bos.dao.base.impl.BaseDaoImpl;
import cn.itcast.bos.domain.Workordermanage;
import cn.itcast.bos.utils.PageBean;
@Repository
public class WorkordermanageDao extends BaseDaoImpl<Workordermanage> implements IWorkordermanageDao {

}
