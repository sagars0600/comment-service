package com.comment.commentservice.model;

import com.comment.commentservice.constfiles.ConstFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "comment")
@Data
public class CommentModel {
    @Id
    private String commentID;



    @NotEmpty(message = ConstFile.commentedby)
    private String commentedBy;

    @NotEmpty(message = ConstFile.errorCode)
    private String comment;


    private String postID;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
