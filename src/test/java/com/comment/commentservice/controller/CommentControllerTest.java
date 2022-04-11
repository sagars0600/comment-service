package com.comment.commentservice.controller;

import com.comment.commentservice.enums.BloodGroup;
import com.comment.commentservice.enums.Gender;
import com.comment.commentservice.model.CommentDto;
import com.comment.commentservice.model.CommentModel;
import com.comment.commentservice.model.User;
import com.comment.commentservice.service.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@WebMvcTest(CommentController.class)
class CommentControllerTest {
    @MockBean
    CommentService commentService;

    @Autowired
    MockMvc mockMvc;
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void testCreateComment() throws Exception {
        CommentModel comment = createOneCommentToPost();
        CommentDto commentDto = new CommentDto();
        CommentModel commentRequest = new CommentModel();
        Mockito.when(commentService.saveComment(commentRequest, "1")).thenReturn(commentDto);
        mockMvc.perform(post("/posts/1/comments")
                        .content(asJsonString(comment))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted());
    }

    private CommentModel createOneCommentToPost() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date c = sdf.parse("2015-05-26");
        CommentModel commentDto = new CommentModel();

        commentDto.setCommentID("1");
        commentDto.setComment("Hi");
        commentDto.setCommentedBy(String.valueOf(new User("1", "Roody", "Bro",
                "nik", "1023456789",  Gender.MALE,"Chennai",c,
                "123", BloodGroup.A_POS, "roody@gamil.com")));
        return commentDto;
    }


    @Test
    public void testGetComments() throws Exception {
        List<CommentDto> userDto = createCommentList();

        Mockito.when(commentService.allComments("2", null, null)).thenReturn(userDto);

        mockMvc.perform(get("/posts/2/comments"))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].comment", Matchers.is("commentTestOne")));
    }

    private List<CommentDto> createCommentList() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date c = sdf.parse("2015-05-26");
        List<CommentDto> commentDto = new ArrayList<>();

        CommentDto commentDto1 = new CommentDto();
        commentDto1.setCommentID("1");
        commentDto1.setComment("commentTestOne");
        commentDto1.setCommentedBy(new User("1", "Roody", "Bro",
                "nik", "1023456789",  Gender.MALE,"Chennai",c,
                "123", BloodGroup.A_POS, "roody@gamil.com"));
        commentDto1.setLikesCount(3);

        CommentDto commentDto2 = new CommentDto();
        commentDto2.setCommentID("2");
        commentDto2.setComment("commentTestTwo");
        commentDto2.setCommentedBy(new User("1", "Nikhil", "Arun",
                "nik", "1023456789",  Gender.MALE,
                "Ngp",c, "123", BloodGroup.A_POS, "nikhil@gamil.com"));
        commentDto2.setLikesCount(3);
        commentDto.add(commentDto1);
        commentDto.add(commentDto2);

        return commentDto;
    }

    @Test
    public void testGetCommentsByID() throws Exception {
        CommentDto commentDto = createOneComment();

        Mockito.when(commentService.findByCommentId("2")).thenReturn(commentDto);

        mockMvc.perform(get("/posts/1/comments/2"))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$", Matchers.aMapWithSize(6)))
                .andExpect(jsonPath("$.comment", Matchers.is("CommentTest")));
    }

    private CommentDto createOneComment() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date c = sdf.parse("2015-05-26");
        CommentDto commentDto = new CommentDto();
        commentDto.setCommentID("2");
        commentDto.setComment("CommentTest");
        commentDto.setCommentedBy(new User("1", "Roody", "Bro",
                "nik", "1023456789",  Gender.MALE,"Chennai",c,
                "123", BloodGroup.A_POS, "roody@gamil.com"));
        commentDto.setCreatedAt(LocalDateTime.now());
        return commentDto;
    }


    @Test
    public void testDeleteCommentsByID() throws Exception {

        Mockito.when(commentService.deleteByCommentId("2")).thenReturn("deleted sucessfully");
        mockMvc.perform(delete("/posts/1/comments/2"))
                .andDo(print())
                .andExpect(status().isAccepted());

    }

    @Test
    public void testUpdateCommentsByID() throws Exception {
        CommentDto commentDto=new CommentDto();
        CommentModel commentModel= updateOneComment();
        CommentModel commentModel1= new CommentModel();

Mockito.when(commentService.updateComment(commentModel1,"1","1")).thenReturn(commentDto);
mockMvc.perform(put("/posts/1/comments/2")
        .content(asJsonString(commentModel))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted());

    }

    private CommentModel updateOneComment() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date c = sdf.parse("2015-05-26");
        CommentModel commentModel= new CommentModel();
        commentModel.setComment("hello");
        commentModel.setCommentID("1");
        commentModel.setPostID("2");
        commentModel.setCommentedBy("roody");
        commentModel.setCommentedBy(String.valueOf(new User("1", "Roody", "Bro",
                "nik", "1023456789",  Gender.MALE,"Chennai",c,
                "123", BloodGroup.A_POS, "roody@gamil.com")));
        commentModel.setCreatedAt(null);
        commentModel.setCreatedAt(null);
        return commentModel;


    }

    @Test
    public void testCountCommentsByID() throws Exception {

        Integer ints=testCount();
        Mockito.when(commentService.commentCount("2")).thenReturn(ints);
        mockMvc.perform(get("/posts/1/comments/2"))
                .andDo(print())
                .andExpect(status().isAccepted());
    }


private Integer testCount(){
        List<CommentModel>  list= new ArrayList<>();
        CommentModel commentModel1= new CommentModel();
    CommentModel commentModel2= new CommentModel();
    CommentModel commentModel3= new CommentModel();
    list.add(commentModel1);
    list.add(commentModel2);
    list.add(commentModel3);
    return  list.size();
}








}
