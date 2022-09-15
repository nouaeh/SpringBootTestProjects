package com.dev.nou.springjwt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dev.nou.springjwt.entity.ErrorType;

//@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UserNotFoundException.class)
	@ResponseBody
	public ResponseEntity<ErrorType> handleUserNotFoundException(UserNotFoundException ex){
		return new ResponseEntity<ErrorType>(new ErrorType(
				ex.getMessage(),
				HttpStatus.UNAUTHORIZED.value(),
				//Integer.parseInt(HttpStatus.BAD_REQUEST).replaceAll("\\D+", ""),
				ex.getLocalizedMessage(),
				HttpStatus.UNAUTHORIZED.name()
				), HttpStatus.UNAUTHORIZED);
	}
	@ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
	@ResponseBody
	public ResponseEntity<ErrorType> handleAuthenticationException(Exception ex){
		return new ResponseEntity<ErrorType>(new ErrorType(
				ex.getMessage(),
				HttpStatus.UNAUTHORIZED.value(),
				ex.getLocalizedMessage(),
				HttpStatus.UNAUTHORIZED.name()
				
				), HttpStatus.UNAUTHORIZED);
	}
	
	
	@ExceptionHandler(RuntimeException.class)
	@ResponseBody
	public ResponseEntity<ErrorType> handleRuntimException(Exception ex){
		return new ResponseEntity<ErrorType>(new ErrorType(
				ex.getMessage(),
				HttpStatus.BAD_REQUEST.value(),
				ex.getLocalizedMessage(),
				HttpStatus.BAD_REQUEST.name()
				
				), HttpStatus.BAD_REQUEST);
	}
}
