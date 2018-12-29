package com.app.note.bean;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the note_info database table.
 * 
 */
@Entity
@Table(name="note_info")
// @NamedQuery(name="NoteInfo.findAll", query="SELECT n FROM NoteInfo n")
public class NoteInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="note_id")
	private Integer noteId;

	@Column(name="login_id")
	private Integer loginId;

	@Column(name="note_color")
	private String noteColor;

	@Column(name="note_crdate")
	private Date noteCrdate;

	@Column(name="note_deldate")
	private Date noteDeldate;

	@Lob
	@Column(name="note_description")
	private String noteDescription;

	@Column(name="note_ind")
	private Integer noteInd;

	@Temporal(TemporalType.DATE)
	@Column(name="note_remainder")
	private Date noteRemainder;

	@Column(name="note_resdate")
	private Date noteResdate;

	@Column(name="note_title")
	private String noteTitle;

	@Column(name="note_update")
	private Date noteUpdate;

	//bi-directional many-to-one association to NoteImg
	@OneToMany(mappedBy="noteInfo")
	private List<NoteImg> noteImgs;

	//bi-directional many-to-one association to UserDetail
	@ManyToOne
	@JoinColumn(name="user_id")
	private UserDetail userDetail;

	//bi-directional many-to-one association to TagDetail
	@OneToMany(mappedBy="noteInfo",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private List<TagDetail> tagDetails;

	public NoteInfo() {
	}

	public Integer getNoteId() {
		return this.noteId;
	}

	public void setNoteId(Integer noteId) {
		this.noteId = noteId;
	}

	public Integer getLoginId() {
		return this.loginId;
	}

	public void setLoginId(Integer loginId) {
		this.loginId = loginId;
	}

	public String getNoteColor() {
		return this.noteColor;
	}

	public void setNoteColor(String noteColor) {
		this.noteColor = noteColor;
	}

	public Date getNoteCrdate() {
		return this.noteCrdate;
	}

	public void setNoteCrdate(Date noteCrdate) {
		this.noteCrdate = noteCrdate;
	}

	public Date getNoteDeldate() {
		return this.noteDeldate;
	}

	public void setNoteDeldate(Date noteDeldate) {
		this.noteDeldate = noteDeldate;
	}

	public String getNoteDescription() {
		return this.noteDescription;
	}

	public void setNoteDescription(String noteDescription) {
		this.noteDescription = noteDescription;
	}

	public Integer getNoteInd() {
		return this.noteInd;
	}

	public void setNoteInd(Integer noteInd) {
		this.noteInd = noteInd;
	}

	public Date getNoteRemainder() {
		return this.noteRemainder;
	}

	public void setNoteRemainder(Date noteRemainder) {
		this.noteRemainder = noteRemainder;
	}

	public Date getNoteResdate() {
		return this.noteResdate;
	}

	public void setNoteResdate(Date noteResdate) {
		this.noteResdate = noteResdate;
	}

	public String getNoteTitle() {
		return this.noteTitle;
	}

	public void setNoteTitle(String noteTitle) {
		this.noteTitle = noteTitle;
	}

	public Date getNoteUpdate() {
		return this.noteUpdate;
	}

	public void setNoteUpdate(Date noteUpdate) {
		this.noteUpdate = noteUpdate;
	}

	public List<NoteImg> getNoteImgs() {
		return this.noteImgs;
	}

	public void setNoteImgs(List<NoteImg> noteImgs) {
		this.noteImgs = noteImgs;
	}

	public NoteImg addNoteImg(NoteImg noteImg) {
		getNoteImgs().add(noteImg);
		noteImg.setNoteInfo(this);

		return noteImg;
	}

	public NoteImg removeNoteImg(NoteImg noteImg) {
		getNoteImgs().remove(noteImg);
		noteImg.setNoteInfo(null);

		return noteImg;
	}

	public UserDetail getUserDetail() {
		return this.userDetail;
	}

	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}

	public List<TagDetail> getTagDetails() {
		return this.tagDetails;
	}

	public void setTagDetails(List<TagDetail> tagDetails) {
		this.tagDetails = tagDetails;
	}

	public TagDetail addTagDetail(TagDetail tagDetail) {
		getTagDetails().add(tagDetail);
		tagDetail.setNoteInfo(this);

		return tagDetail;
	}

	public TagDetail removeTagDetail(TagDetail tagDetail) {
		getTagDetails().remove(tagDetail);
		tagDetail.setNoteInfo(null);

		return tagDetail;
	}

}