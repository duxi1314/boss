package com.itheima.bos.web.action.base;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.service.base.SubAreaService;

/**  
 * ClassName:SubAreaAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月18日 上午12:05:57 <br/>       
 */

@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class SubAreaAction extends CommonAction<SubArea>{

	public SubAreaAction() {
		super(SubArea.class);  
	}
	
	@Autowired
	private SubAreaService subAreaService;
	
	

	@Action(value = "subareaAction_save", results = {
			@Result(name = "success", location = "/pages/base/sub_area.html", type = "redirect") })
	public String save(){
		
		subAreaService.save(getModel());
		
		return SUCCESS;
	}
	
}
  
