package com.example.algamoney.api.security.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeradorSenha {

	public static void main(String[] args) {
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		System.out.println("@ngul@r0 = " + encoder.encode("@ngul@r0"));
		System.out.println("m0b1l30 = " + encoder.encode("m0b1l30"));		
		
		
	}
	
}