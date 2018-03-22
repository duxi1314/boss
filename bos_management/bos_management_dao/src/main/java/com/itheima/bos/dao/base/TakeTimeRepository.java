package com.itheima.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itheima.bos.domain.base.TakeTime;

/**
 * ClassName:TakeTimeRepository <br/>
 * Function: <br/>
 * Date: 2018年3月21日 下午8:44:25 <br/>
 */
public interface TakeTimeRepository extends JpaRepository<TakeTime, Long> {

}
