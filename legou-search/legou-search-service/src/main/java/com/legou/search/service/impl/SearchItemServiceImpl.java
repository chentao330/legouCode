package com.legou.search.service.impl;

import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.legou.common.pojo.SearchItem;
import com.legou.common.utils.LegouResult;
import com.legou.search.mapper.SearchItemMapper;
import com.legou.search.service.SearchItemService;
@Service
public class SearchItemServiceImpl implements SearchItemService {

	@Autowired
	private SearchItemMapper searchItemMapper;
	@Autowired
	private SolrServer SolrServer;
	@Override
	public LegouResult importItems() {
		
		try {
			//获取item数据集合
			List<SearchItem> itemsList = searchItemMapper.getItemsList();
			
			//循环遍历集合
			for (SearchItem searchItem : itemsList) {
				//创建solr文档对象
				SolrInputDocument document=new SolrInputDocument();
				document.addField("id", searchItem.getId());
				document.addField("item_title", searchItem.getTitle());
				document.addField("item_sell_point", searchItem.getSell_point());
				document.addField("item_price", searchItem.getPrice());
				document.addField("item_image", searchItem.getImage());
				document.addField("item_category_name", searchItem.getCategory_name());
				SolrServer.add(document);
				SolrServer.commit();
			}
				return LegouResult.ok();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return LegouResult.build(500, "导入失败，出现数据异常");
			} 
			
		

	}

}
