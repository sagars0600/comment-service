package com.comment.commentservice.service;

import com.comment.commentservice.Exception.CommentNotFoundException;
import com.comment.commentservice.enums.BloodGroup;
import com.comment.commentservice.enums.Gender;
import com.comment.commentservice.feign.FeignLike;
import com.comment.commentservice.feign.FeignUser;
import com.comment.commentservice.model.CommentDto;
import com.comment.commentservice.model.CommentModel;
import com.comment.commentservice.model.User;
import com.comment.commentservice.repo.CommentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CommentServiceTest {

    @InjectMocks
    CommentService service;

    @Mock
    CommentRepository commentRepo;


    @Mock
    FeignUser feignUser;

    @Mock
    FeignLike feignLike;
    @Test
    void commentCount() throws ParseException {
        CommentModel commentModel = createCommentModel();
        CommentDto commentDTO =createCommentDTO();
        List<CommentModel> list = new ArrayList<>();
        list.add(commentModel);
        when(this.commentRepo.findByPostID("1")).thenReturn(list);
        assertThat(this.service.commentCount("1")).isEqualTo(0);
    }

    private CommentModel createCommentModel(){
        return new CommentModel("1","1","comment","1",null,null);
    }
    private CommentDto createCommentDTO() throws ParseException {
        return new CommentDto("1","comment",createUser(),1,null,null);
    }
    private User createUser() throws ParseException, ParseException {
        User user = new User();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date c= sdf.parse("2015-05-26");
        user.setUserId("123");
        user.setFirstName("firstTest");
        user.setMiddleName("J");
        user.setLastName("S");
        user.setPhoneNumber("9090909090");
        user.setEmail("natsu@mail.com");
        user.setDateOfBirth(c);
        user.setEmployeeNumber("12345");
        user.setBloodGroup(BloodGroup.B_NEG);
        user.setGender(Gender.FEMALE);
        return user;
    }

    @Test
    void updateComment() throws ParseException {
        CommentModel commentModel = createCommentModel();
        CommentDto commentDTO =createCommentDTO();

        when(this.commentRepo.save(commentModel)).thenReturn(commentModel);
        when(this.commentRepo.findById("1")).thenReturn(Optional.of(commentModel));

        assertThat(this.service.updateComment(commentModel,"1","1").getComment()).isEqualTo(
                commentDTO.getComment()
        );
    }

    @Test
    void deleteByCommentId() throws ParseException {

        CommentModel commentModel = createCommentModel();
        CommentDto commentDTO =createCommentDTO();
        doNothing().when(this.commentRepo).deleteById("1");
        when(this.commentRepo.findById("1")).thenReturn(Optional.of(commentModel));
        assertThat(this.service.deleteByCommentId("1"));
        assertThrows(CommentNotFoundException.class,()->this.service.deleteByCommentId("2"));
    }

    @Test
    void allComments() throws ParseException {
        CommentModel commentModel = createCommentModel();
        CommentDto commentDTO =createCommentDTO();
        List<CommentModel> list = new ArrayList<>();
        list.add(commentModel);
        PageImpl<CommentModel> pageImpl = new PageImpl<>(list);
        Pageable firstPage = PageRequest.of(0, 2);
        when(this.commentRepo.findBypostID("1",firstPage)).thenReturn( list);
        assertEquals(1,this.service.allComments("1",null,2).size());

    }

    @Test
    void saveComment() throws ParseException {
        CommentDto commentDTO = createCommentDTO();
        CommentModel commentModel =createCommentModel();
        Mockito.when(this.commentRepo.save(any(CommentModel.class))).thenReturn(commentModel);
        assertThat(this.service.saveComment(commentModel,"1").getComment()).isEqualTo(commentDTO.getComment());
    }

    @Test
    void findByCommentId() throws ParseException {

        CommentModel commentModel = createCommentModel();
        CommentDto commentDTO =createCommentDTO();

        when(this.commentRepo.findById("1")).thenReturn(Optional.of(commentModel));
        assertThat(this.service.findByCommentId("1").getComment()).isEqualTo(commentDTO.getComment());
        assertThrows(CommentNotFoundException.class,()->this.service.findByCommentId("2"));
    }
}