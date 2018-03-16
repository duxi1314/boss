package com.itheima.bos.web.action.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.base.Area;
import com.itheima.bos.service.base.AreaService;
import com.itheima.utils.PinYin4jUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * ClassName:AreaAction <br/>
 * Function: <br/>
 * Date: 2018年3月16日 下午3:27:35 <br/>
 */

@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class AreaAction extends ActionSupport implements ModelDriven<Area> {

	private Area model = new Area();

	@Autowired
	private AreaService areaService;

	@Override
	public Area getModel() {

		return model;
	}

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

			String citycode = PinYin4jUtils.hanziToPinyin(city,"").toUpperCase();
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

}
