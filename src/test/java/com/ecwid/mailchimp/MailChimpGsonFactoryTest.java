/*
 * Copyright 2012 Ecwid, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ecwid.mailchimp;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.util.Date;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 *
 * @author Vasily Karyaev <v.karyaev@gmail.com>
 */
public class MailChimpGsonFactoryTest {
	
	private class TestObject extends MailChimpObject {
		@Field
		Date date;
		
		@Field(name = "email_address")
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
		assertEquals(json.entrySet().size(), 2);
		assertEquals(json.get("date").getAsString(), "1970-01-01 00:00:00");
		assertEquals(json.get("email_address").getAsString(), "pupkin@gmail.com");
		assertNull(json.get("unserializable"));
		
		json.addProperty("unserializable", "Unserializable");
		
		o = gson.fromJson(json, TestObject.class);
		assertEquals(o.date, new Date(0));
		assertEquals(o.email, "pupkin@gmail.com");
		assertNull(o.unserializable);
	}
}
