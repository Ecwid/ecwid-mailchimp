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
public class AccountRewardCounter extends MailChimpObject {
	@Field
	public Integer this_month;

	@Field
	public Integer total_earned;

	@Field
	public Integer remaining;
}
