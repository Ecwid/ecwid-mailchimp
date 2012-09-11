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
import java.util.Map;

/**
 * See http://apidocs.mailchimp.com/api/1.3/listsubscribe.func.php
 * 
 * @author Vasily Karyaev <v.karyaev@gmail.com>
 */
@APIMethod
public class ListSubscribe extends HasListIdMethod<Boolean> {

	@APIMethodParam
	public String email_address;
	
	@APIMethodParam
	public Map<String, Object> merge_vars;
	
	@APIMethodParam
	public EmailType email_type;
	
	@APIMethodParam
	public Boolean double_optin;
	
	@APIMethodParam
	public Boolean update_existing;
	
	@APIMethodParam
	public Boolean replace_interests;
	
	@APIMethodParam
	public Boolean send_welcome;
	
	@Override
	public Class<Boolean> getResultType() {
		return Boolean.class;
	}
}
