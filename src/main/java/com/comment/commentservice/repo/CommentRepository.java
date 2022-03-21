package com.comment.commentservice.repo;

import com.comment.commentservice.model.CommentModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends MongoRepository<CommentModel,String> {
}
