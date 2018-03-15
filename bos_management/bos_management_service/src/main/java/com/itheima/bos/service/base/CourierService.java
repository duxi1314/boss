package com.itheima.bos.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itheima.bos.domain.base.Courier;

/**  
 * ClassName:CourierService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月15日 下午3:54:29 <br/>       
 */
public interface CourierService {

	void save(Courier model);

	Page<Courier> findAll(Pageable pageable);

	void batchDel(String ids);


}
  
