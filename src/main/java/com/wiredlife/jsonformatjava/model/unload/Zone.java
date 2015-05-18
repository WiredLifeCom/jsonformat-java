package com.wiredlife.jsonformatjava.model.unload;

import org.joda.time.DateTime;

public class Zone {

	private double latitude;
	private double longitude;
	private int radius;
	private String material;

	private DateTime arrival;
	private DateTime departure;

	public Zone() {

	}

	public Zone(double latitude, double longitude, int radius, String material) {
		this();
		this.radius = radius;
		this.material = material;
		this.latitude = latitude;
		this.longitude = longitude;
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
		result = prime * result + ((this.arrival == null) ? 0 : this.arrival.hashCode());
		result = prime * result + ((this.departure == null) ? 0 : this.departure.hashCode());
		long temp;
		temp = Double.doubleToLongBits(this.latitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(this.longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((this.material == null) ? 0 : this.material.hashCode());
		result = prime * result + this.radius;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Zone)) {
			return false;
		}
		Zone other = (Zone) obj;
		if (this.arrival == null) {
			if (other.arrival != null) {
				return false;
			}
		} else if (!this.arrival.equals(other.arrival)) {
			return false;
		}
		if (this.departure == null) {
			if (other.departure != null) {
				return false;
			}
		} else if (!this.departure.equals(other.departure)) {
			return false;
		}
		if (Double.doubleToLongBits(this.latitude) != Double.doubleToLongBits(other.latitude)) {
			return false;
		}
		if (Double.doubleToLongBits(this.longitude) != Double.doubleToLongBits(other.longitude)) {
			return false;
		}
		if (this.material == null) {
			if (other.material != null) {
				return false;
			}
		} else if (!this.material.equals(other.material)) {
			return false;
		}
		if (this.radius != other.radius) {
			return false;
		}
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
		builder.append(", arrival=");
		builder.append(this.arrival);
		builder.append(", departure=");
		builder.append(this.departure);
		builder.append("]");
		return builder.toString();
	}

}
