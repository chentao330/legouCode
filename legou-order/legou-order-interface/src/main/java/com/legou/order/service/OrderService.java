package com.legou.order.service;

import com.legou.common.utils.LegouResult;
import com.legou.order.pojo.OrderInfo;



public interface OrderService {

	LegouResult createOrder(OrderInfo orderInfo);
}
