package com.itheima.bos.web.action.base;

import java.io.IOException;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.base.TakeTime;
import com.itheima.bos.service.base.TakeTimeService;

/**
 * ClassName:TakeTimeAction <br/>
 * Function: <br/>
 * Date: 2018年3月21日 下午8:35:58 <br/>
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class TakeTimeAction extends CommonAction<TakeTime> {

	public TakeTimeAction() {
		super(TakeTime.class);
	}

	@Autowired
	private TakeTimeService takeTimeService;

	@Action(value = "takeTimeAction_findAllTime")
	public String findAllTime() throws IOException {

		List<TakeTime> times = takeTimeService.findAll();

		listToJson(times, null);
		return NONE;

	}

}
