package com.legou.search.message;

import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import com.legou.common.pojo.SearchItem;
import com.legou.pojo.TbItem;
import com.legou.search.mapper.SearchItemMapper;

public class ItemAddMessageListener implements  MessageListener{

	@Autowired
	private SearchItemMapper searchItemMapper;
	@Autowired
	private SolrServer solrServer;
	
	@Override
	public void onMessage(Message message) {
		try {
			//把message转成文本消息可以获取值
			TextMessage textMessage=(TextMessage) message;
			//获取传过来的id文本
			String text = textMessage.getText();
			//将文本强转成long类型
			long id=new Long(text);
			
			//设置线程等1秒
			Thread.sleep(1000);
			//查询数据库对应的数据
			SearchItem searchItem=searchItemMapper.getItemById(id);
			
			SolrInputDocument document=new SolrInputDocument();
			document.addField("id", searchItem.getId());
			document.addField("item_title", searchItem.getTitle());
			document.addField("item_sell_point", searchItem.getSell_point());
			document.addField("item_price", searchItem.getPrice());
			document.addField("item_image", searchItem.getImage());
			document.addField("item_category_name", searchItem.getCategory_name());
			solrServer.add(document);
			solrServer.commit();
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
