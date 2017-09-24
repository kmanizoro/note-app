package com.app.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the note_img database table.
 * 
 */
@Entity
@Table(name="note_img")
@NamedQuery(name="NoteImg.findAll", query="SELECT n FROM NoteImg n")
public class NoteImg implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="img_id")
	private int imgId;

	@Lob
	@Column(name="img_content")
	private byte[] imgContent;

	@Column(name="img_crdate")
	private Date imgCrdate;

	@Column(name="img_ind")
	private byte imgInd;

	@Column(name="img_name")
	private String imgName;

	@Column(name="img_update")
	private Date imgUpdate;

	@Column(name="img_url")
	private String imgUrl;

	//bi-directional many-to-one association to NoteInfo
	@ManyToOne
	@JoinColumn(name="note_id")
	private NoteInfo noteInfo;

	public NoteImg() {
	}

	public int getImgId() {
		return this.imgId;
	}

	public void setImgId(int imgId) {
		this.imgId = imgId;
	}

	public byte[] getImgContent() {
		return this.imgContent;
	}

	public void setImgContent(byte[] imgContent) {
		this.imgContent = imgContent;
	}

	public Date getImgCrdate() {
		return this.imgCrdate;
	}

	public void setImgCrdate(Date imgCrdate) {
		this.imgCrdate = imgCrdate;
	}

	public byte getImgInd() {
		return this.imgInd;
	}

	public void setImgInd(byte imgInd) {
		this.imgInd = imgInd;
	}

	public String getImgName() {
		return this.imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public Date getImgUpdate() {
		return this.imgUpdate;
	}

	public void setImgUpdate(Date imgUpdate) {
		this.imgUpdate = imgUpdate;
	}

	public String getImgUrl() {
		return this.imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public NoteInfo getNoteInfo() {
		return this.noteInfo;
	}

	public void setNoteInfo(NoteInfo noteInfo) {
		this.noteInfo = noteInfo;
	}

}