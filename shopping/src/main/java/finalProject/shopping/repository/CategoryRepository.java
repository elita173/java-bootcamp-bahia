package finalProject.shopping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import finalProject.shopping.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{
	List<Category> findByCode(long code);
}
