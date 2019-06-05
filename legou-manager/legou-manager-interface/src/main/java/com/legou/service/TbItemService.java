package com.legou.service;

import com.legou.common.pojo.EasyUIDataGridResult;
import com.legou.pojo.TbItem;

public interface TbItemService {
	TbItem getTbItemById(Long id);
	EasyUIDataGridResult getDataGridResult(int page,int rows);
}
