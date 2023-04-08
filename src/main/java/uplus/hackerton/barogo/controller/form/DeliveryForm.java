package uplus.hackerton.barogo.controller.form;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import uplus.hackerton.barogo.domain.code.MaxWeight;
import uplus.hackerton.barogo.service.command.DeliverCommand;

@Data
public class DeliveryForm {

	@NotBlank
	private String loginName;
	private String loginEmail;

	@NotBlank(message = "출발지주소를 입력해주세요")
	private String start;

	private String requestUserPhone;
	private String end;

	private String recipientUserPhone;
	private String content;
	private String product;

	@NotNull
	private MaxWeight weight;

	private String startLat;
	private String startLon;
	private String endLat;
	private String endLon;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime dueDate;

	@NotNull
	@Range(min = 1000, max = 1000000)
	private int price;

	@NotNull
	private double distance; // unit : km

	public DeliverCommand toCommand() {
		return DeliverCommand.builder()
			.loginName(loginName)
			.loginEmail(loginEmail)
			.start(start)
			.requestUserPhone(requestUserPhone)
			.end(end)
			.recipientUserPhone(recipientUserPhone)
			.content(content)
			.product(product)
			.weight(weight)
			.price(price)
			.distance(distance)
			.dueDate(dueDate)
			.build();
	}
}
