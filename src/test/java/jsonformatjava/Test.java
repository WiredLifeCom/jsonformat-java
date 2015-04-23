package jsonformatjava;

import com.wiredlife.jsonformatjava.model.Data;

public class Test {

	public static void main(String[] args) {
		StringBuilder builder = new StringBuilder();
		builder.append("{");
		// builder.append("\"data\": {");
		builder.append("\"user\": {");
		builder.append("\"username\": \"TestUser\",");
		builder.append("\"zones\": [");
		builder.append("{");
		builder.append("\"arrival\": \"2015-04-21T11:42:11+00:00\",");
		builder.append("\"departure\": \"2015-04-21T11:58:32+00:00\",");
		builder.append("\"latitude\": 55.615920,");
		builder.append("\"longitude\": 12.987113");
		builder.append("}");
		builder.append("],");
		builder.append("\"inventory\": {");
		builder.append("\"resources\": [");
		builder.append("\"Dirt\",");
		builder.append("\"Dirt\",");
		builder.append("\"Stone\"");
		builder.append("],");
		builder.append("\"items\": [");
		builder.append("\"DiamondPickaxe\",");
		builder.append("\"WoodenAxe\"");
		builder.append("]");
		builder.append("}");
		builder.append("},");
		builder.append("\"unload\" : \"2015-04-21T13:04:54+00:00\"");
		// builder.append("}");
		builder.append("}");

		System.out.println(builder.toString());

		Data data = Data.fromJson(builder.toString());

		System.out.println(data);
	}
}
