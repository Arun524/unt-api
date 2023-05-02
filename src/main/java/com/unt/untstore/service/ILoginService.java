package com.unt.untstore.service;

import com.unt.untstore.dto.LoginResponseDto;
import com.unt.untstore.dto.UserDto;

import java.util.Map;


public interface ILoginService {
	
	public boolean signUp(UserDto userDto);
	
	public Boolean updatePassword(String username, String password);
	
	public LoginResponseDto login(Map<String, String> request);

}
