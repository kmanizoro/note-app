package com.app.note.bean;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the user_details database table.
 * 
 */
@Entity
@Table(name="user_details")
//@NamedQuery(name="UserDetail.findAll", query="SELECT u FROM UserDetail u")
public class UserDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_id")
	private Long userId;

	@Column(name="user_crdate")
	private Date userCrdate;

	@Column(name="user_displayname")
	private String userDisplayname;

	@Temporal(TemporalType.DATE)
	@Column(name="user_dob")
	private Date userDob;

	@Column(name="user_firstname")
	private String userFirstname;

	@Column(name="user_gender")
	private String userGender;

	@Column(name="user_ind")
	private Integer userInd;

	@Column(name="user_lastname")
	private String userLastname;

	@Column(name="user_mail")
	private String userMail;

	@Column(name="user_name")
	private String userName;

	@Column(name="user_pass")
	private String userPass;

	@Column(name="user_role")
	private String userRole;

	@Column(name="user_update")
	private Date userUpdate;

	//bi-directional many-to-one association to LoginDetail
	@JsonIgnore
	@OneToMany(mappedBy="userDetail")
	private List<LoginDetail> loginDetails;

	//bi-directional many-to-one association to NoteInfo
	@JsonIgnore
	@OneToMany(mappedBy="userDetail")
	private List<NoteInfo> noteInfos;

	//bi-directional many-to-one association to TagDetail
	@JsonIgnore
	@OneToMany(mappedBy="userDetail")
	private List<TagDetail> tagDetails;

	//bi-directional many-to-one association to TokenDetail
	@JsonIgnore
	@OneToMany(mappedBy="userDetail")
	private List<TokenDetail> tokenDetails;

	public UserDetail() {
	}

	public UserDetail(String userName, String password, String emailId, Integer invalidInd, String userRole) {
		this.userName = userName;
		this.userPass = password;
		this.userMail = emailId;
		this.userInd = invalidInd;
		this.userRole = userRole;
	}



	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getUserCrdate() {
		return this.userCrdate;
	}

	public void setUserCrdate(Date userCrdate) {
		this.userCrdate = userCrdate;
	}

	public String getUserDisplayname() {
		return this.userDisplayname;
	}

	public void setUserDisplayname(String userDisplayname) {
		this.userDisplayname = userDisplayname;
	}

	public Date getUserDob() {
		return this.userDob;
	}

	public void setUserDob(Date userDob) {
		this.userDob = userDob;
	}

	public String getUserFirstname() {
		return this.userFirstname;
	}

	public void setUserFirstname(String userFirstname) {
		this.userFirstname = userFirstname;
	}

	public String getUserGender() {
		return this.userGender;
	}

	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}

	public Integer getUserInd() {
		return this.userInd;
	}

	public void setUserInd(Integer userInd) {
		this.userInd = userInd;
	}

	public String getUserLastname() {
		return this.userLastname;
	}

	public void setUserLastname(String userLastname) {
		this.userLastname = userLastname;
	}

	public String getUserMail() {
		return this.userMail;
	}

	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPass() {
		return this.userPass;
	}

	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}

	public String getUserRole() {
		return this.userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public Date getUserUpdate() {
		return this.userUpdate;
	}

	public void setUserUpdate(Date userUpdate) {
		this.userUpdate = userUpdate;
	}

	public List<LoginDetail> getLoginDetails() {
		return this.loginDetails;
	}

	public void setLoginDetails(List<LoginDetail> loginDetails) {
		this.loginDetails = loginDetails;
	}

	public LoginDetail addLoginDetail(LoginDetail loginDetail) {
		getLoginDetails().add(loginDetail);
		loginDetail.setUserDetail(this);

		return loginDetail;
	}

	public LoginDetail removeLoginDetail(LoginDetail loginDetail) {
		getLoginDetails().remove(loginDetail);
		loginDetail.setUserDetail(null);

		return loginDetail;
	}

	public List<NoteInfo> getNoteInfos() {
		return this.noteInfos;
	}

	public void setNoteInfos(List<NoteInfo> noteInfos) {
		this.noteInfos = noteInfos;
	}

	public NoteInfo addNoteInfo(NoteInfo noteInfo) {
		getNoteInfos().add(noteInfo);
		noteInfo.setUserDetail(this);

		return noteInfo;
	}

	public NoteInfo removeNoteInfo(NoteInfo noteInfo) {
		getNoteInfos().remove(noteInfo);
		noteInfo.setUserDetail(null);

		return noteInfo;
	}

	public List<TagDetail> getTagDetails() {
		return this.tagDetails;
	}

	public void setTagDetails(List<TagDetail> tagDetails) {
		this.tagDetails = tagDetails;
	}

	public TagDetail addTagDetail(TagDetail tagDetail) {
		getTagDetails().add(tagDetail);
		tagDetail.setUserDetail(this);

		return tagDetail;
	}

	public TagDetail removeTagDetail(TagDetail tagDetail) {
		getTagDetails().remove(tagDetail);
		tagDetail.setUserDetail(null);

		return tagDetail;
	}

	public List<TokenDetail> getTokenDetails() {
		return this.tokenDetails;
	}

	public void setTokenDetails(List<TokenDetail> tokenDetails) {
		this.tokenDetails = tokenDetails;
	}

	public TokenDetail addTokenDetail(TokenDetail tokenDetail) {
		getTokenDetails().add(tokenDetail);
		tokenDetail.setUserDetail(this);

		return tokenDetail;
	}

	public TokenDetail removeTokenDetail(TokenDetail tokenDetail) {
		getTokenDetails().remove(tokenDetail);
		tokenDetail.setUserDetail(null);

		return tokenDetail;
	}

	public String toString() {
		return "UserDetail [userId=" + userId + ", userDisplayname=" + userDisplayname + ", userDob=" + userDob
				+ ", userGender=" + userGender + ", userInd=" + userInd + ", userMail=" + userMail + ", userName="
				+ userName + ", userPass=" + userPass + ", userRole=" + userRole + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		result = prime * result + ((userMail == null) ? 0 : userMail.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

	public boolean equals(Object obj){
		if(this == obj)
			return true;
		if(obj==null){
			return false;
		}
		UserDetail detail = (UserDetail) obj;
		if(userId == null){
			if(detail.userId != null){
				return false;
			}
		}else if(detail.userId != userId)
			return false;
		
		if(userName==null){
			if(userName!=null)
				return false;
		} else if(userName!=detail.userName)
			return false;
		
		if(userMail==null){
			if(userMail!=null)
				return false;
		} else if(userMail!=detail.userMail)
			return false;
		
		return true;
	}
	
	

}