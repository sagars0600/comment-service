package com.comment.commentservice.service;

import com.comment.commentservice.model.CommentModel;
import com.comment.commentservice.repo.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public String deleteByCommentId(String commentId){
        this.commentRepository.deleteById(commentId);
        return "Delete CommentID "+commentId+" from DB";
    }
}
