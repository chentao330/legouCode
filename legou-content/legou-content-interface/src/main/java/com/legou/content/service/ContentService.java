package com.legou.content.service;

import com.legou.common.pojo.EasyUIDataGridResult;
import com.legou.common.utils.LegouResult;
import com.legou.pojo.TbContent;

public interface ContentService {

	EasyUIDataGridResult getContentList(long categoryId, int page, int rows);

	LegouResult addContent(TbContent tbContent);

	LegouResult editContent(TbContent tbContent);

	LegouResult deleteContent(long ids);



}
