package backend.apiscart.model;

import java.io.Serial;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "order_registration",schema = "sch_purchase_order")
@Data
public class OrderRegistration {
	@Serial private static final long serialVersionUID = 4264632325800388398L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_order")
	private Long id;
	
	@Column(name = "creation_date")
	private Timestamp creationDate;
	
	@Column(name = "modification_date")
	private Timestamp modificationDate;
	
	private String customer;
	
	@Column(name = "order_status")
	private String orderStatus;
	
	@Column(name = "payment_method")
	private String paymentMethod;
	
	@Column(name = "payment_status")
	private String paymentStatus;
	
	private BigDecimal total;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	@ToString.Exclude
	private List<OrderDetails> orderDetails;

	public void setOrderDetails(List<OrderDetails> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public List<OrderDetails> getOrderDetails() {
		return orderDetails;
	}
}
