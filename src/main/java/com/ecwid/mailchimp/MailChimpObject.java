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


/**
 * Base class for all objects wrapping MailChimp API calls.
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

	@Override
	public final String toString() {
		return getClass().getSimpleName()+":"+toJson();
	}
	
	/**
	 * Compares this object to <code>obj</code>, taking into account all fields marked with the {@link Field} annotation.
	 * All other fields are ignored.
	 * <p>
	 * If <code>obj</code> is not an instance of the same class, the result is <code>false</code>.
	 */
	@Override
	public final boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		// TODO: use reflection instead of serialization
		return this.toJson().equals(((MailChimpObject) obj).toJson());
	}
	
	/**
	 * Calculates hashCode based on all fields marked with the {@link Field} annotation.
	 * All other fields are ignored.
	 */
	@Override
	public final int hashCode() {
		// TODO: use reflection instead of serialization
		return toJson().hashCode();
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
}
