package finalProject.shopping.model;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class CashPayment extends Payment {

	protected CashPayment() {
	}

	public CashPayment(ShoppingCart cart) {
		super();
		this.shopCart = cart;
		this.date = new Date();
	}

	@Override
	public double applyDiscount() {
		if (!shopCart.equals(null)) {
			Item mostExpensive = shopCart.getMostExpensiveItem();
			double discount = mostExpensive.getProduct().getPrice() * 0.9f;
			if (!mostExpensive.getProduct().isUnit()) {
				discount = discount * mostExpensive.getQuantity();
			}
			return (shopCart.getTotal() - discount);
		} else {
			return 0;
		}
	}

	@Override
	public String toString() {
		return date.toString() + "\nTransaction:"
				+ String.format("%06d", transactionNumber) + "\nClient: "
				+ "\nPaying in Cash\n" + shopCart.toString()
				+ "__________________________\nTotal: "
				+ String.format("%.2f", shopCart.getTotal())
				+ "\n__________________________\nWith discount: "
				+ String.format("%.2f", this.applyDiscount());
	}
}
