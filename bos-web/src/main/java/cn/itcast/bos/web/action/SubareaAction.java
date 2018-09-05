package cn.itcast.bos.web.action;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.Region;
import cn.itcast.bos.domain.Staff;
import cn.itcast.bos.domain.Subarea;
import cn.itcast.bos.service.IRegionService;
import cn.itcast.bos.service.ISubareaService;
import cn.itcast.bos.utils.FileUtils;
import cn.itcast.bos.utils.PinYin4jUtils;
import cn.itcast.bos.web.action.base.BaseAction;
@Controller
@Scope("prototype")
public class SubareaAction extends BaseAction<Subarea> {
	@Autowired
	private ISubareaService subareaService;
	@Autowired
	private IRegionService regionService;
	private String query;
	private String ids;
	private File subareaFile;
	private String decidedzoneId;
	
	public String getDecidedzoneId() {
		return decidedzoneId;
	}

	public void setDecidedzoneId(String decidedzoneId) {
		this.decidedzoneId = decidedzoneId;
	}

	public void setSubareaFile(File subareaFile) {
		this.subareaFile = subareaFile;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	/**
	 * 保存分区
	 * @return
	 */
	@RequiresPermissions(value= {"subarea-add","subarea-edit"}, logical=Logical.OR)
	public String add() {
		Subarea subarea = subareaService.findById(model.getId());
		if (subarea != null) {
			subarea.setRegion(model.getRegion());
			subarea.setAddresskey(model.getAddresskey());
			subarea.setStartnum(model.getStartnum());
			subarea.setEndnum(model.getEndnum());
			subarea.setSingle(model.getSingle());
			subarea.setPosition(model.getPosition());
			subareaService.update(subarea);
		} else {
			subareaService.save(model);
		}
		return LIST;
	}
	
	/**
	 * 分页查询
	 * @return
	 */
	@RequiresPermissions("subarea-list")
	public String pageQuery() {
		DetachedCriteria dc = pageBean.getDetachedCriteria();
		//动态添加过滤条件
		String addresskey = model.getAddresskey();
		Region region = model.getRegion();
		if (StringUtils.isNotBlank(addresskey)) {
			//添加过滤条件，根据地址关键字进行模糊查询
			dc.add(Restrictions.like("addresskey", "%"+addresskey+"%"));
		}
		if (region != null) {
			String province = region.getProvince();
			String city = region.getCity();
			String district = region.getDistrict();
			
			//添加过滤条件，根据省份进行模糊查询--多表关联查询，使用别名方式实现
			//参数一：分区对象中关联的区域对象属性名称
			//参数二：别名写什么都行
			dc.createAlias("region", "r");
			if (StringUtils.isNotBlank(province)) {
				dc.add(Restrictions.like("r.province", "%"+province+"%"));
			}
			if (StringUtils.isNotBlank(city)) {
				dc.add(Restrictions.like("r.city", "%"+city+"%"));
			}
			if (StringUtils.isNotBlank(district)) {
				dc.add(Restrictions.like("r.district", "%"+district+"%"));
			}
		}
		subareaService.pageQuery(pageBean);
		WriteObject2Json(pageBean, new String[] {"currentPage","pageSize","detachedCriteria","decidedzone", "subareas"});
		return NONE;
	}
	
	/**
	 * 分区导出功能
	 * @return
	 * @throws IOException 
	 */
	@RequiresPermissions("subarea-export")
	public String exportXls() throws IOException {
		//1.查询分区所有数据
		List<Subarea> list = subareaService.findAll();
		
		//2.使用POI将数据写到Excel文件中
		
		//在内存中创建一个Excel文件
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
		
		//在Excel中创建一个sheet
		HSSFSheet hssfSheet = hssfWorkbook.createSheet("分区数据");
		
		//创建标题行
		HSSFRow titleRow = hssfSheet.createRow(0);
		titleRow.createCell(0).setCellValue("分区编号");
		titleRow.createCell(1).setCellValue("分区关键字");
		titleRow.createCell(2).setCellValue("分区地址信息");
		titleRow.createCell(3).setCellValue("省市区");
		
		//变量List集合，将分区数据写到Excel表格中
		for (Subarea subarea : list) {
			HSSFRow dataRow = hssfSheet.createRow(hssfSheet.getLastRowNum()+1);
			dataRow.createCell(0).setCellValue(subarea.getId());
			dataRow.createCell(1).setCellValue(subarea.getAddresskey());
			dataRow.createCell(2).setCellValue(subarea.getPosition());
			dataRow.createCell(3).setCellValue(subarea.getRegion().getName());
		}
		
		String filename = "分区数据.xls";
		String agent = ServletActionContext.getRequest().getHeader("User-Agent");
		filename = FileUtils.encodeDownloadFilename(filename, agent);
		
		//通过输出流将文件下载（一个流，两个头）
		ServletOutputStream out = ServletActionContext.getResponse().getOutputStream();
		String mimeType = ServletActionContext.getServletContext().getMimeType(filename);
		
		//一个头信息
		ServletActionContext.getResponse().setContentType(mimeType);;
		
		//一个头信息
		ServletActionContext.getResponse().setHeader("content-disposition", "attchment;filename="+filename);
		
		hssfWorkbook.write(out);
		return NONE;
	}
	
	/**
	 * 删除分区
	 * @return
	 */
	@RequiresPermissions("subarea-delete")
	public String delete() {
		subareaService.batch(ids);
		return LIST;
	}
	
	/**
	 * 导入分区
	 * @return
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	@RequiresPermissions("subarea-import")
	public String importXls() throws FileNotFoundException, IOException {
		List<Subarea> list = new ArrayList<Subarea>();
		//使用POI解析Excel文件
		//创建一个workbook对象，加载Excel文件
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(new FileInputStream(subareaFile));
		//取第一个标签页
		HSSFSheet sheet = hssfWorkbook.getSheet("分区数据");
		for (Row row : sheet) {
			int rowNum = row.getRowNum();//行索引
			if(rowNum == 0) {
				continue;
			}
			row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
			String id = row.getCell(0).getStringCellValue();
			row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
			String region = row.getCell(1).getStringCellValue();
			row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
			String addresskey = row.getCell(2).getStringCellValue();
			row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
			String startnum = row.getCell(3).getStringCellValue();
			row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
			String endnum = row.getCell(4).getStringCellValue();
			row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
			String single = row.getCell(5).getStringCellValue();
			row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
			String position = row.getCell(6).getStringCellValue();
			
			Subarea subarea = new Subarea(id);
			subarea.setAddresskey(addresskey);
			subarea.setStartnum(startnum);
			subarea.setEndnum(endnum);
			subarea.setPosition(position);
			
			if (single.equals("单双号")) {
				subarea.setSingle("0");
			} else if (single.equals("单号")) {
				subarea.setSingle("1");
			} else if (single.equals("双号")) {
				subarea.setSingle("2");
			}
			
			if (StringUtils.isNotBlank(region)) {
				String[] r = region.split("\\s+");
				DetachedCriteria dc = DetachedCriteria.forClass(Region.class);
				dc.add(Restrictions.eq("province", r[0]));
				dc.add(Restrictions.eq("city", r[1]));
				dc.add(Restrictions.eq("district", r[2]));
				Region re = regionService.findByCriteria(dc);
				subarea.setRegion(re);
			}
			list.add(subarea);
		}
		subareaService.saveBatch(list);
		return LIST;
	}
	
	/**
	 * 查询未关联的分区，返回json
	 * @return
	 */
	public String listajax() {
		List<Subarea> list = subareaService.findListNotAssociation();
		WriteObject2Json(list, new String[] {"decidedzone","region","startnum","endnum","single"});
		return NONE;
	}
	
	/**
	 * 根据定区id查询关联的分区
	 * @return
	 */
	public String findListByDecidedzoneId() {
		List<Subarea> list = subareaService.findListByDecidedzoneId(decidedzoneId);
		this.WriteObject2Json(list, new String[] {"decidedzone","subareas"});
		return NONE;
	}
	
	/**
	 * 查询分组分区
	 * @return
	 */
	@RequiresPermissions("subarea-charts")
	public String findGroupedSubareas() {
		List<Subarea> list = subareaService.findGroupedSubareas();
		this.WriteObject2Json(list, new String[] {});
		return NONE;
	}
}
