package finalProject.shopping.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class Item {
	@ManyToOne
	@JoinColumn(name = "CODE")
	private Product product;

	@Column(name = "QTY")
	private double quantity;

	protected Item() {
	}

	public Item(Product product, double d) {
		this.setProduct(product);
		this.setQuantity(d);
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public double getTotalPrice() {
		return product.getPrice() * quantity;
	}

	public String toString() {
		String output = "";

		output = output.concat(product.toString() + "\t\t x");

		if (product.isUnit()) {
			output = output.concat(String.format("%.0f", quantity));
		} else {
			output = output.concat(String.format("%.3f", quantity));
		}

		return (output.concat(String.format(" ... $%.2f \n", product.getPrice()
				* quantity)));
	}
}
