package finalProject.shopping.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import finalProject.shopping.model.CashPayment;
import finalProject.shopping.model.Category;
import finalProject.shopping.model.CreditCardPayment;
import finalProject.shopping.model.PayPalPayment;
import finalProject.shopping.model.Payment;
import finalProject.shopping.model.Product;
import finalProject.shopping.model.ShoppingCart;
import finalProject.shopping.model.User;

@Controller
public class ShoppingController {
	@Autowired
	ShoppingServiceImpl cartService;

	@Autowired
	UserService userService;

	public ShoppingController() {
	}

	@RequestMapping("/")
	public String getHomePage() {
		return "home";
	}

	@RequestMapping(value = "/product/load", method = RequestMethod.GET)
	public ResponseEntity<String> loadBaby() {
		User user1, user2, user3;
		Category level1, level2, level3;
		ShoppingCart cart1, cart2, cart3;
		Payment pay1, pay2, pay3;
		Product product;

		level1 = new Category("Food", 10, null);
		level2 = new Category("Canned", 0, level1);
		level3 = new Category("Tuna&SeaFood", 0, level2);
		product = new Product("La Campagnola Tuna", 30.5, true, level3);
		cartService.saveCategory(level1);
		cartService.saveCategory(level2);
		cartService.saveCategory(level3);
		cartService.saveProduct(product);
		
		level2 = new Category("Meat&Poultry", 1, level1);
		level3 = new Category("Pork", 0, level2);
		product = new Product("Paladini Ham", 110, false, level3);
		cartService.saveCategory(level2);
		cartService.saveCategory(level3);
		cartService.saveProduct(product);
		
		level3 = new Category("Beef", 1, level2);
		product = new Product("Paty Hamburger x4", 45, true, level3);
		cartService.saveCategory(level3);
		cartService.saveProduct(product);
		
		level2 = new Category("Dairy&Cheese", 2, level1);
		level3 = new Category("Cheese", 0, level2);
		product = new Product("Cremon Cheese", 98, false, level3);
		cartService.saveCategory(level2);
		cartService.saveCategory(level3);
		cartService.saveProduct(product);
		
		level2 = new Category("Fruit&Vegetables", 3, level1);
		level3 = new Category("Fruit", 0, level2);
		product = new Product("Orange", 12, false, level3);
		cartService.saveCategory(level2);
		cartService.saveCategory(level3);
		cartService.saveProduct(product);
		
		level2 = new Category("Condiments", 4, level1);
		level3 = new Category("Salt", 0, level2);
		product = new Product("Celusal Salt 1/2Kg", 14.2, true, level3);
		cartService.saveCategory(level2);
		cartService.saveCategory(level3);
		cartService.saveProduct(product);
		
		level2 = new Category("Grain&Pasta", 5, level1);
		level3 = new Category("Rice", 0, level2);
		product = new Product("Mocovi Rice 1/2kg", 18.7, true, level3);
		cartService.saveCategory(level2);
		cartService.saveCategory(level3);
		cartService.saveProduct(product);
		
		level2 = new Category("Snacks", 6, level1);
		level3 = new Category("Chips", 0, level2);
		product = new Product("Pringles Chips x360gr", 32.4, true, level3);
		cartService.saveCategory(level2);
		cartService.saveCategory(level3);
		cartService.saveProduct(product);
		
		level1 = new Category("Beverages", 11, null);
		level2 = new Category("Infusions", 0, level1);
		level3 = new Category("Yerba", 0, level2);
		product = new Product("Amanda Yerba", 38.9, true, level3);
		cartService.saveCategory(level1);
		cartService.saveCategory(level2);
		cartService.saveCategory(level3);
		cartService.saveProduct(product);
		
		level2 = new Category("No-Alcohol", 1, level1);
		level3 = new Category("Soft-Drinks", 0, level2);
		product = new Product("Coca Cola 2.25lt", 29.6, true, level3);
		cartService.saveCategory(level2);
		cartService.saveCategory(level3);
		cartService.saveProduct(product);
		
		level2 = new Category("Alcohol", 2, level1);
		level3 = new Category("Beer", 0, level2);
		product = new Product("Heineken Beer 1lt", 28.5, true, level3);
		cartService.saveCategory(level2);
		cartService.saveCategory(level3);
		cartService.saveProduct(product);
		
		user1 = new User("Mario", "Spagnoli", "mspagnoli", "1234");
		user2 = new User("Peg", "Jeanbaptiste", "pjbaptiste", "1234");
		user3 = new User("Kiara", "Boyles", "kboyles", "1234");
		
		userService.saveUser(user1);
		userService.saveUser(user2);
		userService.saveUser(user3);
		
		cart1 = new ShoppingCart(user1);
		cart1.addItem(cartService.findOneProduct(1), 1);
		cart1.addItem(cartService.findOneProduct(2), 2.3);
		cart1.addItem(cartService.findOneProduct(3), 3);
		
		cart2 = new ShoppingCart(user2);
		cart1.addItem(cartService.findOneProduct(4), 0.5);
		cart1.addItem(cartService.findOneProduct(5), 2);
		cart1.addItem(cartService.findOneProduct(6), 1);
		
		cart3=  new ShoppingCart(user3);
		cart1.addItem(cartService.findOneProduct(7), 2);
		cart1.addItem(cartService.findOneProduct(8), 2);
		cart1.addItem(cartService.findOneProduct(9), 3);
		
		cartService.saveShoppingCart(cart1);
		cartService.saveShoppingCart(cart2);
		cartService.saveShoppingCart(cart3);
		
		pay1 = new PayPalPayment("mario@gmail.com", "1234", cart1);
		pay2 = new CreditCardPayment("Peg Jeanbaptiste", "12358754", cart2);
		pay3 = new CashPayment(cart3);
		
		cartService.savePayment(pay1);
		cartService.savePayment(pay2);
		cartService.savePayment(pay3);
		
		return new ResponseEntity<String>("Done.", HttpStatus.OK);
	}

