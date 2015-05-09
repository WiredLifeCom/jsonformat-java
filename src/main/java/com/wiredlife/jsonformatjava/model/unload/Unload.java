package com.wiredlife.jsonformatjava.model.unload;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Unload {

	private User user;

	private List<Zone> zones;
	private List<String> materials;

	private DateTime unload;

	public Unload() {
		this.zones = new ArrayList<Zone>();
		this.materials = new ArrayList<String>();
	}

	public Unload(User user, List<Zone> zones, List<String> materials, DateTime unload) {
		this();
		this.user = user;
		this.zones = zones;
		this.materials = materials;
		this.unload = unload;
	}

	public void addZone(Zone zone) {
		this.zones.add(zone);
	}

	public void deleteZone(Zone zone) {
		this.zones.remove(zone);
	}

	public void addMaterial(String material) {
		this.materials.add(material);
	}

	public void deleteMaterial(String material) {
		this.materials.remove(material);
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<String> getMaterials() {
		return this.materials;
	}

	public void setMaterials(List<String> materials) {
		this.materials = materials;
	}

	public List<Zone> getZones() {
		return this.zones;
	}

	public void setZones(List<Zone> zones) {
		this.zones = zones;
	}

	public void setUnload(DateTime unload) {
		this.unload = unload;
	}

	public DateTime getUnload() {
		return this.unload;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.materials == null) ? 0 : this.materials.hashCode());
		result = prime * result + ((this.unload == null) ? 0 : this.unload.hashCode());
		result = prime * result + ((this.user == null) ? 0 : this.user.hashCode());
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
		if (!(obj instanceof Unload)) {
			return false;
		}
		Unload other = (Unload) obj;
		if (this.materials == null) {
			if (other.materials != null) {
				return false;
			}
		} else if (!this.materials.equals(other.materials)) {
			return false;
		}
		if (this.unload == null) {
			if (other.unload != null) {
				return false;
			}
		} else if (!this.unload.equals(other.unload)) {
			return false;
		}
		if (this.user == null) {
			if (other.user != null) {
				return false;
			}
		} else if (!this.user.equals(other.user)) {
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
		builder.append("Unload [user=");
		builder.append(this.user);
		builder.append(", unload=");
		builder.append(this.unload);
		builder.append(", zones=");
		builder.append(this.zones);
		builder.append(", materials=");
		builder.append(this.materials);
		builder.append("]");
		return builder.toString();
	}

	public static String toJson(Unload unload) {
		Gson gson = Converters.registerDateTime(new GsonBuilder()).create();
		return gson.toJson(unload);
	}

	public static Unload fromJson(String json) {
		Gson gson = Converters.registerDateTime(new GsonBuilder()).create();
		return gson.fromJson(json, Unload.class);
	}

}
