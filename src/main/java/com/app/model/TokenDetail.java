package com.app.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the token_details database table.
 * 
 */
@Entity
@Table(name="token_details")
@NamedQuery(name="TokenDetail.findAll", query="SELECT t FROM TokenDetail t")
public class TokenDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="token_id")
	private int tokenId;

	@Column(name="token_crdate")
	private Date tokenCrdate;

	@Temporal(TemporalType.DATE)
	@Column(name="token_enddate")
	private Date tokenEnddate;

	@Column(name="token_hash")
	private String tokenHash;

	@Column(name="token_ind")
	private int tokenInd;

	@Column(name="token_update")
	private Date tokenUpdate;

	@Column(name="user_name")
	private String userName;

	//bi-directional many-to-one association to UserDetail
	@ManyToOne
	@JoinColumn(name="user_id")
	private UserDetail userDetail;

	public TokenDetail() {
	}

	public TokenDetail(String tokenString, int invalidInd) {
		this.tokenHash = tokenString;
		this.tokenInd = invalidInd;
	}

	public int getTokenId() {
		return this.tokenId;
	}

	public void setTokenId(int tokenId) {
		this.tokenId = tokenId;
	}

	public Date getTokenCrdate() {
		return this.tokenCrdate;
	}

	public void setTokenCrdate(Date tokenCrdate) {
		this.tokenCrdate = tokenCrdate;
	}

	public Date getTokenEnddate() {
		return this.tokenEnddate;
	}

	public void setTokenEnddate(Date tokenEnddate) {
		this.tokenEnddate = tokenEnddate;
	}

	public String getTokenHash() {
		return this.tokenHash;
	}

	public void setTokenHash(String tokenHash) {
		this.tokenHash = tokenHash;
	}

	public int getTokenInd() {
		return this.tokenInd;
	}

	public void setTokenInd(int tokenInd) {
		this.tokenInd = tokenInd;
	}

	public Date getTokenUpdate() {
		return this.tokenUpdate;
	}

	public void setTokenUpdate(Date tokenUpdate) {
		this.tokenUpdate = tokenUpdate;
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