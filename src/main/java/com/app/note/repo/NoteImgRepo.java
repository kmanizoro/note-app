package com.app.note.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.note.bean.NoteImg;

public interface NoteImgRepo  extends JpaRepository<NoteImg, Integer>{

}
