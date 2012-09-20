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

import com.google.gson.internal.StringMap;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Represents JSON-object as map.
 * 
 * @author Vasily Karyaev <v.karyaev@gmail.com>
 */
public final class MailChimpMap extends MailChimpObject implements Map<String, Object> {

	private final StringMap<Object> peer = new StringMap<Object>();

	@Override
	public int size() {
		return peer.size();
	}

	@Override
	public boolean isEmpty() {
		return peer.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return peer.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return peer.containsValue(value);
	}

	@Override
	public Object get(Object key) {
		return peer.get(key);
	}

	@Override
	public Object put(String key, Object value) {
		return peer.put(key, value);
	}

	@Override
	public Object remove(Object key) {
		return peer.remove(key);
	}

	@Override
	public void putAll(Map<? extends String, ? extends Object> m) {
		peer.putAll(m);
	}

	@Override
	public void clear() {
		peer.clear();
	}

	@Override
	public Set<String> keySet() {
		return peer.keySet();
	}

	@Override
	public Collection<Object> values() {
		return peer.values();
	}

	@Override
	public Set<Entry<String, Object>> entrySet() {
		return peer.entrySet();
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 29 * hash + (this.peer != null ? this.peer.hashCode() : 0);
		return hash;
	}

	/**
	 * Converts this map to a regular MailChimp object.
	 */
	public <T extends MailChimpObject> T as(Class<T> clazz) {
		return fromJson(toJson(), clazz);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final MailChimpMap other = (MailChimpMap) obj;
		if (this.peer != other.peer && (this.peer == null || !this.peer.equals(other.peer))) {
			return false;
		}
		return true;
	}
}
