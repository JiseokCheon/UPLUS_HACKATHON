package uplus.hackerton.barogo.service.command;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import uplus.hackerton.barogo.domain.Deliver;
import uplus.hackerton.barogo.domain.DeliveryRequest;
import uplus.hackerton.barogo.domain.DeliveryStatus;
import uplus.hackerton.barogo.domain.User;

@Getter
@Builder
@ToString
public class BidRequestCommand {

	private String loginName;

	private String loginEmail;

	private Long deliverId;

	private Deliver deliver;
	private User deliverUser;

	private int bidPrice;

	private String deliverUserPhone;

	private String comment;

	private String transportType;

	private DeliveryStatus status;

	public DeliveryRequest toEntity() {
		return DeliveryRequest.builder()
			.deliver(deliver)
			.deliverUser(deliverUser)
			.bidPrice(bidPrice)
			.deliverUserPhone(deliverUserPhone)
			.comment(comment)
			.transportType(transportType)
			.build();
	}

	public void setStatus(DeliveryStatus status) {
		this.status = status;
	}

	public void setDeliverUser(User user) {
		this.deliverUser = user;
	}

	public void setDeliver(Deliver deliver) {
		this.deliver = deliver;
	}

}
