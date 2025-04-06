package backend.apiscart.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "order_details",schema = "sch_purchase_order")
public class OrderDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_order_detail")
	private Long id;
	
	@ManyToOne
    @JoinColumn(name = "id_order")
    private OrderRegistration order;
	
	@Column(name = "id_product")
	private int idProduct;
	
	private int quantity;
	
	@Column(name = "price")
	private BigDecimal price;
	
	private BigDecimal subtotal;
}
