package finalProject.shopping.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PRODUCT")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CODE")
	private long code;

	@OneToOne
	@JoinColumn(name = "CAT_ID")
	Category category;

	@Column(name = "NAME")
	private String name;

	@Column(name = "DESC")
	private String description;

	@Column(name = "PRICE")
	private double price;

	@Column(name = "IS_UNIT")
	private boolean isUnit;

	protected Product() {
	}

	/*
	 * The parameter 'isUnit' indicates whether the product is sold in units or
	 * in parts of a unit (kilograms, liters, etc)
	 */
	public Product(String name, double price, boolean isUnit) {
		this.name = name;
		this.price = price;
		this.isUnit = isUnit;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String desc) {
		this.description = desc;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public double getPrice() {
		return price;
	}

	public Category getCategory() {
		return category;
	}

	public long getCode() {
		return code;
	}

	public boolean isUnit() {
		return isUnit;
	}

	public String toString() {
		return ("- " + name + "... $" + String.format("%.2f\n", price));
	}
}
