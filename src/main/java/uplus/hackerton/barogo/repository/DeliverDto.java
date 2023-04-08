package uplus.hackerton.barogo.repository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uplus.hackerton.barogo.domain.DeliveryStatus;
import uplus.hackerton.barogo.domain.code.MaxWeight;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DeliverDto {

	private Long id;
	private String requestUserName;
	private String requestUserPhoto;
	private String start;
	private String end;
	private String product;
	private String content;
	private LocalDateTime dueDate;
	private int price;
	private double distance; // unit : km
	private String requestUserPhone;
	private String recipientUserPhone;
	private DeliveryStatus deliveryStatus;
	private MaxWeight weight;
}
