package finalProject.shopping.core.currentUser;

import org.springframework.security.core.authority.AuthorityUtils;

import finalProject.shopping.model.User;

public class CurrentUser extends
		org.springframework.security.core.userdetails.User {
	private User user;

	public CurrentUser(User user) {
		super(user.getUsername(), user.getPassword(), AuthorityUtils
				.createAuthorityList(user.getRole().toString()));
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public Long getId() {
		return user.getId();
	}

	public String getRole() {
		return user.getRole();
	}

	@Override
	public String toString() {
		return "CurrentUser{" + "user=" + user + "} " + super.toString();
	}

}
