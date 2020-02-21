package com.atguigu.scw.order.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.scw.order.bean.TOrder;
import com.atguigu.scw.order.bean.TOrderExample;
import com.atguigu.scw.order.mapper.TOrderMapper;
import com.atguigu.scw.order.service.OrderService;
@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	TOrderMapper orderMapper;
	
	@Override
	public void createOrder(TOrder order) {
		//保证订单创建的幂等性：   scw-webui[ordernum]项目远程调用order项目实现订单的保存，scw-webui如果触发重试机制会导致一个订单创建多次
		//根据传入的order对象的ordernum检查是否重复提交
		TOrderExample example = new TOrderExample();
		example.createCriteria().andOrdernumEqualTo(order.getOrdernum());
		long countByOrderNum = orderMapper.countByExample(example);
		if(countByOrderNum>0) {
			//订单重复创建
			throw new RuntimeException("订单重复提交");
		}
		orderMapper.insertSelective(order);
	}

	@Override
	public void updateOrderStatus(String ordernum, String status) {
		TOrder record = new TOrder();//存储 要修改的数据
		record.setStatus(status);
		TOrderExample example = new TOrderExample();//存储条件  不会修改的字段
		example.setOrderByClause(ordernum);
		orderMapper.updateByExampleSelective(record, example);
	}

}
