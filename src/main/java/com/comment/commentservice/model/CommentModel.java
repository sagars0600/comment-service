package com.comment.commentservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "comment")
@Data
public class CommentModel {
    @Id
    private String commentId;

    @NotEmpty(message = "comment is required")
    private String comment;

    @NotEmpty(message = "Date is required")
    private Date createdAt;

    @NotEmpty(message = "Update date is required")
    private Date updatedAt;
}
