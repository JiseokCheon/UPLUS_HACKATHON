package uplus.hackerton.barogo.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.redis.core.RedisHash;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
@RedisHash("DeliverList")
public class DeliverList implements Serializable {

	private Long id;
	private String requestUserName;
	private String requestUserPhoto;
	private String start;
	private String end;
	private String product;
	private String content;
	private LocalDateTime dueDate;
	private int price;
	private double distance;
	private String requestUserPhone;
	private String recipientUserPhone;
	private DeliveryStatus deliveryStatus;

}
