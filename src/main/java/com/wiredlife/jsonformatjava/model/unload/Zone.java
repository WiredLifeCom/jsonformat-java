package com.wiredlife.jsonformatjava.model.unload;

import org.joda.time.DateTime;

public class Zone {

	private double latitude;
	private double longitude;
	private int radius;
	private String material;
	private int numberOfMaterialBlocks;

	private DateTime arrival;
	private DateTime departure;

	public Zone() {

	}

	public Zone(double latitude, double longitude, int radius, String material, int numberOfMaterialBlocks) {
		this();
		this.latitude = latitude;
		this.longitude = longitude;
		this.radius = radius;
		this.material = material;
		this.numberOfMaterialBlocks = numberOfMaterialBlocks;
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

	public int getRadius() {
		return this.radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public String getMaterial() {
		return this.material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public int getNumberOfMaterialBlocks() {
		return this.numberOfMaterialBlocks;
	}

	public void setNumberOfMaterialBlocks(int numberOfMaterialBlocks) {
		this.numberOfMaterialBlocks = numberOfMaterialBlocks;
	}

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(this.latitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(this.longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Zone other = (Zone) obj;
		if (Double.doubleToLongBits(this.latitude) != Double.doubleToLongBits(other.latitude))
			return false;
		if (Double.doubleToLongBits(this.longitude) != Double.doubleToLongBits(other.longitude))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Zone [latitude=");
		builder.append(this.latitude);
		builder.append(", longitude=");
		builder.append(this.longitude);
		builder.append(", radius=");
		builder.append(this.radius);
		builder.append(", material=");
		builder.append(this.material);
		builder.append(", numberOfMaterialBlocks=");
		builder.append(this.numberOfMaterialBlocks);
		builder.append(", arrival=");
		builder.append(this.arrival);
		builder.append(", departure=");
		builder.append(this.departure);
		builder.append("]");
		return builder.toString();
	}

}
