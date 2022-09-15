package com.dev.nou.springjwt.entity;

import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name="users")
public class User {
	@Id
	@GeneratedValue
	@Column(name="user_id")
	private Integer id;
	
	@Column(name="user_name")
	@NotBlank(message = "Invalid Name: Empty name")
    @NotNull(message = "Invalid Name: Name is NULL")
    @Size(min = 3, max = 30, message = "Invalid Name: Must be of 3 - 30 characters")
	private String username;
	
	@Column(name="user_pwd")
	@NotBlank(message = "Invalid Name: Empty name")
    @NotNull(message = "Invalid Name: Name is NULL")
    @Size(min = 3, max = 30, message = "Invalid Name: Must be of 3 - 30 characters")
	private String password;
	
	@Column(name="user_email")
	@Email(message = "Invalid email")
	private String email;
	
	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name="roles", joinColumns = @JoinColumn(name="user_id"))
	@Column(name="user_role")
	private Set<String> roles;

}
