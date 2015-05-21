package com.wiredlife.jsonformatjava.test.model.status;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.wiredlife.jsonformatjava.model.status.OnlineStatus;
import com.wiredlife.jsonformatjava.model.unload.Unload;
import com.wiredlife.jsonformatjava.model.unload.User;
import com.wiredlife.jsonformatjava.model.unload.Zone;

@RunWith(JUnit4.class)
public class OnlineStatusUnitTest {

	@Test
	public void createWithDefaultConstructor() {
		OnlineStatus onlineStatus = new OnlineStatus();
		
		assertNotNull(onlineStatus);
	}
	
	@Test
	public void createWithOverloadedConstructor() {
		OnlineStatus onlineStatus = new OnlineStatus("TestUser", false, "1.2.3.4");
		
		assertNotNull(onlineStatus);
	}
	
	@Test
	public void toJson() {
		OnlineStatus onlineStatus = new OnlineStatus();
		
		String json = OnlineStatus.toJson(onlineStatus);
		
		assertNotNull(json);
	}
	
	@Test
	public void fromJson() {
		OnlineStatus onlineStatus = new OnlineStatus();
		
		String json = OnlineStatus.toJson(onlineStatus);
		
		OnlineStatus newOnlineStatus = OnlineStatus.fromJson(json);
		
		assertNotNull(onlineStatus);
	}
	
	@Test
	public void toStringTest() {
		OnlineStatus onlineStatus = new OnlineStatus();
		
		assertNotNull(onlineStatus.toString());
	}
	
}
