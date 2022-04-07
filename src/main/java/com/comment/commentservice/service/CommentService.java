package com.comment.commentservice.service;

import com.comment.commentservice.Exception.CommentNotFoundException;
import com.comment.commentservice.constfiles.ConstFile;
import com.comment.commentservice.feign.FeignLike;
import com.comment.commentservice.feign.FeignUser;
import com.comment.commentservice.model.CommentDto;
import com.comment.commentservice.model.CommentModel;
import com.comment.commentservice.repo.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
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
        List<CommentModel> allData = commentRepository.findAll();
        int count = 0;
        for (CommentModel comment : allData) {
            if (comment.getPostID().equals(postId)) {
                count++;
            }
        }
        return count;
    }


    public CommentDto updateComment(CommentModel commentModel, String postId, String commentId) {
        commentModel.setCommentID(commentId);
        commentModel.setUpdatedAt(LocalDateTime.now());
        commentModel.setCreatedAt(commentRepository.findById(commentId).get().getCreatedAt());
        commentModel.setPostID(postId);
       commentRepository.save(commentModel);
        CommentDto commentDTO=new CommentDto(commentModel.getCommentID(),commentModel.getComment(),
                feignUser.findByID(commentModel.getCommentedBy()),
             feignLike.countLike(commentModel.getCommentID()),
                commentModel.getCreatedAt(),commentModel.getUpdatedAt());
        return commentDTO;

    }


    public String deleteByCommentId(String commentId) {
        if(commentRepository.findById(commentId).isPresent()){
            this.commentRepository.deleteById(commentId);
            return ConstFile.idDeleted;
        }
        else {
            throw new CommentNotFoundException(ConstFile.idNot);
        }

    }

    public List<CommentDto> allComments(String postId, Integer page, Integer pageSize) {

        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        CommentDto commentDTO = new CommentDto();
        Pageable firstPage = PageRequest.of(page - 1, pageSize);
        List<CommentModel> commentModels = commentRepository.findBypostID(postId, firstPage);
        List<CommentDto> commentDTOS = new ArrayList<>();

        for(CommentModel commentModel:commentModels){
            CommentDto commentDTO1=new CommentDto(commentModel.getCommentID(),   commentModel.getComment(),
                    feignUser.findByID(commentModel.getCommentedBy()),
                    feignLike.countLike(commentModel.getCommentID()),
                 commentModel.getCreatedAt(),commentModel.getUpdatedAt()
                 );
            commentDTOS.add(commentDTO1);
        }
        return  commentDTOS;

    }


    public CommentDto saveComment(CommentModel commentModel, String postId) {
        commentModel.setPostID(postId);
        commentModel.getComment();
        commentModel.setCreatedAt(LocalDateTime.now());
        commentModel.setUpdatedAt(LocalDateTime.now());
        this.commentRepository.save(commentModel);
        CommentDto commentDTO=new CommentDto(commentModel.getCommentID(),commentModel.getComment(),
                feignUser.findByID(commentModel.getCommentedBy()),
                feignLike.countLike(commentModel.getCommentID()),
                commentModel.getCreatedAt(),commentModel.getUpdatedAt()
               );
        return commentDTO;
    }



    public CommentDto findByCommentId(String commentId) {
try {
    CommentModel commentModel = commentRepository.findById(commentId).get();

    CommentDto commentDTO = new CommentDto(commentModel.getCommentID(),commentModel.getComment(),
            feignUser.findByID(commentModel.getCommentedBy()),
            feignLike.countLike(commentModel.getCommentID()),
            commentModel.getCreatedAt(), commentModel.getUpdatedAt()
            );
    return commentDTO;
}catch (Exception e){
    throw  new CommentNotFoundException(ConstFile.idNot);
}

    }

}
