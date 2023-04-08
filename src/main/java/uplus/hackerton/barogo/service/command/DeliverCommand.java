package uplus.hackerton.barogo.service.command;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import uplus.hackerton.barogo.domain.Deliver;
import uplus.hackerton.barogo.domain.DeliveryStatus;
import uplus.hackerton.barogo.domain.User;
import uplus.hackerton.barogo.domain.code.MaxWeight;

@Getter
@Builder
@ToString
public class DeliverCommand {

	private String loginName;

	private String loginEmail;

	private User requestUser;
	private String start;

	private String requestUserPhone;
	private String end;

	private String recipientUserPhone;
	private String content;

	private String product;
	private int price;

	private double distance;

	private LocalDateTime dueDate;

	private MaxWeight weight;

	private DeliveryStatus status;

	public Deliver toEntity() {
		return Deliver.builder()
			.requestUser(requestUser)
			.start(start)
			.end(end)
			.content(content)
			.product(product)
			.price(price)
			.distance(distance)
			.dueDate(dueDate)
			.weight(weight)
			.requestUserPhone(requestUserPhone)
			.recipientUserPhone(recipientUserPhone)
			.build();
	}

	public void setStatus(DeliveryStatus status) {
		this.status = status;
	}

	public void setRequestUser(User user) {
		this.requestUser = user;
	}

}
