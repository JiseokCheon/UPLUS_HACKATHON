package uplus.hackerton.barogo.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "d_delivery_requests")
@NoArgsConstructor
public class DeliveryRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Deliver deliver;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private User deliverUser;

	private int bidPrice;

	@Column(length = 20)
	private String deliverUserPhone;

	@Lob
	private String comment;

	private String transportType;

	@Enumerated(EnumType.STRING)
	private DeliveryRequestStatus requestStatus;


	public void changeToACCEPT() {
		this.requestStatus = DeliveryRequestStatus.ACCEPT;
	}

	public void changeToDECLINE() {
		this.requestStatus = DeliveryRequestStatus.DECLINE;
	}

	@Builder
	public DeliveryRequest(User deliverUser, Deliver deliver, int bidPrice, String deliverUserPhone,
		String comment, String transportType) {
		this.deliverUser = deliverUser;
		this.deliver = deliver;
		this.bidPrice = bidPrice;
		this.deliverUserPhone = deliverUserPhone;
		this.comment = comment;
		this.transportType = transportType;
		this.requestStatus = DeliveryRequestStatus.WAITING;
	}

}
