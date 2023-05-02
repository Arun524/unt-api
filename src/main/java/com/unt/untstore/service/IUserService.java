package com.unt.untstore.service;

import com.unt.untstore.dto.UserDto;
import com.unt.untstore.model.User;

public interface IUserService {

	public User getUserById(Long id);
	
	public UserDto getUserByUid(String uid);

	public User getUserByEmailId(String emailId);

	public User saveUser(User user);
	
	public UserDto updateUser(UserDto userDto);

}
