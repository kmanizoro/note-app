package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.UserDetail;
import com.app.service.INoteService;
import com.app.util.AppConstants;
import com.app.util.AppException;
import com.app.util.JSONUtil;

@RestController
public class NoteController {

	@Autowired
	private INoteService noteService;
	
	@RequestMapping(value=AppConstants.URL_WELCOME)
	public String getWelcomePage() throws AppException{
		List<UserDetail> userList = null;
		try{
			userList = noteService.getUserDetailList();
			return JSONUtil.getJSONValueAsString(userList);
		}catch(Exception exception){
			throw new AppException(exception);
		}
	}
	
}
