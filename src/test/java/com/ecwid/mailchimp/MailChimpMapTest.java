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

import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Vasily Karyaev <v.karyaev@gmail.com>
 */
public class MailChimpMapTest {

	public MailChimpMapTest() {
	}

	@Test
	public void testEqualsAndHashCode() {
		MailChimpMap m1 = new MailChimpMap();
		MailChimpMap m2 = new MailChimpMap();
		assertTrue(m1.equals(m2));
		assertTrue(m1.hashCode() == m2.hashCode());

		m1.put("name", "Vasya");
		assertFalse(m1.equals(m2));
		assertFalse(m1.hashCode() == m2.hashCode());

		m2.put("name", "Vasya");
		assertTrue(m1.equals(m2));
		assertTrue(m1.hashCode() == m2.hashCode());

		m1.put("name", new Date());
		assertFalse(m1.equals(m2));
		assertFalse(m1.hashCode() == m2.hashCode());
	}
}
