/*
 *  (c) 2008-2012 Creative Development LLC. All rights reserved.
 *                http://www.qualiteam.biz/
 */
package com.ecwid.mailchimp.method;

import com.ecwid.mailchimp.annotation.MailChimpField;

/**
 * See http://apidocs.mailchimp.com/api/1.3/listupdatemember.func.php
 * 
 * @author Vasily Karyaev <v.karyaev@gmail.com>
 */
public class ListUpdateMember extends MailChimpMethod<Boolean> {

	@MailChimpField
	public String id;
	
	@MailChimpField
	public String email_address;
	
	@MailChimpField
	public MergeVars merge_vars;
	
	@MailChimpField
	public EmailType email_type;
	
	@MailChimpField
	public Boolean replace_interests;
	
	@Override
	public Class<Boolean> getResultType() {
		return Boolean.class;
	}
}
