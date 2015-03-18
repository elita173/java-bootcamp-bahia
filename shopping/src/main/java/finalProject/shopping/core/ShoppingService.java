package finalProject.shopping.core;

import java.util.List;

import finalProject.shopping.model.Category;
import finalProject.shopping.model.Item;
import finalProject.shopping.model.Payment;
import finalProject.shopping.model.Product;
import finalProject.shopping.model.ShoppingCart;
import finalProject.shopping.model.User;

public interface ShoppingService {
	public void saveProduct(Product product);

	public void saveCategory(Category category);

	public void saveShoppingCart(ShoppingCart cart);

	public void saveUser(User user);

	public void savePayment(Payment payment);

	public void addItemToCart(Item item, ShoppingCart cart);

	public void removeItemFromCart(Item item, ShoppingCart cart);

	public List<Product> getProductsFromCategory(Category category);

	public List<Product> findAllProducts();

	public List<Category> findAllCategories();

	public List<ShoppingCart> findAllShoppingCarts();

	public List<Payment> findAllPayments();

	public List<User> findAllUsers();

	public Product findOneProduct(long id);

	public Category findOneCategory(long id);

	public ShoppingCart findOneShoppingCart(long id);

	public Payment findOnePayment(long id);

	public User findOneUser(long id);

}
