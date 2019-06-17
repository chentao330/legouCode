package com.legou.cart.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.legou.cart.service.CartService;
import com.legou.common.jedis.JedisClient;
import com.legou.common.utils.JsonUtils;
import com.legou.common.utils.LegouResult;
import com.legou.mapper.TbItemMapper;
import com.legou.pojo.TbItem;
@Service
public class CartServiceImpl implements CartService {
	@Autowired
	private TbItemMapper tbItemMapper;
	@Autowired
	private JedisClient jedisClient;
	@Override
	public LegouResult addCart(long itemId, Integer num, Long userId) {
		//判断redis里是否存在这条数据
		Boolean hexists = jedisClient.hexists("cart:"+userId, itemId+"");
		System.out.println(hexists);
		//如果存在的话把数据获取出来
		if (hexists) {
			//获取redis里商品的数据
			String json = jedisClient.hget("cart:"+userId, itemId+"");
			//把json转换为商品类型的对象
			TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
			//获取出来对象后给他的num增值
			tbItem.setNum(tbItem.getNum()+num);
			
			//重新赋值后再添加进redis里
			jedisClient.hset("cart:"+userId, itemId+"", JsonUtils.objectToJson(tbItem));
			
			return LegouResult.ok();
		}
		//如果不存在的话
		//查询数据库id
		TbItem tbItem = tbItemMapper.selectByPrimaryKey(itemId);
		//给tbItem的num赋值
		tbItem.setNum(num);
		//购物车显示图片
		String image = tbItem.getImage();
		if(StringUtils.isNotBlank(image)) {
			tbItem.setImage(image.split(",")[0]);
		}
		//添加进redis里
		jedisClient.hset("cart:"+userId, itemId+"", JsonUtils.objectToJson(tbItem));

		return LegouResult.ok();
	}
	@Override
	public LegouResult deleteCartItem(Long userId, long itemId) {
		jedisClient.hdel("cart:"+userId, itemId+"");
		return LegouResult.ok();
	}
	@Override
	public LegouResult mergeCartItem(Long userId, List<TbItem> cartList) {
		
		//循环商品列表把商品信息都添加到购物车
		for (TbItem tbItem : cartList) {
			addCart(tbItem.getId(), tbItem.getNum(), userId);
		}
		
		return LegouResult.ok();
	}
	@Override
	public List<TbItem> getCart(Long userId) {
		//从redis里获取数据
		List<String> list = jedisClient.hvals("cart:"+userId);
		
		//创建一个tbItem用来存放list的对象
		List<TbItem> tbItems=new ArrayList<TbItem>();
		//循环list列表
		for (String json : list) {
			//将string类型的json转换成对象
			TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
			tbItems.add(tbItem);
		}
		
		return tbItems;
	}
	@Override
	public LegouResult updateCartItem(Long userId, long itemId, Integer num) {
		//获取redis里的数据
		String json = jedisClient.hget("cart:"+userId, itemId+"");
		//将json转化为对象
		TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
		//设置item的数量
		tbItem.setNum(num);
		
		//设置好后在把数据存入redis
		jedisClient.hset("cart:"+userId, itemId+"",JsonUtils.objectToJson(tbItem));
		return LegouResult.ok();
	}

}
