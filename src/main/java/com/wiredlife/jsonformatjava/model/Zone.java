package com.wiredlife.jsonformatjava.model;

import org.joda.time.DateTime;

public class Zone {

	private DateTime arrival;
	private DateTime departure;

	private double latitude;
	private double longitude;

	public DateTime getArrival() {
		return this.arrival;
	}

	public void setArrival(DateTime arrival) {
		this.arrival = arrival;
	}

	public DateTime getDeparture() {
		return this.departure;
	}

	public void setDeparture(DateTime departure) {
		this.departure = departure;
	}

	public double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Zone [arrival=");
		builder.append(this.arrival);
		builder.append(", departure=");
		builder.append(this.departure);
		builder.append(", latitude=");
		builder.append(this.latitude);
		builder.append(", longitude=");
		builder.append(this.longitude);
		builder.append("]");
		return builder.toString();
	}

}
