package cn.itcast.bos.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.Region;
import cn.itcast.bos.service.IRegionService;
import cn.itcast.bos.utils.PageBean;
import cn.itcast.bos.utils.PinYin4jUtils;
import cn.itcast.bos.web.action.base.BaseAction;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
/**
 * 区域管理
 * @author 郭子灵
 *
 */
@Controller
@Scope("prototype")
public class RegionAction extends BaseAction<Region> {
	@Autowired
	private IRegionService regionService;
	public File regionFile;
	private String q;
	private String ids;

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public void setQ(String q) {
		this.q = q;
	}

	public void setRegionFile(File regionFile) {
		this.regionFile = regionFile;
	}

	/**
	 * 文件上传方法
	 * @return
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	@RequiresPermissions("region-import")
	public String importXls() throws FileNotFoundException, IOException {
		List<Region> list = new ArrayList<Region>();
		//使用POI解析Excel文件
		//创建一个workbook对象，加载Excel文件
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(new FileInputStream(regionFile));
		//取第一个标签页
		HSSFSheet sheet = hssfWorkbook.getSheet("Sheet1");
		for (Row row : sheet) {
			int rowNum = row.getRowNum();//行索引
			if(rowNum == 0) {
				continue;
			}
			String id = row.getCell(0).getStringCellValue();
			String province = row.getCell(1).getStringCellValue();
			String city = row.getCell(2).getStringCellValue();
			String district = row.getCell(3).getStringCellValue();
			String postcode = row.getCell(4).getStringCellValue();
			
			Region region = new Region(id,province,city,district,postcode,null,null,null);
			
			province = province.substring(0, province.length()-1);
			city = city.substring(0, city.length()-1);
			district = district.substring(0, district.length()-1);
			String info = province + city + district;
			//城市简码
			String[] headByString = PinYin4jUtils.getHeadByString(info);
			String shortcode = StringUtils.join(headByString,"");
			//城市编码
			String citycode = PinYin4jUtils.hanziToPinyin(city,"");
			
			region.setShortcode(shortcode);
			region.setCitycode(citycode);
			
			list.add(region);
		}
		regionService.saveBatch(list);
		return NONE;
	}
	
	/**
	 * 分页查询
	 * @return
	 * @throws IOException 
	 */
	@RequiresPermissions("region-list")
	public String pageQuery() throws IOException {
		regionService.pageQuery(pageBean);
		this.WriteObject2Json(pageBean, new String[] {"currentPage","pageSize","detachedCriteria","subareas"});
		return NONE;
	}
	
	/**
	 * 查询所有区域，写回json数据
	 * @return
	 */
	public String listajax() {
		List<Region> list = null;//regionService.findAll();
		if (StringUtils.isNotBlank(q)) {
			//根据q进行模糊查询
			list = regionService.findByQ(q);
		} else {
			//查询所有区域
			list = regionService.findAll();
		}
		this.WriteObject2Json(list, new String[] {"subareas"});
		return NONE;
	}
	
	/**
	 * 批量删除区域
	 * @return
	 */
	@RequiresPermissions("region-delete")
	public String delete() {
		regionService.batch(ids);
		return LIST;
	}
	
	/**
	 * 添加/修改区域
	 * @return
	 */
	@RequiresPermissions(value= {"region-add","region-edit"},logical=Logical.OR)
	public String add() {
		String id = model.getId();
		if (StringUtils.isNotBlank(id)) {
			Region region = regionService.findById(id);
			region.setProvince(model.getProvince());
			region.setCity(model.getCity());
			region.setDistrict(model.getDistrict());
			region.setPostcode(model.getPostcode());
			region.setShortcode(model.getShortcode());
			region.setCitycode(model.getCitycode());
			regionService.update(region);
		} else {
			regionService.save(model);
		}
		return LIST;
	}
}
