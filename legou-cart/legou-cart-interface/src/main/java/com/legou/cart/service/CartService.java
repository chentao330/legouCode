package com.legou.cart.service;

import java.util.List;

import com.legou.common.utils.LegouResult;
import com.legou.pojo.TbItem;

public interface CartService {
	LegouResult addCart(long itemId, Integer num, Long userId);

	LegouResult deleteCartItem(Long userId, long itemId);

	LegouResult mergeCartItem(Long userId, List<TbItem> cartList);

	List<TbItem> getCart(Long userId);

	LegouResult updateCartItem(Long userId, long itemId, Integer num);

	LegouResult clearCartItem(Long userId);
}
