package com.comment.commentservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentDto {
    @Id
    private String commentID;
    @NotEmpty(message = "commentedBy is required")
    private User commentedBy;
    @NotEmpty(message = "comment is required")
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int likesCount;

}
