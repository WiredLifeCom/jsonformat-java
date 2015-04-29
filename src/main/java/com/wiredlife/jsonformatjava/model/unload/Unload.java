package com.wiredlife.jsonformatjava.model.unload;

import org.joda.time.DateTime;

import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Unload {

	private User user;
	private DateTime unload;

	public Unload() {

	}

	public Unload(User user, DateTime unload) {
		this();
		this.user = user;
		this.unload = unload;
	}

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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.unload == null) ? 0 : this.unload.hashCode());
		result = prime * result + ((this.user == null) ? 0 : this.user.hashCode());
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
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Unload [user=");
		builder.append(this.user);
		builder.append(", unload=");
		builder.append(this.unload);
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
