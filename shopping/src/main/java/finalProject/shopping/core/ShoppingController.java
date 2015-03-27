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

/**
 * This is the controller of the Shopping Cart Service. Once the application has
 * started, you can go to the following URLs without authentication:
 * <ul>
 * <li><a href="http://localhost:8080/">localhost:8080/</a> to start</li>
 * <li><a
 * href="http://localhost:8080/product/load">localhost:8080/product/load</a> to
 * initialize the database</li>
 * <li><a href="http://localhost:8080/login">localhost:8080/login</a> to login</li>
 * <li><a href="http://localhost:8080/register">localhost:8080/register</a> to
 * register a new user</li>
 * </ul>
 * And once you are logged in, you can go to the following URLs:
 * <ul>
 * <li><a
 * href="http://localhost:8080/cart/additem">localhost:8080/cart/additem</a> to
 * add an item to a cart</li>
 * <li><a
 * href="http://localhost:8080/cart/remitem">localhost:8080/cart/remitem</a> to
 * remove an item from a cart</li>
 * <li><a
 * href="http://localhost:8080/cart/choosepay/1">localhost:8080/cart/choosepay
 * /{cartid}</a> to begin the process to pay an order (shopping cart with ID
 * {cartid})</li>
 * <li><a
 * href="http://localhost:8080/payment/1">localhost:8080/payment/{paymentid}</a>
 * to show the payment with ID paymentid</li>
 * <li><a href="http://localhost:8080/user/1">localhost:8080/user/{userid}</a>
 * to show the user with ID userid</li>
 * <li><a
 * href="http://localhost:8080/user/json=1">localhost:8080/user/json={userid
 * }</a> to show the user as a json object with ID userid</li>
 * <li><a href="http://localhost:8080/cart/1">localhost:8080/cart/{cartid}</a>
 * to show the cart with ID cartid</li>
 * <li><a
 * href="http://localhost:8080/cart/json=1">localhost:8080/cart/json={cartid
 * }</a> to show the cart as a json object with ID cartid</li>
 * <li><a
 * href="http://localhost:8080/product/1">localhost:8080/product/{productid}</a>
 * to show the product with ID productid</li>
 * <li><a
 * href="http://localhost:8080/product/cat=10">localhost:8080/product/cat=
 * {categorycode}</a> to show the products that belong to category with code
 * categorycode</li>
 * </ul>
 * 
 * @author Elisabet Arratia
 *
 */
@RestController
public class ShoppingController {
	@Autowired
	ShoppingServiceImpl cartService;

	@Autowired
	UserService userService;

	public ShoppingController() {
	}

