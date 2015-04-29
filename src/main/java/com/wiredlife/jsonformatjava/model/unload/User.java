package com.wiredlife.jsonformatjava.model.unload;

import java.util.ArrayList;
import java.util.List;

public class User {

	private String username;
	private List<Zone> zones;
	private List<String> materials;

	public User() {
		this.zones = new ArrayList<Zone>();
		this.materials = new ArrayList<String>();
	}

	public User(String username, List<Zone> zones, List<String> materials) {
		this();
		this.username = username;
		this.zones = zones;
		this.materials = materials;
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

	public List<String> getMaterials() {
		return this.materials;
	}

	public void setMaterials(List<String> materials) {
		this.materials = materials;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.materials == null) ? 0 : this.materials.hashCode());
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
		if (this.materials == null) {
			if (other.materials != null) {
				return false;
			}
		} else if (!this.materials.equals(other.materials)) {
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
		builder.append(", materials=");
		builder.append(this.materials);
		builder.append("]");
		return builder.toString();
	}

}
