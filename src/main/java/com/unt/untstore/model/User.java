package com.unt.untstore.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "APP_USER")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseModel {

    @Column(name = "USR_TY")
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column(name = "FIRST_NM")
    private String firstName;

    @Column(name = "LAST_NM")
    private String lastName;

    @Column(name = "EMAIL", unique = true)
    private String emailId;

    @Column(name = "PWD")
    private String password;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ROLE_ID")
    private Role role;

    @Column(name = "CREATED_ON")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @Column(name = "USR_STATUS")
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

}
