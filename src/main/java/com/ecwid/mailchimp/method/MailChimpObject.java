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
package com.ecwid.mailchimp.method;

import com.ecwid.mailchimp.MailChimpGsonFactory;


/**
 * Base class for all objects wrapping MailChimp API calls.
 * 
 * @author Vasily Karyaev <v.karyaev@gmail.com>
 */
public abstract class MailChimpObject {
	@Override
	public final String toString() {
		return getClass().getSimpleName()+":"+toJson();
	}
	
	@Override
	public final boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		return this.toJson().equals(((MailChimpObject) obj).toJson());
	}
	
	@Override
	public final int hashCode() {
		return toJson().hashCode();
	}

	public final String toJson() {
		return MailChimpGsonFactory.createGson().toJson(this);
	}
	
	public static <T extends MailChimpObject> T fromJson(String json, Class<T> clazz) {
		return MailChimpGsonFactory.createGson().fromJson(json, clazz);
	}
}
