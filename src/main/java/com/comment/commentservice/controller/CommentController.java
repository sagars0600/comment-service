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

@CrossOrigin(value = "*")
@RestController
@RequestMapping("/posts/{postId}/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;


    @GetMapping("/{commentId}")

    public ResponseEntity<CommentDto> findByCommentId(@PathVariable("postId") String postId, @PathVariable("commentId") String commentId) {
        return new ResponseEntity<>(commentService.findByCommentId(commentId), HttpStatus.ACCEPTED);
    }


    @GetMapping("/count")
    public ResponseEntity<Integer> commentCount(@PathVariable("postId") String postId) {
        return new ResponseEntity<>(commentService.commentCount(postId), HttpStatus.ACCEPTED);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@RequestBody @Valid CommentModel commentModel, @PathVariable("postId") String postId, @PathVariable("commentId") String commentId) {
        return new ResponseEntity<>(commentService.updateComment(commentModel, postId, commentId), HttpStatus.ACCEPTED);
    }


    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deletebyCommentId(@PathVariable("postId") String postId, @PathVariable("commentId") String commentId) {
        return new ResponseEntity<>(commentService.deleteByCommentId(commentId), HttpStatus.ACCEPTED);
    }

    @GetMapping()
    public ResponseEntity<List<CommentDto>> showCommentsByPostId(@PathVariable("postId") String postId, @QueryParam("page") Integer page, @QueryParam("pageSize") Integer pageSize) {
        return new ResponseEntity<>(commentService.allComments(postId, page, pageSize), HttpStatus.ACCEPTED);
    }


    @PostMapping()
    public ResponseEntity<CommentDto>saveComment(@Valid @RequestBody CommentModel commentModel, @PathVariable("postId") String postId) {

        return new ResponseEntity<>(commentService.saveComment(commentModel, postId), HttpStatus.ACCEPTED);

    }

}


