package finalProject.shopping.core;

import java.util.List;

import finalProject.shopping.model.Category;
import finalProject.shopping.model.Item;
import finalProject.shopping.model.Payment;
import finalProject.shopping.model.Product;
import finalProject.shopping.model.ShoppingCart;

public interface ShoppingService {
	
	public void saveProduct(Product product);

	public void saveCategory(Category category);

	public void saveShoppingCart(ShoppingCart cart);

	public void savePayment(Payment payment);

	public void addItemToCart(Item item, ShoppingCart cart);

	public void removeItemFromCart(long productId, ShoppingCart cart);

	public List<Product> findProductsFromCategory(Category category);

	public List<Product> findAllProducts();

	public List<Category> findAllCategories();

	public List<ShoppingCart> findAllShoppingCarts();

	public List<Payment> findAllPayments();

	public Product findOneProduct(long id);

	public Category findOneCategory(long id);
	
	public List<Category> findCategoryByCode(long code);

	public ShoppingCart findOneShoppingCart(long id);

	public Payment findOnePayment(long id);

	public ShoppingCart findCurrentShoppingCartFromUser(Long userId);
}
