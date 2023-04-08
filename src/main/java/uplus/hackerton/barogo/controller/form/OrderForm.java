package uplus.hackerton.barogo.controller.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import lombok.Data;
import uplus.hackerton.barogo.domain.code.MaxWeight;
import uplus.hackerton.barogo.service.command.OrderCommand;

@Data
public class OrderForm {

	@NotBlank
	private String loginName;

	@NotBlank(message = "출발지주소를 입력해주세요")
	private String senderAddress;
	private String senderPhone;
	private String receiverAddress;
	private String receiverPhone;
	private String goods;
	@NotNull
	@Range(min = 1000, max = 1000000)
	private int wishPrice;
	private String transportType;
	@NotNull
	private MaxWeight weight;

	public OrderCommand toCommand() {
		return OrderCommand.builder()
			.loginName(loginName)
			.senderAddress(senderAddress)
			.senderPhone(senderPhone)
			.receiverAddress(receiverAddress)
			.receiverPhone(receiverPhone)
			.goods(goods)
			.wishPrice(wishPrice)
			.transportType(transportType)
			.weight(weight)
			.build();
	}
}
