package uplus.hackerton.barogo.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DeliveryStatus {

	REQUEST("요청 완료"),
	BID("입찰 중"),
	PROCESSING("진행중"),
	COMPLETE("완료");

	private final String status;
}
