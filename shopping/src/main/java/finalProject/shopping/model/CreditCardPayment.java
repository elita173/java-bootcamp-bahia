package finalProject.shopping.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class CreditCardPayment extends Payment {
	@Column(name = "CLIENT_NAME")
	private String clientName;

	@Column(name = "CRED_CARD")
	private String creditCardNo;

	protected CreditCardPayment() {
	}

	public CreditCardPayment(String clientName, String creditCardNumber,
			ShoppingCart cart) {
		super();
		this.setClientName(clientName);
		this.setCreditCardNo(creditCardNumber);
		this.shopCart = cart;
		this.date = new Date();
	}

	@Override
	public double applyDiscount() {
		if (!shopCart.equals(null)) {
			return (shopCart.getTotal() * 0.9);
		} else {
			return 0;
		}
	}

	public String getCreditCardNo() {
		return creditCardNo;
	}

	public void setCreditCardNo(String creditCardNo) {
		this.creditCardNo = creditCardNo;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	@Override
	public String toString() {
		return date.toString() + "\nTransaction:"
				+ String.format("%06d", transactionNumber) + "\nClient: "
				+ "\nPaying with Credit card No." + creditCardNo + "\n"
				+ shopCart.toString() + "__________________________\nTotal: "
				+ String.format("%.2f", shopCart.getTotal())
				+ "\n__________________________\nWith discount: "
				+ String.format("%.2f", this.applyDiscount());
	}

}
