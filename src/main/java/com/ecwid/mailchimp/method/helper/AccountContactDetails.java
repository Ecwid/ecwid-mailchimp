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
package com.ecwid.mailchimp.method.helper;

import com.ecwid.mailchimp.MailChimpObject;

/**
 * See http://apidocs.mailchimp.com/api/1.3/getaccountdetails.func.php
 * 
 * @author Matt Farmer <matt@frmr.me>
 */
public class AccountContactDetails extends MailChimpObject {
	@Field
	public String fname;

	@Field
	public String lname;

	@Field
	public String email;

	@Field
	public String company;

	@Field
	public String address1;

	@Field
	public String address2;

	@Field
	public String city;

	@Field
	public String state;

	@Field
	public String zip;

	@Field
	public String country;

	@Field
	public String url;

	@Field
	public String phone;

	@Field
	public String fax;
}
