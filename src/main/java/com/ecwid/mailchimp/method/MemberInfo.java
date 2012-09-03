/*
 *  (c) 2008-2012 Creative Development LLC. All rights reserved.
 *                http://www.qualiteam.biz/
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
