package com.comment.commentservice.controller;

import com.comment.commentservice.model.CommentDto;
import com.comment.commentservice.model.CommentModel;
import com.comment.commentservice.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.QueryParam;
import java.util.List;


@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentModel> saveComment(@Valid @RequestBody CommentModel commentModel, @PathVariable("postId") String postId) {
        return new ResponseEntity<>(commentService.saveComment(commentModel, postId), HttpStatus.ACCEPTED);
    }


    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentModel> findByCommentId(@PathVariable("postId") String postId, @PathVariable("commentId") String commentId) {
        return new ResponseEntity<>(commentService.findByCommentId(commentId), HttpStatus.ACCEPTED);
    }


    @GetMapping("/posts/{postId}/comments/count")
    public ResponseEntity<Integer> commentCount(@PathVariable("postId") String postId) {
        return new ResponseEntity<>(commentService.commentCount(postId), HttpStatus.ACCEPTED);
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentModel> updateComment(@RequestBody @Valid CommentModel commentModel, @PathVariable("postId") String postId, @PathVariable("commentId") String commentId) {
        return new ResponseEntity<>(commentService.updateComment(commentModel, postId, commentId), HttpStatus.ACCEPTED);
    }


    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deletebyCommentId(@PathVariable("postId") String postId, @PathVariable("commentId") String commentId) {
        return new ResponseEntity<>(commentService.deleteByCommentId(commentId), HttpStatus.ACCEPTED);
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentDto>> showCommentsByPostId(@PathVariable("postId") String postId, @QueryParam("page") Integer page, @QueryParam("pageSize") Integer pageSize) {
        return new ResponseEntity<>(commentService.allComments(postId,page,pageSize), HttpStatus.ACCEPTED);
    }

}


