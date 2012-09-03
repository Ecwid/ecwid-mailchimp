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
public class ListBatchError extends MailChimpObject {
	
	@MailChimpField
	public String email;
	
	@MailChimpField
	public Integer code;
	
	@MailChimpField
	public String message;
}
