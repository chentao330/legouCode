package com.legou.cart.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.omg.PortableInterceptor.USER_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.legou.cart.service.CartService;
import com.legou.common.utils.CookieUtils;
import com.legou.common.utils.JsonUtils;
import com.legou.common.utils.LegouResult;
import com.legou.pojo.TbItem;
import com.legou.pojo.TbUser;
import com.legou.service.TbItemService;

@Controller
public class CartController {
	@Autowired
	private TbItemService itemService;
	@Autowired
	private CartService cartService;
	
	//添加商品至购物车
	@RequestMapping("/cart/add/{itemId}")
	public String addCart(@PathVariable long itemId,@RequestParam(defaultValue="1") Integer num, HttpServletRequest request,HttpServletResponse response) {
		
		TbUser tbUser = (TbUser) request.getAttribute("user");
		if (tbUser!=null) {
			cartService.addCart(itemId,num,tbUser.getId());
			return "cartSuccess";
		}
		
		//获取缓存中的购物车数据
		List<TbItem> cartList = getCartList(request);
		//判断商品是否存在
		boolean flag=false;
		//循环list 查询cookie里是否存在本商品
		for (TbItem tbItem : cartList) {
			//判断传过来的id是否有等于cookie里商品的id
			//如果有
			if (tbItem.getId().longValue()==itemId) {
				flag=true;
				//设置它的数量
				tbItem.setNum(tbItem.getNum()+num);
				//跳出循环
				break;
			}
		}
		//如果不存在的话
		if(!flag) {
			//查询此商品id对应的数据
			TbItem tbItem = itemService.geTbItem(itemId);
			//设置商品的数量
			tbItem.setNum(num);
			//设置商品显示的图片
			String image=tbItem.getImage();
			//判断图片是否为空
			//如果不为空
			if (StringUtils.isNotBlank(image)) {
				//设置图片
				tbItem.setImage(image.split(",")[0]);
			}
			//将商品加入list
			cartList.add(tbItem);
		}
		//将list添加到cookie
		CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList),18000,true);
		
		return "cartSuccess";
	}
	
	//在购物车删除商品
	@RequestMapping("/cart/delete/{itemId}")
	public String deleteCart(@PathVariable long itemId, HttpServletRequest request,HttpServletResponse response) {
		//获取缓存中的购物车数据
		List<TbItem> cartList = getCartList(request);
		//获取用户信息
		TbUser tbUser = (TbUser) request.getAttribute("user");
		if (tbUser!=null) {
			cartService.deleteCartItem(tbUser.getId(),itemId);
			return "redirect:/cart/cart.html";
		}
		
		//循环list 查询cookie里是否存在本商品
		for (TbItem tbItem : cartList) {
			//判断传过来的id是否有等于cookie里商品的id
			//如果有
			if (tbItem.getId().longValue()==itemId) {
				
				//删除
				cartList.remove(tbItem);
				//跳出循环
				break;
			}
		}
		CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList),1800,true);
		
		return "redirect:/cart/cart.html";
	}
	//在购物车更新商品
	@RequestMapping("/cart/update/num/{itemId}/{num}")
	@ResponseBody
	public LegouResult updateCart(@PathVariable long itemId, @PathVariable Integer num,HttpServletRequest request,HttpServletResponse response) {
		//获取缓存中的购物车数据
		List<TbItem> cartList = getCartList(request);
		TbUser tbUser  =(TbUser) request.getAttribute("user");
		if (tbUser!=null) {
			cartService.updateCartItem(tbUser.getId(),itemId,num);
			return LegouResult.ok();
		}
		//循环list 查询cookie里是否存在本商品
		for (TbItem tbItem : cartList) {
			//判断传过来的id是否有等于cookie里商品的id
			//如果有
			if (tbItem.getId().longValue()==itemId) {
				//设置它的数量
				tbItem.setNum(num);
				//跳出循环
				break;
			}
		}
		CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList),18000,true);
		return LegouResult.ok();
	}
	
	//添加商品至购物车
		@RequestMapping("/cart/cart")
		public String getCart(HttpServletRequest request,HttpServletResponse response) {
			//获取缓存中的购物车数据
			List<TbItem> cartList = getCartList(request);
			//获取用户信息
			TbUser tbUser = (TbUser) request.getAttribute("user");
			//如果用户信息不为空的话则传用户id与购物车数据
			if (tbUser!=null) {
				//合并购物车 cookie与redis存在的数据合并
				cartService.mergeCartItem(tbUser.getId(),cartList);
				//删除缓存
				CookieUtils.deleteCookie(request, response, "cart");
				//将页面显示出来合并的数据
				cartList=cartService.getCart(tbUser.getId());
			}
			
			//放入request
			request.setAttribute("cartList", cartList);
			return "cart";
		}
	
	
	
	

	public List<TbItem> getCartList(HttpServletRequest request){
		//使用cookie工具类获取cookie的值
		String json = CookieUtils.getCookieValue(request, "cart",true);
		//如果为空则创建一个新的arrayList
		if (StringUtils.isBlank(json)) {
			return new ArrayList<TbItem>();
		}
		//不为空返回数据
		List<TbItem> list = JsonUtils.jsonToList(json, TbItem.class);
		return list;
	}
	
}
