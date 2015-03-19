package finalProject.shopping.core.currentUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import finalProject.shopping.core.UserService;
import finalProject.shopping.model.User;

@Service
public class CurrentUserDetailsService implements UserDetailsService {

	@Autowired
    private UserService userService;

    public CurrentUserDetailsService() {
    }

    @Override
    public CurrentUser loadUserByUsername(String username) {
        User user = userService.findUserByUsername(username);
        return new CurrentUser(user);
    }

}