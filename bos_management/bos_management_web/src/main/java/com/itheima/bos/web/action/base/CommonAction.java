package com.itheima.bos.web.action.base;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.data.domain.Page;

import com.itheima.bos.domain.base.Area;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * ClassName:CommonAction <br/>
 * Function: <br/>
 * Date: 2018年3月17日 下午8:35:56 <br/>
 */
public class CommonAction<T> extends ActionSupport implements ModelDriven<T> {

	private T model;
	private Class<T> clazz;

	public CommonAction(Class<T> clazz) {
		this.clazz = clazz;
	}

	@Override
	public T getModel() {
		try {
			if (model == null) {
				//每次都会重新创建一次对象,所以要进行判断
				model = clazz.newInstance();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	// 分页

	protected int page;
	protected int rows;

	public void setPage(int page) {
		this.page = page;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public void pageToJson(Page<T> page, JsonConfig jsonConfig)
			throws IOException {

		// 总数据条数
		long total = page.getTotalElements();

		// 当前页面要实现的内容
		List<T> list = page.getContent();

		// 封装数据
		Map<String, Object> map = new HashMap<>();
		map.put("total", total);
		map.put("rows", list);

		// Ajax用JSON传数据,所以要把对象转成JSON字符串

		String json;

		if (jsonConfig != null) {
			json = JSONObject.fromObject(map, jsonConfig).toString();
		} else {
			json = JSONObject.fromObject(map).toString();
		}
		// 解决乱码
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(json);
	}

	public void listToJson(List<T> list, JsonConfig jsonConfig)
			throws IOException {
		String json;

		if (jsonConfig != null) {
			json = JSONArray.fromObject(list, jsonConfig).toString();
		} else {
			json = JSONArray.fromObject(list).toString();
		}

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(json);
	}
}
