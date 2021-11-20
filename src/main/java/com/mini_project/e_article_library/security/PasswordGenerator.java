package com.mini_project.e_article_library.security;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		System.out.println(encoder.encode("venu"));
		System.out.println(encoder.encode("dileep"));
		System.out.println(encoder.encode("mani"));
	}
}
