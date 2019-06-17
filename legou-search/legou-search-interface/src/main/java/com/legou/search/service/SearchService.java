package com.legou.search.service;



import com.legou.common.pojo.SearchResult;
import com.legou.common.utils.LegouResult;

public interface SearchService {
	SearchResult search(String keyword,int page,int rows) throws Exception;
}