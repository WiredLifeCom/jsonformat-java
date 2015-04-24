package com.wiredlife.jsonformatjava.model;

import java.util.ArrayList;
import java.util.List;

public class Inventory {

	private List<String> resources;
	private List<String> items;

	public Inventory() {
		this.resources = new ArrayList<String>();
		this.items = new ArrayList<String>();
	}

	public Inventory(List<String> resources, List<String> items) {
		this();
		this.resources = resources;
		this.items = items;
	}

	public void addResource(String resource) {
		this.resources.add(resource);
	}

	public void deleteResource(String resource) {
		this.resources.remove(resource);
	}

	public void addItem(String item) {
		this.items.add(item);
	}

	public void deleteItem(String item) {
		this.items.remove(item);
	}

	public List<String> getResources() {
		return this.resources;
	}

	public void setResources(List<String> resources) {
		this.resources = resources;
	}

	public List<String> getItems() {
		return this.items;
	}

	public void setItems(List<String> items) {
		this.items = items;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.items == null) ? 0 : this.items.hashCode());
		result = prime * result + ((this.resources == null) ? 0 : this.resources.hashCode());
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
		if (!(obj instanceof Inventory)) {
			return false;
		}
		Inventory other = (Inventory) obj;
		if (this.items == null) {
			if (other.items != null) {
				return false;
			}
		} else if (!this.items.equals(other.items)) {
			return false;
		}
		if (this.resources == null) {
			if (other.resources != null) {
				return false;
			}
		} else if (!this.resources.equals(other.resources)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Inventory [resources=");
		builder.append(this.resources);
		builder.append(", items=");
		builder.append(this.items);
		builder.append("]");
		return builder.toString();
	}

}
