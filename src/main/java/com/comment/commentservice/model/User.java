package com.comment.commentservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
@Data
public class User {
    @Id

    private String userId;

    @NotEmpty(message = "First Name is required")
    private String firstName;

    private String middleName;

    @NotEmpty(message = "Last Name is required")
    private String lastName;

    @NotEmpty(message = "Phone Number is required")
    private String phoneNumber;

    @NotEmpty(message = "Gender is required")
    private String gender;

    @NotEmpty(message = "Marital Status is required")
    private String address;

    @NotNull
    private Date dateOfBirth;

    private String employeeNumber;

    @NotEmpty(message = "Blood Group  is required")
    private String bloodGroup;

    @NotEmpty(message = "Email is required")
    private String email;
    private String password;

}
