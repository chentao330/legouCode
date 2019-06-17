package com.legou.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.multipart.MultipartFile;

import com.legou.common.utils.FastDFSClient;
import com.legou.common.utils.JsonUtils;

@Controller
public class PicController {
	@Value("${IMAGE_SERVER_URL}")
	private String IMAGE_SERVER_URL;//IMAGE_SERVER_URL==图片服务器的地址：http://192.168.25.133/
	
	@RequestMapping("/pic/upload")
	@ResponseBody
	public String fileUpload(MultipartFile uploadFile,HttpServletRequest request ){
		FastDFSClient fastDFSClient;
		try {
			//client.conf：图片服务器的track地址：192.168.25.133:22122
			fastDFSClient = new FastDFSClient("classpath:conf/client.conf");
			//图片名
			String name=uploadFile.getOriginalFilename();
			//获取扩展名
			String extname=name.substring(name.lastIndexOf(".")+1);
			//获取图片的地址
			String url = fastDFSClient.uploadFile(uploadFile.getBytes(),extname);
			url=IMAGE_SERVER_URL+url;
			
			Map<String, Object> resultMap=new HashMap<String, Object>();
			resultMap.put("error", 0);
			resultMap.put("url",url);
			return JsonUtils.objectToJson(resultMap);
		} catch (Exception e) {
			Map<String, Object> resultMap=new HashMap<String, Object>();
			resultMap.put("error", 1);
			e.printStackTrace();
			return JsonUtils.objectToJson(resultMap);
		}
		
	}
}
