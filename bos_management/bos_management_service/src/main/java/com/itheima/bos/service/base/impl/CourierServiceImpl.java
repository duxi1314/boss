package com.itheima.bos.service.base.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.CourierRepository;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.service.base.CourierService;

/**  
 * ClassName:CourierServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月15日 下午3:55:07 <br/>       
 */

@Transactional
@Service
public class CourierServiceImpl implements CourierService {

	
	@Autowired
	private CourierRepository courierRepository;
	

	@Override
	public Page<Courier> findAll(Pageable pageable) {
		Page<Courier> findAll = courierRepository.findAll(pageable);
		return findAll;
	}


	@Override
	public void save(Courier model) {
		courierRepository.save(model);
	}


	@Override
	public void batchDel(String ids) {
		
		if(StringUtils.isNotEmpty(ids)){
			String[] split = ids.split(",");
			for(String string: split){
				courierRepository.batchDel(Long.parseLong(string));
			}
			
		}
		
	}

}
  
