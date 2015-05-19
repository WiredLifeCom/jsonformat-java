package com.wiredlife.jsonformatjava.test;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.wiredlife.jsonformatjava.dba.DBA;
import com.wiredlife.jsonformatjava.model.unload.Unload;
import com.wiredlife.jsonformatjava.model.unload.User;
import com.wiredlife.jsonformatjava.model.unload.Zone;

public class Test {

	public static void main(String[] args) {
		StringBuilder builder = new StringBuilder();
		builder.append("{");
		// builder.append("\"data\": {");
		builder.append("\"user\":{");
		builder.append("\"username\":\"bejbejpomp\"");
		builder.append("},");
		builder.append("\"zones\":[");
		builder.append("{");
		builder.append("\"latitude\":55.61592,");
		builder.append("\"longitude\":12.987113");
		builder.append("\"radius:30\",");
		builder.append("\"material\":\"Dirt\",");
		builder.append("\"arrival\":\"2015-04-21T11:42:11.000+02:00\",");
		builder.append("\"departure\":\"2015-04-21T11:58:32.000+02:00\"");
		builder.append("}");
		builder.append("],");
		builder.append("\"materials\":[");
		builder.append("\"Dirt\",");
		builder.append("\"Dirt\",");
		builder.append("\"Stone\"");
		builder.append("],");
		builder.append("\"unload\":\"2015-04-21T13:04:54.000+02:00\"");
		// builder.append("}");
		builder.append("}");

		System.out.println(builder.toString());

		Unload data = Unload.fromJson(builder.toString());

		System.out.println(data);
		System.out.println(builder.toString());

		// Construct a Data object and convert it to JSON
		List<String> materials = new ArrayList<String>();
		materials.add("Dirt");
		materials.add("Dirt");
		materials.add("Stone");

		Zone zone = new Zone();
		zone.setLatitude(55.61592);
		zone.setLongitude(12.987113);
		zone.setRadius(20);
		zone.setMaterial("Dirt");
		zone.setArrival(DateTime.parse("2015-04-21T11:42:11.000+02:00"));
		zone.setDeparture(DateTime.parse("2015-04-21T11:58:32.000+02:00"));

		Zone zone2 = new Zone();
		zone2.setLatitude(32.42154);
		zone2.setLongitude(14.15231);
		zone2.setRadius(80);
		zone2.setMaterial("Wood");
		zone2.setArrival(DateTime.now());
		zone2.setDeparture(DateTime.now());

		List<Zone> zones = new ArrayList<Zone>();
		zones.add(zone);
		zones.add(zone2);

		User user = new User();
		user.setUsername("bejbejpomp");

		Unload unload = new Unload();
		unload.setUser(user);

		unload.setZones(zones);
		unload.setMaterials(materials);

		unload.setUnload(DateTime.parse("2015-04-21T13:04:54.000+02:00"));

		System.out.println(unload);
		System.out.println(Unload.toJson(unload));

		if (builder.toString().equals(Unload.toJson(unload))) {
			System.out.println("Is equals");
		}

		DateTime time1 = DateTime.parse("2015-04-21T11:42:11.000+02:00");
		DateTime time2 = DateTime.now();

		int result = time1.compareTo(time2);
		if (result == -1) {
			System.out.println("time1 is older than time2; expected outcome!");
		}

		DBA dba = new DBA("database.sqlite");
		dba.addUnload(unload);

		System.out.println(dba.getUnloads("bejbejpomp"));

		List<Unload> dbaUnloads = dba.getUnloads("bejbejpomp");
		System.out.println(Unload.toJson(dbaUnloads.get(dbaUnloads.size() - 1)));
		// System.out.println(dba.getUnloads(data.getUser().getUsername()));
		// dba.addUnload(Data.fromJson(builder.toString()));
		//
		// List<String> contents = dba.getUnloads("TestUser");
		// System.out.println(contents);
		//
		// dba.deleteUnloads("TestUser");
		//
		// List<String> contents2 = dba.getUnloads("TestUser");
		// System.out.println(contents2);
	}
}
