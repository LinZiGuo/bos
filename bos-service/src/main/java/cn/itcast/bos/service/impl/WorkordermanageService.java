package cn.itcast.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.IWorkordermanageDao;
import cn.itcast.bos.domain.Workordermanage;
import cn.itcast.bos.service.IWorkordermanageService;
@Service
@Transactional
public class WorkordermanageService implements IWorkordermanageService {
	@Autowired
	private IWorkordermanageDao workordermanageDao;
	
	public void save(Workordermanage model) {
		workordermanageDao.save(model);
	}

}