	/* Methods to get info about the shopping carts */
	/**
	 * It shows the content of a given shopping cart. If the cart is not related
	 * to a payment, i.e., it has not yet been paid, the view shows the
	 * following options:
	 * <ul>
	 * <li>Add an item to the current cart</li>
	 * <li>Remove an item from the current cart</li>
	 * <li>Start the process to pay the items</li>
	 * </ul>
	 * 
	 * @param shoppingCartId
	 *            the ID of the cart being showed.
	 * @return The list of items and some options if the
	 *         <code>shoppingCartId</code> is correct, otherwise it shows an
	 *         error message.
	 */
	@RequestMapping(value = "/cart/{shoppingCartId}", method = RequestMethod.GET)
	public ResponseEntity<String> getStringCartByGET(
			@PathVariable long shoppingCartId) {
		ShoppingCart cart = cartService.findOneShoppingCart(shoppingCartId);
		if (cart != null) {
			String response = "<div>" + cart.toString();
			if (cart.getPayment() == null) {
				response += "</div><div><a href='/cart/additem'>Add an item</a></div><div><a href='/cart/remitem'>Remove an item</a></div>"
						+ "<div><a href='/choosepay?cartid="
						+ cart.getId()
						+ "'>Pay</a></div>";
			}
			return new ResponseEntity<String>(response, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Cart not found", HttpStatus.OK);
		}
	}

	/**
	 * It shows the content of a given shopping cart as a json object.
	 * 
	 * @param shoppingCartId
	 *            the ID of the cart being showed.
	 * @return the given shopping cart as a json object.
	 */
	@RequestMapping(value = "/cart/json={shoppingCartId}", method = RequestMethod.GET)
	public ResponseEntity<ShoppingCart> getJsonCartByGET(
			@PathVariable long shoppingCartId) {
		ShoppingCart cart = cartService.findOneShoppingCart(shoppingCartId);
		return new ResponseEntity<ShoppingCart>(cart, HttpStatus.OK);
	}

	/**
	 * It shows the content of the current cart of the given user with ID
	 * <code>userId</code>. The current cart is the one that does not have a
	 * payment associated. It is assumed that each user has exactly one current
	 * cart. If there is a chance that one user has more than two current carts,
	 * this method will take as valid the first one it finds. If the user does
	 * not have a current cart, it means there was an error inserting carts
	 * inthe DB, or the user does not exists.
	 * 
	 * @param userId
	 *            The ID of the user whose current cart is showed.
	 * @return The list of items and some options if the <code>userId</code> is
	 *         correct, otherwise it shows an error message.
	 */
	@RequestMapping(value = "/cart/user={userId}", method = RequestMethod.GET)
	public ResponseEntity<String> getStringCartFromUserByGET(
			@PathVariable long userId) {
		ShoppingCart cart = cartService.findCurrentShoppingCartFromUser(userId);
		if (cart != null) {
			return new ResponseEntity<String>(
					"<div>"
							+ cart.toString()
							+ "</div><div><a href='/cart/additem'>Add an item</a></div><div><a href='/cart/remitem'>Remove an item</a></div>"
							+ "<div><a href='/choosepay?cartid=" + cart.getId()
							+ "'>Pay</a></div>", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("There was an error.",
					HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * It shows a form where the user can add items to a cart
	 * 
	 * @return the view.
	 */
	@RequestMapping(value = "/cart/additem")
	public ModelAndView addItem() {
		ModelAndView mav = new ModelAndView("additem");
		return mav;
	}

	/**
	 * It shows a form where the user can remove items from a cart
	 * 
	 * @return the view.
	 */
	@RequestMapping(value = "/cart/remitem")
	public ModelAndView remItem() {
		ModelAndView mav = new ModelAndView("remitem");
		return mav;
	}

	/**
	 * It shows the view where the user must insert data according to the
	 * payment type chosen
	 * 
	 * @return the view.
	 */
	@RequestMapping(value = "/cart/pay")
	public ModelAndView pay() {
		ModelAndView mav = new ModelAndView("pay");
		return mav;
	}

	/**
	 * It shows the view where the user must choose a payment type to pay the
	 * items in the cart given by <code>cartId</code>.
	 * 
	 * @param cartId
	 *            the ID of the user
	 * @return the view.
	 */
	@RequestMapping(value = "/cart/choosepay/{cartId}")
	public ModelAndView choosePay(@PathVariable long cartId) {
		ModelAndView mav = new ModelAndView("choosepay");
		mav.addObject("cartid", cartId);
		return mav;
	}

	/**
	 * It shows the view with information about a completed transaction: items
	 * in the cart, total and total with discount.
	 * 
	 * @return the view
	 */
	@RequestMapping(value = "/payment/paymentcomplete")
	public ModelAndView paymentComplete() {
		ModelAndView mav = new ModelAndView("paymentcomplete");
		return mav;
	}

	/* Methods to get info about the products */
	/**
	 * It shows the information about a product given by <code>productId</code>.
	 * 
	 * @param productId
	 *            The ID of the product being showed.
	 * @return information about a product if it exists.
	 */
	@RequestMapping(value = "/product/{productId}", method = RequestMethod.GET)
	public ResponseEntity<String> getStringProductByGET(
			@PathVariable long productId) {
		Product product = cartService.findOneProduct(productId);
		if (product != null) {
			return new ResponseEntity<String>(product.toString(), HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Product could not be found.",
					HttpStatus.OK);
		}
	}

	/**
	 * It shows all the products that belong to a given category. There is a
	 * category tree, in this case with 3 levels. Each level has children, which
	 * are also categories themselves. A product belongs to exactly one category
	 * in the lower level: i.e., one of the leaves of the tree. This method
	 * shows all the products belonging to the subtree of the given category.
	 * 
	 * @param categoryCode
	 *            The Code of the category.
	 * @return all the products belonging to the category with code
	 *         <code>categoryCode</code>, if the code is not correct, it shows
	 *         an error message.
	 */
	@RequestMapping(value = "/product/cat={categoryCode}", method = RequestMethod.GET)
	public ResponseEntity<String> getStringProductsInCategoryByGET(
			@PathVariable long categoryCode) {
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

	/* Methods related with users */
	/**
	 * Shows data of a given User.
	 * 
	 * @param userId
	 *            The ID of the user being showed.
	 * @return Data of a given User. If the user does not exist, it shows a
	 *         message.
	 */
	@RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
	public ResponseEntity<String> getStringUserByGET(@PathVariable long userId) {
		User user = userService.findOneUser(userId);
		if (user != null) {
			return new ResponseEntity<String>(user.toString(), HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("User not found", HttpStatus.OK);
		}
	}

	/**
	 * Shows data of a given User in json format.
	 * 
	 * @param userId
	 *            The ID of the user being showed.
	 * @return Data of a given User in json format. If the user does not exist,
	 *         it shows a message.
	 */
	@RequestMapping(value = "/user/json={userId}", method = RequestMethod.GET)
	public ResponseEntity<User> getJsonUserByGET(@PathVariable long userId) {
		User user = userService.findOneUser(userId);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	/**
	 * It receives data from a form with all the parameters needed to create a
	 * new User. If the username provided already exists, it asks the user to
	 * change it. It also creates a new empty shopping cart for the user.
	 * 
	 * @param firstName
	 *            first name of the user
	 * @param lastName
	 *            last name of the user
	 * @param username
	 *            username of the user
	 * @param password
	 *            password of the user
	 * @return a message to indicate if the user was created or not.
	 */
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
	/**
	 * It adds an item to a given shopping cart. If the user requesting this
	 * method is not the owner of the shopping cart, it does not add the given
	 * Item. If the product already exists in the cart, it adds the quantities.
	 * The following will return an error message:
	 * <ul>
	 * <li>The cart doesn't exist</li>
	 * <li>The product doesn't exist</li>
	 * <li>The quantity is zero or less</li>
	 * <li>The requesting user is not the owner of the cart</li>
	 * <li>The cart has been paid</li>
	 * </ul>
	 * 
	 * @param authentication
	 *            object to obtain the user logged in
	 * @param cartId
	 *            the ID of the cart where the item will be added
	 * @param productId
	 *            the ID of the product to be added
	 * @param qty
	 *            the quantity of product to be added
	 * @return A message indicating if the request was successful.
	 */
	@RequestMapping(value = "/cart/_additem", method = RequestMethod.POST)
	public ResponseEntity<String> addItemByPOST(
			Authentication authentication,
			@RequestParam(value = "cartId", defaultValue = "0") long cartId,
			@RequestParam(value = "productId", defaultValue = "0") long productId,
			@RequestParam(value = "qty", defaultValue = "0") double qty) {

		ShoppingCart cart = cartService.findOneShoppingCart(cartId);
		Product product = cartService.findOneProduct(productId);
		UserDetails currentUser = (UserDetails) authentication.getPrincipal();

		if (cart != null) {
			if (cart.getOwner().getUsername().equals(currentUser.getUsername())) {
				if (cart.getPayment() == null) {
					if ((product != null) && (qty > 0)) {
						cartService.addItemToCart(new Item(product, qty), cart);
						return new ResponseEntity<String>(
								"The item has been added to your cart. You can <A HREF='/cart/additem'>add another item</A>.",
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
				return new ResponseEntity<String>(
						"You cannot add an item to a cart that has already been paid.",
						HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<String>("Shopping cart not found.",
					HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * It removes an item from a given shopping cart. If the user requesting
	 * this method is not the owner of the shopping cart, it does not remove the
	 * given Item. It does not indicate an error if the product is not in the
	 * cart. The following will return an error message:
	 * <ul>
	 * <li>The cart doesn't exist</li>
	 * <li>The product doesn't exist</li>
	 * <li>The requesting user is not the owner of the cart</li>
	 * <li>The cart has been paid</li>
	 * </ul>
	 * 
	 * @param authentication
	 *            object to obtain the user logged in
	 * @param cartId
	 *            ID of the cart from where the item is to be removed
	 * @param productId
	 *            ID of the product to be removed
	 * @return A message indicating if the request was successful.
	 */
	@RequestMapping(value = "/cart/_remitem", method = RequestMethod.POST)
	public ResponseEntity<String> removeItemByPOST(
			Authentication authentication,
			@RequestParam(value = "cartId", defaultValue = "0") long cartId,
			@RequestParam(value = "productId", defaultValue = "0") long productId) {

		ShoppingCart cart = cartService.findOneShoppingCart(cartId);
		Product product = cartService.findOneProduct(productId);
		UserDetails currentUser = (UserDetails) authentication.getPrincipal();

		if (cart != null) {
			if (cart.getOwner().getUsername().equals(currentUser.getUsername())) {
				if (cart.getPayment() == null) {
					if (product != null) {
						cartService.removeItemFromCart(productId, cart);
						return new ResponseEntity<String>(
								"The item has been removed from your cart. You can <A HREF='/cart/remitem'>remove another item</A>.",
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
				return new ResponseEntity<String>(
						"You cannot remove an item from a cart that has already been paid.",
						HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<String>("Shopping cart not found.",
					HttpStatus.BAD_REQUEST);
		}
	}

	/* Methods related to payment */
	/**
	 * It receives <code>payment</code> and <code>cartid</code> from a form. It
	 * will get the object ShoppingCart from the DB and compare its owner with
	 * the user logged in. If they are note the same, it will show an error. It
	 * also shows the item in the cart and the total amount to pay.
	 * 
	 * @param authentication
	 *            object to obtain the user logged in
	 * @param payment
	 *            a string with the type of payment chosen by the user (cash,
	 *            Paypal or credit card)
	 * @param cartid
	 *            the ID of the cart that will be paid
	 * @return a view with information about the cart, or a message in case of
	 *         error.
	 */
	@RequestMapping(value = "/payment/showpayment", method = RequestMethod.POST)
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

	/**
	 * It receives a payment type, the ID of a shopping cart and data related to
	 * the payment type:
	 * <ul>
	 * <li>If the payment type is 'cash', it does not receive extra data</li>
	 * <li>If the payment type is 'credit', it receives the name of the client
	 * and the credit card number</li>
	 * <li>If the payment type is PayPal, it receives the username and the
	 * password of the client</li>
	 * </ul>
	 * 
	 * If the given cart is related to an object Payment, it indicates an error.
	 * It means that the order has already been paid, so the user cannot pay it
	 * again. If the user logged in is not the owner of the given cart, it
	 * indicates an error.
	 * 
	 * @param authentication
	 *            object to obtain the user logged in
	 * @param paymentType
	 *            chosen payment type
	 * @param cartid
	 *            ID of the cart
	 * @param clientEmail
	 *            Client's e-mail
	 * @param password
	 *            Client's password
	 * @param clientName
	 *            Client's name
	 * @param cardNumber
	 *            Credit card number
	 * @return Data of the order being paid, or an error message if the order
	 *         has already been paid or the user logged in is not the owner of
	 *         the cart.
	 */
	@RequestMapping(value = "/payment/addpayment", method = RequestMethod.POST)
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

	/**
	 * It shows the data of a given payment as a json object.
	 * 
	 * @param paymentId
	 *            the ID of the payment being showed.
	 * @return the given payment as a json object.
	 */
	@RequestMapping(value = "/payment/{paymentId}", method = RequestMethod.GET)
	public ResponseEntity<Payment> getJsonPaymentByGET(
			@PathVariable long paymentId) {
		Payment payment = cartService.findOnePayment(paymentId);
		return new ResponseEntity<Payment>(payment, HttpStatus.OK);
	}

	/**
	 * It loads some examples. Categories: (Code - Category)
	 * <ul>
	 * <li>10 - Food</li>
	 * <li>1000 - Canned</li>
	 * <li>100000 - Tuna&amp;SeaFood</li>
	 * <li>1001 - Meat&amp;Poultry</li>
	 * <li>100100 - Pork</li>
	 * <li>100101 - Beef</li>
	 * <li>1002 - Dairy&amp;Cheese</li>
	 * <li>100200 - Cheese</li>
	 * <li>1003 - Fruit&amp;Vegetables</li>
	 * <li>100300 - Fruit</li>
	 * <li>1004 - Condiments</li>
	 * <li>100400 - Salt</li>
	 * <li>1005 - Grain&amp;Pasta</li>
	 * <li>100500 - Rice</li>
	 * <li>1006 - Snacks</li>
	 * <li>100600 - Chips</li>
	 * <li>11 - Beverages</li>
	 * <li>1100 - Infusions</li>
	 * <li>110000 - Yerba</li>
	 * <li>1101 - No-Alcohol</li>
	 * <li>110100 - Soft-Drinks</li>
	 * <li>1102 - Alcohol</li>
	 * <li>110200 - Beer</li>
	 * </ul>
	 * 
	 * Products:
	 * <ul>
	 * <li>1- La Campagnola Tuna</li>
	 * <li>2- Paladini Ham</li>
	 * <li>3- Paty Hamburger x4</li>
	 * <li>4- Cremon Cheese</li>
	 * <li>5- Orange</li>
	 * <li>6- Celusal Salt 1/2Kg</li>
	 * <li>7- Mocovi Rice 1/2kg</li>
	 * <li>8- Pringles Chips x360gr</li>
	 * <li>9- Amanda Yerba</li>
	 * <li>10- Coca Cola 2.25lt</li>
	 * <li>11- Heineken Beer 1lt</li>
	 * </ul>
	 * 
	 * Clients:
	 * <ul>
	 * <li>Mario Spagnoli, mspagnoli, pass: 1234</li>
	 * <li>Peg Jeanbaptiste, pjbaptiste, pass: 1234</li>
	 * <li>Kiara Boyles, kboyles, pass: 1234</li>
	 * </ul>
	 * 
	 * Each one of these users has one paid cart, and one current cart.
	 * 
	 * @return a message to indicate the items had been added
	 */
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
