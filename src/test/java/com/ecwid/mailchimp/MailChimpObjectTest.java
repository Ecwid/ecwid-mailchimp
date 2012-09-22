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

import java.lang.reflect.Type;
import java.util.AbstractMap;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 *
 * @author Vasily Karyaev <v.karyaev@gmail.com>
 */
public class MailChimpObjectTest {

	private static class TestBase extends MailChimpObject {
		@Field
		String f1;
	}

	private static class TestObject extends TestBase {
		@Field
		int f2;

		@Field(name="f-3")
		Date f3;

		String ignored;
	}

	@Test
	public void testGetReflectiveMappingTypes() {
		TestObject o = new TestObject();
		assertEquals(o.size(), 3);

		Iterator<Map.Entry<String, Type>> i = o.getReflectiveMappingTypes().entrySet().iterator();
		Map.Entry<String, Type> entry = i.next();
		assertEquals(entry.getKey(), "f1");
		assertEquals(entry.getValue(), String.class);
		entry = i.next();
		assertEquals(entry.getKey(), "f2");
		assertEquals(entry.getValue(), int.class);
		entry = i.next();
		assertEquals(entry.getKey(), "f-3");
		assertEquals(entry.getValue(), Date.class);
		assertFalse(i.hasNext());
	}

	@Test
	public void testPutAndGet() {
		TestObject o = new TestObject();
		assertEquals(o.size(), 3);

		o.f1 = "f1_value";
		o.f2 = 666;
		o.put("f-3", new Date(123456));
		o.put("f-4", new Date(654321));
		
		assertEquals(o.size(), 4);
		assertEquals(o.get("f1"), "f1_value");
		assertEquals(o.get("f2"), 666);
		assertEquals(o.get("f-3"), new Date(123456));
		assertEquals(o.get("f-4"), new Date(654321));
		assertEquals(o.f3, new Date(123456));
		
		assertEquals(o.put("f2", 777), 666);
		assertEquals(o.get("f2"), 777);
		assertEquals(o.f2, 777);

		assertEquals(o.put("f-4", 777), new Date(654321));
		assertEquals(o.get("f-4"), 777);

		try {
			o.put("f2", "a string");
			fail();
		} catch(IllegalArgumentException e) {
			// types mismatch
		}
	}

	@Test
	public void testContains() {
		TestObject o = new TestObject();
		assertEquals(o.size(), 3);

		assertTrue(o.containsKey("f1"));
		assertTrue(o.containsKey("f2"));
		assertTrue(o.containsKey("f-3"));
		assertFalse(o.containsKey("f-4"));

		assertFalse(o.containsValue("f1_value"));
		o.f1 = "f1_value";
		assertTrue(o.containsValue("f1_value"));

		assertFalse(o.containsValue(666));
		o.f2 = 666;
		assertTrue(o.containsValue(666));

		assertFalse(o.containsValue(new Date(111222333)));
		o.f3 = new Date(111222333);
		assertTrue(o.containsValue(new Date(111222333)));

		assertFalse(o.containsValue(MailChimpObjectTest.this));
		o.put("f-4", MailChimpObjectTest.this);
		assertTrue(o.containsValue(MailChimpObjectTest.this));
	}

	@Test
	public void testRemove() {
		TestObject o = new TestObject();
		
		assertEquals(o.size(), 3);
		assertNull(o.remove("f-4"));
		assertEquals(o.size(), 3);
		o.put("f-4", null);
		assertEquals(o.size(), 4);
		assertNull(o.remove("f-4"));
		assertEquals(o.size(), 3);
		o.put("f-4", new Date(654321));
		assertEquals(o.size(), 4);
		assertEquals(o.remove("f-4"), new Date(654321));
		assertEquals(o.size(), 3);

		try {
			o.remove("f-3");
			fail();
		} catch (IllegalArgumentException e) {
			// cannot remove reflective mapping
		}
	}

	@Test
	public void testClear() {
		TestObject o = new TestObject();
		try {
			o.clear();
			fail();
		} catch(UnsupportedOperationException e) { }
	}

	@Test
	public void testKeySet() {
		TestObject o = new TestObject();
		Set<String> set = o.keySet();

		o.put("f-4", 111);
		assertEquals(set.size(), 4);

		Iterator<String> i = set.iterator();
		assertEquals(i.next(), "f1");
		assertEquals(i.next(), "f2");
		assertEquals(i.next(), "f-3");

		try {
			i.remove();
			fail();
		} catch (IllegalArgumentException e) {
			// cannot remove reflective mapping
		}
		
		assertEquals(set.size(), 4);
		assertEquals(i.next(), "f-4");
		i.remove();
		assertFalse(i.hasNext());
		assertEquals(set.size(), 3);

		assertTrue(set.contains("f-3"));
		assertFalse(set.contains("rrr"));
		o.put("rrr", null);
		assertTrue(set.contains("rrr"));
		assertEquals(set.size(), 4);
		assertTrue(set.remove("rrr"));
		assertFalse(set.contains("rrr"));
		assertFalse(set.remove("rrr"));
		assertEquals(set.size(), 3);

		try {
			o.remove("f-3");
			fail();
		} catch (IllegalArgumentException e) {
			// cannot remove reflective mapping
		}
	}

	@Test
	public void testValues() {
		TestObject o = new TestObject();

		assertFalse(o.values().contains(new Date(666)));
		o.f3 = new Date(666);
		assertTrue(o.values().contains(new Date(666)));

		assertFalse(o.values().contains(111));
		o.put("f-4", 111);
		assertTrue(o.values().contains(111));

		Iterator<Object> i = o.values().iterator();
		assertNull(i.next());
		assertEquals(i.next(), 0);
		assertEquals(i.next(), new Date(666));

		try {
			i.remove();
			fail();
		} catch (IllegalArgumentException e) {
			// cannot remove reflective mapping
		}

		assertEquals(i.next(), 111);
		assertFalse(i.hasNext());

		assertEquals(o.size(), 4);
		i.remove();
		assertEquals(o.size(), 3);
		assertFalse(i.hasNext());
	}

	@Test
	public void testEntrySet() {
		TestObject o = new TestObject();
		Set<Map.Entry<String, Object>> set = o.entrySet();

		o.f3 = new Date(222222);
		assertEquals(set.size(), 3);
		o.put("f-4", 456);
		assertEquals(set.size(), 4);

		assertTrue(set.contains(new AbstractMap.SimpleEntry<String, Object>("f1", null)));
		assertFalse(set.contains(new AbstractMap.SimpleEntry<String, Object>("f-3", null)));
		assertTrue(set.contains(new AbstractMap.SimpleEntry<String, Object>("f-3", new Date(222222))));
		assertTrue(set.contains(new AbstractMap.SimpleEntry<String, Object>("f-4", 456)));
		assertFalse(set.contains(new AbstractMap.SimpleEntry<String, Object>("f-5", 456)));

		assertFalse(set.remove(new AbstractMap.SimpleEntry<Object, Object>("f1", 777)));
		assertEquals(set.size(), 4);
		try {
			set.remove(new AbstractMap.SimpleEntry<Object, Object>("f1", null));
			fail();
		} catch (IllegalArgumentException e) {
			// cannot remove reflective mapping
		}

		assertTrue(set.remove(new AbstractMap.SimpleEntry<Object, Object>("f-4", 456)));
		assertEquals(set.size(), 3);
	}
}
