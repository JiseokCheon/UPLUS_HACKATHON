package uplus.hackerton.barogo.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DeliveryRequestStatus {

	WAITING("수락 대기 중"),
	ACCEPT("수락"),
	DECLINE("거절");

	private final String status;
}
