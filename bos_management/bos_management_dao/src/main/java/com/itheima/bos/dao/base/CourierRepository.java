package com.itheima.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.itheima.bos.domain.base.Courier;

/**  
 * ClassName:CourierRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月15日 下午4:01:27 <br/>       
 */
public interface CourierRepository extends JpaRepository<Courier,Long> {

	
	@Modifying
	@Query("update Courier set deltag=1 where id=?")
	void batchDel(long parseLong);
	
}
  
