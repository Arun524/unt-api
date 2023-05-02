package com.unt.untstore.service.impl;

import com.unt.untstore.dto.LoginResponseDto;
import com.unt.untstore.dto.UserDto;
import com.unt.untstore.model.User;
import com.unt.untstore.model.UserStatus;
import com.unt.untstore.model.UserType;
import com.unt.untstore.repository.ProductWishlistRepository;
import com.unt.untstore.repository.UserRepository;
import com.unt.untstore.security.JwtTokenUtil;
import com.unt.untstore.service.ILoginService;
import com.unt.untstore.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class LoginServiceImpl implements ILoginService {

    Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IUserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenFactory;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductWishlistRepository productWishlistRepository;


    @Override
    public LoginResponseDto login(Map<String, String> request) {
        logger.info("username"+request.get("username"));  //senstive data exposure
        logger.info("user password"+request.get("password"));

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                request.get("username"), request.get("password"));
        try {
            authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        } catch (BadCredentialsException bce) {
            throw new RuntimeException("INVALID_USER_NAME_OR_PASSWORD");
        } catch (UsernameNotFoundException une) {
            throw new RuntimeException("INVALID_USER_NAME_OR_PASSWORD");
        }
        String username = request.get("username");
        Optional<User> userOptional = userRepository.findByEmailId(username);

        if (userOptional.isEmpty()) {
            throw new RuntimeException("INVALID_USER_NAME_OR_PASSWORD");
        }

        String token = jwtTokenFactory.generateToken(userOptional.get());
        User user = userOptional.get();
        UserDto userDto = UserDto.builder()
                .uid(user.getUid())
                .userType(user.getUserType().name())
                .status(user.getUserStatus().name())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .emailId(user.getEmailId())
                .uid(user.getUid()).build();

        LoginResponseDto loginResponseDto = LoginResponseDto.buildLoginResponse(token, userDto);

        List<String> favoritesUids = productWishlistRepository.findAllProductUidByUserUid(user.getUid());
        loginResponseDto.setWishlist(favoritesUids);

        return loginResponseDto;
    }

    @Override
    public boolean signUp(UserDto userDto) {
        Optional<User> userOptional = userRepository.findByEmailId(userDto.getUsername());
        if (userOptional.isPresent()) {
            throw new RuntimeException("USER_ALREADY_EXITS");
        }
        logger.info("user password"+userDto.getUsername());
        logger.info("user password"+userDto.getPassword());
        User user = User.builder().firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .userType(UserType.valueOf(userDto.getUserType()))
                .userStatus(UserStatus.ACTIVE)
                .password(passwordEncoder.encode(userDto.getPassword()))
                .emailId(userDto.getEmailId())
                .createdOn(Calendar.getInstance().getTime())
                .build();
        user = userService.saveUser(user);

        return true;
    }

    @Override
    public Boolean updatePassword(String userUid, String password) {
        Optional<User> userOptional = userRepository.findByUid(userUid);
        if (userOptional.isPresent()) {
            userOptional.get().setPassword(passwordEncoder.encode(password));
            userService.saveUser(userOptional.get());
            return true;
        } else {
            throw new RuntimeException("Invalid User");
        }

    }

}
