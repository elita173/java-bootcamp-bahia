package finalProject.shopping.core;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class UserCreateForm {
	@NotEmpty
	private String firstName = "";

	@NotEmpty
	private String lastName = "";
	
	@NotEmpty
	private String username = "";
	
	@NotEmpty
	private String password = "";

	@NotNull
	private String role = "USER";

	public String getFirstName(){
		return firstName;
	}

	public String getLastName(){
		return lastName;
	}
	
	public String getUsername(){
		return username;
	}
	
	public String getPassword(){
		return password;
	}
}
