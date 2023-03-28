package com.eorder.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserDto {
	private String uid;
	private String email;
	private String companyName;
	private String companyAddress;
	private String phoneNumber;
	private String role;
	private String password;
	private String name;
	
	public UserDto() {
		
	}
}