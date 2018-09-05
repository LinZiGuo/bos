package cn.itcast.bos.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.IFunctionDao;
import cn.itcast.bos.domain.Function;
import cn.itcast.bos.domain.User;
import cn.itcast.bos.service.IFunctionService;
import cn.itcast.bos.utils.BOSUtils;
import cn.itcast.bos.utils.PageBean;
@Service
@Transactional
public class FunctionServiceImpl implements IFunctionService {
	@Autowired
	private IFunctionDao functionDao;

	/**
	 * 查询所有权限数据
	 */
	public List<Function> findAll() {
		return functionDao.findAll();
	}

	/**
	 * 添加新权限
	 */
	public void save(Function model) {
		if (model.getParentFunction() != null && model.getParentFunction().getId() != null &&
				model.getParentFunction().getId().equals("")) {
			model.setParentFunction(null);
		}
		functionDao.save(model);
	}

	/**
	 * 分页查询
	 */
	public void pageQuery(PageBean pageBean) {
		functionDao.pageQuery(pageBean);
	}

	/**
	 * 根据登录人查询对应菜单
	 */
	public List<Function> findMenu() {
		List<Function> list = null;
		User user = BOSUtils.getLoginUser();
		if(user.getUsername().equals("admin")){
			//如果是超级管理员内置用户，查询所有菜单
			list = functionDao.findAllMenu();
		}else{
			//其他用户，根据用户id查询菜单
			list = functionDao.findMenuByUserId(user.getId());
		}
		return list;
	}

	/**
	 * 批量删除功能
	 */
	public void batch(String ids) {
		if (StringUtils.isNotBlank(ids)) {
			String[] functionIds = ids.split(",");
			for (String functionId : functionIds) {
				functionDao.delete(functionDao.findById(functionId));
			}
		}
	}

}
