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
package com.ecwid.mailchimp.internal.gson;

import com.ecwid.mailchimp.MailChimpObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 *
 * @author Vasily Karyaev <v.karyaev@gmail.com>
 */
public class MailChimpGsonFactoryTest {

	private static class TestObject extends MailChimpObject {
		@Field
		Date date;

		@Field
		Timestamp timestamp;

		@Field
		Integer integer;

		@Field
		MailChimpObject object;

		@Field
		TestObject child;

		@Field(name="children_array")
		TestObject array[];

		@Field(name="children_list")
		List<TestObject> list;

		String ignored;
	}

	@Test
	public void test() {
		Gson gson = MailChimpGsonFactory.createGson();

		TestObject o = new TestObject();
		o.date = new Date(0);
		o.timestamp = new Timestamp(0);
		o.integer = 666;
		o.object = new MailChimpObject();
		o.object.put("key", "value");
		o.child = new TestObject();
		o.child.integer = 555;
		o.array = new TestObject[] { new TestObject() };
		o.array[0].integer = 111;
		o.list = Arrays.asList(new TestObject());
		o.list.get(0).integer = 222;
		o.ignored = "ignored";

		JsonObject json = gson.toJsonTree(o).getAsJsonObject();
		assertEquals(json.entrySet().size(), 7);
		assertEquals(json.get("date").getAsString(), "1970-01-01 00:00:00");
		assertEquals(json.get("timestamp").getAsString(), "1970-01-01 00:00:00");
		assertEquals(json.get("integer").getAsInt(), 666);
		assertEquals(json.get("object").getAsJsonObject().get("key").getAsString(), "value");
		assertEquals(json.get("child").getAsJsonObject().get("integer").getAsInt(), 555);
		assertEquals(json.get("children_array").getAsJsonArray().get(0).getAsJsonObject().get("integer").getAsInt(), 111);
		assertEquals(json.get("children_list").getAsJsonArray().get(0).getAsJsonObject().get("integer").getAsInt(), 222);
		assertNull(json.get("ignored"));
		json.addProperty("ignored", "really ignored?");

		o = gson.fromJson(json, TestObject.class);
		assertEquals(o.date, new Date(0));
		assertEquals(o.timestamp, new Timestamp(0));
		assertEquals((int) o.integer, 666);
		assertEquals(o.object.get("key"), "value");
		assertEquals((int) o.child.integer, 555);
		assertEquals((int) o.array[0].integer, 111);
		assertEquals((int) o.list.get(0).integer, 222);
		assertNull(o.ignored);
	}
	
	private static class PlainOldObject {
		@MailChimpObject.Field
		Date date;
		
		@MailChimpObject.Field
		Timestamp timestamp;

		@MailChimpObject.Field(name = "email_address")
		String email;
		
		String unserializable;
	}

	@Test
	public void testSerialization_PlainOld() {
		Gson gson = MailChimpGsonFactory.createGson();
		
		PlainOldObject o = new PlainOldObject();
		o.email = "pupkin@gmail.com";
		o.date = new Date(0);
		o.timestamp = new Timestamp(0);
		o.unserializable = "Unserializable";
		
		JsonObject json = gson.toJsonTree(o).getAsJsonObject();
		assertEquals(json.entrySet().size(), 3);
		assertEquals(json.get("date").getAsString(), "1970-01-01 00:00:00");
		assertEquals(json.get("timestamp").getAsString(), "1970-01-01 00:00:00");
		assertEquals(json.get("email_address").getAsString(), "pupkin@gmail.com");
		assertNull(json.get("unserializable"));
		
		json.addProperty("unserializable", "Unserializable");
		
		o = gson.fromJson(json, PlainOldObject.class);
		assertEquals(o.date, new Date(0));
		assertEquals(o.timestamp, new Timestamp(0));
		assertEquals(o.email, "pupkin@gmail.com");
		assertNull(o.unserializable);
	}

	@Test
	public void testDeserialization_untyped() {
		Gson gson = MailChimpGsonFactory.createGson();

		MailChimpObject test1 = gson.fromJson("{ test1:{test1:{test1:{test1:{}}}} }", MailChimpObject.class);
		assertEquals(test1.getClass(), MailChimpObject.class);
		test1 = (MailChimpObject) test1.get("test1");
		assertEquals(test1.getClass(), MailChimpObject.class);
		test1 = (MailChimpObject) test1.get("test1");
		assertEquals(test1.getClass(), MailChimpObject.class);
		test1 = (MailChimpObject) test1.get("test1");
		assertEquals(test1.getClass(), MailChimpObject.class);
		test1 = (MailChimpObject) test1.get("test1");
		assertEquals(test1.getClass(), MailChimpObject.class);
		assertNull(test1.get("test1"));

		MailChimpObject test2 = gson.fromJson("{ 'test2':[{\"test2\":[{},{}]},true,[{}]] }", MailChimpObject.class);
		assertEquals(test2.getClass(), MailChimpObject.class);
		Iterator i = ((List) test2.get("test2")).iterator();

		test2 = (MailChimpObject) i.next();
		assertEquals(test2.getClass(), MailChimpObject.class);
		assertEquals(((List) test2.get("test2")).size(), 2);
		assertEquals(((List) test2.get("test2")).get(0).getClass(), MailChimpObject.class);
		assertEquals(((List) test2.get("test2")).get(1).getClass(), MailChimpObject.class);

		assertEquals(i.next(), true);

		List<?> test2_list = (List) i.next();
		assertEquals(test2_list.size(), 1);
		test2 = (MailChimpObject) test2_list.get(0);
		assertEquals(test2.getClass(), MailChimpObject.class);

		assertFalse(i.hasNext());
	}
}
