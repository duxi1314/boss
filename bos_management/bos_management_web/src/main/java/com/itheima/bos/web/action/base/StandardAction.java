package com.itheima.bos.web.action.base;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

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

import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.base.StandardService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * ClassName:StandardAction <br/>
 * Function: <br/>
 * Date: 2018年3月14日 下午4:15:54 <br/>
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class StandardAction extends ActionSupport
		implements ModelDriven<Standard> {

	private Standard model = new Standard();

	@Autowired
	private StandardService standardService;

	@Override
	public Standard getModel() {

		return model;
	}

	@Action(value = "standardAction_save", results = {
			@Result(name = "success", location = "/pages/base/standard.html", type = "redirect") })
	public String save() {
		System.out.println("do save:"+model.getName());
		standardService.save(model);
		System.out.println("after save:"+model);

		return SUCCESS;
	}
	
	
	private int page;
	private int rows;
	
	public void setPage(int page) {
		this.page = page;
	}
	
	public void setRows(int rows) {
		this.rows = rows;
	}
	
	
	//Ajax不用跳转界面
	@Action(value="standardAction_pageQuery")
	public String pageQuery() throws Exception {
		//EasyUI页面从1开始,Spring Data JPA页面从0开始,所以page要-1
		
		Pageable pageable= new PageRequest(page-1, rows);
		
		Page<Standard> page =standardService.findAll(pageable);
		
		//总数据条数
		long total = page.getTotalElements();
		
		//当前页面要实现的内容
		List<Standard> list = page.getContent();
		
		//封装数据
		Map<String,Object> map=new HashMap<>();
		map.put("total", total);
		map.put("rows", list);
		
		//JSONObject:封装对象或者Map集合
		//JSONArray:封装数组或者list集合
		
		//Ajax用JSON传数据,所以要把对象转成JSON字符串
		String json = JSONObject.fromObject(map).toString();
		
		
		HttpServletResponse response = ServletActionContext.getResponse();
		//解决乱码
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(json);
		
		return NONE;
	}
	
	@Action(value="standard_findAll")
	public String findAll() throws IOException{
		
		Page<Standard> page =standardService.findAll(null);
		List<Standard> list = page.getContent();
		
		String json = JSONArray.fromObject(list).toString();
		
		HttpServletResponse response = ServletActionContext.getResponse();
		//解决乱码
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(json);
		return NONE;
		
	}

}
