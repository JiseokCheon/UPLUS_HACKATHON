package uplus.hackerton.barogo.domain.code;

import java.util.Arrays;
import java.util.List;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TransportTypeFactory {

	private static final List<TransportType> transportTypes
		= Arrays.asList(
		new TransportType("WALKING", "도보"),
		new TransportType("CAR", "자동차"),
		new TransportType("BIKE", "오토바이")
	);

	public static List<TransportType> getInstance() {
		return transportTypes;
	}

}
