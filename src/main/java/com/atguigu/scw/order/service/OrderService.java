package com.atguigu.scw.order.service;

import com.atguigu.scw.order.bean.TOrder;

public interface OrderService {

	void createOrder(TOrder order);

	void updateOrderStatus(String ordernum, String status);

}
