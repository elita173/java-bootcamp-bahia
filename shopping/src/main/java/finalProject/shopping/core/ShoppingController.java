package finalProject.shopping.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import finalProject.shopping.model.CashPayment;
import finalProject.shopping.model.Category;
import finalProject.shopping.model.CreditCardPayment;
import finalProject.shopping.model.Item;
import finalProject.shopping.model.PayPalPayment;
import finalProject.shopping.model.Payment;
import finalProject.shopping.model.Product;
import finalProject.shopping.model.ShoppingCart;
import finalProject.shopping.model.User;

@RestController
public class ShoppingController {
	@Autowired
	ShoppingServiceImpl cartService;

	@Autowired
	UserService userService;

	public ShoppingController() {
	}

	/* Methods to get info about the shopping carts */
	@RequestMapping(value = "/cart/{shoppingCartId}", method = RequestMethod.GET)
	public ResponseEntity<String> getStringCartByGET(
			@PathVariable Long shoppingCartId) {
		ShoppingCart cart = cartService.findOneShoppingCart(shoppingCartId);
		if (cart != null) {
			String response = "<div>" + cart.toString();
			if (cart.getPayment() == null) {
				response += "</div><div><a href='/additem'>Add an item</a></div><div><a href='/remitem'>Remove an item</a></div>"
						+ "<div><a href='/choosepay?cartid="
						+ cart.getId()
						+ "'>Pay</a></div>";
			}
			return new ResponseEntity<String>(response, HttpStatus.OK);
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

	@RequestMapping(value = "/cart/user={userId}", method = RequestMethod.GET)
	public ResponseEntity<String> getStringCartFromUserByGET(
			@PathVariable Long userId) {
		ShoppingCart cart = cartService.findCurrentShoppingCartFromUser(userId);
		if (cart != null) {
			return new ResponseEntity<String>(cart.toString(), HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("There was an error.",
					HttpStatus.BAD_REQUEST);
		}
	}

	/* Methods to get info about the products */
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

	@RequestMapping(value = "/product/cat={categoryCode}", method = RequestMethod.GET)
	public ResponseEntity<String> getStringProductsInCategoryByGET(
			@PathVariable Long categoryCode) {
		List<Category> categories = cartService
				.findCategoryByCode(categoryCode);
		if (categories.size() == 1) {
			List<Product> products = cartService
					.findProductsFromCategory(categories.get(0));
			if (products != null) {
				String result = "Products: <br><ul>";
				for (Product product : products) {
					result += "<li>" + product.toString() + "</li>";
				}
				result += "</ul>";
				return new ResponseEntity<String>(result, HttpStatus.OK);
			} else {
				return new ResponseEntity<String>(
						"There are not products in this category.",
						HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<String>(
					"There was an error with the given category.",
					HttpStatus.BAD_REQUEST);
		}
	}
	
	/*Methods related with users*/
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

	@RequestMapping(value = "/newuser", method = RequestMethod.POST)
	public ResponseEntity<String> insertUserByPOST(
			@RequestParam(value = "firstname", defaultValue = "") String firstName,
			@RequestParam(value = "lastname", defaultValue = "") String lastName,
			@RequestParam(value = "username", defaultValue = "") String username,
			@RequestParam(value = "password", defaultValue = "") String password) {
		User user = userService.findUserByUsername(username);
		if (user == null) {
			user = new User(firstName, lastName, username, password);
			ShoppingCart cart = new ShoppingCart(user);
			userService.saveUser(user);
			cartService.saveShoppingCart(cart);
			return new ResponseEntity<String>(
					"Your user has been added. You can now <A HREF='/login'>login</A>.",
					HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>(
					"Username already exists. Use a differente username. <A HREF='/register'>Try again</A>.",
					HttpStatus.BAD_REQUEST);
		}
	}

	/* Methods to add and remove items form a cart */
	@RequestMapping(value = "/_additem", method = RequestMethod.POST)
	public ResponseEntity<String> addItemByPOST(
			Authentication authentication,
			@RequestParam(value = "cartId", defaultValue = "0") long cartId,
			@RequestParam(value = "productId", defaultValue = "0") long productId,
			@RequestParam(value = "qty", defaultValue = "0") double qty) {

		ShoppingCart cart = cartService.findOneShoppingCart(cartId);
		Product product = cartService.findOneProduct(productId);
		UserDetails currentUser = (UserDetails) authentication.getPrincipal();

		if (cart != null) {
			if (cart.getOwner().getUsername()
					.equals(currentUser.getUsername())) {
				if ((product != null) && (qty > 0)) {
					cartService.addItemToCart(new Item(product, qty), cart);
					return new ResponseEntity<String>(
							"The item has been added to your cart. You can <A HREF='/additem'>add another item</A>.",
							HttpStatus.CREATED);
				} else {
					return new ResponseEntity<String>(
							"Product or quantity are not correct.",
							HttpStatus.BAD_REQUEST);
				}
			} else {
				return new ResponseEntity<String>(
						"You are not the owner of this cart.",
						HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<String>("Shopping cart not found.",
					HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/_remitem", method = RequestMethod.POST)
	public ResponseEntity<String> removeItemByPOST(
			Authentication authentication,
			@RequestParam(value = "cartId", defaultValue = "0") long cartId,
			@RequestParam(value = "productId", defaultValue = "0") long productId) {

		ShoppingCart cart = cartService.findOneShoppingCart(cartId);
		Product product = cartService.findOneProduct(productId);
		UserDetails currentUser = (UserDetails) authentication.getPrincipal();

		if (cart != null) {
			if (cart.getOwner().getUsername()
					.equals(currentUser.getUsername())) {
				if (product != null) {
					cartService.removeItemFromCart(productId, cart);
					return new ResponseEntity<String>(
							"The item has been removed from your cart. You can <A HREF='/remitem'>remove another item</A>.",
							HttpStatus.CREATED);
				} else {
					return new ResponseEntity<String>(
							"Product ID is not correct.",
							HttpStatus.BAD_REQUEST);
				}
			} else {
				return new ResponseEntity<String>(
						"You are not the owner of this cart.",
						HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<String>("Shopping cart not found.",
					HttpStatus.BAD_REQUEST);
		}
	}

	/*Methods related to payment*/
	@RequestMapping(value = "/showpayment", method = RequestMethod.POST)
	public ModelAndView payRedirect(Authentication authentication,
			@RequestParam(value = "payment", required = true) String payment,
			@RequestParam(value = "cartid", required = true) long cartid) {

		ShoppingCart cart = cartService.findOneShoppingCart(cartid);
		UserDetails currentUser = (UserDetails) authentication.getPrincipal();

		if (cart.getOwner().getUsername().equals(currentUser.getUsername())) {
			ModelAndView mav = new ModelAndView("pay");
			mav.addObject("cartid", cartid);
			mav.addObject("payment", payment);
			mav.addObject("items", cart.getItems());
			mav.addObject("total", String.format("%.2f", cart.getTotal()));
			return mav;
		} else {
			ModelAndView mav = new ModelAndView("error");
			mav.addObject("errorCode", HttpStatus.BAD_REQUEST);
			mav.addObject("errorMessage", "You are not the owner of this cart");
			return mav;
		}
	}

	@RequestMapping(value = "/addpayment", method = RequestMethod.POST)
	public ModelAndView addPayment(
			Authentication authentication,
			@RequestParam(value = "payment", required = true) String paymentType,
			@RequestParam(value = "cartid", required = true) long cartid,
			@RequestParam(value = "clientemail", required = false) String clientEmail,
			@RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "clientname", required = false) String clientName,
			@RequestParam(value = "creditcard", required = false) String cardNumber) {

		ShoppingCart cart = cartService.findOneShoppingCart(cartid);
		UserDetails currentUser = (UserDetails) authentication.getPrincipal();
		Payment payment = null;

		if (cart.getOwner().getUsername().equals(currentUser.getUsername())) {
			if (cart.getPayment() == null) {
				ModelAndView mav = new ModelAndView("paymentcomplete");
				if (paymentType.equals("cash")) {
					payment = new CashPayment(cart);
				} else if (paymentType.equals("credit")) {
					payment = new CreditCardPayment(clientName, cardNumber,
							cart);
				} else if (paymentType.equals("paypal")) {
					payment = new PayPalPayment(clientEmail, password, cart);
				}

				cartService.savePayment(payment);
				cartService.saveShoppingCart(new ShoppingCart(cart.getOwner()));
				
				mav.addObject("items", cart.toString());
				mav.addObject("total", cart.getTotal());
				mav.addObject("discount", payment.applyDiscount());
				return mav;
			} else {
				ModelAndView mav = new ModelAndView("error");
				mav.addObject("errorCode", HttpStatus.BAD_REQUEST);
				mav.addObject("errorMessage",
						"This order has already been paid");
				return mav;
			}
		} else {
			ModelAndView mav = new ModelAndView("error");
			mav.addObject("errorCode", HttpStatus.BAD_REQUEST);
			mav.addObject("errorMessage", "You are not the owner of this cart");
			return mav;
		}
	}

	@RequestMapping(value = "/product/load", method = RequestMethod.GET)
	public ResponseEntity<String> loadBaby() {
		User user1, user2, user3;
		Category level1, level2, level3;
		ShoppingCart cart1, cart2, cart3;
		Payment pay1, pay2, pay3;
		Product product;

		// Create the categories and some products
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

		// Create some users
		user1 = new User("Mario", "Spagnoli", "mspagnoli", "1234");
		user2 = new User("Peg", "Jeanbaptiste", "pjbaptiste", "1234");
		user3 = new User("Kiara", "Boyles", "kboyles", "1234");

		userService.saveUser(user1);
		userService.saveUser(user2);
		userService.saveUser(user3);

		// Create shopping carts with payments
		cart1 = new ShoppingCart(user1);
		cart1.addItem(cartService.findOneProduct(1), 1);
		cart1.addItem(cartService.findOneProduct(2), 2.3);
		cart1.addItem(cartService.findOneProduct(3), 3);

		cart2 = new ShoppingCart(user2);
		cart2.addItem(cartService.findOneProduct(4), 0.5);
		cart2.addItem(cartService.findOneProduct(5), 2);
		cart2.addItem(cartService.findOneProduct(6), 1);

		cart3 = new ShoppingCart(user3);
		cart3.addItem(cartService.findOneProduct(7), 2);
		cart3.addItem(cartService.findOneProduct(8), 2);
		cart3.addItem(cartService.findOneProduct(9), 3);

		cartService.saveShoppingCart(cart1);
		cartService.saveShoppingCart(cart2);
		cartService.saveShoppingCart(cart3);

		pay1 = new PayPalPayment("mario@gmail.com", "1234", cart1);
		pay2 = new CreditCardPayment("Peg Jeanbaptiste", "12358754", cart2);
		pay3 = new CashPayment(cart3);

		cartService.savePayment(pay1);
		cartService.savePayment(pay2);
		cartService.savePayment(pay3);

		// Create carts without payment
		cart1 = new ShoppingCart(user1);
		cart1.addItem(cartService.findOneProduct(10), 1);
		cart1.addItem(cartService.findOneProduct(11), 2);
		cart1.addItem(cartService.findOneProduct(7), 3);

		cart2 = new ShoppingCart(user2);
		cart2.addItem(cartService.findOneProduct(5), 0.5);
		cart2.addItem(cartService.findOneProduct(9), 2);
		cart2.addItem(cartService.findOneProduct(4), 1);

		cart3 = new ShoppingCart(user3);
		cart3.addItem(cartService.findOneProduct(2), 2.3);
		cart3.addItem(cartService.findOneProduct(8), 2);
		cart3.addItem(cartService.findOneProduct(1), 3);

		cartService.saveShoppingCart(cart1);
		cartService.saveShoppingCart(cart2);
		cartService.saveShoppingCart(cart3);

		return new ResponseEntity<String>("Done.", HttpStatus.OK);
	}
}
