package com.itheima.bos.web.action.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.base.Area;
import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.base.AreaService;
import com.itheima.utils.PinYin4jUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * ClassName:AreaAction <br/>
 * Function: <br/>
 * Date: 2018年3月16日 下午3:27:35 <br/>
 */

@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class AreaAction extends CommonAction<Area> {

	// 本类创建AreaService对象需要的无参依旧可以用
	// 如果在本来创建一个有参,再创建一个无参,会使得有参构造方法失效,进而使得父类获取不到所需的字节码
	public AreaAction() {
		super(Area.class);
	}

	@Autowired
	private AreaService areaService;

	private File file;

	public void setFile(File file) {
		this.file = file;
	}

	@Action(value = "areaAction_importXLS", results = {
			@Result(name = "success", location = "/pages/base/area.html", type = "redirect") })
	public String importXLS() throws Exception {
		// 用来保存数据的集合
		ArrayList<Area> list = new ArrayList<>();

		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(file));
		// 读取工作簿
		HSSFSheet sheetAt = workbook.getSheetAt(0);
		// 遍历行
		for (Row row : sheetAt) {
			// 第一行是标题,这一行的数据不要
			if (row.getRowNum() == 0) {
				continue;
			}
			// 读取表格的数据
			String province = row.getCell(1).getStringCellValue();
			String city = row.getCell(2).getStringCellValue();
			String district = row.getCell(3).getStringCellValue();
			String postcode = row.getCell(4).getStringCellValue();
			// 截掉省市区的最后一个字符
			province.substring(0, province.length() - 1);
			city.substring(0, city.length() - 1);
			district.substring(0, district.length() - 1);

			String citycode = PinYin4jUtils.hanziToPinyin(city, "")
					.toUpperCase();
			String[] headByString = PinYin4jUtils
					.getHeadByString(province + city + district);

			String shortcode = PinYin4jUtils.stringArrayToString(headByString);

			// 构造一个Area
			Area area = new Area();
			area.setProvince(province);
			area.setCity(city);
			area.setDistrict(district);
			area.setPostcode(postcode);
			area.setCitycode(citycode);
			area.setShortcode(shortcode);

			// area添加到list
			list.add(area);

			// 保存区域数据
			areaService.save(list);

			// 关闭流
			workbook.close();

		}
		return SUCCESS;
	}

	// 区域分页

	@Action(value = "areaAction_pageQuery")
	public String pageQuery() throws Exception {
		// EasyUI页面从1开始,Spring Data JPA页面从0开始,所以page要-1

		Pageable pageable = new PageRequest(page - 1, rows);
		Page<Area> page = areaService.findAll(pageable);

		// 避免懒加载,用JsonConfig忽略不用字段
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[] { "subareas" });

		pageToJson(page, jsonConfig);

		return NONE;
	}
	
	//属性驱动获取输入框内容
	private String q;
	
	public void setQ(String q) {
		this.q = q;
	}
	
	@Action(value = "areaAction_findAll")
	public String findAll() throws Exception {
		List<Area> list;
		
		if(StringUtils.isNotEmpty(q)){
			list = areaService.findBy(q);
		}else{
			Page<Area> page = areaService.findAll(null);
			list = page.getContent();
		}


		// 避免懒加载,用JsonConfig忽略不用字段
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[] { "subareas" });

		listToJson(list, jsonConfig);
		return NONE;
	}
}
