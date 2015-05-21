package com.wiredlife.jsonformatjava.test.model.unload;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.wiredlife.jsonformatjava.model.unload.User;

@RunWith(JUnit4.class)
public class UserUnitTest {

	@Test
	public void createWithDefaultConstructor() {
		User user = new User();
		
		assertNotNull(user);
	}
	
	@Test
	public void createWithOverloadedConstructor() {
		User user = new User("TestUser");
		
		assertNotNull(user);
	}
	
	@Test
	public void toStringTest() {
		User user = new User();
		
		assertNotNull(user.toString());
	}
	
}
