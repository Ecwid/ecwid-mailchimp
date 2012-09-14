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

import com.ecwid.mailchimp.annotation.MailChimpField;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author Vasily Karyaev <v.karyaev@gmail.com>
 */
public class MemberInfo extends MailChimpObject {
	@MailChimpField
	public String id;
	
	@MailChimpField
	public String email;
	
	@MailChimpField
	public EmailType email_type;
	
	@MailChimpField
	public Map<String, String> merges;
	
	@MailChimpField
	public MemberStatus status;
	
	@MailChimpField
	public Date timestamp;
	
	/**
	 * This field indicates email, associated with {@link #error}.
	 */
	@MailChimpField
	public String email_address;

	/**
	 * Error message.
	 */
	@MailChimpField
	public String error;
}
