package com.legou.service;

import java.util.Map;

import com.legou.common.pojo.EasyUIDataGridResult;
import com.legou.common.utils.LegouResult;
import com.legou.pojo.TbItem;
import com.legou.pojo.TbItemDesc;

public interface TbItemService {
	Map<String ,Object> getTbItemById(Long id);
	EasyUIDataGridResult getDataGridResult(int page,int rows);
	LegouResult addItemAndDesc(TbItem tbItem, String desc);
	LegouResult deleteItemAndDesc(long ids);
	LegouResult instock(long ids);
	LegouResult reshelf(long ids);
	LegouResult updateItemAndDesc(Map<String, Object> map);
	LegouResult getDesc(long id);
	
	//详情页需获取tbItem数据和desc的内容
	TbItem geTbItem(long id);
	TbItemDesc geTbItemDesc(long itemid);
	
	
}
