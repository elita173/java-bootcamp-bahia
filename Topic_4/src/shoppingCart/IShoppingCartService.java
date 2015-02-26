package shoppingCart;

/**
 * Interface to a Shopping Cart Service It is assumed one cart per client: when
 * a new cart is created, it deletes the existent one and creates a new one.
 * When the client executes the payment, a new order is created and stored; and
 * a new cart is created.
 * 
 * @author Elisabet Arratia
 */

public interface IShoppingCartService {
	/**
	 * Creates a new cart
	 *
	 * @param userId
	 *            a String with the client's ID for which a new cart is created.
	 */
	public void createCart(String userId);

	/**
	 * Represents the payment of the products in the cart: the order with the
	 * payment is stored and a new cart is created
	 *
	 * @param payment
	 *            The transaction being executed: it must contain the shopping
	 *            cart
	 */
	public void payAndCheckout(Payment payment);

	/**
	 * Adds a product and its quantity to the cart of a specific user
	 *
	 * @param userId
	 *            a String with the client's ID.
	 * @param idProduct
	 *            The ID of the product that is to be added to the cart of the
	 *            user with ID <code>idUser</code>
	 * @param quantity
	 *            The quantity of the product added to the cart
	 */
	public void addItem(String userId, int idProduct, float quantity);

	/**
	 * Removes a product from the cart of a specific user
	 *
	 * @param userId
	 *            a String with the client's ID.
	 * @param idProduct
	 *            the ID of the Product to be removed from the cart of the user
	 *            with ID <code>idUser</code>
	 */
	public void removeItem(String userId, int idProduct);

	/**
	 * Gets the Shopping cart of a specific user with ID <code>idUser</code>
	 *
	 * @param userId
	 *            a String with the client's ID.
	 */
	public ShoppingCart getShoppingCart(String userId);

	/**
	 * Gets the number of Shopping carts in the DB
	 */
	public int numberOfShoppingCarts();

	/**
	 * Gets the number of transactions in the DB.
	 */
	public int numberOfTransactions();
}
