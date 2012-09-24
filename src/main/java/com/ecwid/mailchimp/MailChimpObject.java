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

import com.ecwid.mailchimp.internal.guava.Iterators;
import com.ecwid.mailchimp.internal.guava.Objects;
import com.ecwid.mailchimp.internal.gson.MailChimpGsonFactory;
import com.google.gson.JsonParseException;
import java.io.Serializable;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Base class for all objects wrapping MailChimp API calls.
 * Since this object is generally a map, its mappings are used to serilize/deserialize the object to/from JSON.
 * <p>
 * Some entries in the map are <i>regular</i> mappings, whereas others are <i>reflective</i> ones.
 * Reflective mappings are represented by fields marked with the {@link Field} annotation.
 * Changes in such fields are reflected in the map and vice versa.
 * <p>
 * Reflective mappings cannot be removed from this map.
 * 
 * @author Vasily Karyaev <v.karyaev@gmail.com>
 */
public class MailChimpObject extends AbstractMap<String, Object> implements Serializable {

	/**
	 * Marks fields to be serialized/deserialed to/from JSON.
	 */
	@Documented
	@Retention(value = RetentionPolicy.RUNTIME)
	@Target(value = ElementType.FIELD)
	protected @interface Field {
		/**
		 * Property name in JSON mapping.
		 * If not specified, the field name will be used.
		 */
		public String name() default "";
	}

	private static Map<Class<?>, Map<String, java.lang.reflect.Field>> reflectiveMappings = new ConcurrentHashMap<Class<?>, Map<String, java.lang.reflect.Field>>();
	private static Map<Class<?>, Map<String, Type>> reflectiveMappingsTypes = new ConcurrentHashMap<Class<?>, Map<String, Type>>();

	private static Map<String, java.lang.reflect.Field> getReflectiveMapping(Class<? extends MailChimpObject> clazz) {
		Map<String, java.lang.reflect.Field> mapping = reflectiveMappings.get(clazz);

		if(mapping == null) {
			mapping = new LinkedHashMap<String, java.lang.reflect.Field>();

			LinkedList<Class> classes = new LinkedList<Class>();
			for(Class<?> c=clazz; MailChimpObject.class.isAssignableFrom(c); c=c.getSuperclass()) {
				classes.addFirst(c);
			}

			for(Class<?> c: classes) {
				for(java.lang.reflect.Field f: c.getDeclaredFields()) {
					Field annotation = f.getAnnotation(Field.class);
					if(annotation != null) {
						if(Modifier.isStatic(f.getModifiers())) {
							throw new IllegalArgumentException("Field must not be static: "+f);
						}

						String name = annotation.name();
						if(name.isEmpty()) {
							name = f.getName();
						}

						if(mapping.put(name, f) != null) {
							throw new IllegalArgumentException("Ambiguous reflective mapping: "+name);
						}

						f.setAccessible(true);
					}
				}
			}

			reflectiveMappings.put(clazz, Collections.unmodifiableMap(mapping));
		}

		return mapping;
	}

	/**
	 * Get desciption of the reflective mappings in class <code>clazz</code>.
	 *
	 * @return map in which keys are the mapping keys, and values are the corresponding field types.
	 */
	public static Map<String, Type> getReflectiveMappingTypes(Class<? extends MailChimpObject> clazz) {
		Map<String, Type> types = reflectiveMappingsTypes.get(clazz);

		if (types == null) {
			types = new LinkedHashMap<String, Type>();

			for(Map.Entry<String, java.lang.reflect.Field> entry: getReflectiveMapping(clazz).entrySet()) {
				types.put(entry.getKey(), entry.getValue().getGenericType());
			}

			reflectiveMappingsTypes.put(clazz, Collections.unmodifiableMap(types));
		}

		return types;
	}

	private final Map<String, java.lang.reflect.Field> reflective = getReflectiveMapping(getClass());
	private final Map<String, Object> regular = new LinkedHashMap<String, Object>();

	/**
	 * Default constructior.
	 */
	public MailChimpObject() { }

	private class ReflectiveValueRemovalException extends IllegalArgumentException {
		ReflectiveValueRemovalException(String key) {
			super("Cannot remove reflective mapping: " + key + " -> " + reflective.get(key));
		}
	}

	private class ReflectiveEntry implements Map.Entry<String, Object> {
		final String key;
		final java.lang.reflect.Field field;

		ReflectiveEntry(String key, java.lang.reflect.Field field) {
			this.key = key;
			this.field = field;
		}

		@Override
		public String getKey() {
			return key;
		}

		@Override
		public Object getValue() {
			return get(field);
		}

		@Override
		public Object setValue(Object value) {
			return set(field, value);
		}

		@Override
		public int hashCode() {
			return Objects.hashCode(getKey(), getValue());
		}

		@Override
		public boolean equals(Object obj) {
			if(obj instanceof Map.Entry) {
				Map.Entry<?,?> e1 = this, e2 = (Map.Entry) obj;
				return Objects.equal(e1.getKey(), e2.getKey()) && Objects.equal(e1.getValue(), e2.getValue());
			} else {
				return false;
			}
		}

		@Override
		public String toString() {
			return getKey() + "=" + getValue();
		}
	}

	private abstract class AbstractReflectiveIterator<T> implements Iterator<T> {
		private final Iterator<Map.Entry<String, java.lang.reflect.Field>> i = reflective.entrySet().iterator();
		private Map.Entry<String, java.lang.reflect.Field> lastEntry;

