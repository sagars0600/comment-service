package com.comment.commentservice.model;

import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

public class CommentDto {
    @Id
    private String commentID;
    @NotEmpty(message = "commentedBy is required")
    private String commentedBy;
    @NotEmpty(message = "comment is required")
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int likesCount;

}
