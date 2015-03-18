package finalProject.shopping.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CATEGORY")
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CAT_ID")
	private long code;

	@Column(name="CAT_NAME")
	private String name;

	protected Category() {
	}

	public Category(String name, int code, Category parent) {
		if (parent != null) {
			setCode(parent.getCode() * 100 + code);
		} else {
			setCode(code);
		}
		this.name = name;
	}

	private void setCode(long l) {
		this.code = l;
	}

	private long getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		int i = 0;
		long aux = code;
		while (aux > 0) {
			aux = aux / 100;
			i++;
		}
		return i;
	}

	public long getSubtreeUpperLimit(int maxLevel) {
		return (long) ((code + 1) * Math.pow(100, (double) (maxLevel - getLevel())));
	}

	public long getSubtreeLowerLimit(int maxLevel) {
		return (long) (code * Math.pow(100, (double) (maxLevel - getLevel())));
	}
}
