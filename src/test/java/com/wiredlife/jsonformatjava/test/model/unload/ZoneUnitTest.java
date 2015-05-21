package com.wiredlife.jsonformatjava.test.model.unload;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.wiredlife.jsonformatjava.model.unload.Unload;
import com.wiredlife.jsonformatjava.model.unload.User;
import com.wiredlife.jsonformatjava.model.unload.Zone;

@RunWith(JUnit4.class)
public class ZoneUnitTest {

	@Test
	public void createWithDefaultConstructor() {
		Zone zone = new Zone();
		
		assertNotNull(zone);
	}
	
	@Test
	public void createWithOverloadedConstructor() {
		Zone zone = new Zone(55.4521, 16.2512, 30, "Dirt", 50);
		
		assertNotNull(zone);
	}
	
	@Test
	public void toStringTest() {
		Zone zone = new Zone();
		
		assertNotNull(zone.toString());
	}
	
}
