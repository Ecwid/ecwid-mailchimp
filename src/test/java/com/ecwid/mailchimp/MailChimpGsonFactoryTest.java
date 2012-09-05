/*
 *  MailChimp API Wrapper
 *  Copyright (c) 2012 Ecwid, Inc
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.ecwid.mailchimp;

import com.ecwid.mailchimp.annotation.MailChimpField;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Vasily Karyaev <v.karyaev@gmail.com>
 */
public class MailChimpGsonFactoryTest {
	
	private class TestObject {
		@MailChimpField 
		Date date;
		
		@MailChimpField(name = "email_address")
		String email;
		
		String unserializable;
	}
	
	@Test
	public void testSerialization() {
		Gson gson = MailChimpGsonFactory.createGson();
		
		TestObject o = new TestObject();
		o.email = "pupkin@gmail.com";
		o.date = new Date(0);
		o.unserializable = "Unserializable";
		
		JsonObject json = gson.toJsonTree(o).getAsJsonObject();
		assertEquals(2, json.entrySet().size());
		assertEquals("1970-01-01 00:00:00", json.get("date").getAsString());
		assertEquals("pupkin@gmail.com", json.get("email_address").getAsString());
		assertNull(json.get("unserializable"));
		
		json.addProperty("unserializable", "Unserializable");
		
		o = gson.fromJson(json, TestObject.class);
		assertEquals(new Date(0), o.date);
		assertEquals("pupkin@gmail.com", o.email);
		assertNull(o.unserializable);
	}
}
