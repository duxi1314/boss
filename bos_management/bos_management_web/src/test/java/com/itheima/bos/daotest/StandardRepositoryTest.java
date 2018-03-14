package com.itheima.bos.daotest;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.StandardRepository;
import com.itheima.bos.domain.base.Standard;

/**  
 * ClassName:StandardRepositoryTest <br/>  
 * Function:  <br/>  
 * Date:     2018年3月13日 下午10:36:24 <br/>       
 */


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class StandardRepositoryTest {
	@Autowired
	private StandardRepository standardRepository;
	@Test
	public void test() {
	
		List<Standard> findAll = standardRepository.findAll();
		
		for(Standard standard:findAll ){
			System.out.println(standard);
		}
	}

	@Test
	public void test01() {
	
		Standard standard=new Standard();
		standard.setName("张三三");
		standard.setMaxWeight(200);
		
		standardRepository.save(standard);
	}
	
	@Test
	public void test02() {
	
		Standard standard=new Standard();
		standard.setId(1L);
		standard.setName("张三");
		standard.setMaxWeight(999);
		
		standardRepository.save(standard);
	}
	
	
	@Test
	public void test03() {
	
		Standard one = standardRepository.findOne(2L);
		System.out.println(one);
		
	}
	
	
	@Test
	public void test04() {
		
		standardRepository.delete(2L);
		
	}
	
	
	@Test
	public void test05() {
		
		List<Standard> list = standardRepository.findByNameLike("张三%");
		for(Standard standard:list){
			
			System.out.println(standard);
		}
	}
	
	
	@Test
	public void test06() {
		
		List<Standard> list = standardRepository.findByNameAndMaxWeight("张三", 999);
		for(Standard standard:list){
			
			System.out.println(standard);
		}
		
	}
	
	
	@Test
	public void test07() {
		
		List<Standard> list = standardRepository.findByNameAndMaxWeight1223131("张三", 999);
		for(Standard standard:list){
			
			System.out.println(standard);
		}
		
	}
	
	@Test
	public void test08() {
		
		List<Standard> list = standardRepository.findByNameAndMaxWeight1223131(999, "张三");
		for(Standard standard:list){
			
			System.out.println(standard);
		}
		
	}
		
		@Test
		public void test09() {
			
			List<Standard> list = standardRepository.findByNameAndMaxWeight11111("张三", 999);
			for(Standard standard:list){
				
				System.out.println(standard);
			}
		}
		
		
		
		@Test
		public void test10() {
			
			standardRepository.updateMaxWeightByName(666, "张三");
		}
		
		@Test
		public void test11() {
			
			standardRepository.deleteByName("张三");
		}
}
  
