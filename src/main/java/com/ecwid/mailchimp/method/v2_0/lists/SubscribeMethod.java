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
package com.ecwid.mailchimp.method.v2_0.lists;

import com.ecwid.mailchimp.MailChimpAPIVersion;
import com.ecwid.mailchimp.MailChimpMethod;
import com.ecwid.mailchimp.MailChimpObject;

/**
 * @author Vasily Karyaev <v.karyaev@gmail.com>
 */
@MailChimpMethod.Method(name = "lists/subscribe", version = MailChimpAPIVersion.v2_0)
public class SubscribeMethod extends ListsRelatedMethod<Email> {

	@Field
	public Email email;
	
	@Field
	public MailChimpObject merge_vars;
	
	@Field
	public String email_type;
	
	@Field
	public Boolean double_optin;
	
	@Field
	public Boolean update_existing;
	
	@Field
	public Boolean replace_interests;
	
	@Field
	public Boolean send_welcome;
	
	@Override
	public Class<Email> getResultType() {
		return Email.class;
	}
}
