package com.sahu.um.model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Mian {

	public static void main(String[] args) {
		BCryptPasswordEncoder ff = new BCryptPasswordEncoder();
		System.out.println(ff.encode("ityug123"));
	}
	
}
