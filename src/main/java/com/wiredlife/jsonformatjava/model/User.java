package com.wiredlife.jsonformatjava.model;

import java.util.ArrayList;
import java.util.List;

public class User {

	private String username;
	private List<Zone> zones;
	private Inventory inventory;

	public User() {
		this.zones = new ArrayList<Zone>();
	}

	public User(String username, List<Zone> zones, Inventory inventory) {
		this();
		this.username = username;
		this.zones = zones;
		this.inventory = inventory;
	}

	public void addZone(Zone zone) {
		this.zones.add(zone);
	}

	public void deleteZone(Zone zone) {
		this.zones.remove(zone);
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Zone> getZones() {
		return this.zones;
	}

	public void setZones(List<Zone> zones) {
		this.zones = zones;
	}

	public Inventory getInventory() {
		return this.inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.inventory == null) ? 0 : this.inventory.hashCode());
		result = prime * result + ((this.username == null) ? 0 : this.username.hashCode());
		result = prime * result + ((this.zones == null) ? 0 : this.zones.hashCode());
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
		if (!(obj instanceof User)) {
			return false;
		}
		User other = (User) obj;
		if (this.inventory == null) {
			if (other.inventory != null) {
				return false;
			}
		} else if (!this.inventory.equals(other.inventory)) {
			return false;
		}
		if (this.username == null) {
			if (other.username != null) {
				return false;
			}
		} else if (!this.username.equals(other.username)) {
			return false;
		}
		if (this.zones == null) {
			if (other.zones != null) {
				return false;
			}
		} else if (!this.zones.equals(other.zones)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [username=");
		builder.append(this.username);
		builder.append(", zones=");
		builder.append(this.zones);
		builder.append(", inventory=");
		builder.append(this.inventory);
		builder.append("]");
		return builder.toString();
	}

}
