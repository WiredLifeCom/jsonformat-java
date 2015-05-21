package com.wiredlife.jsonformatjava.test.model.status;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.wiredlife.jsonformatjava.model.status.OnlineStatus;

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
