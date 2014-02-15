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

import com.google.common.reflect.TypeToken;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;


/**
 * Abstract class representing MailChimp API calls.
 * <p>
 * Only a limited number of MailChimp API methods have wrappers in the current version of ecwid-mailchimp.
 * If you need to wrap a method which is not in the list,
 * you can easily create the wrapper by extending this class.
 *
 * @param R type of the method call result
 * 
 * @author Vasily Karyaev <v.karyaev@gmail.com>
 */
public abstract class MailChimpMethod<R> extends MailChimpObject {

	/**
	 * This annotation marks subclasses of {@link MailChimpMethod} to specify the corresponding API method meta-info.
	 */
	@Documented
	@Retention(value = RetentionPolicy.RUNTIME)
	@Target(value = ElementType.TYPE)
	public @interface Method {
		/**
		 * MailChimp API version to be used to execute the annotated method.
		 */
		public MailChimpAPIVersion version();
		
		/**
		 * The MailChimp API method name.
		 */
		public String name();
	}

	private final TypeToken<R> resultTypeToken = new TypeToken<R>(getClass()) { };

	/**
	 * API key to access MailChimp service.
	 */
	@Field
	public String apikey;
	
	/**
	 * Get the MailChimp API method meta-info.
	 *
	 * @throws IllegalArgumentException if neither this class nor any of its superclasses
	 * are annotated with {@link Method}.
	 */
	public final Method getMetaInfo() {
		for(Class<?> c=getClass(); c != null; c=c.getSuperclass()) {
			Method a = c.getAnnotation(Method.class);
			if(a != null) {
				return a;
			}
		}
		
		throw new IllegalArgumentException("Neither "+getClass()+" nor its superclasses are annotated with "+Method.class);
	}
	
	/**
	 * Get the method result type.
	 */
	public final Type getResultType() {
		Type type = resultTypeToken.getType();
		if (type instanceof Class || type instanceof ParameterizedType || type instanceof GenericArrayType) {
			return type;
		} else {
			throw new IllegalArgumentException("Cannot resolve result type: "+resultTypeToken);
		}
	}
}
