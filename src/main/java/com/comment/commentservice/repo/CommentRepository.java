package com.comment.commentservice.repo;

import com.comment.commentservice.model.CommentModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends MongoRepository<CommentModel,String> {

    public List<CommentModel> findByPostID(String postId);
}
