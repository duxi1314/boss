package com.itheima.bos.web.action.base;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
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

import com.itheima.bos.domain.base.Customer;
import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.service.base.FixedAreaService;

import net.sf.json.JsonConfig;

/**
 * ClassName:FixedAreaAction <br/>
 * Function: <br/>
 * Date: 2018年3月19日 下午9:04:29 <br/>
 */

@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class FixedAreaAction extends CommonAction<FixedArea> {

	@Autowired
	private FixedAreaService fixedAreaService;

	public FixedAreaAction() {
		super(FixedArea.class);
	}

	@Action(value = "fixedAreaAction_save", results = {
			@Result(name = "success", location = "/pages/base/fixed_area.html", type = "redirect") })
	public String save() {
		fixedAreaService.save(getModel());
		return SUCCESS;
	}

	// Ajax不用跳转界面
	@Action(value = "fixedAreaAction_pageQuery")
	public String pageQuery() throws Exception {
		// EasyUI页面从1开始,Spring Data JPA页面从0开始,所以page要-1

		Pageable pageable = new PageRequest(page - 1, rows);
		Page<FixedArea> page = fixedAreaService.findAll(pageable);

		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[] { "subareas", "couriers" });

		pageToJson(page, jsonConfig);
		return NONE;
	}

	// 向CRM系统发送请求,获取未关联定区的客户
	@Action(value = "fixedAreaAction_findUnAssociatedCustomers")
	public String findUnAssociatedCustomers() throws IOException {

		List<Customer> list = (List<Customer>) WebClient
				.create("http://localhost:8180/crm/webService/customerService/findByUnAssociated")
				.type(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.getCollection(Customer.class);

		listToJson(list, null);
		return NONE;
	}

	// 向CRM系统发送请求,获取已关联定区的客户
	@Action(value = "fixedAreaAction_findAssociatedCustomers")
	public String findAssociatedCustomers() throws IOException {

		List<Customer> list = (List<Customer>) WebClient
				.create("http://localhost:8180/crm/webService/customerService/findByAssociated")
				.query("fixedAreaId", getModel().getId())
				.type(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.getCollection(Customer.class);

		listToJson(list, null);
		return NONE;
	}

	//-------关联客户到定区
	private Long[] customerIds;

	public void setCustomerIds(Long[] customerIds) {
		this.customerIds = customerIds;
	}

	@Action(value = "fixedAreaAction_assignCustomers2FixedArea", results = {
			@Result(name = "success", location = "/pages/base/fixed_area.html", type = "redirect") })
	public String assignCustomers2FixedArea() {
				WebClient
				.create("http://localhost:8180/crm/webService/customerService/bindCustomer2FixedArea")
				.query("fixedAreaId", getModel().getId())
				.query("customerIds", customerIds)
				.type(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.put(null);

		return SUCCESS;
	}
	
	
	//-----关联快递员到定区
	private Long courierId;
	public void setCourierId(Long courierId) {
		this.courierId = courierId;
	}
	
	private Long takeTimeId;
	public void setTakeTimeId(Long takeTimeId) {
		this.takeTimeId = takeTimeId;
	}
	
	
	@Action(value = "fixedAreaAction_associationCourierToFixedArea", results = {
			@Result(name = "success", location = "/pages/base/fixed_area.html", type = "redirect") })
	public String associationCourierToFixedArea() throws Exception{
		
		
		fixedAreaService.associationCourierToFixedArea(getModel().getId(),courierId,takeTimeId);

		return SUCCESS;
	}
}
