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
package com.ecwid.mailchimp.internal.gson;

import com.ecwid.mailchimp.MailChimpObject;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Factory creating {@link Gson} objects to be used for MailChimp API calls wrapping.
 * 
 * @author Vasily Karyaev <v.karyaev@gmail.com>
 */
public class MailChimpGsonFactory {
	
	private MailChimpGsonFactory() { }

	/**
	 * Excludes every field in any class.
	 * Only instances of {@link MailChimpObject} can be serialized
	 * (see {@link MailChimpObjectTypeAdapter}).
	 */
	private static final ExclusionStrategy exclusionStrategy = new ExclusionStrategy() {
		@Override
		public boolean shouldSkipField(FieldAttributes fa) {
			return true;
		}

		@Override
		public boolean shouldSkipClass(Class<?> type) {
			return false;
		}
	};

	/**
	 * Translates dates to json strings and vice versa.
	 * <p>
	 * This adapter is used instead of {@link GsonBuilder#setDateFormat(java.lang.String)}
	 * due to gson's lack of ability to set proper time zone.
	 */
	private static class DateTypeAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {
		private final DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		DateTypeAdapter() {
			format.setTimeZone(TimeZone.getTimeZone("UTC"));
		}
		
		@Override
		public JsonElement serialize(Date t, Type type, JsonSerializationContext jsc) {
			synchronized(format) {
				return new JsonPrimitive(format.format(t));
			}
		}

		@Override
		public Date deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
			synchronized(format) {
				try {
					return !je.getAsString().isEmpty()? format.parse(je.getAsString()) : null;
				} catch(ParseException e) {
					throw new IllegalArgumentException("Cannot deserialize date: "+je);
				}
			}
		}
	}

	/**
	 * Creates a new {@link Gson} object.
	 */
	public static Gson createGson() {
		GsonBuilder builder = new GsonBuilder();
		builder.setExclusionStrategies(exclusionStrategy);
		builder.registerTypeAdapter(Date.class, new DateTypeAdapter());
		builder.registerTypeAdapterFactory(MailChimpObjectTypeAdapter.FACTORY);
		return builder.create();
	}
}
