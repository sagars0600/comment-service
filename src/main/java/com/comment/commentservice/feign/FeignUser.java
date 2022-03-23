package com.comment.commentservice.feign;

import com.comment.commentservice.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="user-service")
public interface FeignUser {

    @GetMapping("/users/{userId}")
    public User findByID(@PathVariable("userId") String userId);



}
