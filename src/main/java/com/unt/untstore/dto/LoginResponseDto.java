package com.unt.untstore.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class LoginResponseDto {

	private UserDto userDto;

	private String token;

	private List<String> wishlist;

	public static LoginResponseDto buildLoginResponse(String token, UserDto userDto) {
		LoginResponseDto loginResponseDto = new LoginResponseDto();
		loginResponseDto.setToken(token);
		loginResponseDto.setUserDto(userDto);
		return loginResponseDto;
	}
}
