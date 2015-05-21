package com.wiredlife.jsonformatjava.model.status;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class OnlineStatus {

	private String username;
	private boolean isHome;
	private String ipAddress;

	public OnlineStatus() {
		
	}
	
	public OnlineStatus(String username, boolean isHome, String ipAddress) {
		this.username = username;
		this.isHome = isHome;
		this.ipAddress = ipAddress;
	}
	
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isHome() {
		return this.isHome;
	}

	public void setHome(boolean isHome) {
		this.isHome = isHome;
	}

	public String getIpAddress() {
		return this.ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OnlineStatus [username=");
		builder.append(username);
		builder.append(", isHome=");
		builder.append(isHome);
		builder.append(", ipAddress=");
		builder.append(ipAddress);
		builder.append("]");
		return builder.toString();
	}

	public static String toJson(OnlineStatus onlineStatus) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(onlineStatus);
	}

	public static OnlineStatus fromJson(String json) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.fromJson(json, OnlineStatus.class);
	}

}
