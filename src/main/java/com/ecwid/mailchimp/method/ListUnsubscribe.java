/*
 *  (c) 2008-2012 Creative Development LLC. All rights reserved.
 *                http://www.qualiteam.biz/
 */
package com.ecwid.mailchimp.method;

import com.ecwid.mailchimp.annotation.MailChimpField;

/**
 * See http://apidocs.mailchimp.com/api/1.3/listunsubscribe.func.php
 * 
 * @author Vasily Karyaev <v.karyaev@gmail.com>
 */
public class ListUnsubscribe extends MailChimpMethod<Boolean> {

	@MailChimpField
	public String id;
	
	@MailChimpField
	public String email_address;
	
	@MailChimpField
	public Boolean delete_member;
	
	@MailChimpField
	public Boolean send_goodbye;
	
	@MailChimpField
	public Boolean send_notify;
	
	@Override
	public Class<Boolean> getResultType() {
		return Boolean.class;
	}
}
