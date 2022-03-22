package com.comment.commentservice.controller;

import com.comment.commentservice.model.CommentModel;
import com.comment.commentservice.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentModel> findByCommentId(@PathVariable("postId") String postId,@PathVariable("commentId") String commentId){
        return new ResponseEntity<>(commentService.findByCommentId(commentId), HttpStatus.ACCEPTED);
    }
}
