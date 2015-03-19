package finalProject.shopping.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import finalProject.shopping.model.Item;
import finalProject.shopping.model.Payment;
import finalProject.shopping.model.Product;
import finalProject.shopping.model.ShoppingCart;
import finalProject.shopping.model.Category;
import finalProject.shopping.repository.CategoryRepository;
import finalProject.shopping.repository.PaymentRepository;
import finalProject.shopping.repository.ProductRepository;
import finalProject.shopping.repository.ShoppingCartRepository;

@Service
public class ShoppingServiceImpl implements ShoppingService {
	@Autowired
	private ProductRepository productRepo;

	@Autowired
	private ShoppingCartRepository shoppingCartRepo;

	@Autowired
	private PaymentRepository paymentRepo;

	@Autowired
	private CategoryRepository categoryRepo;

	public ShoppingServiceImpl() {
	}

	public void saveProduct(Product product) {
		productRepo.saveAndFlush(product);
	}

	public void saveCategory(Category category) {
		categoryRepo.saveAndFlush(category);
	}

	public void saveShoppingCart(ShoppingCart cart) {
		shoppingCartRepo.saveAndFlush(cart);
	}

	public void savePayment(Payment payment) {
		paymentRepo.saveAndFlush(payment);
	}

	public void addItemToCart(Item item, ShoppingCart cart) {
		cart.addItem(item);
		shoppingCartRepo.saveAndFlush(cart);
	}

	public void removeItemFromCart(Item item, ShoppingCart cart) {
		cart.removeItem(item.getProduct().getCode());
		shoppingCartRepo.saveAndFlush(cart);
	}

	public List<Product> findProductsFromCategory(Category category) {
		return productRepo.findByCategory(category.getSubtreeLowerLimit(3), category.getSubtreeUpperLimit(3));
	}

	public List<Product> findAllProducts() {
		return productRepo.findAll();
	}

	public List<Category> findAllCategories() {
		return categoryRepo.findAll();
	}

	public List<ShoppingCart> findAllShoppingCarts() {
		return shoppingCartRepo.findAll();
	}

	public List<Payment> findAllPayments() {
		return paymentRepo.findAll();
	}

	public Product findOneProduct(long id) {
		return productRepo.findOne(id);
	}

	public Category findOneCategory(long id) {
		return categoryRepo.findOne(id);
	}

	public ShoppingCart findOneShoppingCart(long id) {
		return shoppingCartRepo.findOne(id);
	}

	public Payment findOnePayment(long id) {
		return paymentRepo.findOne(id);
	}
}
