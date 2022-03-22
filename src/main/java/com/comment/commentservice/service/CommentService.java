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

    public CommentModel updateComment(CommentModel commentModel, String postId,String commentId){
        commentModel.setCommentID(commentId);
        commentModel.setUpdatedAt(LocalDateTime.now());
        commentModel.setCreatedAt(commentRepository.findById(commentId).get().getCreatedAt());
        commentModel.setPostID(postId);
        return commentRepository.save(commentModel);
    }


}
