package com.atguigu.scw.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.common.bean.AppResponse;
import com.atguigu.scw.order.bean.TOrder;
import com.atguigu.scw.order.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {
	@Autowired
	OrderService orderService;
	//2、更新订单的方法
	@GetMapping("/updateOrderStatus")
	public AppResponse<Object> updateOrderStatus(@RequestParam("ordernum") String ordernum,@RequestParam("status")String status){
		orderService.updateOrderStatus(ordernum,status);
		return AppResponse.ok("更新成功");
	}
	
	//1、创建订单的方法    复杂类型数据在远程调用传递时，作为方法参数 必须使用@RequestBody标注
	@PostMapping("/createOrder")
	public AppResponse<Object> createOrder(@RequestBody TOrder order){
		//调用订单业务层将订单存到数据库中
		try {
			orderService.createOrder(order);
		} catch (Exception e) {
			e.printStackTrace();
			return AppResponse.fail("30001", e.getMessage());
		}
		return AppResponse.ok("订单创建成功");
	}
	
}
