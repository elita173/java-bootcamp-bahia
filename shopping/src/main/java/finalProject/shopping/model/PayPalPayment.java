package finalProject.shopping.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "PP_PAYMENT")
@DiscriminatorValue("1")
public class PayPalPayment extends Payment {

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "PASS")
	private String password;

	protected PayPalPayment() {
	}

	public PayPalPayment(String email, String password, ShoppingCart cart) {
		super();
		this.setEmail(email);
		this.setPassword(password);
		this.shopCart = cart;
		this.date = new Date();
	}

	@Override
	public double applyDiscount() {
		if (!shopCart.equals(null)) {
			Item cheapest = shopCart.getCheapestItem();
			double discount = cheapest.getProduct().getPrice();
			if (!cheapest.getProduct().isUnit()) {
				discount = discount * cheapest.getQuantity();
			}
			return (shopCart.getTotal() - discount);
		} else {
			return 0;
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return date.toString() + "\nTransaction:"
				+ String.format("%06d", transactionNumber) + "\nClient: "
				+ "\nPaying via PayPal. E-mail: " + email + "\n"
				+ shopCart.toString() + "__________________________\nTotal: "
				+ String.format("%.2f", shopCart.getTotal())
				+ "\n__________________________\nWith discount: "
				+ String.format("%.2f", this.applyDiscount());
	}

}
