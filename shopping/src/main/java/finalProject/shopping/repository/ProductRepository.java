package finalProject.shopping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import finalProject.shopping.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
	@Query("SELECT p FROM Product p JOIN p.category c WHERE c.code >= ?1 AND c.code < ?2")
	List<Product> findByCategory(long minValue, long maxValue);
}
