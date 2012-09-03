/*
 *  (c) 2008-2012 Creative Development LLC. All rights reserved.
 *                http://www.qualiteam.biz/
 */
package com.ecwid.mailchimp.method;

import com.ecwid.mailchimp.annotation.MailChimpField;
import java.util.List;

/**
 *
 * @author Vasily Karyaev <v.karyaev@gmail.com>
 */
public class ListBatchSubscribeResult extends MailChimpObject {
	
	@MailChimpField
	public Integer add_count;
	
	@MailChimpField
	public Integer update_count;
	
	@MailChimpField
	public Integer error_count;
	
	@MailChimpField
	public List<ListBatchError> errors;
}
