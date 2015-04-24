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

	public void addResource(String resource) {
		this.resources.add(resource);
	}

	public void deleteResource(String resource) {
		this.resources.remove(this.resources.indexOf(resource));
	}

	public void addItem(String item) {
		this.items.add(item);
	}

	public void deleteItem(String item) {
		this.items.remove(this.resources.indexOf(item));
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
