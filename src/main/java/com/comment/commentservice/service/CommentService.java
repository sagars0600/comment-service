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

    public int commentCount(String postId){
        int count=this.commentRepository.findByPostID(postId).size();
        return count;
    }
}
