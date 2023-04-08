package uplus.hackerton.barogo.controller.form;

import lombok.Getter;
import uplus.hackerton.barogo.domain.Deliver;
import uplus.hackerton.barogo.domain.DeliveryRequest;

@Getter
public class PerformDeliverView {

	private final Long deliverId;
	private final String requestUserName;

	private final String start;

	private final String requestUserPhone;

	private final String end;

	private final String recipientUserPhone;

	private final int originPrice;
	private final int bidPrice;

	private final String product;

	private final String deliverStatus;

	private final String deliverRequestStatus;

	public PerformDeliverView(DeliveryRequest deliveryRequest) {
		Deliver deliver = deliveryRequest.getDeliver();

		this.deliverId = deliver.getId();
		this.requestUserName = deliver.getRequestUser().getName();
		this.start = deliver.getStart();
		this.requestUserPhone = deliver.getRequestUserPhone();
		this.end = deliver.getEnd();
		this.recipientUserPhone = deliver.getRecipientUserPhone();
		this.originPrice = deliver.getPrice();
		this.bidPrice = deliveryRequest.getBidPrice();
		this.product = deliver.getProduct();
		this.deliverStatus = deliver.getDeliveryStatus().getStatus();
		this.deliverRequestStatus = deliveryRequest.getRequestStatus().getStatus();
	}
}
