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
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.Region;
import cn.itcast.bos.domain.Subarea;
import cn.itcast.bos.domain.Workordermanage;
import cn.itcast.bos.service.IWorkordermanageService;
import cn.itcast.bos.utils.FileUtils;
import cn.itcast.bos.web.action.base.BaseAction;
@Controller
@Scope("prototype")
public class WorkordermanageAction extends BaseAction<Workordermanage> {
	@Autowired
	private IWorkordermanageService workordermanageService;
	private File upload;

	public void setUpload(File upload) {
		this.upload = upload;
	}

	@RequiresPermissions("workordermanage-add")
	public String add() throws IOException {
		String f = "1";
		try{
			workordermanageService.save(model);
		}catch(Exception e){
			e.printStackTrace();
			f = "0";
		}
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(f);
		return NONE;
	}
	
	@RequiresPermissions("workordermanage-list")
	public String pageQuery() {
		workordermanageService.pageQuery(pageBean);
		this.WriteObject2Json(pageBean, new String[] {});
		return NONE;
	}
	
	/**
	 * 模板下载
	 * @return
	 * @throws IOException
	 */
	@RequiresPermissions("workordermanage-download")
	public String download() throws IOException {
		//2.使用POI将数据写到Excel文件中
		
		//在内存中创建一个Excel文件
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
		
		//在Excel中创建一个sheet
		HSSFSheet hssfSheet = hssfWorkbook.createSheet("工作单");
		
		//创建标题行
		HSSFRow titleRow = hssfSheet.createRow(0);
		titleRow.createCell(0).setCellValue("编号");
		titleRow.createCell(1).setCellValue("产品");
		titleRow.createCell(2).setCellValue("产品时限");
		titleRow.createCell(3).setCellValue("产品类型");
		titleRow.createCell(4).setCellValue("发件人姓名");
		titleRow.createCell(5).setCellValue("发件人电话");
		titleRow.createCell(6).setCellValue("发件人地址");
		titleRow.createCell(7).setCellValue("收件人姓名");
		titleRow.createCell(8).setCellValue("收件人电话");
		titleRow.createCell(9).setCellValue("收件人地址");
		titleRow.createCell(10).setCellValue("实际重量");
		
		String filename = "工作单导入模板.xls";
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
	
	@RequiresPermissions("workordermanage-batchImport")
	public String batchImport() throws IOException{
		String f = "success";
		try {
			List<Workordermanage> list = new ArrayList<Workordermanage>();
			//使用POI解析Excel文件
			//创建一个workbook对象，加载Excel文件
			HSSFWorkbook hssfWorkbook;
			hssfWorkbook = new HSSFWorkbook(new FileInputStream(upload));
			//取第一个标签页
			HSSFSheet sheet = hssfWorkbook.getSheet("工作单");
			for (Row row : sheet) {
				int rowNum = row.getRowNum();//行索引
				if(rowNum == 0) {
					continue;
				}
				row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
				String id = row.getCell(0).getStringCellValue();
				row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
				String product = row.getCell(1).getStringCellValue();
				row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
				String prodtimelimit = row.getCell(2).getStringCellValue();
				row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
				String prodtype = row.getCell(3).getStringCellValue();
				row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
				String sendername = row.getCell(4).getStringCellValue();
				row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
				String senderphone = row.getCell(5).getStringCellValue();
				row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
				String senderaddr = row.getCell(6).getStringCellValue();
				row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
				String receivername = row.getCell(7).getStringCellValue();
				row.getCell(8).setCellType(Cell.CELL_TYPE_STRING);
				String receiverphone = row.getCell(8).getStringCellValue();
				row.getCell(9).setCellType(Cell.CELL_TYPE_STRING);
				String receiveraddr = row.getCell(9).getStringCellValue();
				row.getCell(10).setCellType(Cell.CELL_TYPE_STRING);
				Double actlweit = Double.valueOf(row.getCell(10).getStringCellValue());
				
				Workordermanage workordermanage = new Workordermanage(id);
				workordermanage.setProduct(product);
				workordermanage.setProdtimelimit(prodtimelimit);
				workordermanage.setProdtype(prodtype);
				workordermanage.setSendername(sendername);
				workordermanage.setSenderphone(senderphone);
				workordermanage.setSenderaddr(senderaddr);
				workordermanage.setReceivername(receivername);
				workordermanage.setReceiverphone(receiverphone);
				workordermanage.setReceiveraddr(receiveraddr);
				workordermanage.setActlweit(actlweit);
				
				list.add(workordermanage);
			}
			workordermanageService.saveBatch(list);
		} catch (Exception e) {
			e.printStackTrace();
			f = "failure";
		}
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(f);
		return NONE;
	}
}
