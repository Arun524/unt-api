package com.unt.untstore.service.impl;

import com.unt.untstore.dto.UserDto;
import com.unt.untstore.model.User;
import com.unt.untstore.repository.UserRepository;
import com.unt.untstore.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            userOptional.get();
            return userOptional.get();
        } else {
            return null;
        }
    }

    @Override
    public User getUserByEmailId(String emailId) {
        Optional<User> userOptional = userRepository.findByEmailId(emailId);
        if (userOptional.isPresent()) {
            userOptional.get();
            return userOptional.get();
        } else {
            return null;
        }
    }

    @Override
    public User saveUser(User user) {
        user = userRepository.save(user);
        return user;
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        Optional<User> optionalUser = userRepository.findByUid(userDto.getUid());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setEmailId(userDto.getEmailId());
            userRepository.save(user);
        }
        return userDto;
    }

    @Override
    public UserDto getUserByUid(String uid) {
        Optional<User> optionalUser = userRepository.findByUid(uid);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            UserDto userDto = UserDto.builder()
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .emailId(user.getEmailId())
                    .userType(user.getUserType().name()).uid(user.getUid()).build();
            return userDto;
        } else {
            throw new RuntimeException("INVALID_USER");
        }
    }

}
