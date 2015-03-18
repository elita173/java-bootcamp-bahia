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
	private float quantity;

	protected Item() {
	}

	public Item(Product product, float quantity) {
		this.setProduct(product);
		this.setQuantity(quantity);
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public float getQuantity() {
		return quantity;
	}

	public void setQuantity(float quantity) {
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
