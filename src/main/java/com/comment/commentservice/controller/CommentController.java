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

    @GetMapping("/posts/{postId}/comments/count")
    public ResponseEntity<Integer> commentCount(@PathVariable("postId") String postId){
        return  new ResponseEntity<>(commentService.commentCount(postId), HttpStatus.ACCEPTED);
    }
}
