package com.comment.commentservice.service;

import com.comment.commentservice.feign.FeignLike;
import com.comment.commentservice.feign.FeignUser;
import com.comment.commentservice.model.CommentDto;
import com.comment.commentservice.model.CommentModel;
import com.comment.commentservice.repo.CommentRepository;
import com.customer.Exception.CommentNotFoundException;
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

    @Autowired
    private FeignLike feignLike;
    @Autowired
    private FeignUser feignUser;


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
        for(CommentModel commentModel:commentModels){
            CommentDto commentDTO1=new CommentDto(commentModel.getCommentID(),
                    feignUser.findByID(commentModel.getCommentedBy()).getFirstName(),
                    commentModel.getComment(),commentModel.getCreatedAt(),commentModel.getUpdatedAt(),
                   feignLike.countLike(commentModel.getCommentID()));
        }
        return  commentDTOS;


    }


    public CommentDto saveComment(CommentModel commentModel, String postId) {
        commentModel.setPostID(postId);
        commentModel.setCreatedAt(LocalDateTime.now());
        commentModel.setUpdatedAt(LocalDateTime.now());
        this.commentRepository.save(commentModel);
        CommentDto commentDTO=new CommentDto(commentModel.getCommentID(),
                feignUser.findByID(commentModel.getCommentedBy()).getFirstName(),
                commentModel.getComment(),commentModel.getCreatedAt(),commentModel.getUpdatedAt(),
                feignLike.countLike(commentModel.getCommentID()));
        return commentDTO;
    }



    public CommentDto findByCommentId(String commentId) {
try {
    CommentModel commentModel = commentRepository.findById(commentId).get();

    CommentDto commentDTO = new CommentDto(commentModel.getCommentID(),
            feignUser.findByID(commentModel.getCommentedBy()).getFirstName(),
            commentModel.getComment(), commentModel.getCreatedAt(), commentModel.getUpdatedAt(),
            feignLike.countLike(commentModel.getCommentID()));
    return commentDTO;
}catch (Exception e){
    throw  new CommentNotFoundException("comment id not found ");
}

    }

}
