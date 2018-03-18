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
public class StandardAction extends CommonAction<Standard> {

	public StandardAction() {
		super(Standard.class);  
	}

	@Autowired
	private StandardService standardService;

	@Action(value = "standardAction_save", results = {
			@Result(name = "success", location = "/pages/base/standard.html", type = "redirect") })
	public String save() {
		standardService.save(getModel());
		return SUCCESS;
	}
	
	
	
	//Ajax不用跳转界面
	@Action(value="standardAction_pageQuery")
	public String pageQuery() throws Exception {
		//EasyUI页面从1开始,Spring Data JPA页面从0开始,所以page要-1
		
		Pageable pageable= new PageRequest(page-1, rows);
		Page<Standard> page =standardService.findAll(pageable);
		
		pageToJson(page,null);
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
