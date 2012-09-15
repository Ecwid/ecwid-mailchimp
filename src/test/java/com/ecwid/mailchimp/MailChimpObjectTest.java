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

import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 *
 * @author Vasily Karyaev <v.karyaev@gmail.com>
 */
public class MailChimpObjectTest {

	private class TestBase extends MailChimpObject {
		@Field
		String f1;
	}

	private class TestObject extends TestBase {
		@Field
		int f2;

		String ignored;
	}

	private class Test1 extends TestObject { }
	private class Test2 extends TestObject { }

	@Test
	public void testEquals_same() {
		TestObject test = new TestObject();
		assertEqual(test, test);
	}

	@Test
	public void testEquals_null() {
		TestObject test = new TestObject();
		assertFalse(test.equals(null));
	}

	@Test
	public void testEqualsAndHashCode_differentClasses() {
		Test1 t1 = new Test1();
		Test2 t2 = new Test2();
		assertNotEqual(t1, t2);
	}

	@Test
	public void testEqualsAndHashCode_sameClass() {
		class Test1 extends TestObject { }
		class Test2 extends TestObject { }

		TestObject t1 = new TestObject();
		TestObject t2 = new TestObject();
		assertEqual(t1, t2);

		t1.f1 = "f1";
		assertNotEqual(t1, t2);

		t2.f1 = "???";
		assertNotEqual(t1, t2);

		t2.f1 = "f1";
		assertEqual(t1, t2);

		t1.f2 = 555;
		assertNotEqual(t1, t2);

		t2.f2 = 666;
		assertNotEqual(t1, t2);

		t2.f2 = 555;
		assertEqual(t1, t2);

		t1.ignored = "111";
		t2.ignored = "222";
		assertEqual(t1, t2);
	}

	private void assertEqual(TestObject t1, TestObject t2) {
		assertTrue(t1.equals(t2));
		assertTrue(t1.hashCode() == t2.hashCode());
	}

	private void assertNotEqual(TestObject t1, TestObject t2) {
		assertFalse(t1.equals(t2));
		assertFalse(t1.hashCode() == t2.hashCode());
	}
}
