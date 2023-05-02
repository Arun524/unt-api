package com.unt.untstore;

import com.unt.untstore.model.User;
import com.unt.untstore.model.UserStatus;
import com.unt.untstore.model.UserType;
import com.unt.untstore.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@SpringBootTest
public class UserTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void createUser() {
        User user = User.builder()
                .firstName("Arun").lastName("Thotakuri")
                .password(passwordEncoder.encode("welcome"))
                .emailId("arunadmin@yopmail.com")
                .userStatus(UserStatus.ACTIVE)
                .userType(UserType.ADMIN)
                .createdOn(new Date()).build();
        userRepository.save(user);
    }
}
