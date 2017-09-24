package com.app.model;

public class NoteDetailsVO {

	private Integer noteId;
	private Integer noteInd;
	private boolean newObj = false;
	private String noteTitle;
	private String tmpTitle;
	private String noteTextContent;
	private boolean isSelected = false;
	private String noteCrdate;
	private String noteUpdate;
	private String noteDeldate;
	private String noteResdate;
	private String[] tagItems;
	private String loginDetail;
	private String userDetail;
	private boolean tagShow = false;
	private boolean calShow = false;
	private boolean colorShow = false ;
	private String noteBgColor;
	private String noteColor;
	private boolean isHovered = false;
	public Integer getNoteId() {
		return noteId;
	}
	public void setNoteId(Integer noteId) {
		this.noteId = noteId;
	}
	public Integer getNoteInd() {
		return noteInd;
	}
	public void setNoteInd(Integer noteInd) {
		this.noteInd = noteInd;
	}
	public boolean getNewObj() {
		return newObj;
	}
	public void setNewObj(boolean newObj) {
		this.newObj = newObj;
	}
	public String getNoteTitle() {
		return noteTitle;
	}
	public void setNoteTitle(String noteTitle) {
		this.noteTitle = noteTitle;
	}
	public String getTmpTitle() {
		return tmpTitle;
	}
	public void setTmpTitle(String tmpTitle) {
		this.tmpTitle = tmpTitle;
	}
	public String getNoteTextContent() {
		return noteTextContent;
	}
	public void setNoteTextContent(String noteTextContent) {
		this.noteTextContent = noteTextContent;
	}
	public boolean getIsSelected() {
		return isSelected;
	}
	public void setIsSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	public String getNoteCrdate() {
		return noteCrdate;
	}
	public void setNoteCrdate(String noteCrdate) {
		this.noteCrdate = noteCrdate;
	}
	public String getNoteUpdate() {
		return noteUpdate;
	}
	public void setNoteUpdate(String noteUpdate) {
		this.noteUpdate = noteUpdate;
	}
	public String getNoteDeldate() {
		return noteDeldate;
	}
	public void setNoteDeldate(String noteDeldate) {
		this.noteDeldate = noteDeldate;
	}
	public String getNoteResdate() {
		return noteResdate;
	}
	public void setNoteResdate(String noteResdate) {
		this.noteResdate = noteResdate;
	}
	public String[] getTagItems() {
		return tagItems;
	}
	public void setTagItems(String[] tagItems) {
		this.tagItems = tagItems;
	}
	public String getLoginDetail() {
		return loginDetail;
	}
	public void setLoginDetail(String loginDetail) {
		this.loginDetail = loginDetail;
	}
	public String getUserDetail() {
		return userDetail;
	}
	public void setUserDetail(String userDetail) {
		this.userDetail = userDetail;
	}
	public boolean getTagShow() {
		return tagShow;
	}
	public void setTagShow(boolean tagShow) {
		this.tagShow = tagShow;
	}
	public boolean getCalShow() {
		return calShow;
	}
	public void setCalShow(boolean calShow) {
		this.calShow = calShow;
	}
	public boolean getColorShow() {
		return colorShow;
	}
	public void setColorShow(boolean colorShow) {
		this.colorShow = colorShow;
	}
	public String getNoteBgColor() {
		return noteBgColor;
	}
	public void setNoteBgColor(String noteBgColor) {
		this.noteBgColor = noteBgColor;
	}
	public String getNoteColor() {
		return noteColor;
	}
	public void setNoteColor(String noteColor) {
		this.noteColor = noteColor;
	}
	public boolean getIsHovered() {
		return isHovered;
	}
	public void setIsHovered(boolean isHovered) {
		this.isHovered = isHovered;
	}
	
	
}