		@Override
		public boolean hasNext() {
			return i.hasNext();
		}

		@Override
		public T next() {
			return reify(lastEntry = i.next());
		}

		@Override
		public void remove() {
			throw new ReflectiveValueRemovalException(lastEntry.getKey());
		}

		protected abstract T reify(Map.Entry<String, java.lang.reflect.Field> entry);
	}

	private class ReflectiveKeyIterator extends AbstractReflectiveIterator<String> {
		@Override
		protected String reify(Entry<String, java.lang.reflect.Field> entry) {
			return entry.getKey();
		}
	}

	private class ReflectiveEntryIterator extends AbstractReflectiveIterator<Map.Entry<String, Object>> {
		@Override
		protected Entry<String, Object> reify(Entry<String, java.lang.reflect.Field> entry) {
			return new ReflectiveEntry(entry.getKey(), entry.getValue());
		}
	}

	/**
	 * Get desciption of the reflective mappings in this object.
	 *
	 * @return map in which keys are the mapping keys, and values are the corresponding field types.
	 */
	public Map<String, Type> getReflectiveMappingTypes() {
		return getReflectiveMappingTypes(getClass());
	}

	@Override
	public Object get(Object key) {
		java.lang.reflect.Field field = reflective.get(key);
		if(field != null) {
			return get(field);
		} else {
			return regular.get(key);
		}
	}

	@Override
	public Object put(String key, Object value) {
		if(key == null) {
			throw new NullPointerException("Null keys not allowed");
		}

		java.lang.reflect.Field field = reflective.get(key);
		if(field != null) {
			return set(field, value);
		} else {
			return regular.put(key, value);
		}
	}

	/**
	 * Removes a regular mapping for a key from this map if it is present.
	 *
	 * @param key key for a regular mapping
	 * @return the previous value associated with key, or null if there was no mapping for key
	 * @throws IllegalArgumentException if key is associated with a reflective mapping
	 */
	@Override
	public Object remove(Object key) {
		if(key instanceof String && reflective.containsKey((String) key)) {
			throw new ReflectiveValueRemovalException((String) key);
		} else {
			return regular.remove(key);
		}
	}

	@Override
	public boolean containsKey(Object key) {
		return reflective.containsKey(key) || regular.containsKey(key);
	}

	@Override
	public int size() {
		return reflective.size() + regular.size();
	}

	/**
	 * Unsupported operation.
	 * Since reflective mappings cannot be removed, this operation is not supported.
	 */
	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<String> keySet() {
		return new AbstractSet<String>() {
			@Override
			public Iterator<String> iterator() {
				return Iterators.concat(new ReflectiveKeyIterator(), regular.keySet().iterator());
			}

			@Override
			public boolean contains(Object o) {
				return o instanceof String && MailChimpObject.this.containsKey((String) o);
			}

			@Override
			public boolean remove(Object o) {
				if(o instanceof String && MailChimpObject.this.containsKey((String) o)) {
					MailChimpObject.this.remove((String) o);
					return true;
				} else {
					return false;
				}
			}

			@Override
			public int size() {
				return MailChimpObject.this.size();
			}
		};
	}

	@Override
	public Set<Map.Entry<String, Object>> entrySet() {
		return new AbstractSet<Map.Entry<String, Object>>() {
			@Override
			public Iterator<Map.Entry<String, Object>> iterator() {
				return Iterators.concat(new ReflectiveEntryIterator(), regular.entrySet().iterator());
			}

			@Override
			public boolean remove(Object o) {
				if(o instanceof Map.Entry) {
					if(contains((Map.Entry) o)) {
						MailChimpObject.this.remove((String) ((Map.Entry) o).getKey());
						return true;
					} else {
						return false;
					}
				} else {
					return false;
				}
			}

			@Override
			public boolean contains(Object o) {
				if(o instanceof Map.Entry && ((Map.Entry) o).getKey() instanceof String) {
					String key = (String) ((Map.Entry) o).getKey();
					if(MailChimpObject.this.containsKey(key)) {
						Object value1 = ((Map.Entry) o).getValue();
						Object value2 = MailChimpObject.this.get(key);
						return Objects.equal(value1, value2);
					} else {
						return false;
					}
				} else {
					return false;
				}
			}

			@Override
			public int size() {
				return MailChimpObject.this.size();
			}
		};
	}

	/**
	 * Serializes this object to JSON.
	 */
	public final String toJson() {
		return MailChimpGsonFactory.createGson().toJson(this);
	}
	
	/**
	 * Deserializes an object from JSON.
	 *
	 * @throws IllegalArgumentException if <code>json</code> cannot be deserialized to an object of class <code>clazz</code>.
	 */
	public static <T extends MailChimpObject> T fromJson(String json, Class<T> clazz) {
		try {
			return MailChimpGsonFactory.createGson().fromJson(json, clazz);
		} catch(JsonParseException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * Converts this object to another object of class <code>clazz</code>.
	 * 
	 * @return a new object of the specified class.
	 */
	public <T extends MailChimpObject> T as(Class<T> clazz) {
		return fromJson(toJson(), clazz);
	}

	private Object get(java.lang.reflect.Field field) {
		try {
			return field.get(this);
		} catch(IllegalAccessException e) {
			throw new AssertionError(e);
		}
	}

	private Object set(java.lang.reflect.Field field, Object value) {
		try {
			Object oldValue = field.get(this);
			field.set(this, value);
			return oldValue;
		} catch(IllegalAccessException e) {
			throw new AssertionError(e);
		}
	}
}
