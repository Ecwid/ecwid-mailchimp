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

import com.ecwid.mailchimp.annotation.APIMethod;
import com.ecwid.mailchimp.annotation.APIMethodParam;
import java.lang.annotation.Annotation;

/**
 * Abstract class representing MailChimp API call.
 *
 * @param R type of the method call result
 * 
 * @author Vasily Karyaev <v.karyaev@gmail.com>
 */
public abstract class MailChimpMethod<R> extends MailChimpObject {

	/**
	 * API key to access MailChimp service.
	 */
	@APIMethodParam
	public String apikey;
	
	/**
	 * Get MailChimp API method name.
	 * See {@link APIMethod} for details.
	 *
	 * @throws IllegalArgumentException if neither this class nor any of its superclasses
	 * are annotated with {@link APIMethod}.
	 */
	public final String getMethodName() {
		for(Class<?> c=getClass(); c != null; c=c.getSuperclass()) {
			APIMethod a = c.getAnnotation(APIMethod.class);
			if(a != null) {
				String name = a.name();
				if(name.isEmpty()) {
					name = c.getSimpleName();
					String head = name.substring(0, 1).toLowerCase();
					String tail = name.substring(1);
					name = head + tail;
				}
				return name;
			}
		}

		throw new IllegalArgumentException("Neither "+getClass()+" nor its superclasses are annotated with "+APIMethod.class);
	}

	/**
	 * Get the class object representing method result type.
	 */
	public abstract Class<R> getResultType();
}
