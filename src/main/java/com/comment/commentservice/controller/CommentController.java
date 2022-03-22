package com.comment.commentservice.controller;

import com.comment.commentservice.model.CommentModel;
import com.comment.commentservice.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentModel> updateComment(@RequestBody @Valid CommentModel commentModel, @PathVariable("postId") String postId, @PathVariable("commentId") String commentId){
        return new ResponseEntity<>(commentService.updateComment(commentModel,postId,commentId), HttpStatus.ACCEPTED);


    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deletebyCommentId(@PathVariable("postId") String postId,@PathVariable("commentId") String commentId){
        return new ResponseEntity<>(commentService.deleteByCommentId(commentId), HttpStatus.ACCEPTED);

    }
}

