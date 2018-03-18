package com.itheima.bos.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.SubAreaRepository;
import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.service.base.SubAreaService;

/**
 * ClassName:SubAreaServiceImpl <br/>
 * Function: <br/>
 * Date: 2018年3月18日 上午12:04:11 <br/>
 */

@Transactional
@Service
public class SubAreaServiceImpl implements SubAreaService {

	@Autowired
	private SubAreaRepository subAreaRepository;

	@Override
	public void save(SubArea SubArea) {

		subAreaRepository.save(SubArea);
	}
	
}
