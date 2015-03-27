package finalProject.shopping.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import finalProject.shopping.model.User;
import finalProject.shopping.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepo;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepo = userRepository;
	}

	@Override
	public User findUserByUsername(String username) {
		return userRepo.findOneByUsername(username);
	}

	@Override
	public List<User> findAllUsers() {
		return userRepo.findAll();
	}

	@Override
	public User findOneUser(long id) {
		return userRepo.findOne(id);
	}

	@Override
	public User saveUser(User user) {
		return userRepo.saveAndFlush(user);
	}

}
