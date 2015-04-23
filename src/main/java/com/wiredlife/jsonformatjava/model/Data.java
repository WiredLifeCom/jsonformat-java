package com.wiredlife.jsonformatjava.model;

import com.google.gson.Gson;

public class Data {

	private User user;
	private String unload;

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUnload() {
		return this.unload;
	}

	public void setUnload(String unload) {
		this.unload = unload;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Data [user=");
		builder.append(this.user);
		builder.append(", unload=");
		builder.append(this.unload);
		builder.append("]");
		return builder.toString();
	}

	public static String toJson(Data data) {
		Gson gson = new Gson();
		return gson.toJson(data);
	}

	public static Data fromJson(String json) {
		Gson gson = new Gson();
		return gson.fromJson(json, Data.class);
	}

}
