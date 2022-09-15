package com.dev.nou.springjwt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorType {

	private String message;
	private int code;
	private String error;
	private String status;
}
