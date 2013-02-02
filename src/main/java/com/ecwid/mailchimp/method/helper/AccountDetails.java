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

import java.util.List;
import java.util.Date;
import com.ecwid.mailchimp.MailChimpObject;

/**
 * See http://apidocs.mailchimp.com/api/1.3/getaccountdetails.func.php
 * 
 * @author Matt Farmer <matt@frmr.me>
 */
public class AccountDetails extends MailChimpObject {
	@Field
	public String username;

	@Field
	public String user_id;

	@Field
	public Boolean is_trial;

	@Field
	public Boolean is_approved;

	@Field
	public Boolean has_activated;

	@Field
	public String timezone;

	@Field
	public String plan_type;

	@Field
	public Integer plan_low;

	@Field
	public Integer plan_high;

	@Field
	public Date plan_start_date;

	@Field
	public Integer emails_left;

	@Field
	public Boolean pending_monthly;

	@Field
	public Date first_payment;

	@Field
	public Date last_payment;

	@Field
	public Integer times_logged_in;

	@Field
	public Date last_login;

	@Field
	public String affiliate_link;

	@Field
	public AccountContactDetails contact;

	@Field
	public List<AccountModuleDetails> modules;

	@Field
	public List<AccountOrderDetails> orders;

	@Field
	public AccountRewardDetails rewards;
}
