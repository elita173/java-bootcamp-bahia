package finalProject.shopping.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "SHOPPING_CART")
public class ShoppingCart {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CART_ID")
	private long cartId;

	@ElementCollection
	@CollectionTable(name = "ITEM", joinColumns = @JoinColumn(name = "IN_CART_ID"))
	private List<Item> listOfItems;

	@Column(name = "TOTAL")
	private double total;

	@ManyToOne
	@JoinColumn(name="USER_ID")
	private User user;
	
	@OneToOne(mappedBy="shopCart")
	private Payment payment;
	
	protected ShoppingCart() {
	}

	public ShoppingCart(User user) {
		listOfItems = new ArrayList<Item>();
		total = 0;
		setUser(user);
	}

	private void setUser(User user) {
		this.user = user;
	}

	public void addItem(Item item) {
		listOfItems.add(item);
		total += item.getQuantity() * item.getProduct().getPrice();
	}

	public void addItem(Product product, double d) {
		listOfItems.add(new Item(product, d));
		total += d * product.getPrice();
	}

	public void removeItem(long l) {
		int i;
		for (i = 0; i < listOfItems.size(); i++) {
			if (listOfItems.get(i).getProduct().getCode() == l) {
				listOfItems.remove(i);
			}
		}
	}

	public Item getItem(int index) {
		if (index < listOfItems.size()) {
			return listOfItems.get(index);
		} else {
			return null;
		}
	}

	public double getTotal() {
		return total;
	}

	public Item getCheapestItem() {
		Product auxProduct;
		Item cheapestItem = null;
		double finalPrice;
		double cheapestPrice = Double.MAX_VALUE;

		for (Item actualItem : listOfItems) {
			auxProduct = actualItem.getProduct();
			if (auxProduct.isUnit()) {
				finalPrice = auxProduct.getPrice();
			} else {
				finalPrice = auxProduct.getPrice() * actualItem.getQuantity();
			}
			if (finalPrice < cheapestPrice) {
				cheapestPrice = actualItem.getProduct().getPrice();
				cheapestItem = actualItem;
			}
		}
		return cheapestItem;
	}

	public Item getMostExpensiveItem() {
		Product auxProduct;
		Item mostExpensiveItem = null;
		double finalPrice;
		double mostExpensivePrice = Double.MIN_VALUE;

		for (Item actualItem : listOfItems) {
			auxProduct = actualItem.getProduct();
			if (auxProduct.isUnit()) {
				finalPrice = auxProduct.getPrice();
			} else {
				finalPrice = auxProduct.getPrice() * actualItem.getQuantity();
			}
			if (finalPrice > mostExpensivePrice) {
				mostExpensivePrice = actualItem.getProduct().getPrice();
				mostExpensiveItem = actualItem;
			}
		}
		return mostExpensiveItem;
	}

	public String toString() {
		String output = "";
		for (Item actualItem : listOfItems) {
			output = output.concat(actualItem.toString());
		}
		return output;
	}
}
