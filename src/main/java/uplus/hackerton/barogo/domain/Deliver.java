package uplus.hackerton.barogo.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uplus.hackerton.barogo.domain.code.MaxWeight;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "d_delivers")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Deliver {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private User deliverUser;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private User requestUser;

	private String start;

	private String end;

	@Column(length = 100)
	private String product;

	@Lob
	private String content;

	private LocalDateTime dueDate;

	private int price;

	private double distance; // unit : km

	@Column(length = 20)
	private String requestUserPhone;

	@Column(length = 20)
	private String recipientUserPhone;

	@Enumerated(EnumType.STRING)
	private MaxWeight weight;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private DeliveryStatus deliveryStatus;

	@Builder
	public Deliver(User deliverUser, User requestUser, String start, String end,
		String product, String content, LocalDateTime dueDate, int price,
		double distance, String requestUserPhone, String recipientUserPhone,
		MaxWeight weight) {
		this.deliverUser = deliverUser;
		this.requestUser = requestUser;
		this.start = start;
		this.end = end;
		this.price = price;
		this.product = product;
		this.content = content;
		this.dueDate = dueDate;
		this.distance = distance;
		this.requestUserPhone = requestUserPhone;
		this.recipientUserPhone = recipientUserPhone;
		this.weight = weight;
		this.deliveryStatus = DeliveryStatus.REQUEST;
	}

	public void changeToBid() {
		if (this.deliveryStatus == DeliveryStatus.PROCESSING || this.deliveryStatus == DeliveryStatus.COMPLETE) {
			throw new IllegalArgumentException("변경하려는 요청은 이미 진행중입니다.");
		}
		this.deliveryStatus = DeliveryStatus.BID;
	}

	public void insertDeliveryUser(User deliveryUser) {
		this.deliverUser = deliveryUser;
	}

	public void completeDelivery() {
		this.deliveryStatus = DeliveryStatus.COMPLETE;
	}

}
