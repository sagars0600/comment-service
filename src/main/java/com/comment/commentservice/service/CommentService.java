package com.comment.commentservice.service;

import com.comment.commentservice.model.CommentDto;
import com.comment.commentservice.model.CommentModel;
import com.comment.commentservice.repo.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public CommentModel saveComment(CommentModel commentModel, String postId) {
        commentModel.setPostID(postId);
        commentModel.setCreatedAt(LocalDateTime.now());
        commentModel.setUpdatedAt(LocalDateTime.now());
        return commentRepository.save(commentModel);
    }


    public CommentModel findByCommentId(String commentId) {
        return this.commentRepository.findById(commentId).get();
    }

    public int commentCount(String postId) {
        int count = this.commentRepository.findByPostID(postId).size();
        return count;
    }


    public CommentModel updateComment(CommentModel commentModel, String postId, String commentId) {
        commentModel.setCommentID(commentId);
        commentModel.setUpdatedAt(LocalDateTime.now());
        commentModel.setCreatedAt(commentRepository.findById(commentId).get().getCreatedAt());
        commentModel.setPostID(postId);
        return commentRepository.save(commentModel);
    }


    public String deleteByCommentId(String commentId) {
        this.commentRepository.deleteById(commentId);
        return "Delete CommentID " + commentId + " from DB";
    }

    public List<CommentDto> allComments(String postId, Integer page, Integer pageSize) {
        if(page==null){
            page=1;
        }
        if(pageSize==null){
            pageSize=10;
        }
        CommentDto commentDTO=new CommentDto();
        Pageable firstPage = PageRequest.of(page-1, pageSize);
        List<CommentModel> commentModels  = commentRepository.findBypostID(postId,firstPage);
        List<CommentDto> commentDTOS = new ArrayList<>();

        return  commentDTOS;


    }


}
