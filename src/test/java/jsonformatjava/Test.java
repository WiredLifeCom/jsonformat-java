package jsonformatjava;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.wiredlife.jsonformatjava.dba.SQLiteDBA;
import com.wiredlife.jsonformatjava.model.Data;
import com.wiredlife.jsonformatjava.model.Inventory;
import com.wiredlife.jsonformatjava.model.User;
import com.wiredlife.jsonformatjava.model.Zone;

public class Test {

	public static void main(String[] args) {
		StringBuilder builder = new StringBuilder();
		builder.append("{");
		// builder.append("\"data\": {");
		builder.append("\"user\":{");
		builder.append("\"username\":\"TestUser\",");
		builder.append("\"zones\":[");
		builder.append("{");
		builder.append("\"arrival\":\"2015-04-21T11:42:11.000+02:00\",");
		builder.append("\"departure\":\"2015-04-21T11:58:32.000+02:00\",");
		builder.append("\"latitude\":55.61592,");
		builder.append("\"longitude\":12.987113");
		builder.append("}");
		builder.append("],");
		builder.append("\"inventory\":{");
		builder.append("\"resources\":[");
		builder.append("\"Dirt\",");
		builder.append("\"Dirt\",");
		builder.append("\"Stone\"");
		builder.append("],");
		builder.append("\"items\":[");
		builder.append("\"DiamondPickaxe\",");
		builder.append("\"WoodenAxe\"");
		builder.append("]");
		builder.append("}");
		builder.append("},");
		builder.append("\"unload\":\"2015-04-21T13:04:54.000+02:00\"");
		// builder.append("}");
		builder.append("}");

		Data data = Data.fromJson(builder.toString());

		System.out.println(data);
		System.out.println(builder.toString());

		// Construct a Data object and convert it to JSON
		List<String> resources = new ArrayList<String>();
		resources.add("Dirt");
		resources.add("Dirt");
		resources.add("Stone");

		List<String> items = new ArrayList<String>();
		items.add("DiamondPickaxe");
		items.add("WoodenAxe");

		Inventory inventory = new Inventory();
		inventory.setResources(resources);
		inventory.setItems(items);

		Zone zone = new Zone();
		zone.setArrival(DateTime.parse("2015-04-21T11:42:11.000+02:00"));
		zone.setDeparture(DateTime.parse("2015-04-21T11:58:32.000+02:00"));
		zone.setLatitude(55.61592);
		zone.setLongitude(12.987113);

		List<Zone> zones = new ArrayList<Zone>();
		zones.add(zone);

		User user = new User();
		user.setUsername("TestUser");
		user.setZones(zones);
		user.setInventory(inventory);

		Data newData = new Data();
		newData.setUser(user);
		newData.setUnload(DateTime.parse("2015-04-21T13:04:54.000+02:00"));

		System.out.println(newData);
		System.out.println(Data.toJson(newData));

		if (builder.toString().equals(Data.toJson(newData))) {
			System.out.println("Is equals");
		}

		DateTime time1 = DateTime.parse("2015-04-21T11:42:11.000+02:00");
		DateTime time2 = DateTime.now();

		int result = time1.compareTo(time2);
		if (result == -1) {
			System.out.println("time1 is older than time2; expected outcome!");
		}

		SQLiteDBA dba = new SQLiteDBA("database.db");
		dba.addUnload(Data.fromJson(builder.toString()));

		List<String> contents = dba.getUnloads("TestUser");
		System.out.println(contents);

		dba.deleteUnloads("TestUser");

		List<String> contents2 = dba.getUnloads("TestUser");
		System.out.println(contents2);
	}
}
