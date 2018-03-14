package com.itheima.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.domain.base.Standard;


/**  
 * ClassName:StandardRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月13日 下午10:32:46 <br/>       
 */
public interface StandardRepository extends JpaRepository<Standard, Long> {
	
		List<Standard> findByNameLike(String name);
		List<Standard> findByNameAndMaxWeight(String name,Integer maxWeight);
		
		@Query("from Standard where name=? and maxWeight=?")
		List<Standard> findByNameAndMaxWeight1223131(String name,Integer maxWeight);
		
		@Query("from Standard where name=?2 and maxWeight=?1")
		List<Standard> findByNameAndMaxWeight1223131(Integer maxWeight,String name);
		
		
		@Query(value="select * from T_STANDARD where C_NAME= ? and C_MAX_WEIGHT =?",
				nativeQuery=true)
		List<Standard> findByNameAndMaxWeight11111(String name,Integer maxWeight);
		
		@Transactional
		@Modifying
		@Query("update Standard set maxWeight=? Where name=?")
		void updateMaxWeightByName(Integer maxWeight,String name);
		
		@Transactional
		@Modifying
		@Query("delete from Standard Where name=?")
		void deleteByName(String name);
}
  
