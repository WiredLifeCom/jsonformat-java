package com.wiredlife.jsonformatjava.test.model.unload;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.wiredlife.jsonformatjava.model.unload.Unload;
import com.wiredlife.jsonformatjava.model.unload.User;
import com.wiredlife.jsonformatjava.model.unload.Zone;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class UnloadUnitTest {

	@Test
	public void createWithDefaultConstructor() {
		Unload unload = new Unload();
		
		assertNotNull(unload);
	}
	
	@Test
	public void createWithOverloadedConstructor() {
		User user = new User();
		List<Zone> zones = new ArrayList<Zone>();
		List<String> materials = new ArrayList<String>();
		DateTime unloadTime = DateTime.now();
		
		Unload unload = new Unload(user, zones, materials, unloadTime);
		
		assertNotNull(unload);
	}
	
	@Test
	public void addZone() {
		Unload unload = new Unload();
		
		Zone zone = new Zone();
		unload.addZone(zone);
		
		assertEquals(1, unload.getZones().size());
	}
	
	@Test
	public void deleteZone() {
		Unload unload = new Unload();
		
		Zone zone = new Zone();
		unload.addZone(zone);
		unload.deleteZone(zone);
		
		assertEquals(0, unload.getZones().size());
	}
	
	@Test
	public void addMaterial() {
		Unload unload = new Unload();
		
		unload.addMaterial("Dirt");
		
		assertEquals(1, unload.getMaterials().size());
	}
	
	@Test
	public void deleteMaterial() {
		Unload unload = new Unload();
		
		unload.addMaterial("Dirt");
		unload.deleteMaterial("Dirt");
		
		assertEquals(0, unload.getMaterials().size());
	}
	
	@Test
	public void toJson() {
		Unload unload = new Unload();
		
		String json = Unload.toJson(unload);
		
		assertNotNull(json);
	}
	
	@Test
	public void fromJson() {
		Unload unload = new Unload();
		
		String json = Unload.toJson(unload);
		
		Unload newUnload = Unload.fromJson(json);
		
		assertEquals(unload, newUnload);
	}
	
	@Test
	public void toStringTest() {
		Unload unload = new Unload();
		
		assertNotNull(unload.toString());
	}
	
}
