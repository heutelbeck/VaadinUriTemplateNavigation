package org.apples.mvpviews;

public class HouseEvents {

	public static interface HouseEvent {
	}

	public static class HouseEntered implements HouseEvent {
		private final String streetId;
		private final String houseId;

		public HouseEntered(String streetId, String houseId) {
			super();
			this.streetId = streetId;
			this.houseId = houseId;
		}

		public String getStreetId() {
			return streetId;
		}

		public String getHouseId() {
			return houseId;
		}
	}

	public static class HouseCountClicked implements HouseEvent {

	}
}
