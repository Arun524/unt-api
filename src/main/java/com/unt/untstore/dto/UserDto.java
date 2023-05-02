package com.unt.untstore.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    private String uid;

    private String lastName;

    private String firstName;

    private String username;

    private String emailId;

    private String password;

    private Date createdOn;

    private String userType;

    private String status;

}
