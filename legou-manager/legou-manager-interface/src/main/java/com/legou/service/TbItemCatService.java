package com.legou.service;

import java.util.List;


import com.legou.common.pojo.EasyUITreeNode;

public interface TbItemCatService {
	
	List<EasyUITreeNode> getItemCatList(long parentId);
}
