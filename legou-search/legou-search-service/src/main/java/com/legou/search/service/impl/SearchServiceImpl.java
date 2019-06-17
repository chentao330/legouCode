package com.legou.search.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.legou.common.pojo.SearchResult;
import com.legou.search.dao.SearchDao;
import com.legou.search.service.SearchService;
@Service
public class SearchServiceImpl implements SearchService {
	@Autowired
	private SearchDao searchDao;
	
	@Override
	public SearchResult search(String keyword, int page, int rows) throws Exception {
		//创建solrQuery查询对象
		SolrQuery query=new SolrQuery();
		//设置查询的对象
		query.setQuery(keyword);
		//设置分页
		query.setStart((page-1)*rows);
		query.setRows(rows);
		//设置为根据item_title方式来查询
		query.set("df", "item_title");
		//开启高亮
		query.setHighlight(true);
		//设置高亮
		query.setHighlightSimplePre("<em style='color:red'>");
		query.setHighlightSimplePost("</em>");
		//设置高亮的属性为item_title
		query.addHighlightField("item_title");
		
		SearchResult searchResult = searchDao.getSearchResult(query);
		
		long recordCount = searchResult.getRecordCount();
		int totalPage=(int) (recordCount/rows);
		if (recordCount % rows>0) {
			totalPage++;
		}
		searchResult.setTotalPages(totalPage);
		
		return searchResult;
	}

}
