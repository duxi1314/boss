package com.itheima.bos.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.CourierRepository;
import com.itheima.bos.dao.base.FixedAreaRepository;
import com.itheima.bos.dao.base.TakeTimeRepository;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.domain.base.TakeTime;
import com.itheima.bos.service.base.FixedAreaService;

/**
 * ClassName:FixedAreaServiceImpl <br/>
 * Function: <br/>
 * Date: 2018年3月19日 下午9:11:09 <br/>
 */

@Transactional
@Service
public class FixedAreaServiceImpl implements FixedAreaService {

	@Autowired
	private FixedAreaRepository fixedAreaRepository;

	@Autowired
	private CourierRepository courierRepository;
	
	@Autowired
	private TakeTimeRepository takeTimeRepository;

	@Override
	public void save(FixedArea model) {

		fixedAreaRepository.save(model);
	}

	@Override
	public Page<FixedArea> findAll(Pageable pageable) {

		return fixedAreaRepository.findAll(pageable);

	}

	@Override
	public void associationCourierToFixedArea(Long fixedAreaId, Long courierId,
			Long takeTimeId) {
		
		FixedArea fixedArea = fixedAreaRepository.findOne(fixedAreaId);
		Courier courier = courierRepository.findOne(courierId);
		TakeTime time = takeTimeRepository.findOne(takeTimeId);

		// 关联快递员和时间
		courier.setTakeTime(time);

		// 建立快递员和定区的关联
		// 在建立关联的时候只能使用定区关联快递员,不能使用快递员关联定区
		// 原因是因为Courier类中fixedAreas属性使用了mappedBy属性,代表快递员放弃了对中间表的维护

		fixedArea.getCouriers().add(courier);

	}

}
