package com.app.note.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.security.crypto.password.PasswordEncoder;

public class AppPasswordEncoder implements PasswordEncoder {
	
	@Override
	public String encode(CharSequence rawPassword) {
		String pass = String.valueOf(rawPassword);
		byte[] chars = pass.getBytes(StandardCharsets.UTF_8);
		return Base64.getEncoder().encodeToString(chars);
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return encodedPassword.equals(encode(rawPassword));
	}

	public String decode(String encodedPassword) {
		byte[] pass = Base64.getDecoder().decode(encodedPassword);
		return new String(pass, StandardCharsets.UTF_8);
	}
}
