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
package com.ecwid.mailchimp.internal.guava;

import com.ecwid.mailchimp.internal.guava.Iterators;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 *
 * @author Vasily Karyaev <v.karyaev@gmail.com>
 */
public class IteratorsTest {

	@Test
	public void testEmpty() {
		Iterator<Integer> i = Iterators.concat(Collections.<Integer>emptySet().iterator(), Collections.<Integer>emptySet().iterator());
		assertFalse(i.hasNext());

		try {
			i.next();
			fail();
		} catch(NoSuchElementException e) { }

		try {
			i.remove();
			fail();
		} catch(IllegalStateException e) { }
	}

	@Test
	public void test() {
		List<Integer> l1 = new ArrayList<Integer>(Arrays.asList(1, 2, 3));
		List<Integer> l2 = new ArrayList<Integer>(Arrays.asList(5, 7));

		Iterator<Integer> i = Iterators.concat(l1.iterator(), l2.iterator());
		assertTrue(i.hasNext());
		assertEquals((int) i.next(), 1);
		assertTrue(i.hasNext());
		assertEquals((int) i.next(), 2);
		assertTrue(i.hasNext());
		assertEquals((int) i.next(), 3);
		assertTrue(i.hasNext());
		assertEquals((int) i.next(), 5);
		assertTrue(i.hasNext());
		assertEquals((int) i.next(), 7);
		assertFalse(i.hasNext());

		try {
			i.next();
			fail();
		} catch(NoSuchElementException e) { }

		i = Iterators.concat(l1.iterator(), l2.iterator());

		try {
			i.remove();
		} catch(IllegalStateException e) { }

		i.next();
		i.next();
		i.remove();

		try {
			i.remove();
		} catch(IllegalStateException e) { }

		i.next();
		i.remove();
		i.next();
		i.remove();
		i.next();
		assertFalse(i.hasNext());

		assertEquals(l1, Arrays.asList(1));
		assertEquals(l2, Arrays.asList(7));
	}
}
