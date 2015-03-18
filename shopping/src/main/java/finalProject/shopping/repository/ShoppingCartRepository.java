package finalProject.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import finalProject.shopping.model.ShoppingCart;

public interface ShoppingCartRepository extends
		JpaRepository<ShoppingCart, Long> {

}
