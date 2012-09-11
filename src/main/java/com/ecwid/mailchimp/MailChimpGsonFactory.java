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

import com.ecwid.mailchimp.annotation.APIMethodParam;
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
 * Factory creating {@link Gson} obects to be used for API calls wrapping.
 * 
 * @author Vasily Karyaev <v.karyaev@gmail.com>
 */
public class MailChimpGsonFactory {
	
	private MailChimpGsonFactory() { }
	
	private static final ExclusionStrategy exclusionStrategy = new ExclusionStrategy() {
		@Override
		public boolean shouldSkipField(FieldAttributes fa) {
			return fa.getAnnotation(APIMethodParam.class) == null;
		}

		@Override
		public boolean shouldSkipClass(Class<?> type) {
			return false;
		}
	};
	
	private static FieldNamingStrategy fieldNamingStrategy = new FieldNamingStrategy() {
		@Override
		public String translateName(Field field) {
			String name = field.getAnnotation(APIMethodParam.class).name();
			return name.isEmpty()? field.getName() : name;
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
					return format.parse(je.getAsString());
				} catch(ParseException e) {
					throw new IllegalArgumentException("Cannot deserialize date: "+je);
				}
			}
		}
	}
	
	public static Gson createGson() {
		GsonBuilder builder = new GsonBuilder();
		builder.setExclusionStrategies(exclusionStrategy);
		builder.setFieldNamingStrategy(fieldNamingStrategy);
		builder.registerTypeAdapter(Date.class, new DateTypeAdapter());
		return builder.create();
	}
}
