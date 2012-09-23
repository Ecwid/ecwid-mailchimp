/*
 * Copyright (C) 2007 The Guava Authors
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

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This class contains static utility methods that operate on or return objects
 * of type {@link Iterator}. Except as noted, each method has a corresponding
 * {@link Iterable}-based method in the {@link Iterables} class.
 *
 * <p><i>Performance notes:</i> Unless otherwise noted, all of the iterators
 * produced in this class are <i>lazy</i>, which means that they only advance
 * the backing iteration when absolutely necessary.
 *
 * <p>See the Guava User Guide section on <a href=
 * "http://code.google.com/p/guava-libraries/wiki/CollectionUtilitiesExplained#Iterables">
 * {@code Iterators}</a>.
 *
 * @author Kevin Bourrillion
 * @author Jared Levy
 * @since 2.0 (imported from Google Collections Library)
 */
public class Iterators {

	private Iterators() { }

	/**
	 * Combines two iterators into a single iterator. The returned iterator
	 * iterates across the elements in {@code a}, followed by the elements in
	 * {@code b}. The source iterators are not polled until necessary.
	 *
	 * <p>The returned iterator supports {@code remove()} when the corresponding
	 * input iterator supports it.
	 *
	 * <p><b>Note:</b> the current implementation is not suitable for nested
	 * concatenated iterators, i.e. the following should be avoided when in a
	 * loop: {@code iterator = Iterators.concat(iterator, suffix);}, since
	 * iteration over the resulting iterator has a cubic complexity to the depth
	 * of the nesting.
	 */
	@SuppressWarnings("unchecked")
	public static <T> Iterator<T> concat(Iterator<? extends T> a,
			Iterator<? extends T> b) {
		checkNotNull(a);
		checkNotNull(b);
		return concat(Arrays.asList(a, b).iterator());
	}

	/**
	 * Combines three iterators into a single iterator. The returned iterator
	 * iterates across the elements in {@code a}, followed by the elements in
	 * {@code b}, followed by the elements in {@code c}. The source iterators
	 * are not polled until necessary.
	 *
	 * <p>The returned iterator supports {@code remove()} when the corresponding
	 * input iterator supports it.
	 *
	 * <p><b>Note:</b> the current implementation is not suitable for nested
	 * concatenated iterators, i.e. the following should be avoided when in a
	 * loop: {@code iterator = Iterators.concat(iterator, suffix);}, since
	 * iteration over the resulting iterator has a cubic complexity to the depth
	 * of the nesting.
	 */
	@SuppressWarnings("unchecked")
	public static <T> Iterator<T> concat(Iterator<? extends T> a,
			Iterator<? extends T> b, Iterator<? extends T> c) {
		checkNotNull(a);
		checkNotNull(b);
		checkNotNull(c);
		return concat(Arrays.asList(a, b, c).iterator());
	}

	/**
	 * Combines four iterators into a single iterator. The returned iterator
	 * iterates across the elements in {@code a}, followed by the elements in
	 * {@code b}, followed by the elements in {@code c}, followed by the
	 * elements in {@code d}. The source iterators are not polled until
	 * necessary.
	 *
	 * <p>The returned iterator supports {@code remove()} when the corresponding
	 * input iterator supports it.
	 *
	 * <p><b>Note:</b> the current implementation is not suitable for nested
	 * concatenated iterators, i.e. the following should be avoided when in a
	 * loop: {@code iterator = Iterators.concat(iterator, suffix);}, since
	 * iteration over the resulting iterator has a cubic complexity to the depth
	 * of the nesting.
	 */
	@SuppressWarnings("unchecked")
	public static <T> Iterator<T> concat(Iterator<? extends T> a,
			Iterator<? extends T> b, Iterator<? extends T> c,
			Iterator<? extends T> d) {
		checkNotNull(a);
		checkNotNull(b);
		checkNotNull(c);
		checkNotNull(d);
		return concat(Arrays.asList(a, b, c, d).iterator());
	}

	/**
	 * Combines multiple iterators into a single iterator. The returned iterator
	 * iterates across the elements of each iterator in {@code inputs}. The
	 * input iterators are not polled until necessary.
	 *
	 * <p>The returned iterator supports {@code remove()} when the corresponding
	 * input iterator supports it. The methods of the returned iterator may
	 * throw {@code NullPointerException} if any of the input iterators is null.
	 *
	 * <p><b>Note:</b> the current implementation is not suitable for nested
	 * concatenated iterators, i.e. the following should be avoided when in a
	 * loop: {@code iterator = Iterators.concat(iterator, suffix);}, since
	 * iteration over the resulting iterator has a cubic complexity to the depth
	 * of the nesting.
	 */
	public static <T> Iterator<T> concat(
			final Iterator<? extends Iterator<? extends T>> inputs) {
		checkNotNull(inputs);
		return new Iterator<T>() {
			Iterator<? extends T> current = Collections.<T>emptyList().iterator();
			Iterator<? extends T> removeFrom;

			@Override
			public boolean hasNext() {
				// http://code.google.com/p/google-collections/issues/detail?id=151
				// current.hasNext() might be relatively expensive, worth minimizing.
				boolean currentHasNext;
				// checkNotNull eager for GWT
				// note: it must be here & not where 'current' is assigned,
				// because otherwise we'll have called inputs.next() before throwing
				// the first NPE, and the next time around we'll call inputs.next()
				// again, incorrectly moving beyond the error.
				while (!(currentHasNext = checkNotNull(current).hasNext())
						&& inputs.hasNext()) {
					current = inputs.next();
				}
				return currentHasNext;
			}

			@Override
			public T next() {
				if (!hasNext()) {
					throw new NoSuchElementException();
				}
				removeFrom = current;
				return current.next();
			}

			@Override
			public void remove() {
				checkState(removeFrom != null,
						"no calls to next() since last call to remove()");
				removeFrom.remove();
				removeFrom = null;
			}
		};
	}

	private static <T> T checkNotNull(T reference) {
		if (reference == null) {
			throw new NullPointerException();
		}
		return reference;
	}

	private static void checkState(
			boolean expression, Object errorMessage) {
		if (!expression) {
			throw new IllegalStateException(String.valueOf(errorMessage));
		}
	}
}
