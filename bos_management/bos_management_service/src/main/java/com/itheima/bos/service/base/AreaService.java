package com.itheima.bos.service.base;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itheima.bos.domain.base.Area;

/**  
 * ClassName:AreaService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月16日 下午3:59:09 <br/>       
 */
public interface AreaService {

	void save(ArrayList<Area> list);

	Page<Area> findAll(Pageable pageable);

	List<Area> findBy(String q);
	
}
  
