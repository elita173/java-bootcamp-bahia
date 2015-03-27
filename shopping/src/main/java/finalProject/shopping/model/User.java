package finalProject.shopping.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "USER_ID")
	private long userId;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "USERNAME")
	private String username;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "ROLE", nullable = false)
	private String role;

	protected User() {
	}

	public User(String firstName, String lastName, String username,
			String password) {
		setFirstName(firstName);
		setLastName(lastName);
		setUsername(username);
		setPassword(password);
		setRole("USER");
	}

	private void setRole(String string) {
		role = string;
	}

	public void setAdminRole() {
		setRole("ADMIN");
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public String toString() {
		return ("UserID: " + getId() + "<br>First Name: " + getFirstName()
				+ "<br>Last Name: " + getLastName() + "<br>Username: "
				+ getUsername() + "<br>Password: " + getPassword()
				+ "<br>Role: " + getRole());
	}

	public Long getId() {
		return userId;
	}
}
