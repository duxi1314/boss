package com.itheima.bos.web.action.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.base.CourierService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * ClassName:CourierAction <br/>
 * Function: <br/>
 * Date: 2018年3月15日 下午3:47:08 <br/>
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class CourierAction extends ActionSupport
		implements ModelDriven<Courier> {

	private Courier model = new Courier();

	@Autowired
	private CourierService courierService;

	@Override
	public Courier getModel() {

		return model;
	}

	@Action(value = "courierAction_save", results = {
			@Result(name = "success", location = "/pages/base/courier.html", type = "redirect") })
	public String save() {

		courierService.save(model);
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

	@Action("courierAction_pageQuery")
	public String pageQuery() throws IOException {

		// 构建查询条件
		Specification<Courier> specification = new Specification<Courier>() {
			/**
			 * 构建一个where条件语句
			 * 
			 * @param root
			 *            : 根对象,简单的理解为泛型的对象,
			 * @param cb
			 *            : 构建查询条件的对象
			 * @return a {@link Predicate}, must not be {@literal null}.
			 */
			@Override
			public Predicate toPredicate(Root<Courier> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {

				String courierNum = model.getCourierNum();
				String company = model.getCompany();
				String type = model.getType();
				Standard standard = model.getStandard();

				ArrayList<Predicate> list = new ArrayList<>();

				// 快递员工号不为空,构建一个等值查询
				if (StringUtils.isNoneEmpty(courierNum)) {
					Predicate p1 = cb.equal(
							root.get("courierNum").as(String.class),
							courierNum);
					list.add(p1);
				}
				if (StringUtils.isNoneEmpty(company)) {
					Predicate p2 = cb.like(root.get("company").as(String.class),
							company);
					list.add(p2);
				}
				if (StringUtils.isNoneEmpty(type)) {
					Predicate p3 = cb.equal(root.get("type").as(String.class),
							type);
					list.add(p3);
				}

				if (standard != null) {
					String name = standard.getName();
					if (StringUtils.isNoneEmpty(type)) {
						Join<Object, Object> join = root.join("standard");
						Predicate p4 = cb
								.equal(join.get("name").as(String.class), name);
						list.add(p4);
					}
				}
				// 用户没有输入查询条件
				if (list.size() == 0) {
					return null;
				}
				// 构造数组
				Predicate[] arr = new Predicate[list.size()];

				list.toArray(arr);
				return cb.and(arr);
			}
		};

		Pageable pageable = new PageRequest(page - 1, rows);
		Page<Courier> page = courierService.findAll(specification,pageable);

		long total = page.getTotalElements();
		List<Courier> list = page.getContent();

		Map<String, Object> map = new HashMap<>();

		map.put("total", total);
		map.put("rows", list);

		// 这里会发生懒加载,spring中只有集合属性会发生懒加载
		// 发生懒加载异常时的解决方式:
		// a.属性上增加transient 关键字,回导致所有调用都无法生成该字段的值
		// b.注解中增加fetch=FetchType.EAGER 属性,将该字段的值加载出来
		// c.使用JsonConfig灵活控制忽略字段(一般用这种,忽略前台页面不需要的字段,可以提高服务器性能.)

		JsonConfig jsonConfig = new JsonConfig();
		// Excludes忽略不用字段
		jsonConfig.setExcludes(new String[] { "fixedAreas", "takeTime" });

		String json = JSONObject.fromObject(map, jsonConfig).toString();

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(json);
		return NONE;

	}

	private String ids;

	public void setIds(String ids) {
		this.ids = ids;
	}

	@Action(value = "courierAction_batchDel", results = {
			@Result(name = "success", location = "/pages/base/courier.html", type = "redirect") })
	public String batchDel() {

		courierService.batchDel(ids);
		return SUCCESS;
	}

}
