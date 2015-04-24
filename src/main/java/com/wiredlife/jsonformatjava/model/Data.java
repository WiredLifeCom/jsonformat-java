package com.wiredlife.jsonformatjava.model;

import org.joda.time.DateTime;

import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Data {

	private User user;
	private DateTime unload;

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public DateTime getUnload() {
		return this.unload;
	}

	public void setUnload(DateTime unload) {
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
		Gson gson = Converters.registerDateTime(new GsonBuilder()).create();
		return gson.toJson(data);
	}

	public static Data fromJson(String json) {
		Gson gson = Converters.registerDateTime(new GsonBuilder()).create();
		return gson.fromJson(json, Data.class);
	}

}
