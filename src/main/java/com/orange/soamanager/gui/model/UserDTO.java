package com.orange.soamanager.gui.model;

import java.util.List;

public class UserDTO {
	private List<User>users;
	
	//default and parameterized constructor
	public void addUser(User user) {
		this.users.add(user);
	}
	
     public UserDTO(List<User> users) {
		super();
		this.users = users;
	}

	//getter and setter

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
}
