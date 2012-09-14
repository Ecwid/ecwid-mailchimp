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

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Vasily Karyaev <v.karyaev@gmail.com>
 */
public class MailChimpObjectTest {

	private class Test1 extends MailChimpObject {
		@Field
		private String f1;

		private String f2;
	}

	private class Test2 extends MailChimpObject {
		@Field
		private String f1;

		private String f2;

		@Override
		public int hashCode() {
			int hash = 7;
			hash = 59 * hash + (this.f1 != null ? this.f1.hashCode() : 0);
			hash = 59 * hash + (this.f2 != null ? this.f2.hashCode() : 0);
			return hash;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			final Test2 other = (Test2) obj;
			if ((this.f1 == null) ? (other.f1 != null) : !this.f1.equals(other.f1)) {
				return false;
			}
			if ((this.f2 == null) ? (other.f2 != null) : !this.f2.equals(other.f2)) {
				return false;
			}
			return true;
		}
	}

	@Test
	public void testEqualsAndHashCode_differentClasses() {
		Test1 t1 = new Test1();
		Test2 t2 = new Test2();

		assertFalse(t1.equals(t2));
		assertFalse(t1.hashCode() == t2.hashCode());
	}

	@Test
	public void testEqualsAndHashCode_sameClass() {
		Test1 t1 = new Test1();
		Test1 t2 = new Test1();
		assertTrue(t1.equals(t2));
		assertTrue(t1.hashCode() == t2.hashCode());

		t1.f1 = "f1";
		assertFalse(t1.equals(t2));
		assertFalse(t1.hashCode() == t2.hashCode());

		t2.f1 = "???";
		assertFalse(t1.equals(t2));
		assertFalse(t1.hashCode() == t2.hashCode());

		t2.f1 = "f1";
		assertTrue(t1.equals(t2));
		assertTrue(t1.hashCode() == t2.hashCode());

		// ignored field
		t1.f2 = "111";
		t2.f2 = "222";
		assertTrue(t1.equals(t2));
		assertTrue(t1.hashCode() == t2.hashCode());
	}
}
