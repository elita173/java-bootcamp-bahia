package finalProject.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import finalProject.shopping.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
