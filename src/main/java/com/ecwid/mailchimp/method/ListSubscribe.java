/*
 *  (c) 2008-2012 Creative Development LLC. All rights reserved.
 *                http://www.qualiteam.biz/
 */
package com.ecwid.mailchimp.method;

import com.ecwid.mailchimp.annotation.MailChimpField;

/**
 * See http://apidocs.mailchimp.com/api/1.3/listsubscribe.func.php
 * 
 * @author Vasily Karyaev <v.karyaev@gmail.com>
 */
public class ListSubscribe extends AbstractListMethod<Boolean> {

	@MailChimpField
	public String email_address;
	
	@MailChimpField
	public MergeVars merge_vars;
	
	@MailChimpField
	public EmailType email_type;
	
	@MailChimpField
	public Boolean double_optin;
	
	@MailChimpField
	public Boolean update_existing;
	
	@MailChimpField
	public Boolean replace_interests;
	
	@MailChimpField
	public Boolean send_welcome;
	
	@Override
	public Class<Boolean> getResultType() {
		return Boolean.class;
	}
}
