package com.sahu.digital.marketing.dto.request;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserDTO {
	private String firstName;
	private String middleName;
	private String lastName;
	private String emailorPhoneNo;
	private String email;
	private Long phoneNo;
	private String password;
	private MultipartFile profiePhoto;
}
