package com.legou.content.service;

import java.util.List;

import com.legou.common.pojo.EasyUITreeNode;
import com.legou.common.utils.LegouResult;
import com.legou.pojo.TbContentCategory;

public interface ContentCatService {

	List<EasyUITreeNode> getCatList(long parentid);
	LegouResult addCat(long parentId, String name);
	LegouResult deleteCat(long id);
	LegouResult updateCat(long id, String name);

}
