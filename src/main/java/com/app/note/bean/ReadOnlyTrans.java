package com.app.note.bean;

import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly=true)
public @interface ReadOnlyTrans {
}
