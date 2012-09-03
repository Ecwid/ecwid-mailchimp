/*
 *  (c) 2008-2012 Creative Development LLC. All rights reserved.
 *                http://www.qualiteam.biz/
 */
package com.ecwid.mailchimp.method;

import com.ecwid.mailchimp.annotation.MailChimpField;

/**
 *
 * @author Vasily Karyaev <v.karyaev@gmail.com>
 */
public class MergeVars extends MailChimpObject {
	@MailChimpField
	public String EMAIL;
	
	@MailChimpField(name="NEW-EMAIL")
	public String NEW_EMAIL;
	
	@MailChimpField
	public EmailType EMAIL_TYPE;
}
