package finalProject.shopping.core;

import java.util.List;

import finalProject.shopping.model.User;

public interface UserService {

	public User findUserByUsername(String username);

	public List<User> findAllUsers();

	public User findOneUser(long id);

	public User saveUser(User user);
}
