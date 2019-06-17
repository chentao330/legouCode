package com.legou.search.cookie;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

public class cookiesave {
	/**
	* 保存Cookie到客户端
	* 传递进来的user对象中封装了在登陆时填写的用户名与密码
	* @param user
	* @param response
	*/
	//保存cookie时的cookieName
    private final static String cookieDomainName = "gamecenter";
    //加密cookie时的网站自定码
    private final static String webKey = "gamecenter";
    //设置cookie有效期是两个星期，根据需要自定义
    private final static long cookieMaxAge = 60 * 60 * 24 * 7 * 2;
	@Test
	public static void saveCookie( HttpServletResponse response) throws Exception {
		
		String username="chentao";
		String password="123456";
		 //cookie的有效期
        long validTime = System.currentTimeMillis() + (cookieMaxAge * 5000);
        //MD5加密用户详细信息
        String cookieValueWithMd5 =getMD5(username + ":" + password+ ":" + validTime + ":" + webKey);
        //将要被保存的完整的Cookie值
        String cookieValue = username + ":" + validTime + ":" + cookieValueWithMd5;
        //再一次对Cookie的值进行BASE64编码
       // String cookieValueBase64 = new String(Base64Util.encode(cookieValue.getBytes()));
        //开始保存Cookie
        Cookie cookie = new Cookie(cookieDomainName, cookieValue);
        //存两年(这个值应该大于或等于validTime)
        cookie.setMaxAge(60 * 60 * 24 * 365 * 2);
        //cookie有效路径是网站根目录
        cookie.setPath("/");
        //向客户端写入
        response.addCookie(cookie);
        
        
        
//		//写cookie     
//		String name="chen";
//		String password="123456";
//		Cookie namecookie = new Cookie("name", name);
//		//写cookie     
//
//		Cookie passwordcookie  =new Cookie("password",password);
//
//		//生命周期         
//		namecookie.setMaxAge(60*60*24*365);
//		passwordcookie.setMaxAge(60*60*24*365);
//		//设置哪个域名写cookie  
//		namecookie.setDomain("www.***.com");
//		passwordcookie.setDomain("www.***.com");
//
//		response.addCookie(namecookie);
//		response.addCookie(passwordcookie);

	}
	 //获取Cookie组合字符串的MD5码的字符串----------------------------------------------------------------
    public static String getMD5(String value) {
           String result = null;
           try{
                  byte[] valueByte = value.getBytes();
                  MessageDigest md = MessageDigest.getInstance("MD5");
                  md.update(valueByte);
                  result = toHex(md.digest());
           } catch (NoSuchAlgorithmException e){
                  e.printStackTrace();
           }
           return result;
    }
    //将传递进来的字节数组转换成十六进制的字符串形式并返回
    private static String toHex(byte[] buffer){
           StringBuffer sb = new StringBuffer(buffer.length * 2);
           for (int i = 0; i < buffer.length; i++){
                  sb.append(Character.forDigit((buffer[i] & 0xf0) >> 4, 16));
                  sb.append(Character.forDigit(buffer[i] & 0x0f, 16));
           }
           return sb.toString();
    }
}
