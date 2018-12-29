package com.app.note.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.app.note.bean.NoteDetailsVO;
import com.app.note.bean.NoteInfo;
import com.app.note.bean.TagDetail;
import com.app.note.bean.TokenDetail;
import com.app.note.bean.UserDetail;
import com.app.note.service.INoteService;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class AppHelper {

	private static final Logger logger = LogManager.getLogger(AppHelper.class);

	@Autowired
	INoteService noteService;

	@Autowired
	MessageSource messageSource;

	public ModelMap validateSignUpUser(ModelMap modelMap, String userName,String password,String emailId,Locale locale) throws AppException{

		boolean validated = true;

		if (AppUtil.isNull(userName)) {
			modelMap.put(AppConstants.MESSAGE_ERROR, 
					getMessage(AppConstants.LOGIN_USERNAME_EMPTY, locale));
			validated = false;
		} if (AppUtil.isNull(emailId)) {
			modelMap.put(AppConstants.MESSAGE_ERROR, 
					getMessage(AppConstants.LOGIN_PASSWORD_EMPTY, locale));
			validated = false;
		} if (AppUtil.isNull(password)) {
			modelMap.put(AppConstants.MESSAGE_ERROR, 
					getMessage(AppConstants.LOGIN_EMAIL_EMPTY, locale));
			validated = false;
		}
		if (validated && !org.apache.commons.validator.routines.
				EmailValidator.getInstance(false, true).isValid(emailId)) {
			modelMap.put(AppConstants.MESSAGE_ERROR, 
					getMessage(AppConstants.LOGIN_EMAIL_ID_INVALID, locale));
			validated = false;
		}
		if (validated && (noteService.isUserExistsByEMail(emailId) ||
				noteService.isUserExistsByUserName(userName))) {
			modelMap.put(AppConstants.MESSAGE_ERROR, 
					getMessage(AppConstants.LOGIN_USERNAME_EMAIL_ALREADY_EXISTS, locale));
			validated = false;
		} 

		return modelMap;
	}

	public ModelMap validateNewUserHash(ModelMap modelMap, String tokenStr,String userEmail,Locale locale) throws AppException{
		boolean isValid = true;
		TokenDetail tokenDetail = noteService.getTokenDetails(tokenStr);

		if (!noteService.isUserExistsByEMail(userEmail)) {
			modelMap.put(AppConstants.MESSAGE_ERROR, 
					getMessage(AppConstants.LOGIN_VERIFICATION_EMAIL_NOT_FOUND, locale));
			isValid = false;
		}

		if (isValid && AppUtil.isNotNull(tokenDetail)) {
			String diff = AppUtil.compareDate(AppUtil.getCurrentDate(), tokenDetail.getTokenEnddate());
			if (!(AppConstants.LESS.equals(diff) || 
					AppConstants.EQUAL.equals(diff))) {
				modelMap.put(AppConstants.MESSAGE_ERROR, 
						getMessage(AppConstants.LOGIN_VERIFICATION_TIMEOUT, locale));
				isValid = false;
			}
		}

		if (isValid && AppUtil.isNotNull(tokenDetail) && AppConstants.VALID_IND==tokenDetail.getTokenInd()) {

			UserDetail userDetail = noteService.getUserDetailsByEmail(userEmail); 
			if (AppUtil.isNotNull(userDetail) && AppConstants.VALID_IND == userDetail.getUserInd()) {
				modelMap.put(AppConstants.MESSAGE_ERROR, 
						getMessage(AppConstants.LOGIN_USER_VERIFIED_ALREADY, locale));
				isValid = false;
			}
		}

		if (isValid && AppUtil.isNotNull(tokenDetail) && 
				AppUtil.isNotNull(tokenStr) && tokenStr.equalsIgnoreCase(tokenStr)) {
			return modelMap;
		}else{
			modelMap.put(AppConstants.MESSAGE_ERROR, 
					getMessage(AppConstants.LOGIN_VERIFICATION_INVALID, locale));
		}

		return modelMap;
	}

	public ModelMap verifyRequestEmailVerification(ModelMap modelMap, String userEmail,Locale locale){

		UserDetail userDetail = noteService.getUserDetailsByEmail(userEmail);
		boolean isValid = true;

		if (AppUtil.isNull(userDetail)) {
			modelMap.put(AppConstants.MESSAGE_ERROR, 
					getMessage(AppConstants.LOGIN_VERIFICATION_EMAIL_NOT_FOUND, locale));
			isValid = false;
		} 

		else if(isValid && AppUtil.isNotNull(userDetail) && AppConstants.VALID_IND == userDetail.getUserInd()) {
			modelMap.put(AppConstants.MESSAGE_ERROR, 
					getMessage(AppConstants.LOGIN_USER_VERIFIED_ALREADY, locale));
			isValid = false;
		}

		return modelMap;
	}

	public ModelMap sendSupportMail(ModelMap modelMap, ModelMap helpMap,Locale locale){

		if((AppUtil.isNull(helpMap) || (AppUtil.isNull(helpMap.get("name")) || 
				AppUtil.isNull(helpMap.get("email")) || AppUtil.isNull(helpMap.get("content")))) || (helpMap.get("email")!=null && 
				!org.apache.commons.validator.routines.EmailValidator.getInstance(false, true).isValid((String)helpMap.get("email")))) {
			modelMap = (modelMap==null)? new ModelMap():modelMap;
			modelMap.put(AppConstants.MESSAGE_ERROR, "Please Enter All Valid Details");
		}

		return modelMap;
	}

	public ModelMap verifyRequestPasswordVerification(ModelMap modelMap, String userEmail,Locale locale){

		UserDetail userDetail = noteService.getUserDetailsByEmail(userEmail);
		boolean isValid = true;

		if (AppUtil.isNull(userDetail) || AppUtil.isNull(userDetail.getUserMail()) ) {
			modelMap.put(AppConstants.MESSAGE_ERROR, 
					getMessage(AppConstants.LOGIN_VERIFICATION_EMAIL_NOT_FOUND, locale));
			isValid = false;
		} 

		else if(isValid && AppUtil.isNotNull(userDetail) && AppConstants.INVALID_IND == userDetail.getUserInd()) {
			modelMap.put(AppConstants.MESSAGE_ERROR, 
					getMessage(AppConstants.LOGIN_USER_PLZ_VERIFY_FIRST, locale));
			isValid = false;
		}

		return modelMap;
	}

	public String getMessage(String code,Locale locale){
		try{
			return (AppUtil.isNotNull(code)?messageSource.getMessage(code, null, AppUtil.getLocale(locale)):"");
		}catch(NoSuchMessageException nsme){
			logger.error("NoSuchMessageException : ", nsme);
			return null;
		}
	}

	public boolean validateApiRequest(ModelMap requestMap){
		try{
			if(AppUtil.isNotNull(requestMap)){
				String tmpSsnId = (String) requestMap.get(AppConstants.SESSION_LOGIN_SESSION_ID);
				String userIdStr = (String) requestMap.get(AppConstants.SESSION_USER_ID);
				Long userId = AppUtil.isNotNull(userIdStr)? new Long(userIdStr) : null;
				return noteService.checkValidUserSession(tmpSsnId, userId);
			}
		}catch(Exception ex){
			logger.error("validateApiRequest : ", ex);
			return false;
		}
		return false;
	}
	
	public String getUserId(ModelMap requestMap){
		return AppUtil.isNotNull(requestMap)? (String) requestMap.get(AppConstants.SESSION_USER_ID) : null;
	}

	@SuppressWarnings("unchecked")
	public void convertNoteListUIToModelForSave(ModelMap requestObject,List<NoteInfo> noteInfoList){

		List<NoteDetailsVO> noteDetailsList = null;
		List<Object> listOfObjects 			= null;
		try{
			ObjectMapper objectMapper 	= new ObjectMapper();
			String requestString 		= null;

			if(AppUtil.isNotNull(requestObject)){

				if(AppUtil.isNotNull(requestObject.get(AppConstants.DASH_SINGLE_NOTE_OBJECT_SAVE))){	

					Object object = (Object) requestObject.get(AppConstants.DASH_SINGLE_NOTE_OBJECT_SAVE);
					requestString = objectMapper.writeValueAsString(object);
					NoteDetailsVO noteDetailsVO = (NoteDetailsVO) objectMapper.readValue(requestString,NoteDetailsVO.class);
					noteDetailsList = new ArrayList<NoteDetailsVO>(); noteDetailsList.add(noteDetailsVO);
				} else if(AppUtil.isNotNull(requestObject.get(AppConstants.DASH_LIST_OF_NOTE_OBJECTS))){

					listOfObjects 	= (List<Object>) requestObject.get(AppConstants.DASH_LIST_OF_NOTE_OBJECTS);
					requestString 	= objectMapper.writeValueAsString(listOfObjects);
					noteDetailsList = (List<NoteDetailsVO>) objectMapper.readValue(requestString, 
							objectMapper.getTypeFactory().constructCollectionType(List.class, NoteDetailsVO.class));
				}
			}

			if(AppUtil.isNotNull(noteDetailsList)){
				for(NoteDetailsVO noteDetailsVO : noteDetailsList){
					if(noteDetailsVO!=null){
						NoteInfo noteInfo = new NoteInfo();
						TagDetail tagDetail = null;
						noteInfo.setNoteCrdate(AppUtil.getCurrentDate());
						noteInfo.setNoteUpdate(AppUtil.getCurrentDate());
						noteInfo.setNoteId(noteDetailsVO.getNoteId());
						noteInfo.setNoteColor(noteDetailsVO.getNoteColor());
						noteInfo.setNoteDescription(noteDetailsVO.getNoteTextContent());
						noteInfo.setNoteTitle(noteDetailsVO.getTmpTitle());
						noteInfo.setNoteInd(noteDetailsVO.getNoteInd());
						if(noteDetailsVO.getTagItems()!=null && 
								noteDetailsVO.getTagItems().length>0){
							List<TagDetail> listOfTags = new ArrayList<TagDetail>();
							for(int i=0;i<noteDetailsVO.getTagItems().length;i++){
								tagDetail = new TagDetail();
								tagDetail.setTagName(noteDetailsVO.getTagItems()[i]);
								listOfTags.add(tagDetail);
							}
							noteInfo.setTagDetails(listOfTags);
						}
						if(AppUtil.isNotNull(noteDetailsVO.getLoginDetail()) && 
								!("0").equalsIgnoreCase(noteDetailsVO.getLoginDetail())){
							noteInfo.setLoginId(new Integer(noteDetailsVO.getLoginDetail()) );
						}
						
						String userIdStr = (String) requestObject.get(AppConstants.SESSION_USER_ID);
						Long userId = null;
						if(noteDetailsVO.getUserDetail()!=null && noteDetailsVO.getUserDetail().trim()!=""){
							userId = new Long(noteDetailsVO.getUserDetail());
						}else{
							userId = AppUtil.isNotNull(userIdStr)? new Long(userIdStr) : null;
						}
						UserDetail userDetail = noteService.getUserDetails(userId);
						noteInfo.setUserDetail(userDetail);
						
						noteInfoList.add(noteInfo);
					}
				}
			}

		}catch(AppException ae){
			logger.error("convertNoteListUIToModelForSave ",ae);
			throw new AppException(ae);
		}
		catch(JsonMappingException ex){
			logger.error("convertNoteListUIToModelForSave ",ex);
			throw new AppException(ex);
		}
		catch(Exception ex){
			logger.error("convertNoteListUIToModelForSave Common Exception ",ex);
		}
	}

	public void convertNoteListModelToUI(List<NoteInfo> noteInfoList,List<NoteDetailsVO> noteVOList){
		try{
			if(AppUtil.isNotNull(noteInfoList)){
				for(NoteInfo noteInfo: noteInfoList){
					if(AppUtil.isNotNull(noteInfo)){
						NoteDetailsVO noteDetailsVO = new NoteDetailsVO();
						noteDetailsVO.setNewObj(false);
						String colorCode = AppUtil.isNotNull(noteInfo.getNoteColor())? AppConstants.colors.get(noteInfo.getNoteColor().toUpperCase()) : AppConstants.colors.get("WHITE"); 
						noteDetailsVO.setNoteBgColor("background-color:"+colorCode);
						noteDetailsVO.setNoteColor(noteInfo.getNoteColor());
						noteDetailsVO.setNoteId(noteInfo.getNoteId());
						noteDetailsVO.setNoteInd(noteInfo.getNoteInd());
						noteDetailsVO.setNoteTextContent(noteInfo.getNoteDescription());
						noteDetailsVO.setTmpTitle(noteInfo.getNoteTitle());
						noteDetailsVO.setNoteTitle(AppUtil.getTempTitleString(noteInfo.getNoteTitle()));
						noteDetailsVO.setNoteCrdate(AppUtil.getDate(noteInfo.getNoteCrdate()));
						noteDetailsVO.setNoteUpdate(AppUtil.getDate(noteInfo.getNoteUpdate()));
						noteDetailsVO.setNoteDeldate(AppUtil.getDate(noteInfo.getNoteDeldate()));
						noteDetailsVO.setNoteResdate(AppUtil.getDate(noteInfo.getNoteResdate()));
						noteVOList.add(noteDetailsVO);
					}
				}
			}
		}catch(AppException ae){
			logger.error("convertNoteListModelToUI ",ae);
			throw new AppException(ae);
		}
		catch(Exception ex){
			logger.error("Common convertNoteListModelToUI Exception ",ex);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Long> convertNoteUIToModelForDelete(ModelMap requestObject,List<Long> noteInfoList){

		List<Object> listOfObjects 			= null;
		try{
			ObjectMapper objectMapper 	= new ObjectMapper();
			String requestString 		= null;

			if(AppUtil.isNotNull(requestObject)){

				if(AppUtil.isNotNull(requestObject.get(AppConstants.DASH_SINGLE_NOTE_OBJECT_DELETE))){	

					Object object = (Object) requestObject.get(AppConstants.DASH_SINGLE_NOTE_OBJECT_DELETE);
					requestString = objectMapper.writeValueAsString(object);
					Long longId = (Long) objectMapper.readValue(requestString,Long.class);
					noteInfoList.add(longId);
				} else if(AppUtil.isNotNull(requestObject.get(AppConstants.DASH_LIST_OF_NOTE_OBJECTS_DELETE))){

					listOfObjects 	= (List<Object>) requestObject.get(AppConstants.DASH_LIST_OF_NOTE_OBJECTS_DELETE);
					requestString 	= objectMapper.writeValueAsString(listOfObjects);
					noteInfoList = (List<Long>) objectMapper.readValue(requestString, 
							objectMapper.getTypeFactory().constructCollectionType(List.class, Long.class));
				}
			}

		}catch(AppException ae){
			logger.error("convertNoteUIToModelForDelete ",ae);
			throw new AppException(ae);
		}
		catch(JsonMappingException ex){
			logger.error("convertNoteUIToModelForDelete JsonMappingException ",ex);
			throw new AppException(ex);
		}
		catch(Exception ex){
			logger.error("convertNoteUIToModelForDelete Common Exception ",ex);
		}
		return noteInfoList;
	}
}
