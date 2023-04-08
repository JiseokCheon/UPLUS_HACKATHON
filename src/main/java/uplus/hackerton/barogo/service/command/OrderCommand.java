package uplus.hackerton.barogo.service.command;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import uplus.hackerton.barogo.domain.Order;
import uplus.hackerton.barogo.domain.OrderStatus;
import uplus.hackerton.barogo.domain.code.MaxWeight;

@Getter
@Builder
@ToString
public class OrderCommand {

	private String loginName;
	private String senderAddress;
	private String senderPhone;
	private String receiverAddress;
	private String receiverPhone;
	private String goods;
	private int wishPrice;
	private String transportType;

	private MaxWeight weight;

	private OrderStatus status;

	public Order toEntity() {
		return Order.builder()
			.loginName(loginName)
			.senderAddress(senderAddress)
			.senderPhone(senderPhone)
			.receiverAddress(receiverAddress)
			.receiverPhone(receiverPhone)
			.goods(goods)
			.wishPrice(wishPrice)
			.transportType(transportType)
			.weight(weight)
			.status(status)
			.build();
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}
}
