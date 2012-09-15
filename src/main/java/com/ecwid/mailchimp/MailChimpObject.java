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

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Base class for all objects wrapping MailChimp API calls to/from JSON strings.
 * 
 * @author Vasily Karyaev <v.karyaev@gmail.com>
 */
public abstract class MailChimpObject {

	/**
	 * This annotation marks fields to be serialized/deserialed to/from JSON.
	 */
	@Documented
	@Retention(value = RetentionPolicy.RUNTIME)
	@Target(value = ElementType.FIELD)
	protected @interface Field {
		public String name() default "";
	}
	
	/**
	 * Compares this object to <code>obj</code>, taking into account all fields marked with the {@link Field} annotation.
	 * All other fields are ignored.
	 * <p>
	 * If <code>obj</code> is not an instance of the same class, the result is <code>false</code>.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		return fieldValues().equals(((MailChimpObject) obj).fieldValues());
	}

	/**
	 * Calculates hashCode based on all fields marked with the {@link Field} annotation.
	 * All other fields are ignored.
	 */
	@Override
	public int hashCode() {
		int hash = 7;
		hash = 59 * hash + getClass().hashCode();
		hash = 59 * hash + fieldValues().hashCode();
		return hash;
	}

	private List<Object> fieldValues() {
		List<Object> result = new ArrayList<Object>();

		for(Class<?> c=getClass(); MailChimpObject.class.isAssignableFrom(c); c=c.getSuperclass()) {
			for(java.lang.reflect.Field f: c.getDeclaredFields()) {
				if(f.isAnnotationPresent(Field.class)) {
					f.setAccessible(true);
					try {
						result.add(f.get(this));
					} catch(IllegalAccessException e) {
						throw new AssertionError(e);
					}
				}
			}
		}

		return result;
	}

	/**
	 * Serializes this object to JSON.
	 */
	public final String toJson() {
		return MailChimpGsonFactory.createGson().toJson(this);
	}
	
	/**
	 * Deserializes an object from JSON.
	 */
	public static <T extends MailChimpObject> T fromJson(String json, Class<T> clazz) {
		return MailChimpGsonFactory.createGson().fromJson(json, clazz);
	}

	/**
	 * @return simple class name followed by a colon and the json-representation of this object
	 */
	@Override
	public final String toString() {
		return getClass().getSimpleName()+":"+toJson();
	}
}
