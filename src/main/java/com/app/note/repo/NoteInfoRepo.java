package com.app.note.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.note.bean.NoteInfo;

public interface NoteInfoRepo extends JpaRepository<NoteInfo, Integer> {

	@Query(value="SELECT COUNT(noteId) FROM NoteInfo WHERE noteId = ?1 and (noteTitle!='' or noteDescription!='')")
	Long checkOldNote(Integer noteId);
	
	@Query(value="SELECT n FROM NoteInfo n WHERE n.userDetail.userId = ?1")
	List<NoteInfo> getListOfNoteInfosByUserId(Long userId);
}
