package com.legou.search.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.legou.common.pojo.SearchItem;
import com.legou.common.pojo.SearchResult;

@Repository
public class SearchDao {
	
	@Autowired
	private SolrServer solrServer;
	
	public SearchResult getSearchResult(SolrQuery query) throws SolrServerException {
		//获取查询对象
		QueryResponse response = solrServer.query(query);
		//获取对象文档的list
		SolrDocumentList results = response.getResults();
		//获取对象总条数
		long numFound = results.getNumFound();
		//获取高亮
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
		//创建searchResult对象用来设置里面的属性
		SearchResult searchResult=new SearchResult();
		searchResult.setRecordCount(numFound);
		
		//创建searchItem的集合类用来循环时添加对象
		List<SearchItem>  searchItemsList=new ArrayList<SearchItem>();
		//遍历文档list
		for (SolrDocument document : results) {
			//创建searchItem用来设置里面的属性
			SearchItem searchItem=new SearchItem();
			searchItem.setId((String) document.get("id"));
			searchItem.setPrice((long) document.get("item_price"));
			searchItem.setImage((String) document.get("item_image"));
			searchItem.setCategory_name((String) document.get("item_category_name"));
			searchItem.setSell_point((String) document.get("item_sell_point"));
			//获取高亮的数据
			List<String> list =highlighting.get(document.get("id")).get("item_title");
			//定义一个title参数用来存放list的高亮数据
			String title="";
			//判断list是否为空
			//如果不为空
			if (list!=null && list.size()>0) {
				title=list.get(0);
			}else {
				//如果为空则显示数据标题即可
				title=(String) document.get("item_title");
			}
			searchItem.setTitle(title);
			
			searchItemsList.add(searchItem);
		}	
		searchResult.setItemList(searchItemsList);
		return searchResult;
		
	}
	
	
}
