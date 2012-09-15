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
	 * This annotation marks subclasses of {@link MailChimpMethod} to specify the corresponding API method names.
	 */
	@Documented
	@Retention(value = RetentionPolicy.RUNTIME)
	@Target(value = ElementType.TYPE)
	public @interface Name {
		/**
		 * The name of MailChimp API method.
		 */
		public String value();
	}

	/**
	 * API key to access MailChimp service.
	 */
	@Field
	public String apikey;
	
	/**
	 * Get MailChimp API method name.
	 * See {@link Name} for details.
	 *
	 * @throws IllegalArgumentException if neither this class nor any of its superclasses
	 * are annotated with {@link Name}.
	 */
	public final String getMethodName() {
		for(Class<?> c=getClass(); c != null; c=c.getSuperclass()) {
			Name a = c.getAnnotation(Name.class);
			if(a != null) {
				return a.value();
			}
		}

		throw new IllegalArgumentException("Neither "+getClass()+" nor its superclasses are annotated with "+Name.class);
	}

	/**
	 * Get the class object representing method result type.
	 */
	public abstract Class<R> getResultType();
}