	@RequestMapping(value = "/cart/{shoppingCartId}", method = RequestMethod.GET)
	public ResponseEntity<String> getStringCartByGET(
			@PathVariable Long shoppingCartId) {
		ShoppingCart cart = cartService.findOneShoppingCart(shoppingCartId);
		if (cart != null) {
			return new ResponseEntity<String>(cart.toString(), HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Cart not found", HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/cart/json={shoppingCartId}", method = RequestMethod.GET)
	public ResponseEntity<ShoppingCart> getJsonCartByGET(
			@PathVariable Long shoppingCartId) {
		ShoppingCart cart = cartService.findOneShoppingCart(shoppingCartId);
		return new ResponseEntity<ShoppingCart>(cart, HttpStatus.OK);
	}

	@RequestMapping(value = "/product/{productId}", method = RequestMethod.GET)
	public ResponseEntity<String> getStringProductByGET(
			@PathVariable Long productId) {
		Product product = cartService.findOneProduct(productId);
		if (product != null) {
			return new ResponseEntity<String>(product.toString(), HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Product could not be found.",
					HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/product/cat={categoryId}", method = RequestMethod.GET)
	public ResponseEntity<String> getStringProductsInCategoryByGET(
			@PathVariable Long categoryId) {
		List<Category> categories = cartService.findCategoryByCode(categoryId);
		List<Product> products = cartService.findProductsFromCategory(categories.get(0));
		if (products != null) {
			String result ="Products: <br />";
			for (Product product : products){
				result += product.toString() + "<br />";
			}
			return new ResponseEntity<String>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("There are not products in this category.",
					HttpStatus.OK);
		}
	}

	/*
	 * Methods related to users
	 */

	@RequestMapping(value = "/user/register", method = RequestMethod.GET)
	public ModelAndView getUserCreatePage() {
		return new ModelAndView("user_create", "form", new UserCreateForm());
	}

	@RequestMapping(value = "/user/register", method = RequestMethod.POST)
	public String handleUserCreateForm(
			@ModelAttribute("form") UserCreateForm form) {
		userService.registerUser(form);
		return "Registered!";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView getLoginPage() {
		return new ModelAndView("login");
	}

	@RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
	public ResponseEntity<String> getStringUserByGET(@PathVariable Long userId) {
		User user = userService.findOneUser(userId);
		if (user != null) {
			return new ResponseEntity<String>(user.getLastName(), HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("User not found", HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/user/json={userId}", method = RequestMethod.GET)
	public ResponseEntity<User> getJsonUserByGET(@PathVariable Long userId) {
		User user = userService.findOneUser(userId);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ResponseEntity<String> insertUserByGET(
			@RequestParam(value = "desc", defaultValue = "") String description,
			@RequestParam(value = "fn", defaultValue = "") String firstName,
			@RequestParam(value = "ln", defaultValue = "") String lastName,
			@RequestParam(value = "un", defaultValue = "") String username,
			@RequestParam(value = "pass", defaultValue = "") String password) {
		userService.saveUser(new User(firstName, lastName, username, password));
		return new ResponseEntity<String>("Done.", HttpStatus.OK);
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<String> insertUserByPOST(
			@RequestParam(value = "desc", defaultValue = "") String description,
			@RequestParam(value = "fn", defaultValue = "") String firstName,
			@RequestParam(value = "ln", defaultValue = "") String lastName,
			@RequestParam(value = "un", defaultValue = "") String username,
			@RequestParam(value = "pass", defaultValue = "") String password) {
		userService.saveUser(new User(firstName, lastName, username, password));
		return new ResponseEntity<String>("Done.", HttpStatus.OK);
	}
}
