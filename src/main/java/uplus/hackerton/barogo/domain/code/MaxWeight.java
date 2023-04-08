package uplus.hackerton.barogo.domain.code;

public enum MaxWeight {

	UNDER_2("2KG 이하"), UNDER_5("5KG 이하"), UNDER_10("10KG 이하"), UNDER_30("30KG 이하");

	private final String description;

	MaxWeight(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

}
