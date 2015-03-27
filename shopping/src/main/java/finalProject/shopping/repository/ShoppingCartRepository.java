package finalProject.shopping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import finalProject.shopping.model.ShoppingCart;

public interface ShoppingCartRepository extends
		JpaRepository<ShoppingCart, Long> {
	@Query("SELECT c FROM ShoppingCart c JOIN c.user u WHERE u.userId = ?1")
	List<ShoppingCart> findAllShopingCartsForUser(long userId);
}
