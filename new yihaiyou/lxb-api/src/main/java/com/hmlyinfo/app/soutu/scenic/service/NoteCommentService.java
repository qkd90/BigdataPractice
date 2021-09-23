package com.hmlyinfo.app.soutu.scenic.service;

import com.hmlyinfo.app.soutu.scenic.domain.NoteComment;
import com.hmlyinfo.app.soutu.scenic.mapper.NoteCommentMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by guoshijie on 2014/8/25.
 */
@Service
public class NoteCommentService extends BaseService<NoteComment, Long> {

    @Autowired
    private NoteCommentMapper<NoteComment> noteCommentMapper;


    @Override
    public BaseMapper<NoteComment> getMapper() {
        return noteCommentMapper;
    }

    @Override
    public String getKey() {
        return "id";
    }

    public void insert(Map<String, Object> params) {
        NoteComment noteComment = new NoteComment();
        noteComment.setNoteId(Long.parseLong(params.get("noteId").toString()));
        if (params.get("noteImageId") != null) {
            noteComment.setNoteImageId(Long.parseLong(params.get("noteImageId").toString()));
        }
        noteComment.setUserId(Long.parseLong(params.get("userId").toString()));
        noteComment.setContent(params.get("content").toString());
        if (params.get("replyTo") != null) {
            noteComment.setReplyTo(Long.parseLong(params.get("replyTo").toString()));
        }
        insert(noteComment);

    }


}
