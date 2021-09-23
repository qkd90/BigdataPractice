package com.hmlyinfo.app.soutu.signet.service;

import java.io.File;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.google.common.collect.Maps;
import com.hmlyinfo.app.soutu.account.domain.User;
import com.hmlyinfo.app.soutu.account.service.UserService;
import com.hmlyinfo.app.soutu.base.properties.Config;
import com.hmlyinfo.app.soutu.signet.domain.SignetBook;
import com.hmlyinfo.app.soutu.signet.mapper.SignetBookMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;
import com.hmlyinfo.base.util.UUIDUtil;

@Service
public class SignetBookService extends BaseService<SignetBook, Long>{

	@Autowired
	private SignetBookMapper<SignetBook> mapper;
	@Autowired
	private UserService userService;

	@Override
	public BaseMapper<SignetBook> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}
	
	public SignetBook get(long userId, String bookType)
	{
		Map<String, Object> paramMap = Maps.newHashMap();
		paramMap.put("userId", userId);
		paramMap.put("bookType", bookType);

		
		SignetBook book = (SignetBook) one(paramMap);
		if (book == null)
		{
			// 查询用户昵称
			User u = userService.info(userId);
			book = new SignetBook();
			book.setBookType(bookType);
			book.setUserId(userId);
			book.setTitle(u.getNickname() + "的曾厝垵之旅");
			
			//从图片服务器上拿到图片
			File srcImg = new File( Config.get("ImageAddress") + "/signet/handdrawn/" + bookType + ".jpg");
			String newUrl = "/signet/footmark/" + UUIDUtil.getUUID() + ".jpg";
			File uploadFile = new File( Config.get("ImageAddress") + newUrl);
			try {
				FileCopyUtils.copy(srcImg, uploadFile);
				book.setFootPrints( Config.get("SRV_ADDR_IMAGE_LOACL") + newUrl );
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			insert(book);
		}
		
		return book;
	}
	
}
