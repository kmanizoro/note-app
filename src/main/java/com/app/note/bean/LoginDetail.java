package com.app.note.bean;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the login_details database table.
 * 
 */
@Entity
@Table(name="login_details")
// @NamedQuery(name="LoginDetail.findAll", query="SELECT l FROM LoginDetail l")
public class LoginDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="login_id")
	private Long loginId;

	@Column(name="login_act_userid")
	private Long loginActUserid;

	@Column(name="login_crdate")
	private Date loginCrdate;

	@Column(name="login_ind")
	private byte loginInd;

	@Column(name="login_log")
	private String loginLog;

	@Column(name="login_sessionid")
	private String loginSessionid;

	@Column(name="login_update")
	private Date loginUpdate;

	@Column(name="user_name")
	private String userName;

	//bi-directional many-to-one association to UserDetail
	@ManyToOne
	@JoinColumn(name="user_id")
	private UserDetail userDetail;

	public LoginDetail() {
	}

	public Long getLoginId() {
		return this.loginId;
	}

	public void setLoginId(Long loginId) {
		this.loginId = loginId;
	}

	public Long getLoginActUserid() {
		return this.loginActUserid;
	}

	public void setLoginActUserid(Long loginActUserid) {
		this.loginActUserid = loginActUserid;
	}

	public Date getLoginCrdate() {
		return this.loginCrdate;
	}

	public void setLoginCrdate(Date loginCrdate) {
		this.loginCrdate = loginCrdate;
	}

	public byte getLoginInd() {
		return this.loginInd;
	}

	public void setLoginInd(byte loginInd) {
		this.loginInd = loginInd;
	}

	public String getLoginLog() {
		return this.loginLog;
	}

	public void setLoginLog(String loginLog) {
		this.loginLog = loginLog;
	}

	public String getLoginSessionid() {
		return this.loginSessionid;
	}

	public void setLoginSessionid(String loginSessionid) {
		this.loginSessionid = loginSessionid;
	}

	public Date getLoginUpdate() {
		return this.loginUpdate;
	}

	public void setLoginUpdate(Date loginUpdate) {
		this.loginUpdate = loginUpdate;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public UserDetail getUserDetail() {
		return this.userDetail;
	}

	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}

}