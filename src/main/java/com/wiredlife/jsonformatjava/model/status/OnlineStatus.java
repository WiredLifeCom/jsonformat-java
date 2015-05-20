package com.wiredlife.jsonformatjava.model.status;

import com.google.gson.Gson;

public class OnlineStatus {

	private String username;
	private boolean isHome;
	private String ipAddress;

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

	public static String toJson(OnlineStatus onlineStatus) {
		Gson gson = new Gson();
		return gson.toJson(onlineStatus);
	}

	public static OnlineStatus fromJson(String json) {
		Gson gson = new Gson();
		return gson.fromJson(json, OnlineStatus.class);
	}

}
