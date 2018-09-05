package cn.itcast.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.IWorkordermanageDao;
import cn.itcast.bos.domain.Workordermanage;
import cn.itcast.bos.service.IWorkordermanageService;
import cn.itcast.bos.utils.PageBean;
@Service
@Transactional
public class WorkordermanageServiceImpl implements IWorkordermanageService {
	@Autowired
	private IWorkordermanageDao workordermanageDao;
	
	public void save(Workordermanage model) {
		workordermanageDao.save(model);
	}

	/**
	 * 分页查询
	 */
	public void pageQuery(PageBean pageBean) {
		workordermanageDao.pageQuery(pageBean);
	}

	/**
	 * 导入工作单
	 */
	public void saveBatch(List<Workordermanage> list) {
		for (Workordermanage workordermanage : list) {
			workordermanageDao.save(workordermanage);
		}
	}

}
