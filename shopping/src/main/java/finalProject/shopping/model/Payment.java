package finalProject.shopping.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PAYMENT")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "PAY_TYPE", discriminatorType = DiscriminatorType.INTEGER)
public abstract class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected int transactionNumber;
	
	@Column(name="DATE")
	protected Date date;
	
	@OneToOne
	@JoinColumn(name="CART_ID")
	protected ShoppingCart shopCart;

	public Payment() {
	}

	public abstract double applyDiscount();

	public abstract String toString();

	double totalPayment() {
		return shopCart.getTotal();
	}
}
