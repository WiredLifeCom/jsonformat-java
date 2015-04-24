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
