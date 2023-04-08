package uplus.hackerton.barogo.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uplus.hackerton.barogo.domain.code.MaxWeight;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "d_order")
public class Order extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String loginName;
	private String senderAddress;
	private String senderPhone;
	private String receiverAddress;
	private String receiverPhone;
	private String goods;
	private int wishPrice;
	private String transportType;

	@Enumerated(EnumType.STRING)
	private MaxWeight weight;

	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	@Builder
	public Order(String loginName, String senderAddress, String senderPhone, String receiverAddress,
		String receiverPhone,
		String goods, int wishPrice, String transportType, MaxWeight weight, OrderStatus status) {
		this.loginName = loginName;
		this.senderAddress = senderAddress;
		this.senderPhone = senderPhone;
		this.receiverAddress = receiverAddress;
		this.receiverPhone = receiverPhone;
		this.goods = goods;
		this.wishPrice = wishPrice;
		this.transportType = transportType;
		this.weight = weight;
		this.status = status;
	}
}
