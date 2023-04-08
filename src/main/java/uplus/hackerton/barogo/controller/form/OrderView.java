package uplus.hackerton.barogo.controller.form;

import lombok.Getter;
import lombok.ToString;
import uplus.hackerton.barogo.domain.Order;
import uplus.hackerton.barogo.domain.OrderStatus;
import uplus.hackerton.barogo.domain.code.MaxWeight;

@Getter
@ToString
public class OrderView {

	private final String loginName;
	private final String senderAddress;
	private final String senderPhone;
	private final String receiverAddress;
	private final String receiverPhone;
	private final String goods;
	private final int wishPrice;
	private final String transportType;
	private final MaxWeight weight;
	private final OrderStatus status;

	public OrderView(Order order) {
		this.loginName = order.getLoginName();
		this.senderAddress = order.getSenderAddress();
		this.senderPhone = order.getSenderPhone();
		this.receiverAddress = order.getReceiverAddress();
		this.receiverPhone = order.getReceiverPhone();
		this.goods = order.getGoods();
		this.wishPrice = order.getWishPrice();
		this.transportType = order.getTransportType();
		this.weight = order.getWeight();
		this.status = order.getStatus();
	}
}
