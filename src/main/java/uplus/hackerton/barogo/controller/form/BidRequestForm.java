package uplus.hackerton.barogo.controller.form;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import uplus.hackerton.barogo.service.command.BidRequestCommand;

@Data
public class BidRequestForm {

	@NotBlank
	private String loginName;
	private String loginEmail;

	private Long deliverId;

	private int bidPrice;

	private String deliverUserPhone;

	private String comment;

	private String transportType;

	public BidRequestCommand toCommand() {
		return BidRequestCommand.builder()
			.loginName(loginName)
			.loginEmail(loginEmail)
			.deliverId(deliverId)
			.bidPrice(bidPrice)
			.deliverUserPhone(deliverUserPhone)
			.comment(comment)
			.transportType(transportType)
			.build();
	}
}
