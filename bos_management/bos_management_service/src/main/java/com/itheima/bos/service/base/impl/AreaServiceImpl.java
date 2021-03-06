package com.itheima.bos.service.base.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.AreaRepository;
import com.itheima.bos.domain.base.Area;
import com.itheima.bos.service.base.AreaService;

/**  
 * ClassName:AreaServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月16日 下午3:59:58 <br/>       
 */
@Transactional
@Service
public class AreaServiceImpl implements AreaService {
	
	@Autowired
	private AreaRepository areaRepository;

	@Override
	public void save(ArrayList<Area> list) {
		  
		areaRepository.save(list);
	}

	@Override
	public Page<Area> findAll(Pageable pageable) {
		  
		return areaRepository.findAll(pageable);
		
	}

	@Override
	public List<Area> findBy(String q) {
		  q="%"+q.toUpperCase()+"%";
		return areaRepository.findAll(q);
	}
}
  
