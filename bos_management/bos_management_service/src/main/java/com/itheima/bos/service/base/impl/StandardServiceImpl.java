package com.itheima.bos.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.StandardRepository;
import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.base.StandardService;

/**
 * ClassName:StandardServiceImpl <br/>
 * Function: <br/>
 * Date: 2018年3月14日 下午4:36:32 <br/>
 */
@Transactional
@Service
public class StandardServiceImpl implements StandardService {

	@Autowired
	private StandardRepository standardRepository;

	@Override
	public void save(Standard model) {
		standardRepository.save(model);
	}

	@Override
	public Page<Standard> findAll(Pageable pageable) {
		  
		Page<Standard> page = standardRepository.findAll(pageable);
		return page;
	}
	

}
